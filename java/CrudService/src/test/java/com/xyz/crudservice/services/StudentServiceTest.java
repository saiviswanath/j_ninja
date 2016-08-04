package com.xyz.crudservice.services;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.xyz.crudservice.dao.StudentDAO;
import com.xyz.crudservice.dto.StudentDto;
import com.xyz.crudservice.exceptions.CrudServiceException;
import com.xyz.crudservice.utilbeans.PageAndSortData;
import com.xyz.crudserviceclient.enums.SortDirection;

public class StudentServiceTest {
  @InjectMocks
  StudentService stuService;
  @Mock
  private StudentDAO studentDao;

  @Before
  public void setUp() {
    stuService = new StudentServiceImpl();
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetAllStudents() {
    StudentDto sDto = new StudentDto();
    sDto.setFirstName("Sai");
    sDto.setLastName("Palaparthi");
    sDto.setGender("Male");
    sDto.setDOB(new Date(System.currentTimeMillis()));
    sDto.setEmail("v.p@gmail.com");
    sDto.setMobileNumber("8121157247");
    sDto.setAddress("Huda");
    sDto.setCourses(Arrays.asList("Math", "Chem"));
    List<StudentDto> sList = Arrays.asList(sDto);
    Mockito.when(studentDao.findAllStudents()).thenReturn(sList);

    PageAndSortData pageAndSortData = new PageAndSortData();
    pageAndSortData.setFirst(0);
    pageAndSortData.setMax(5);
    pageAndSortData.setSortBy("firstname");
    pageAndSortData.setSortDirection(SortDirection.ASCENDING);
    List<StudentDto> stuList = stuService.getAllStudents(pageAndSortData);
    Mockito.verify(studentDao).findAllStudents();
    assertNotNull(stuList);
    assertThat(stuList, hasItems(sDto));
  }

  @Test
  public void testGetStudentCount() {
    Mockito.when(studentDao.findStudentRowCount()).thenReturn(10);
    int count = stuService.getStudentCount();
    assertThat(count, is(10));
    Mockito.verify(studentDao).findStudentRowCount();
  }

  @Test
  public void testGetAllCourses() {
    Mockito.when(studentDao.findAllCourses()).thenReturn(Arrays.asList("Math", "Chem"));
    List<String> courses = stuService.getAllCourses();
    assertNotNull(courses);
    assertThat(courses, hasItems("Math", "Chem"));
    Mockito.verify(studentDao).findAllCourses();
  }

  @SuppressWarnings("unchecked")
  @Test(expected = CrudServiceException.class)
  @Ignore
  public void testGetAllCoursesForException() {
    Mockito.when(studentDao.findAllCourses()).thenThrow(RuntimeException.class);
    stuService.getAllCourses();
    Mockito.verify(studentDao).findAllCourses();
  }

  @Test
  public void testCreateStudent() {
    StudentDto sDto = new StudentDto();
    sDto.setFirstName("Sai");
    sDto.setLastName("Palaparthi");
    sDto.setGender("Male");
    sDto.setDOB(new Date(System.currentTimeMillis()));
    sDto.setEmail("v.p@gmail.com");
    sDto.setMobileNumber("8121157247");
    sDto.setAddress("Huda");
    sDto.setCourses(Arrays.asList("Math", "Chem"));
    Mockito.doNothing().when(studentDao).createStudent(Mockito.any(StudentDto.class));
    stuService.createStudent(Mockito.any(StudentDto.class));
    Mockito.verify(studentDao).createStudent(Mockito.any(StudentDto.class));
  }

  @Test
  public void testFindByName() {
    StudentDto sDto = new StudentDto();
    sDto.setFirstName("Sai");
    sDto.setLastName("Palaparthi");
    sDto.setGender("Male");
    sDto.setDOB(new Date(System.currentTimeMillis()));
    sDto.setEmail("v.p@gmail.com");
    sDto.setMobileNumber("8121157247");
    sDto.setAddress("Huda");
    sDto.setCourses(Arrays.asList("Math", "Chem"));
    Mockito.when(studentDao.findByName(Mockito.anyString(), Mockito.anyString())).thenReturn(sDto);
    StudentDto stuDto = stuService.findByName(Mockito.anyString(), Mockito.anyString());
    assertNotNull(stuDto);
    assertEquals(sDto, stuDto);
    Mockito.verify(studentDao).findByName(Mockito.anyString(), Mockito.anyString());
  }

  @Test
  public void testGetStudentIdByName() {
    Mockito.when(studentDao.findStudentIdByName(Mockito.anyString(), Mockito.anyString()))
    .thenReturn(101);
    int stuId = stuService.getStudentIdByName(Mockito.anyString(), Mockito.anyString());
    assertThat(stuId, is(101));
    Mockito.verify(studentDao).findStudentIdByName(Mockito.anyString(), Mockito.anyString());
  }

  @Test
  public void testUpdateStudent() {
    StudentDto sDto = new StudentDto();
    sDto.setFirstName("Sai");
    sDto.setLastName("Palaparthi");
    sDto.setGender("Male");
    sDto.setDOB(new Date(System.currentTimeMillis()));
    sDto.setEmail("v.p@gmail.com");
    sDto.setMobileNumber("8121157247");
    sDto.setAddress("Huda");
    sDto.setCourses(Arrays.asList("Math", "Chem"));
    Mockito.doNothing().when(studentDao).updateStudent(Mockito.any(StudentDto.class));
    stuService.updateStudent(Mockito.any(StudentDto.class));
    Mockito.verify(studentDao).updateStudent(Mockito.any(StudentDto.class));
  }

  @Test
  public void testDeleteStudentByName() {
    Mockito.when(studentDao.deteteStudentByName(Mockito.anyString(), Mockito.anyString()))
    .thenReturn(true);
    boolean check = stuService.deleteStudentByName(Mockito.anyString(), Mockito.anyString());
    assertThat(check, is(true));
    Mockito.verify(studentDao).deteteStudentByName(Mockito.anyString(), Mockito.anyString());
  }
}
