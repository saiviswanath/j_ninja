package com.xyz.dao;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.xyz.db.DBConnector_MYSQL_Test;
import com.xyz.dto.Student;

public class StudentDAO_Test {
	// TODO: Use Mockito to mimic Container to DI StudentDAOImpl
	private StudentDAO studentDao = new StudentDAOImpl();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DBConnector_MYSQL_Test.setUpBeforeClass();
	}

	@Test
	public void testFindCourseIdsByCourseNames() {
		List<String> courseNames = Arrays.asList("Computers", "Mathematics");
		List<Integer> expectedCourseIds = Arrays.asList(1, 2);

		List<Integer> courseIdList = studentDao
				.findCourseIdsByCourseNames(courseNames);
		assertNotNull(courseIdList);
		assertTrue(courseIdList.containsAll(expectedCourseIds));
	}
	
	public void testCreateStudent() throws Exception {
		Student student = new Student();
		student.setFirstName("Shanti");
		student.setLastName("Palaparthi");
		student.setGender("Female");
		String dateString = "1987-03-24";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		student.setDOB(new Date(sdf.parse(dateString).getTime()));
		student.setEmail("");
		student.setMobileNumber("9949975040");
		student.setAddress("Huda");
		student.setCourses(Arrays.asList("Computers", "Chemistry"));
		
		studentDao.createStudent(student);
	}
}
