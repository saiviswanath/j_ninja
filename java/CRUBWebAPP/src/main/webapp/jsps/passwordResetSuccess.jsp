<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Password Reset Success Page</title>
</head>
<body>
	<div id="body">
		<c:url value="/login" var="login" />
		Hi ${sessionScope.sessionuser},<br />
		<br /> Your password has been reset. Please login at <a
			href="${login}">Login Page</a><br />
		<br /> Thank You.
	</div>
	<div id="footer">
		<%@ include file="footerinclude.jsp"%>
	</div>
</body>
</html>