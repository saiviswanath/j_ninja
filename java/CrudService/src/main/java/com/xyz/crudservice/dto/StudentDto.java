package com.xyz.crudservice.dto;

import java.sql.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class StudentDto {
  private String firstName;
  private String lastName;
  private String gender;
  private Date DOB;
  private String email;
  private String mobileNumber;
  private String address;
  private List<String> courses;

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
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((DOB == null) ? 0 : DOB.hashCode());
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((courses == null) ? 0 : courses.hashCode());
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((gender == null) ? 0 : gender.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + ((mobileNumber == null) ? 0 : mobileNumber.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    StudentDto other = (StudentDto) obj;
    if (DOB == null) {
      if (other.DOB != null)
        return false;
    } else if (!DOB.equals(other.DOB))
      return false;
    if (address == null) {
      if (other.address != null)
        return false;
    } else if (!address.equals(other.address))
      return false;
    if (courses == null) {
      if (other.courses != null)
        return false;
    } else if (!courses.equals(other.courses))
      return false;
    if (email == null) {
      if (other.email != null)
        return false;
    } else if (!email.equals(other.email))
      return false;
    if (firstName == null) {
      if (other.firstName != null)
        return false;
    } else if (!firstName.equals(other.firstName))
      return false;
    if (gender == null) {
      if (other.gender != null)
        return false;
    } else if (!gender.equals(other.gender))
      return false;
    if (lastName == null) {
      if (other.lastName != null)
        return false;
    } else if (!lastName.equals(other.lastName))
      return false;
    if (mobileNumber == null) {
      if (other.mobileNumber != null)
        return false;
    } else if (!mobileNumber.equals(other.mobileNumber))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
