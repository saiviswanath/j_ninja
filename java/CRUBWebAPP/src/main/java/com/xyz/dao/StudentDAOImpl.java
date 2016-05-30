package com.xyz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.xyz.db.DBConnector;
import com.xyz.dto.Student;

@Repository
public class StudentDAOImpl implements StudentDAO {
	private static final Logger logger = Logger.getLogger(StudentDAOImpl.class);

	@Override
	public List<String> findCoursesByStudentId(int studentId) {
		List<String> courseList = new ArrayList<>();
		String sql = "select c.coursename from Student s inner join "
				+ "student_course_mapping scm on s.studentid=scm.studentid "
				+ "inner join course c on scm.courseid=c.courseid where s.studentid=?";
		try (Connection connection = DBConnector.getDBConnection()) {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, studentId);
			ResultSet rs = stmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					courseList.add(rs.getString(1));
				}
			}
		} catch (SQLException e) {
			logger.error("Error executing sql " + sql, e);
		}
		return courseList;
	}

	@Override
	public List<Student> findAllStudents() {
		List<Student> studentList = new ArrayList<>();
		String sql = "select studentId, firstName, lastName, gender, DOB, email, "
				+ "mobileNumber, address from Student";
		try (Connection connection = DBConnector.getDBConnection()) {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					Student student = new Student();
					student.setFirstName(rs.getString(2));
					student.setLastName(rs.getString(3));
					student.setGender(rs.getString(4));
					student.setDOB(rs.getDate(5));
					student.setEmail(rs.getString(6));
					student.setMobileNumber(rs.getString(7));
					student.setAddress(rs.getString(8));
					student.setCourses(findCoursesByStudentId(rs.getInt(1)));
					studentList.add(student);
				}
			}
		} catch (SQLException e) {
			logger.error("Error executing sql " + sql, e);
		}

		return studentList;
	}

	@Override
	public List<String> findAllCourses() {
		List<String> courseList = new ArrayList<>();
		String sql = "select CourseName from Course";
		try (Connection connection = DBConnector.getDBConnection()) {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					courseList.add(rs.getString(1));
				}
			}
		} catch (SQLException e) {
			logger.error("Error executng sql " + sql, e);
		}
		return courseList;
	}

	@Override
	public List<Integer> findCourseIdsByCourseNames(List<String> courseNames) {
		List<Integer> courseIdList = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < courseNames.size(); i++) {
			if (sb.length() == 0) {
				sb.append("?");
			} else {
				sb.append(", ?");
			}
		}
		String sql = "select CourseId from Course where CourseName in ("
				+ sb.toString() + ")";
		try (Connection connection = DBConnector.getDBConnection()) {
			PreparedStatement stmt = connection.prepareStatement(sql);
			int i = 0;
			for (String course : courseNames) {
				stmt.setString(++i, course);
			}

			ResultSet rs = stmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					courseIdList.add(rs.getInt(1));
				}
			}
		} catch (SQLException e) {
			logger.error("Error executing sql " + sql, e);
		}
		return courseIdList;
	}

	@Override
	public void createStudent(Student student) {
		Connection connection = DBConnector.getDBConnection();
		try {
			connection.setAutoCommit(false);
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@"+ student.getDOB() +"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			String studentInsertSql = "insert into Student(FirstName, LastName, Gender, DOB, Email, "
					+ "mobilenumber, Address) values(?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement studentInsertStmt = connection
					.prepareStatement(studentInsertSql);
			studentInsertStmt.setString(1, student.getFirstName());
			studentInsertStmt.setString(2, student.getLastName());
			studentInsertStmt.setString(3, student.getGender());
			studentInsertStmt.setDate(4, student.getDOB());
			studentInsertStmt.setString(5, student.getEmail());
			studentInsertStmt.setString(6, student.getMobileNumber());
			studentInsertStmt.setString(7, student.getAddress());
			studentInsertStmt.executeUpdate();

			String retrieveStudentIdSql = "select max(studentId) from Student";
			PreparedStatement retrieveStudentIdStmt = connection
					.prepareStatement(retrieveStudentIdSql);
			ResultSet rs = retrieveStudentIdStmt.executeQuery();
			int studentId = 0;
			if (rs != null) {
				while (rs.next()) {
					studentId = rs.getInt(1);
				}
			}
		
			List<Integer> courseIds = findCourseIdsByCourseNames(student
					.getCourses());
			String insertStuCourseMappingSql = "insert into student_course_mapping(StudentId, "
					+ "CourseId) values(?, ?)";
			PreparedStatement insertStuCourseMappingStmt = connection
					.prepareStatement(insertStuCourseMappingSql);
			for (Integer courseId : courseIds) {
				insertStuCourseMappingStmt.setInt(1, studentId);
				insertStuCourseMappingStmt.setInt(2, courseId);
				insertStuCourseMappingStmt.executeUpdate();
			}

			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				logger.error("Error rolling back transaction", e);
			}
			logger.error("Error inserting student record", e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("Error closing DB connection", e);
			}
		}

	}

}
