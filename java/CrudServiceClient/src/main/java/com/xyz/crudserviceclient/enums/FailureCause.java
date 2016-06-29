package com.xyz.crudserviceclient.enums;

public enum FailureCause {
  UNEXPECTED("unexpected");

  private final String message;

  private FailureCause(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
