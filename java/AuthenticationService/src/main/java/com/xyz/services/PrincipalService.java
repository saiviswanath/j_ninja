package com.xyz.services;

import java.util.List;

import org.springframework.security.provisioning.UserDetailsManager;

import com.xyz.dto.PrincipalDto;
import com.xyz.util.PageAndSortData;

public interface PrincipalService extends UserDetailsManager {
  public PrincipalDto authenticatePrincipal(String userName, String password);
  public PrincipalDto getPricipal(String userName);
  public List<PrincipalDto> getPrincipals(PageAndSortData pandsdata);
}