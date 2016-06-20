package com.xyz.form.beans;

import javax.validation.constraints.Size;

public class PasswordResetBean {
  @Size(min = 8, max = 30)
  private String password;
  @Size(min = 8, max = 30)
  private String confirmPassword;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

}
