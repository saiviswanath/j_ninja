<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
</head>
<body>
	<c:url value="/j_spring_security_check" var="loginUrl" />
	<form method="post" action="${loginUrl}">
		<table>
			<tr>
				<td><label name="username">User Name</label></td>
				<td><input type="text" name="username" /></td>
			</tr>
			<tr>
				<td><label name="password">Password</label></td>
				<td><input type="password" name="password" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="login" /></td>
			</tr>
		</table>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<c:url value="./forgotPasswordPage" var="fpwdUrl" />
	<table>

		<tr>
			<td><a href="${fpwdUrl}">Forgot Password?</a></td>
		</tr>
	</table>
</body>
</html>