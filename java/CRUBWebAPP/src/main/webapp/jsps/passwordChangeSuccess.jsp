<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Password Success Page</title>
</head>
<body>
	<div id="header">
		<%@ include file="headerinclude.jsp"%>
	</div>
	<sec:authorize access="authenticated">
    User <sec:authentication property="name" /> password changed successfully.
    </sec:authorize>
	<div id="footer">
		<%@ include file="footerinclude.jsp"%>
	</div>
</body>
</html>