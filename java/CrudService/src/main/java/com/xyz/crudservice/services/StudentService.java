package com.xyz.crudservice.services;

import java.util.List;

import com.xyz.crudservice.dto.StudentDto;
import com.xyz.crudservice.utilbeans.PageAndSortData;


public interface StudentService {
  public List<StudentDto> getAllStudents(PageAndSortData pageAndSortData);
  public int getStudentCount();
  public List<String> getAllCourses();
  public void createStudent(StudentDto studentDto);
  public StudentDto findByName(String firstName, String lastName);
  public int getStudentIdByName(String firstName, String lastName);
  public void updateStudent(StudentDto studentDto);
  public boolean deleteStudentByName(String firstName, String lastName);
}
