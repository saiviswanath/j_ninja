package com.xyz.crudserviceclient.exceptions;
import com.xyz.crudserviceclient.utilitybeans.Failure;


public interface ClientPublicException {
  public Failure getFailure();
}
