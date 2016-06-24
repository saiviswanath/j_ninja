package com.xyz.dao;

import java.util.List;

import com.xyz.dto.PrincipalDto;

public interface PrincipalDao {
  public List<String> findRolesByName(String userName);
  public PrincipalDto findPrincipal(String userName);
  public boolean usernameExists(String username);
  public List<PrincipalDto> findPrincipals();
}
