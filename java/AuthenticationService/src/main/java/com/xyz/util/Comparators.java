package com.xyz.util;

import java.util.Comparator;

import com.xyz.dto.PrincipalDto;

public class Comparators {

  private static Comparator<PrincipalDto> usernameComparator = new Comparator<PrincipalDto>() {
    @Override
    public int compare(PrincipalDto o1, PrincipalDto o2) {
      if (o1.getUsername() == null) {
        return -1;
      }
      if (o2.getUsername() == null) {
        return 1;
      }
      return o1.getUsername().compareTo(o2.getUsername());
    }
  };

  private static Comparator<PrincipalDto> emailComparator = new Comparator<PrincipalDto>() {
    @Override
    public int compare(PrincipalDto o1, PrincipalDto o2) {
      if (o1.getEmail() == null) {
        return -1;
      }
      if (o2.getEmail() == null) {
        return 1;
      }
      return o1.getEmail().compareTo(o2.getEmail());
    }
  };

  private static Comparator<PrincipalDto> enabledComparator = new Comparator<PrincipalDto>() {
    @Override
    public int compare(PrincipalDto o1, PrincipalDto o2) {
      long comp = o1.getEnabled() - o2.getEnabled();
      if (comp < 0) {
        return -1;
      }
      if (comp > 0) {
        return 1;
      }
      return 0;
    }
  };

  public static Comparator<PrincipalDto> getComparator(SortColumn sortColumn,
      SortDirection direction) {
    Comparator<PrincipalDto> comparator = null;
    switch (sortColumn) {
      case USERNAME:
        comparator = usernameComparator;
        break;
      case EMAIL:
        comparator = emailComparator;
        break;
      case ENABLED:
        comparator = enabledComparator;
        break;
      default:
        comparator = usernameComparator;
    }
    if (direction == SortDirection.DESCENDING) {
      final Comparator<PrincipalDto> comparatorFinal = comparator;
      return new Comparator<PrincipalDto>() {
        @Override
        public int compare(PrincipalDto o1, PrincipalDto o2) {
          return comparatorFinal.compare(o2, o1);
        }
      };
    }
    return comparator;
  }
}
