package com.xyz.util;

import java.sql.Date;

import org.springframework.stereotype.Component;

import com.xyz.crudserviceclient.beans.StudentBean;
import com.xyz.dto.Student;
import com.xyz.dto.User;
import com.xyz.form.beans.Address;

@Component
public class DataConverter {
  public StudentBean studentFormBeanToBeanConveter(com.xyz.form.beans.Student studentFormBean) {
    StudentBean studentBean = new StudentBean();
    studentBean.setFirstName(studentFormBean.getFirstName().trim());
    studentBean.setLastName(studentFormBean.getLastName().trim());
    studentBean.setGender(studentFormBean.getGender());
    studentBean.setDOB(new Date(studentFormBean.getDOB().getTime()));
    studentBean.setEmail(studentFormBean.getEmail().trim());
    studentBean.setMobileNumber(studentFormBean.getMobileNumber().trim());
    StringBuilder sb = new StringBuilder();
    sb.append(studentFormBean.getAddress().getHouseNo().trim().replace(",", ";")).append(", ");
    sb.append(studentFormBean.getAddress().getStreet().trim().replace(",", ";")).append(", ");
    sb.append(studentFormBean.getAddress().getArea().trim().replace(",", ";")).append(", ");
    sb.append(studentFormBean.getAddress().getCity().trim().replace(",", ";")).append("-");
    sb.append(studentFormBean.getAddress().getPin());
    studentBean.setAddress(sb.toString());
    studentBean.setCourses(studentFormBean.getCourses());
    return studentBean;
  }

  public com.xyz.form.beans.Student studentBeanToStudentFormBeanConverter(StudentBean studentBean) {
    com.xyz.form.beans.Student student = new com.xyz.form.beans.Student();
    student.setFirstName(studentBean.getFirstName());
    student.setLastName(studentBean.getLastName());
    student.setGender(studentBean.getGender());
    student.setDOB(new java.util.Date(studentBean.getDOB().getTime()));
    student.setEmail(studentBean.getEmail());
    student.setMobileNumber(studentBean.getMobileNumber());
    student.setCourses(studentBean.getCourses());
    String addressString = studentBean.getAddress();
    String[] addressDetails = addressString.split(",");
    Address address = new Address();
    address.setHouseNo(addressDetails[0].trim());
    address.setStreet(addressDetails[1].trim());
    address.setArea(addressDetails[2].trim());
    String[] citypinSplit = addressDetails[3].split("-");
    address.setCity(citypinSplit[0].trim());
    address.setPin(Long.parseLong(citypinSplit[1].trim()));
    student.setAddress(address);
    return student;
  }

  @Deprecated
  public Student studentFormBeanToDtoConverter(com.xyz.form.beans.Student studentFormBean) {
    Student studentDto = new Student();
    studentDto.setFirstName(studentFormBean.getFirstName().trim());
    studentDto.setLastName(studentFormBean.getLastName().trim());
    studentDto.setGender(studentFormBean.getGender());
    studentDto.setDOB(new Date(studentFormBean.getDOB().getTime()));
    studentDto.setEmail(studentFormBean.getEmail().trim());
    studentDto.setMobileNumber(studentFormBean.getMobileNumber().trim());
    StringBuilder sb = new StringBuilder();
    sb.append(studentFormBean.getAddress().getHouseNo().trim().replace(",", ";")).append(", ");
    sb.append(studentFormBean.getAddress().getStreet().trim().replace(",", ";")).append(", ");
    sb.append(studentFormBean.getAddress().getArea().trim().replace(",", ";")).append(", ");
    sb.append(studentFormBean.getAddress().getCity().trim().replace(",", ";")).append(", ");
    sb.append(studentFormBean.getAddress().getPin());
    studentDto.setAddress(sb.toString());
    studentDto.setCourses(studentFormBean.getCourses());
    return studentDto;
  }

  @Deprecated
  public com.xyz.form.beans.Student studentDtoToFormBeanConverter(Student studentDto) {
    com.xyz.form.beans.Student student = new com.xyz.form.beans.Student();
    student.setFirstName(studentDto.getFirstName());
    student.setLastName(studentDto.getLastName());
    student.setGender(studentDto.getGender());
    student.setDOB(new java.util.Date(studentDto.getDOB().getTime()));
    student.setEmail(studentDto.getEmail());
    student.setMobileNumber(studentDto.getMobileNumber());
    student.setCourses(studentDto.getCourses());
    String addressString = studentDto.getAddress();
    String[] addressDetails = addressString.split(",");
    Address address = new Address();
    address.setHouseNo(addressDetails[0].trim());
    address.setStreet(addressDetails[1].trim());
    address.setArea(addressDetails[2].trim());
    String[] citypinSplit = addressDetails[3].split("-");
    address.setCity(citypinSplit[0].trim());
    address.setPin(Long.parseLong(citypinSplit[1].trim()));
    student.setAddress(address);
    return student;
  }

  public User userFormBeantoDtoConverter(com.xyz.form.beans.User user) {
    User userDto = new User();
    userDto.setUserName(user.getUserName());
    userDto.setPassword(user.getRetypedPassword());
    userDto.setEmail(user.getEmail());
    boolean userEnabled = user.isEnabled();
    if (userEnabled) {
      userDto.setEnabled(1);
    } else {
      userDto.setEnabled(0);
    }
    userDto.setRoles(user.getRoles());
    return userDto;
  }

  public com.xyz.form.beans.User userDtoToFormBeanConverter(User userDto) {
    com.xyz.form.beans.User user = new com.xyz.form.beans.User();
    user.setUserName(userDto.getUserName());
    user.setPassword(userDto.getPassword());
    user.setRetypedPassword(userDto.getPassword());
    user.setEmail(userDto.getEmail());
    int userEnabled = userDto.getEnabled();
    if (userEnabled == 1) {
      user.setEnabled(true);
    } else {
      user.setEnabled(false);
    }
    user.setRoles(userDto.getRoles());
    return user;
  }
}
