package com.xyz.authenticationserviceclient.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.userdetails.UserDetails;

import com.xyz.authenticationserviceclient.utilitybeans.CustomGrantedAuthority;
import com.xyz.authenticationserviceclient.validators.PrincipalValidator;

public class Principal implements UserDetails, Cloneable {
  private static final long serialVersionUID = 1L;
  @Size(min = 3, max = 128)
  @Pattern(regexp = PrincipalValidator.USERNAME)
  @NotNull
  private String username;
  @Size(min = 8, max = 64)
  @Pattern(regexp = PrincipalValidator.USERNAME)
  private String password;
  @Size(min = 2, max = 100)
  @Pattern(regexp = PrincipalValidator.NAME)
  @NotNull
  private String firstName;
  @Size(min = 2, max = 100)
  @Pattern(regexp = PrincipalValidator.NAME)
  @NotNull
  private String lastName;
  @Size(min = 5, max = 255)
  @Pattern(regexp = PrincipalValidator.NAME)
  @NotNull
  private String email;
  private String preferredTimeZoneId;
  private boolean passwordRequiresReset;
  private boolean disabled;
  private boolean locked;
  private List<String> roles;

  public Principal() {}

  public Principal(String username, String password, String firstName, String lastName) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
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

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPreferredTimeZoneId() {
    return preferredTimeZoneId;
  }

  public void setPreferredTimeZoneId(String preferredTimeZoneId) {
    this.preferredTimeZoneId = preferredTimeZoneId;
  }

  public boolean isPasswordRequiresReset() {
    return passwordRequiresReset;
  }

  public void setPasswordRequiresReset(boolean passwordRequiresReset) {
    this.passwordRequiresReset = passwordRequiresReset;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  public boolean isLocked() {
    return locked;
  }

  public void setLocked(boolean locked) {
    this.locked = locked;
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
    return !disabled;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !locked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return !disabled;
  }

  @Override
  public boolean isEnabled() {
    return !disabled;
  }
}
