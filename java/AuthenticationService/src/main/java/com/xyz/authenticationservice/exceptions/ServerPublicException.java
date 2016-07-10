package com.xyz.authenticationservice.exceptions;

import com.xyz.authenticationserviceclient.enums.ExceptionCause;

public interface ServerPublicException {
  public ExceptionCause getExceptionCause();

  public String getMessage();
}
