package com.xyz.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;

import com.xyz.dao.StudentDAO;
import com.xyz.dto.Student;
import com.xyz.util.DataConverter;

@Controller
@RequestMapping(value = "/rest")
public class RestController {
	@Autowired
	StudentDAO studentDao;
	@Autowired
	DataConverter dataConverter;

	@RequestMapping(value = "/allstudents", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Student> getAllStudents() {
		return studentDao.findAllStudents();
	}

	@RequestMapping(value = "/student/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Student> getStudentById(
			@PathVariable(value = "id") int studentId) {
		Student student = studentDao.findStudentById(studentId);
		if (student == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Student>(student, HttpStatus.OK);
	}

	@RequestMapping(value = "/student/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createStudent(
			@RequestBody com.xyz.form.beans.Student student,
			final HttpServletRequest req) throws URISyntaxException,
			UnsupportedEncodingException {
		Student studentDto = dataConverter
				.studentFormBeanToDtoConverter(student);
		studentDao.createStudent(studentDto);
		HttpHeaders headers = new HttpHeaders();
		String uri = UriUtils.encode(
				req.getRequestURL().append("/").append(student.getFirstName())
				.append("/").append(student.getLastName()).toString(),
				"UTF-8");
		headers.setLocation(new URI(uri));
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/student/{firstName}/{lastName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Student> getStudentByName(
			@PathVariable Map<String, String> map) {
		Student student = studentDao.findByName(map.get("firstName"),
				map.get("lastName"));
		if (student == null) {
			return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(student, HttpStatus.OK);
	}

	@RequestMapping(value = "/student/update/{firstName}/{lastName}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateStudent(
			@RequestBody com.xyz.form.beans.Student student,
			@PathVariable Map<String, String> map) {
		Student studentDto = dataConverter
				.studentFormBeanToDtoConverter(student);
		studentDao.updateStudent(studentDto);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/student/delete/{firstName}/{lastName}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteStudent(
			@PathVariable Map<String, String> map)
					throws UnsupportedEncodingException {
		String firstName = UriUtils.decode(map.get("firstName"), "UTF-8");
		String lastName = UriUtils.decode(map.get("lastName"), "UTF-8");
		studentDao.deteteStudentByName(firstName, lastName);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
