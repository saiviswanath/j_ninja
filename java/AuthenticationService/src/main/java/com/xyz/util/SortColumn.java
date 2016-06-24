package com.xyz.util;

import java.util.HashMap;
import java.util.Map;

public enum SortColumn {
  USERNAME("username"), EMAIL("email"), ENABLED("enabled");

  private final String colName;
  private static final Map<String, SortColumn> lookup = new HashMap<>();
  static {
    for (SortColumn c : SortColumn.values()) {
      lookup.put(c.getColName(), c);
    }
  }

  private SortColumn(String colName) {
    this.colName = colName;
  }

  public String getColName() {
    return colName;
  }

  public static SortColumn get(String colName) {
    return lookup.get(colName);
  }
}
