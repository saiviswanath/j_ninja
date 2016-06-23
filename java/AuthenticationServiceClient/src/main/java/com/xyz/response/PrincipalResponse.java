package com.xyz.response;

import com.xyz.beans.Principal;

public class PrincipalResponse extends RestFulResponse {
  private Principal principal;

  public PrincipalResponse() {}

  public PrincipalResponse(Principal principal) {
    this.principal = principal;
  }

  public Principal getPrincipal() {
    return principal;
  }

  public void setPrincipal(Principal principal) {
    this.principal = principal;
  }
}
