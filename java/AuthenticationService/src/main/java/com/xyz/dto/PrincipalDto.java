package com.xyz.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class PrincipalDto implements UserDetails {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String username;
  private String password;
  private int enabled;
  private String email;
  private List<String> roles;

  private Set<GrantedAuthority> authorities;
  
  public void setUsername(String username) {
    this.username = username;
  }

  public int getEnabled() {
    return enabled;
  }

  public void setEnabled(int enabled) {
    this.enabled = enabled;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    authorities = new HashSet<GrantedAuthority>();
    for (String role : getRoles()) {
      authorities.add(new SimpleGrantedAuthority(role));
    }
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  /**
   * Yet to implement
   */
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  /**
   * Yet to implement
   */
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  /**
   * Yet to implement
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    if (getEnabled() == 1) {
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
