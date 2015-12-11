<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee Details</title>
<script type="text/javascript" src="/HR_APP/js/employeeReq.js"></script>
<link rel="icon" href="/HR_APP/favicon.ico" type="image/x-icon" />
</head>
<body>
	<div id="helloUser" align="right">
		<h4>
			<c:out value="Hello, ${sessionScope.user}" />
		</h4>
	</div>
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
		<c:set var="escFirstName" value="${fn:escapeXml(param.firstName)}"
			scope="request" />
		<c:choose>
			<c:when test="${requestScope.escFirstName eq ''}">
				<c:set var="showAll" value="true" scope="request" />
			</c:when>
			<c:otherwise>
				<c:set var="showAll" value="false" scope="request" />
			</c:otherwise>
		</c:choose>

		<c:import url="includes/employeeinclude.jsp" />

		<div id="export">
			<form id="empex" name="empex" action="EmployeeExport.jsp"
				method="post">
				<input name="name" type="hidden"
					value="${requestScope.escFirstName}" /> <input type="submit"
					value="export" />
			</form>
		</div>
	</c:if>
</body>
</html>