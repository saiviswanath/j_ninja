package com.xyz.form.beans;

import javax.validation.constraints.Size;

public class UpdatePasswordBean {
  @Size(min = 8, max = 30)
  private String oldPassword;
  @Size(min = 8, max = 30)
  private String newPassword;
  @Size(min = 8, max = 30)
  private String cfNewPassword;

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getCfNewPassword() {
    return cfNewPassword;
  }

  public void setCfNewPassword(String cfNewPassword) {
    this.cfNewPassword = cfNewPassword;
  }
}
