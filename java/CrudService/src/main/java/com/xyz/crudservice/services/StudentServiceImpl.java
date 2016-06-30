package com.xyz.crudservice.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.crudservice.dao.StudentDAO;
import com.xyz.crudservice.dto.StudentDto;
import com.xyz.crudservice.enums.SortColumn;
import com.xyz.crudservice.util.Comparators;
import com.xyz.crudservice.utilbeans.PageAndSortData;

@Service
public class StudentServiceImpl implements StudentService {
  @Autowired
  private StudentDAO studentDao;

  @Override
  public List<StudentDto> getAllStudents(PageAndSortData pageAndSortData) {
    List<StudentDto> students = studentDao.findAllStudents();

    int first = pageAndSortData.getFirst();
    int max = pageAndSortData.getMax();

    int studentSize = students.size();

    int toIndex = first + max;
    if (toIndex > studentSize) {
      toIndex = studentSize;
    }
    if (first >= studentSize || toIndex <= first) {
      students.clear();
    } else {
      SortColumn sortColumn = SortColumn.get(pageAndSortData.getSortBy());
      Collections.sort(students,
          Comparators.getComparator(sortColumn, pageAndSortData.getSortDirection()));
      students = students.subList(first, toIndex);
    }
    return students;
  }

  @Override
  public int getStudentCount() {
      return studentDao.findStudentRowCount();
  }

}
