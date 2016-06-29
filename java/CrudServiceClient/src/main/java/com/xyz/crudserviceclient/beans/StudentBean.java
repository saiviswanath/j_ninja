package com.xyz.crudserviceclient.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class StudentBean implements Cloneable {
  private String firstName;
  private String lastName;
  private String gender;
  private Date DOB;
  private String email;
  private String mobileNumber;
  private String address;
  private List<String> courses;

  @Override
  public Object clone() throws CloneNotSupportedException {
    StudentBean copy = (StudentBean) super.clone();
    copy.courses = new ArrayList<>(courses);
    return copy;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Date getDOB() {
    return DOB;
  }

  public void setDOB(Date dOB) {
    DOB = dOB;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public List<String> getCourses() {
    return courses;
  }

  public void setCourses(List<String> courses) {
    this.courses = courses;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }


}
