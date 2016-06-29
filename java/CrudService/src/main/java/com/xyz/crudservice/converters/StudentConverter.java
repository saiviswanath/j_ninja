package com.xyz.crudservice.converters;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.xyz.crudservice.dto.StudentDto;
import com.xyz.crudserviceclient.beans.StudentBean;

@Component
public class StudentConverter {
  public StudentBean studentDtoToStudentBeanConverter(StudentDto studentDto) {
    StudentBean studentBean = new StudentBean();
    studentBean.setFirstName(studentDto.getFirstName());
    studentBean.setLastName(studentDto.getLastName());
    studentBean.setGender(studentDto.getGender());
    studentBean.setDOB(new Date(studentDto.getDOB().getTime()));
    studentBean.setEmail(studentDto.getEmail());
    studentBean.setMobileNumber(studentDto.getMobileNumber());
    studentBean.setAddress(studentDto.getAddress());
    studentBean.setCourses(studentDto.getCourses());
    return studentBean;
  }
}
