<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="pragma" content="no-cache" />
<title>Home</title>
</head>
<body>
	<div id="header">
		<%@ include file="headerinclude.jsp"%>
		<table>
			<tr>
				<td><a href="<c:url value="./newStudentPage.do"/>">New
						Student</a></td>
				<td><a href="<c:url value="./updateInputForm.do"/>">Update
						Student</a></td>
				<td><a href="<c:url value="./deleteInputForm.do"/>">Delete
						Student</a></td>
			</tr>
		</table>
	</div>
	<h3>
		<c:out value="List of active Students" />
	</h3>

	<c:set var="sortByField" value="${sortByField}" />
	<c:set var="sortDirField" value="${sortDirField}" />

	<div id="body">
		<!-- SortBy Form -->
		<form id="sortBySelectForm" name="sortBySelectForm"
			action="./getHomePage.do">
			<input type="hidden" name="first" value="0" /> <input type="hidden"
				name="max" value="${maxPageRecords}" /> <input type="hidden"
				name="sortDirection" value="${sortDirField}" />

			<table>
				<tr>
					<td><label for="sortBy">SortBy</label></td>
					<td><select id="sortBy" name="sortBy"
						onchange="this.form.submit()">
							<option value="select" selected>--Select--</option>
							<option value="firstname">FirstName</option>
							<option value="lastname">LastName</option>
							<option value="gender">Gender</option>
							<option value="dob">DOB</option>
					</select></td>
					<td><c:out value="${sortByField}" /></td>
				</tr>
			</table>
		</form>

		<!-- SortDir Form -->
		<form id="sortDirSelectForm" name="sortDirSelectForm"
			action="./getHomePage.do">
			<input type="hidden" name="first" value="0" /> <input type="hidden"
				name="max" value="${maxPageRecords}" /> <input type="hidden"
				name="sortBy" value="${sortByField}" />
			<table>
				<tr>
					<td><label for="sortDirection">Sort Direction</label></td>
					<td><select id="sortDirection" name="sortDirection"
						onchange="this.form.submit()">
							<option value="select" selected>--Select--</option>
							<option value="asc">Ascending</option>
							<option value="desc">Descending</option>
					</select></td>
					<td><c:out value="${sortDirField}" /></td>
				</tr>
			</table>
		</form>



		<table id="student-table" border="1" cellpadding="1" cellspacing="1">
			<th>FirstName</th>
			<th>LastName</th>
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

		<%--For displaying Previous link except for the 1st page --%>
		<c:if test="${currentPage != 0}">
			<td><a id="prevLink"
				href="./getHomePage.do?first=${currentPage - maxPageRecords}&max=${maxPageRecords}&sortBy=${sortByField}&sortDirection=${sortDirField}">Previous</a></td>
		</c:if>

		<%--For displaying Page numbers. 
	The when condition does not display a link for the current page--%>
		<table id="page-table" border="1" cellpadding="5" cellspacing="5">
			<tr>
				<c:forEach begin="1" end="${noOfPages}" var="i">
					<c:choose>
						<c:when test="${currentPage eq ((i-1) * maxPageRecords)}">
							<td>${i}</td>
						</c:when>
						<c:otherwise>
							<td><a id="numLink"
								href="./getHomePage.do?first=${(i-1) * maxPageRecords}&max=${maxPageRecords}&sortBy=${sortByField}&sortDirection=${sortDirField}">${i}</a></td>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</tr>
		</table>

		<%--For displaying Next link --%>
		<c:if test="${currentPage lt ((noOfPages - 1) * maxPageRecords)}">
			<td><a id="nextLink"
				href="./getHomePage.do?first=${currentPage + maxPageRecords}&max=${maxPageRecords}&sortBy=${sortByField}&sortDirection=${sortDirField}">Next</a></td>
		</c:if>

	</div>
	<div id="footer">
		<%@ include file="footerinclude.jsp"%>
	</div>
</body>
</html>