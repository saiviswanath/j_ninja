package com.xyz.crudserviceclient.exceptions;

import com.xyz.crudserviceclient.utilitybeans.Failure;

@SuppressWarnings("serial")
public class RestFulResponseException extends RuntimeException implements ClientPublicException {
  private Failure failure;

  public RestFulResponseException(Failure failure) {
    super(failure.toString());
    this.failure = failure;
  }

  @Override
  public Failure getFailure() {
    return failure;
  }

}
