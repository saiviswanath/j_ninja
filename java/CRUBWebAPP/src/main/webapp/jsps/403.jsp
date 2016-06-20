<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Access Denied</title>
</head>
<body>
	<div id="header">
		<%@ include file="headerinclude.jsp"%>
	</div>
	<h4>User ${username} don't have sufficient permissions to access the page</h4>
		<div id="footer">
		<%@ include file="footerinclude.jsp"%>
	</div>
</body>
</html>