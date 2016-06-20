<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forgot Password Page</title>
<style type="text/css">
.error {
	color: #ff0000;
}

.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>
</head>
<body>
	<div id="body">
		<table>
			<tr>
				<td><c:url value="/login" var="login" /> <a href="${login}">Login
						Page</a></td>
			</tr>
		</table>
		<h3>
			<c:out value="Enter below Details: " />
		</h3>
		<s:form method="GET" commandName="forGotPasswordBean"
			action="./forGotPassword">
			<s:errors path="*" cssClass="errorblock" element="div" />
			<table>
				<tr>
					<td><s:label path="userName">User Name</s:label></td>
					<td><s:input path="userName" /></td>
					<td><s:errors path="userName" cssClass="error" /></td>
				</tr>
				<tr>
					<td><s:label path="email">Email</s:label></td>
					<td><s:input path="email" /></td>
					<td><s:errors path="email" cssClass="error" /></td>
				</tr>
			</table>
			<input type="submit" value="Submit" />
		</s:form>
	</div>
	<div id="footer">
		<%@ include file="footerinclude.jsp"%>
	</div>
</body>
</html>