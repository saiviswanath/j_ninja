package com.xyz.authenticationserviceclient.utilitybeans;

import com.xyz.authenticationserviceclient.enums.SortDirection;

public class SortablePagedCommand extends PagedCommand {
  private String sortBy;
  private SortDirection sortDirection;

  public String getSort() {
    return sortBy;
  }

  public void setSort(String sortBy) {
    this.sortBy = sortBy;
  }

  public SortDirection getSortDirection() {
    return sortDirection;
  }

  public void setSortDirection(SortDirection sortDirection) {
    this.sortDirection = sortDirection;
  }

  public void setSortDir(String sortDir) {
    if (sortDir.equals("asc")) {
      sortDirection = SortDirection.ASCENDING;
    } else if (sortDir.equals("desc")) {
      sortDirection = SortDirection.DESCENDING;
    }
  }

}
