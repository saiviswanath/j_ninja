package com.xyz.authenticationservice.util;

import java.util.Comparator;

import com.xyz.authenticationservice.dto.PrincipalDto;
import com.xyz.authenticationservice.enums.SortColumn;
import com.xyz.authenticationserviceclient.enums.SortDirection;

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

  private static Comparator<PrincipalDto> firstNameComparator = new Comparator<PrincipalDto>() {
    @Override
    public int compare(PrincipalDto o1, PrincipalDto o2) {
      if (o1.getFirstName() == null) {
        return -1;
      }
      if (o2.getFirstName() == null) {
        return 1;
      }
      return o1.getFirstName().compareTo(o2.getFirstName());
    }
  };

  private static Comparator<PrincipalDto> lastNameComparator = new Comparator<PrincipalDto>() {
    @Override
    public int compare(PrincipalDto o1, PrincipalDto o2) {
      if (o1.getLastName() == null) {
        return -1;
      }
      if (o2.getLastName() == null) {
        return 1;
      }
      return o1.getLastName().compareTo(o2.getLastName());
    }
  };

  private static Comparator<PrincipalDto> timeZoneComparator = new Comparator<PrincipalDto>() {
    @Override
    public int compare(PrincipalDto o1, PrincipalDto o2) {
      if (o1.getPreferredTimeZoneId() == null) {
        return -1;
      }
      if (o2.getPreferredTimeZoneId() == null) {
        return 1;
      }
      return o1.getPreferredTimeZoneId().compareTo(o2.getPreferredTimeZoneId());
    }
  };

  public static Comparator<PrincipalDto> getComparator(SortColumn sortColumn,
      SortDirection direction) {
    Comparator<PrincipalDto> comparator = null;
    switch (sortColumn) {
      case USERNAME:
        comparator = usernameComparator;
        break;
      case FIRSTNAME:
        comparator = firstNameComparator;
        break;
      case LASTNAME:
        comparator = lastNameComparator;
        break;
      case TIMEZONE:
        comparator = timeZoneComparator;
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
