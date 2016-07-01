package com.xyz.crudserviceclient.enums;

public enum FailureCause {
  UNEXPECTED("unexpected"), BAD_URI_SYNTAX("Bad Uri Syntax"), UNSUPPORTED_ENCODING("Unsupported Encoding"), 
  MISSING_PARAM("Missing parameter");

  private final String message;

  private FailureCause(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
