package com.xyz.exceptions;

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
