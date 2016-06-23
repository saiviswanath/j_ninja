package com.xyz.exceptions;

public class ConfigurationException extends RuntimeException {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public ConfigurationException() {
    this(null, null);
  }

  public ConfigurationException(String message) {
    this(message, null);
  }

  public ConfigurationException(Throwable causedBy) {
    this(null, causedBy);
  }

  public ConfigurationException(String message, Throwable causedBy) {
    super(message, causedBy);
  }
}
