<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
</head>
<body>
	<div id="header">
		<%@ include file="headerinclude.jsp"%>
		<table>
			<tr>
				<td><a href="<c:url value="./newStudentPage.do"/>">New
						Student</a></td>
				<td><a href="<c:url value="./updateStudentPage.do"/>">Update
						Student</a></td>
				<td><a href="<c:url value="./deletStudentPage.do"/>">Delete
						Student</a></td>
			</tr>
		</table>
	</div>
	<h3>
		<c:out value="List of active Students" />
	</h3>
	<div id="body">
		<table id="student-table" border="1" cellpadding="1" cellspacing="1">
			<th>FirstName</th>
			<th>LAstName</th>
			<th>Gender</th>
			<th>DOB</th>
			<th>Email</th>
			<th>MobilNumber</th>
			<th>Address</th>
			<th>Courses</th>
			<c:forEach var="student" items="${studentList}">
				<tr>
					<td>${student.firstName}</td>
					<td>${student.lastName}</td>
					<td>${student.gender}</td>
					<td>${student.DOB}</td>
					<td>${student.email}</td>
					<td>${student.mobileNumber}</td>
					<td>${student.address}</td>
					<td><c:forEach var="course" items="${student.courses}">
					${course}&nbsp;
				</c:forEach></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div id="footer">
		<%@ include file="footerinclude.jsp"%>
	</div>
</body>
</html>