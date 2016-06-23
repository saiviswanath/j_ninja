package com.xyz.response;

import com.xyz.exceptions.Failure;
import com.xyz.exceptions.RestFulResponseException;

public class RestFulResponse {
  private Failure failure;

  public RestFulResponse() {}

  public RestFulResponse(Failure failure) {
    this.failure = failure;
  }

  public Failure getFailure() {
    return failure;
  }

  public void setFailure(Failure failure) {
    this.failure = failure;
  }

  public void verify() {
    if (failure != null) {
      throw new RestFulResponseException(failure);
    }
  }

}
