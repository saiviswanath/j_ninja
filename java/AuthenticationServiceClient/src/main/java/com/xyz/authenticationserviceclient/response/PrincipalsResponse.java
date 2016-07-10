package com.xyz.authenticationserviceclient.response;

import com.xyz.authenticationserviceclient.beans.Principal;
import com.xyz.authenticationserviceclient.utilitybeans.PagedResult;

public class PrincipalsResponse extends RestFulResponse {

  private PagedResult<Principal> principals;

  public PrincipalsResponse() {}

  public PrincipalsResponse(PagedResult<Principal> principals) {
    this.principals = principals;
  }

  public PagedResult<Principal> getPrincipals() {
    return principals;
  }

  public void setPrincipals(PagedResult<Principal> principals) {
    this.principals = principals;
  }
}
