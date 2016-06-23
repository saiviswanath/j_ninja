package com.xyz.exceptions;

public interface ServerPublicException {
  public ExceptionCause getExceptionCause();

  public String getMessage();
}
