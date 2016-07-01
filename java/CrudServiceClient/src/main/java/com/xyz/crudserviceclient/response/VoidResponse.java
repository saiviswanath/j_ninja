package com.xyz.crudserviceclient.response;

public class VoidResponse extends RestFulResponse {
  private Void voidRes;

  public VoidResponse() {}

  public VoidResponse(Void voidRes) {
    this.voidRes = voidRes;
  }

  public Void getVoidRes() {
    return voidRes;
  }

  public void setVoidRes(Void voidRes) {
    this.voidRes = voidRes;
  }
}
