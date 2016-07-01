package com.xyz.crudserviceclient.response;

public class BooleanResponse extends RestFulResponse {
  private boolean response;

  public BooleanResponse() {}

  public BooleanResponse(boolean response) {
    this.response = response;
  }

  public boolean isResponse() {
    return response;
  }

  public void setResponse(boolean response) {
    this.response = response;
  }


}
