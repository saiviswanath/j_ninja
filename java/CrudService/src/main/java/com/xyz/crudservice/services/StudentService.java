package com.xyz.crudservice.services;

import java.util.List;

import com.xyz.crudservice.dto.StudentDto;
import com.xyz.crudservice.utilbeans.PageAndSortData;


public interface StudentService {
  public List<StudentDto> getAllStudents(PageAndSortData pageAndSortData);
}
