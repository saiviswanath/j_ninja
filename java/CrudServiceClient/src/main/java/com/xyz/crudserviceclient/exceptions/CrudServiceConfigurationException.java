package com.xyz.crudserviceclient.exceptions;

@SuppressWarnings("serial")
public class CrudServiceConfigurationException extends RuntimeException {
  public CrudServiceConfigurationException() {
    this(null, null);
  }

  public CrudServiceConfigurationException(String message) {
    this(message, null);
  }

  public CrudServiceConfigurationException(Throwable causedBy) {
    this(null, causedBy);
  }

  public CrudServiceConfigurationException(String message, Throwable causedBy) {
    super(message, causedBy);
  }
}
