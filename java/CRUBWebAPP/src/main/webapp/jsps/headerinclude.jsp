<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Header Page</title>
</head>
<body>
	<div id="header">
		<table>
			<tr>
				<td><a href="/CRUBWebAPP/index.jsp">Home</a></td>
				<td><a href="<c:url value="./getPermissionPage.do"/>">Permissions</a></td>
			</tr>
		</table>
		<sec:authorize access="authenticated">
			<div id="loDiv" align="right">
				<table>
					<tr>
						<td>Welcome, <sec:authentication property="name" /></td>
						<c:url var="logoutUrl" value="/logout" />
						<td><a href="<c:url value="./passwordChangeForm.do"/>">Change
								Password</a></td>
						<td><form action="${logoutUrl}" method="post">
								<input type="submit" value="Log out" /> <input type="hidden"
									name="${_csrf.parameterName}" value="${_csrf.token}" />
							</form></td>
					</tr>
				</table>
			</div>
		</sec:authorize>
	</div>
</body>
</html>