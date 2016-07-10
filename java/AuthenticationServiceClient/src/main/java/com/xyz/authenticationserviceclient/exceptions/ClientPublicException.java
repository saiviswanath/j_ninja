package com.xyz.authenticationserviceclient.exceptions;

import com.xyz.authenticationserviceclient.utilitybeans.Failure;

public interface ClientPublicException {
  public Failure getFailure();
}
