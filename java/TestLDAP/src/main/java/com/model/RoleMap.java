package com.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RoleMap implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private Map<String, Set<String>> roleMap;

  public RoleMap() {
    roleMap = new HashMap<String, Set<String>>();
  }

  public void put(String role) {
    roleMap.put(role, null);
  }

  public void put(String role, String link) {
    Set<String> linkList = roleMap.get(role);
    if (linkList == null) {
      linkList = new HashSet<String>();
      linkList.add(link);
      roleMap.put(role, linkList);
    } else {
      linkList.add(link);
    }
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return roleMap.toString();
  }

  public Set<String> getLinksForRole(String role) {
    return this.roleMap.get(role);
  }

  public Set<String> getRoles() {
    return roleMap.keySet();
  }
}
