package com.xyz.form.beans;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class ForGotPasswordBean {
  @Size(min = 2, max = 30)
  private String userName;
  @Email
  private String email;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
