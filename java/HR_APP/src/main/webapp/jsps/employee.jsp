<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="com.xyz.hrapp.dao.HRAppDaoImpl"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="EmployeeFunctions" prefix="empf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee Details</title>
<script type="text/javascript" src="/HR_APP/js/employeeReq.js"></script>
<link rel="icon" href="/HR_APP/favicon.ico" type="image/x-icon" />
</head>
<body>
	<div id="empFilterDiv">
		<h3>
			<c:out value="Search by Employee First Name:" />
		</h3>
		<form id="filter" name="filter" method="post">
			<input type="hidden" id="flip" name="flip" value="true" />
			<table id="searchTable">
				<tr>
					<td><label for="firstName">First Name</label> <input
						id="firstName" name="firstName" type="text" /></td>
					<td><input type="submit" value="submit"
						onclick="employeeJSPReq()" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="image"></div>

	<c:if test="${param.flip eq true}">
		<!-- Preferred is commns-lang3 escapeHtml to mitigate XSS -->
		<c:set var="escFirstName" value="${fn:escapeXml(param.firstName)}" />
		<c:choose>
			<c:when test="${pageScope.escFirstName eq ''}">
				<c:set var="showAll" value="true" />
			</c:when>
			<c:otherwise>
				<c:set var="showAll" value="false" />
			</c:otherwise>
		</c:choose>


		<div id="empDetails">
			<h3>
				<c:out value="Employee Details: " />
			</h3>
			<table id="emptable" border="1" cellpadding="1" cellspacing="1">
				<th>Id</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
				<th>Phone Number</th>
				<th>Hire Date</th>
				<c:choose>
					<c:when test="${pageScope.showAll eq true}">
						<c:forEach var="employee" items="${empf:getAllEmployees()}">
							<tr>
								<td>${employee.id}</td>
								<td>${employee.firstName}</td>
								<td>${employee.lastName}</td>
								<td>${employee.email}</td>
								<td>${employee.phoneNumber}</td>
								<td>${employee.hireDate}</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:set var="employee"
							value="${empf:getEmployeeByFirstName(pageScope.escFirstName)}" />
						<tr>
							<td>${employee.id}</td>
							<td>${employee.firstName}</td>
							<td>${employee.lastName}</td>
							<td>${employee.email}</td>
							<td>${employee.phoneNumber}</td>
							<td>${employee.hireDate}</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
	</c:if>
</body>
</html>