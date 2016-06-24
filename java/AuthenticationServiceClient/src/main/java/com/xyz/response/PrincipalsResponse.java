package com.xyz.response;

import com.xyz.beans.PagedResult;
import com.xyz.beans.Principal;

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
