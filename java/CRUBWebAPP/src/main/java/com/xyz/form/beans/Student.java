package com.xyz.form.beans;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.xyz.validators.DateNotEmpty;
import com.xyz.validators.ListNotEmpty;

public class Student {
	@Size(min = 2, max = 30)
	private String firstName;
	@Size(min = 2, max = 30)
	private String lastName;
	@NotEmpty
	private String gender;
	@DateNotEmpty
	@Past
	private Date DOB;
	private String email;
	private String mobileNumber;
	@ListNotEmpty
	private List<String> courses;

	@Override
	public String toString() {
		return "Student [firstName=" + firstName + ", lastName=" + lastName
				+ ", gender=" + gender + ", DOB=" + DOB + ", email=" + email
				+ ", mobileNumber=" + mobileNumber + ", courses=" + courses
				+ ", address=" + address + "]";
	}

	@Valid
	private Address address;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
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

	public List<String> getCourses() {
		return courses;
	}

	public void setCourses(List<String> courses) {
		this.courses = courses;
	}
}
