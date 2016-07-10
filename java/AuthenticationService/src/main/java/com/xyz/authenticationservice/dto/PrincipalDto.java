package com.xyz.authenticationservice.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class PrincipalDto implements UserDetails {
  private static final long serialVersionUID = 1L;

  private String username;
  private String passwordHash;
  private String firstName;
  private String lastName;
  private String emailAddress;
  private String preferredTimeZoneId;
  private boolean passwordRequiresReset;
  private int version;
  private boolean archived;
  private List<String> roles;

  private boolean locked = false;
  private Set<GrantedAuthority> authorities;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
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

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
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

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public boolean isArchived() {
    return archived;
  }

  public void setArchived(boolean archived) {
    this.archived = archived;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public boolean isLocked() {
    return locked;
  }

  public void setLocked(boolean locked) {
    this.locked = locked;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
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
    return passwordHash;
  }

  @Override
  public boolean isAccountNonExpired() {
    return !isArchived();
  }

  @Override
  public boolean isAccountNonLocked() {
    return !locked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return !isArchived();
  }

  @Override
  public boolean isEnabled() {
    return !isArchived();
  }
}
