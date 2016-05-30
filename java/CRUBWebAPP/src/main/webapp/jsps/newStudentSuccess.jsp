<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Index Page</title>
</head>
<body>
	<div id="header">
		<%@ include file="headerinclude.jsp"%>
	</div>
	Thank you, ${student.firstName} ${student.lastName} for registering to
	courses &nbsp;
	<c:forEach var="course" items="${student.courses}">
  		${course} &nbsp;
 	</c:forEach>
 		<div id="footer">
		<%@ include file="footerinclude.jsp"%>
	</div>
</body>
</html>