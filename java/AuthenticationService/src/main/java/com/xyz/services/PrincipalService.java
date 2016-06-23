package com.xyz.services;

import org.springframework.security.provisioning.UserDetailsManager;

import com.xyz.dto.PrincipalDto;

public interface PrincipalService extends UserDetailsManager {
  public PrincipalDto authenticatePrincipal(String userName, String password);
}
