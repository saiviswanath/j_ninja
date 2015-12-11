<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="EmployeeFunctions" prefix="empf"%>

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
			<c:when test="${requestScope.showAll eq true}">
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
					value="${empf:getEmployeeByFirstName(requestScope.escFirstName)}" />
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
