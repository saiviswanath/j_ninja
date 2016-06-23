package com.xyz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.xyz.dao.PrincipalDao;
import com.xyz.dto.PrincipalDto;
import com.xyz.exceptions.AutheticationServiceException;
import com.xyz.exceptions.ExceptionCause;

public class PrincipalServiceImpl implements PrincipalService {

  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private PrincipalDao principalDao;

  @Override
  public void changePassword(String s, String s1) {
    throw new UnsupportedOperationException("Yet to be implemented");
  }

  @Override
  public void createUser(UserDetails userdetails) {
    throw new UnsupportedOperationException("Yet to be implemented");
  }

  @Override
  public void deleteUser(String s) {
    throw new UnsupportedOperationException("Yet to be implemented");
  }

  @Override
  public void updateUser(UserDetails userdetails) {
    throw new UnsupportedOperationException("Yet to be implemented");
  }

  @Override
  public boolean userExists(String userName) {
    if (userName == null) {
      userName = "";
    }
    String lowerCaseUserName = userName.toLowerCase();
    return principalDao.usernameExists(lowerCaseUserName);
  }
  
  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    if (userName == null) {
      userName = "";
    }
    String lowerCaseUserName = userName.toLowerCase();
    PrincipalDto pDto = principalDao.findPrincipal(lowerCaseUserName);
    if (pDto == null) {
      throw new UsernameNotFoundException("No user found matching " + userName);
    }
    return pDto;
  }

  @Override
  public PrincipalDto authenticatePrincipal(String userName, String password) {
    if (userName == null) {
      userName = "";
    }
    String lowerCaseUserName = userName.toLowerCase();
    Authentication authentication =
        new UsernamePasswordAuthenticationToken(lowerCaseUserName, password);
    Authentication authResult = null;
    try {
      authResult = authenticationManager.authenticate(authentication);
    } catch (DisabledException e) {
      throw new AutheticationServiceException(ExceptionCause.USER_DISABLED,
          "User Account disabled for: " + lowerCaseUserName);
    } catch (LockedException e) {
      throw new AutheticationServiceException(ExceptionCause.USER_LOCKED,
          "User account locked for: " + lowerCaseUserName);
    } catch (BadCredentialsException e) {
      throw new AutheticationServiceException(ExceptionCause.AUTHENTICATION,
          "Authetication failed for: " + lowerCaseUserName);
    } catch (Exception e) {
      throw new AutheticationServiceException(ExceptionCause.UNEXPECTED, "Unexcepted Exception");
    }
    return (PrincipalDto) authResult.getPrincipal();
  }

  @Override
  public PrincipalDto getPricipal(String userName) throws RuntimeException {
    if (userName == null) {
      userName = "";
    }
    String lowerCaseUserName = userName.toLowerCase();
    PrincipalDto pDto = principalDao.findPrincipal(lowerCaseUserName);
    if (pDto == null) {
      throw new RuntimeException("No user found for username " + userName);
    }
    return pDto;
  }

}
