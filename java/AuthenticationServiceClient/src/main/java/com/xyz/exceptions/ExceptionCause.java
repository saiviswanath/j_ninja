package com.xyz.exceptions;

public enum ExceptionCause {
  ALREADY_EXISTS("alreadyExists"), MISSING("missing"), OUT_OF_DATE("outOfDate"), UNAUTHORIZED(
      "unauthorized"), UNEXPECTED("unexpected"), VALIDATION("validation"), AUTHENTICATION(
      "authentication"), USER_DISABLED("userDisabled"), USER_LOCKED("userLocked");

  private final String message;

  private ExceptionCause(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
