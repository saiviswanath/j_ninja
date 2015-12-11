<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="ExcelFunctions" prefix="excf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head />
<body>
	<c:choose>
		<c:when test="${param.name eq ''}">
			<c:set var="showAll" value="true" scope="request" />
		</c:when>
		<c:otherwise>
			<c:set var="showAll" value="false" scope="request" />
			<c:set var="escFirstName" value="${param.name}" scope="request" />
		</c:otherwise>
	</c:choose>

	${excf:excelExport(pageContext.response)}
	<c:import url="includes/employeeinclude.jsp" />
</body>
</html>