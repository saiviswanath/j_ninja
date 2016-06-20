package com.xyz.form.beans;

import javax.validation.constraints.Size;

public class UserUpdateInputBean {
  @Size(min = 8, max = 30)
  private String userName;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

}
