package com.xyz.crudservice.exceptions;

import com.xyz.crudserviceclient.enums.FailureCause;

public interface ServerPublicException {
  public FailureCause getFailureCause();

  public String getMessage();
}
