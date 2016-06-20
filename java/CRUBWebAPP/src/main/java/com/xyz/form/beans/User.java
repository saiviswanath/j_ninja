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
  private boolean enabled;
  public String getEmail() {
    return email;
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

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }
}
