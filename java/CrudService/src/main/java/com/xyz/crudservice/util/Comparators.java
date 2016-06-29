package com.xyz.crudservice.util;

import java.util.Comparator;

import com.xyz.crudservice.dto.StudentDto;
import com.xyz.crudservice.enums.SortColumn;
import com.xyz.crudserviceclient.enums.SortDirection;


public class Comparators {
  private static Comparator<StudentDto> firstNameComparator = new Comparator<StudentDto>() {
    @Override
    public int compare(StudentDto o1, StudentDto o2) {
      if (o1.getFirstName() == null) {
        return -1;
      }
      if (o2.getFirstName() == null) {
        return 1;
      }
      return o1.getFirstName().compareTo(o2.getFirstName());
    }
  };

  private static Comparator<StudentDto> lastNameComparator = new Comparator<StudentDto>() {
    @Override
    public int compare(StudentDto o1, StudentDto o2) {
      if (o1.getLastName() == null) {
        return -1;
      }
      if (o2.getLastName() == null) {
        return 1;
      }
      return o1.getLastName().compareTo(o2.getLastName());
    }
  };

  private static Comparator<StudentDto> genderComparator = new Comparator<StudentDto>() {
    @Override
    public int compare(StudentDto o1, StudentDto o2) {
      if (o1.getGender() == null) {
        return -1;
      }
      if (o2.getGender() == null) {
        return 1;
      }
      return o1.getGender().compareTo(o2.getGender());
    }
  };

  private static Comparator<StudentDto> dobComparator = new Comparator<StudentDto>() {
    @Override
    public int compare(StudentDto o1, StudentDto o2) {
      if (o1.getDOB().before(o2.getDOB())) {
        return -1;
      } else if (o1.getDOB().after(o2.getDOB())) {
        return 1;
      } else {
        return 0;
      }
    }
  };

  public static Comparator<StudentDto> getComparator(SortColumn sortColumn, SortDirection direction) {
    Comparator<StudentDto> comparator = null;
    switch (sortColumn) {
      case FIRSTNAME:
        comparator = firstNameComparator;
        break;
      case LASTNAME:
        comparator = lastNameComparator;
        break;
      case GENDER:
        comparator = genderComparator;
        break;
      case DOB:
        comparator = dobComparator;
        break;
      default:
        comparator = firstNameComparator;
    }
    if (direction == SortDirection.DESCENDING) {
      final Comparator<StudentDto> comparatorFinal = comparator;
      return new Comparator<StudentDto>() {
        @Override
        public int compare(StudentDto o1, StudentDto o2) {
          return comparatorFinal.compare(o2, o1);
        }
      };
    }
    return comparator;
  }
}
