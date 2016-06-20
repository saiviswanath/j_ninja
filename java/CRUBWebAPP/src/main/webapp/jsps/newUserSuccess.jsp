<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Success Page</title>
</head>
<body>
	<div id="header">
		<%@ include file="headerinclude.jsp"%>
	</div>
	User ${user.userName} is added to roles &nbsp;
	<c:forEach var="role" items="${user.roles}">
  		${role} &nbsp;
 	</c:forEach>
	<div id="footer">
		<%@ include file="footerinclude.jsp"%>
	</div>
</body>
</html>