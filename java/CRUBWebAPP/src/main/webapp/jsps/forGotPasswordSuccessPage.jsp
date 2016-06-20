<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forgot Password Success Page</title>
</head>
<body>
	<div id="body">
		<table>
			<tr>
				<td><c:url value="/login" var="login" /> <a href="${login}">Login
						Page</a></td>
			</tr>
		</table>
		Hi ${forGotPasswordBean.userName},<br/><br/> Your password reset link has
		been sent to email ${forGotPasswordBean.email}. <br/><br/> Thank You.
	</div>
	<div id="footer">
		<%@ include file="footerinclude.jsp"%>
	</div>
</body>
</html>