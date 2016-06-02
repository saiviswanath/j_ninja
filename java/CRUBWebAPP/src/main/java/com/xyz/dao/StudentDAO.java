package com.xyz.dao;

import java.util.List;

import com.xyz.dto.Student;

public interface StudentDAO {
	public List<String> findCoursesByStudentId(int studentId);
	public List<Student> findAllStudents();
	public List<String> findAllCourses();
	public List<Integer> findCourseIdsByCourseNames(List<String> courseNames); 
	public void createStudent(Student student);
	public Student findByName(String firstName, String lastName);
	public void updateStudent(Student student);
	public boolean deteteStudentByName(String firstName, String lastName);
	public int findStudentIdByName(String firstName, String lastName);
	public Student findStudentById(int studentId);
}
