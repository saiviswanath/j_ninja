package com.xyz.crudservice.enums;

import java.util.HashMap;
import java.util.Map;

public enum SortColumn {
  FIRSTNAME("firstname"), LASTNAME("lastname"), GENDER("gender"), DOB("dob");

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
