package com.xyz.crudserviceclient.utilitybeans;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PagedResult<T> {
  private List<T> items;
  private Integer total;
  private Integer first;
  private Integer unfilteredItems;

  public Integer getUnfilteredItems() {
    return unfilteredItems;
  }

  public void setUnfilteredItems(Integer unfilteredItems) {
    this.unfilteredItems = unfilteredItems;
  }

  public PagedResult() {}

  public PagedResult(List<T> items, Integer total) {
    this.items = items;
    this.total = total;
  }

  public List<T> getItems() {
    return items;
  }

  public void setItems(List<T> items) {
    this.items = items;
  }

  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }

  public Integer getFirst() {
    return first;
  }

  public void setFirst(Integer first) {
    this.first = first;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  public static <J> PagedResult<J> emptyResult(J typeParam) {
    List<J> emptyList = Collections.emptyList();
    PagedResult<J> empty = new PagedResult<J>(emptyList, 0);
    empty.setFirst(0);
    return empty;
  }
}
