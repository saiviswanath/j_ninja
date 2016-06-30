package com.xyz.crudservice.dao;

import java.util.List;

import com.xyz.crudservice.dto.StudentDto;

public interface StudentDAO {
  public List<String> findCoursesByStudentId(int studentId);

  public List<StudentDto> findAllStudents();

  public List<String> findAllCourses();

  public List<Integer> findCourseIdsByCourseNames(List<String> courseNames);

  public void createStudent(StudentDto student);

  public StudentDto findByName(String firstName, String lastName);

  public void updateStudent(StudentDto student);

  public boolean deteteStudentByName(String firstName, String lastName);

  public int findStudentIdByName(String firstName, String lastName);

  public StudentDto findStudentById(int studentId);
  
  public int findStudentRowCount();
}
