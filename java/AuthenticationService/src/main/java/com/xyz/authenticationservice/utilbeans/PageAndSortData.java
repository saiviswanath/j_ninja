package com.xyz.authenticationservice.utilbeans;

import com.xyz.authenticationserviceclient.enums.SortDirection;

public class PageAndSortData {
  private int first;
  private int max;
  private String sortBy;
  private SortDirection sortDirection;

  public int getFirst() {
    return first;
  }

  public void setFirst(int first) {
    this.first = first;
  }

  public int getMax() {
    return max;
  }

  public void setMax(int max) {
    this.max = max;
  }

  public String getSortBy() {
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
}
