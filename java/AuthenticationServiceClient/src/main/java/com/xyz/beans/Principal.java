package com.xyz.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.userdetails.UserDetails;

import com.xyz.validators.PrincipalValidator;

public class Principal implements UserDetails, Cloneable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  @Size(min = 3, max = 128)
  @Pattern(regexp = PrincipalValidator.USERNAME)
  @NotNull
  private String username;
  @Size(min = 8, max = 64)
  @Pattern(regexp = PrincipalValidator.USERNAME)
  private String password;
  private boolean enabled;
  @Size(min = 5, max = 255)
  @Pattern(regexp = PrincipalValidator.NAME)
  @NotNull
  private String email;
  private List<String> roles;

  public Principal() {}

  public Principal(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    Principal copy = (Principal) super.clone();
    copy.roles = new ArrayList<>(roles);
    return copy;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<String> getRoles() {
    if (roles == null) {
      roles = new ArrayList<>();
    }
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override
  public Collection<CustomGrantedAuthority> getAuthorities() {
    List<CustomGrantedAuthority> authorities = new ArrayList<>();
    for (String role : getRoles()) {
      authorities.add(new CustomGrantedAuthority(role));
    }
    return authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

}
