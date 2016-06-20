<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New User Page</title>

<link rel="stylesheet" href="./css/global.css" />
</head>
<body>
	<div id="header">
		<%@ include file="headerinclude.jsp"%>
	</div>
	<h3>
		<c:out value="Enter New User Details: " />
	</h3>
	<div id="body">
		<s:form action="./newUserDetails.do" method="POST" commandName="user">
			<s:errors path="*" cssClass="errorblock" element="div" />
			<table id="user-table" cellspacing="1" cellpadding="1">
				<tr>
					<td><s:label path="userName">User Name</s:label></td>
					<td><s:input path="userName" /></td>
					<td><s:errors path="userName" cssClass="error" /></td>
				</tr>
				<tr>
					<td><s:label path="password">Password</s:label></td>
					<td><s:password path="password" /></td>
					<td><s:errors path="password" cssClass="error" /></td>
				</tr>
				<tr>
					<td><s:label path="retypedPassword">ReType Password</s:label></td>
					<td><s:password path="retypedPassword" /></td>
					<td><s:errors path="retypedPassword" cssClass="error" /></td>
				</tr>
				<tr>
					<td><s:label path="email">Email</s:label></td>
					<td><s:input path="email" /></td>
					<td><s:errors path="email" cssClass="error" /></td>
				</tr>
				<tr>
					<td><s:checkbox path="enabled" />Enabled</td>
					<td><s:errors path="enabled" cssClass="error" /></td>
				</tr>
				<tr>
					<td><s:label path="roles">Roles</s:label></td>
					<td><s:select path="roles" items="${roleList}" multiple="true" /></td>
					<td><s:errors path="roles" cssClass="error" /></td>
				</tr>
			</table>
			<input type="submit" value="CreateUser" />
		</s:form>
	</div>
	<div id="footer">
		<%@ include file="footerinclude.jsp"%>
	</div>
</body>
</html>
