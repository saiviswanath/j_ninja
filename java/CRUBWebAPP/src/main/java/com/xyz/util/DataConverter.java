package com.xyz.util;

import java.sql.Date;

import org.springframework.stereotype.Component;

import com.xyz.dto.Student;
import com.xyz.form.beans.Address;

@Component
public class DataConverter {
	public Student studentFormBeanToDtoConverter(
			com.xyz.form.beans.Student studentFormBean) {
		Student studentDto = new Student();
		studentDto.setFirstName(studentFormBean.getFirstName().trim());
		studentDto.setLastName(studentFormBean.getLastName().trim());
		studentDto.setGender(studentFormBean.getGender());
		studentDto.setDOB(new Date(studentFormBean.getDOB().getTime()));
		studentDto.setEmail(studentFormBean.getEmail().trim());
		studentDto.setMobileNumber(studentFormBean.getMobileNumber().trim());
		StringBuilder sb = new StringBuilder();
		sb.append(
				studentFormBean.getAddress().getHouseNo().trim()
				.replace(",", ";")).append(", ");
		sb.append(
				studentFormBean.getAddress().getStreet().trim()
				.replace(",", ";")).append(", ");
		sb.append(
				studentFormBean.getAddress().getArea().trim().replace(",", ";"))
				.append(", ");
		sb.append(
				studentFormBean.getAddress().getCity().trim().replace(",", ";"))
				.append(", ");
		sb.append(studentFormBean.getAddress().getPin());
		studentDto.setAddress(sb.toString());
		studentDto.setCourses(studentFormBean.getCourses());
		return studentDto;
	}

	public com.xyz.form.beans.Student studentDtoToFormBeanConverter(
			Student studentDto) {
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
}
