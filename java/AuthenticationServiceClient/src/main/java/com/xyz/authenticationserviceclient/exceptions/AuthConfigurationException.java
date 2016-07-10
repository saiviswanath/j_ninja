package com.xyz.authenticationserviceclient.exceptions;

public class AuthConfigurationException extends RuntimeException {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public AuthConfigurationException() {
    this(null, null);
  }

  public AuthConfigurationException(String message) {
    this(message, null);
  }

  public AuthConfigurationException(Throwable causedBy) {
    this(null, causedBy);
  }

  public AuthConfigurationException(String message, Throwable causedBy) {
    super(message, causedBy);
  }
}
