package com.xyz.crudservice.exceptions;

import com.xyz.crudserviceclient.enums.FailureCause;

@SuppressWarnings("serial")
public class CrudServiceException extends RuntimeException implements ServerPublicException {

  private FailureCause failurecause;
  private String message;

  public CrudServiceException(FailureCause failurecause) {
    this(failurecause, null, null);
  }

  public CrudServiceException(FailureCause failurecause, String message) {
    this(failurecause, message, null);
  }

  public CrudServiceException(FailureCause failurecause, Throwable causedBy) {
    this(failurecause, null, causedBy);
  }

  public CrudServiceException(FailureCause failurecause, String message, Throwable causedBy) {
    super(causedBy);
    this.failurecause = failurecause;
    this.message = message;
  }

  @Override
  public FailureCause getFailureCause() {
    return failurecause;
  }

  @Override
  public String getMessage() {
    return message;
  }

}
