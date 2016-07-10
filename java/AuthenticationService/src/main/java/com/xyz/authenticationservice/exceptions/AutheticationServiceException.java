package com.xyz.authenticationservice.exceptions;

import com.xyz.authenticationserviceclient.enums.ExceptionCause;

public class AutheticationServiceException extends RuntimeException implements ServerPublicException {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private ExceptionCause exceptionCause;
  private String message;

  public AutheticationServiceException(ExceptionCause exceptionCause) {
    this(exceptionCause, null, null);
  }

  public AutheticationServiceException(ExceptionCause exceptionCause, String message) {
    this(exceptionCause, message, null);
  }

  public AutheticationServiceException(ExceptionCause exceptionCause, Throwable causedBy) {
    this(exceptionCause, null, causedBy);
  }

  public AutheticationServiceException(ExceptionCause exceptionCause, String message,
      Throwable causedBy) {
    super(causedBy);
    this.exceptionCause = exceptionCause;
    this.message = message;
  }

  @Override
  public ExceptionCause getExceptionCause() {
    return exceptionCause;
  }

  @Override
  public String getMessage() {
    return message;
  }

}
