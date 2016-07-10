package com.xyz.form.beans;

import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.xyz.validators.ListNotEmpty;

public class User {
  @Size(min = 2, max = 30)
  private String userName;
  @Size(min = 8, max = 30)
  private String password;
  @Size(min = 8, max = 30)
  private String retypedPassword;
  @Size(min = 2, max = 30)
  private String firstName;
  @Size(min = 2, max = 30)
  private String lastName;
  private String preferredTimeZoneId;
  private boolean disabled;

  public String getEmail() {
    return email;
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

  public String getPreferredTimeZoneId() {
    return preferredTimeZoneId;
  }

  public void setPreferredTimeZoneId(String preferredTimeZoneId) {
    this.preferredTimeZoneId = preferredTimeZoneId;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Email
  private String email;
  @ListNotEmpty
  private List<String> roles;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRetypedPassword() {
    return retypedPassword;
  }

  public void setRetypedPassword(String retypedPassword) {
    this.retypedPassword = retypedPassword;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }
}
