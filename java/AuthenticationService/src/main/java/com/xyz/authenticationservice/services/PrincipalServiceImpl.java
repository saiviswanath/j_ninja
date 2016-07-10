package com.xyz.authenticationservice.services;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.xyz.authenticationservice.dto.PrincipalDto;
import com.xyz.authenticationservice.enums.SortColumn;
import com.xyz.authenticationservice.exceptions.AutheticationServiceException;
import com.xyz.authenticationservice.util.Comparators;
import com.xyz.authenticationservice.utilbeans.PageAndSortData;
import com.xyz.authenticationserviceclient.beans.Principal;
import com.xyz.authenticationserviceclient.enums.ExceptionCause;
import com.xyz.autheticationservice.dao.PrincipalDao;

public class PrincipalServiceImpl implements PrincipalService {

  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private PrincipalDao principalDao;
  @Autowired
  private UserLockingService userLockingService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void changePassword(String s, String s1) {
    throw new UnsupportedOperationException("Yet to be implemented");
  }

  @Override
  public void createUser(UserDetails userdetails) {
    createPrincipal((Principal) userdetails);
    userLockingService.registerLoginSuccess(userdetails.getUsername());
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
    pDto.setLocked(isLocked(lowerCaseUserName));
    return pDto;
  }

  private boolean isLocked(String username) {
    if (username == null) {
      username = "";
    }
    String lowerCaseUserName = username.toLowerCase();
    return userLockingService.isUserLocked(lowerCaseUserName);
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

  @Override
  public List<PrincipalDto> getPrincipals(PageAndSortData pandsdata) {
    List<PrincipalDto> pDtoList = principalDao.findPrincipals();

    int toIndex = pandsdata.getFirst() + pandsdata.getMax();
    if (toIndex > pDtoList.size()) {
      toIndex = pDtoList.size();
    }
    if (pandsdata.getFirst() >= pDtoList.size() || toIndex <= pandsdata.getFirst()) {
      pDtoList.clear();
    } else {
      SortColumn sortColumn = SortColumn.get(pandsdata.getSortBy());
      Collections.sort(pDtoList,
          Comparators.getComparator(sortColumn, pandsdata.getSortDirection()));
      pDtoList = pDtoList.subList(pandsdata.getFirst(), toIndex);
    }

    return pDtoList;
  }

  @Override
  public PrincipalDto createPrincipal(Principal principal) {
    if (StringUtils.isEmpty(principal.getUsername())) {
      throw new AutheticationServiceException(ExceptionCause.MISSING, "User name required");
    } else {
      principal.setUsername(principal.getUsername().toLowerCase());
    }

    if (StringUtils.isEmpty(principal.getPassword())) {
      throw new AutheticationServiceException(ExceptionCause.MISSING, "Password required");
    }

    if (principalDao.usernameExists(principal.getUsername())) {
      throw new AutheticationServiceException(ExceptionCause.ALREADY_EXISTS, "Username "
          + principal.getUsername() + " already in use.");
    }

    PrincipalDto principalDto = new PrincipalDto();
    populatePersistedPrincipal(principalDto, principal);

    principalDto.setUsername(principal.getUsername());
    principalDto.setPasswordRequiresReset(true);

    hashPassword(principalDto, principal.getPassword());
    
    principalDto.setRoles(principal.getRoles());

    principalDao.createPrincipal(principalDto);

    userLockingService.registerLoginSuccess(principalDto.getUsername());
    return principalDto;
  }

  private void populatePersistedPrincipal(PrincipalDto principalDto, Principal principal) {
    principalDto.setFirstName(principal.getFirstName());
    principalDto.setLastName(principal.getLastName());
    principalDto.setEmailAddress(principal.getEmail());
    principalDto.setPreferredTimeZoneId(principal.getPreferredTimeZoneId());
    principalDto.setArchived(principal.isDisabled());
  }

  private void hashPassword(PrincipalDto principalDto, String cleartextPassword) {
    String encryptedPassword = passwordEncoder.encode(cleartextPassword);
    principalDto.setPasswordHash(encryptedPassword);
  }
}
