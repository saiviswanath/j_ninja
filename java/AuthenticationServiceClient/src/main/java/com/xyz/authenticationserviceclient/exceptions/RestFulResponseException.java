package com.xyz.authenticationserviceclient.exceptions;

import com.xyz.authenticationserviceclient.utilitybeans.Failure;

public class RestFulResponseException extends RuntimeException implements ClientPublicException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private Failure failure;

  public RestFulResponseException(Failure failure) {
    super(failure.toString());
    this.failure = failure;
  }

  public Failure getFailure() {
    return failure;
  }

}
