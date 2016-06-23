package com.xyz.providers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;

import com.xyz.beans.Principal;
import com.xyz.client.PrincipalClient;
import com.xyz.exceptions.ExceptionCause;
import com.xyz.exceptions.RestFulResponseException;

public class CustomAuthProvider implements AuthenticationProvider {
  private static final Logger logger = Logger.getLogger(CustomAuthProvider.class);
  @Autowired
  private PrincipalClient principalClient;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    try {
      if (!StringUtils.hasText((String) authentication.getPrincipal())
          || !StringUtils.hasText((String) authentication.getCredentials())) {
        throw new BadCredentialsException("Bad credentials");
      }

      UsernamePasswordAuthenticationToken authRequest =
          (UsernamePasswordAuthenticationToken) authentication;
      String username = authRequest.getPrincipal().toString();
      if (username == null) {
        username = "";
      }
      username = username.toLowerCase();
      String password = authRequest.getCredentials().toString();
      try {
        Principal principal = principalClient.authenticate(username, password);
        if (!username.equalsIgnoreCase(principal.getUsername())) {
          throw new BadCredentialsException("Username not matching request.");
        }
        return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
      } catch (RestFulResponseException exception) {
        if (exception.getFailure().getCause() == ExceptionCause.USER_LOCKED) {
          throw new LockedException("User locked: " + username);
        }
        if (exception.getFailure().getCause() == ExceptionCause.USER_DISABLED) {
          throw new DisabledException("User account disabled: " + username);
        }
        throw new BadCredentialsException("Bad credentials");
      }
    } catch (AuthenticationException ae) {
      throw ae;
    } catch (ResourceAccessException rae) {
      String message =
          "Communication issue reaching auth service to authenticate: " + rae.getMessage();
      logger.warn(message, rae);
      throw new AuthenticationServiceException(message, rae);
    } catch (Throwable throwable) {
      String message = "Unknown system error in authenticate: " + throwable.getMessage();
      logger.warn(message, throwable);
      throw new AuthenticationServiceException(message, throwable);
    }
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(clazz);
  }
}
