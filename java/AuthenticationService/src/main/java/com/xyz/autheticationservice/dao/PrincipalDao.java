package com.xyz.autheticationservice.dao;

import java.util.List;

import com.xyz.authenticationservice.dto.PrincipalDto;

public interface PrincipalDao {
  public List<String> findRolesByName(String userName);
  public PrincipalDto findPrincipal(String userName);
  public boolean usernameExists(String username);
  public List<PrincipalDto> findPrincipals();
  public void createPrincipal(PrincipalDto principalDto);
}
