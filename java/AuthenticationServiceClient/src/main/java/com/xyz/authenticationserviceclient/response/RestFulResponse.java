package com.xyz.authenticationserviceclient.response;

import com.xyz.authenticationserviceclient.exceptions.RestFulResponseException;
import com.xyz.authenticationserviceclient.utilitybeans.Failure;

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
