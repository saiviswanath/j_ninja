package com.xyz.beans;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

/**
 * Non-final implementation of {@link org.springframework.security.core.authority.SimpleGrantedAuthority}.
 * To be used when {@link org.springframework.security.core.GrantedAuthority} implementation is involved
 * in json ser/deser.
 * @author viswa
 *
 */
public class CustomGrantedAuthority implements GrantedAuthority {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String role;
  
  public CustomGrantedAuthority() {}

  public CustomGrantedAuthority(String role) {
    Assert.hasText(role, "A granted authority textual representation is required");
    this.role = role;
  }

  public String getAuthority() {
    return role;
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj instanceof CustomGrantedAuthority)
      return role.equals(((CustomGrantedAuthority) obj).role);
    else
      return false;
  }

  public int hashCode() {
    return role.hashCode();
  }

  public String toString() {
    return role;
  }

}
