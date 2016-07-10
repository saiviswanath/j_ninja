package com.xyz.authenticationservice.services;

import java.util.List;

import org.springframework.security.provisioning.UserDetailsManager;

import com.xyz.authenticationservice.dto.PrincipalDto;
import com.xyz.authenticationservice.utilbeans.PageAndSortData;
import com.xyz.authenticationserviceclient.beans.Principal;

public interface PrincipalService extends UserDetailsManager {
  public PrincipalDto authenticatePrincipal(String userName, String password);
  public PrincipalDto getPricipal(String userName);
  public List<PrincipalDto> getPrincipals(PageAndSortData pandsdata);
  public PrincipalDto createPrincipal(Principal principal);
}