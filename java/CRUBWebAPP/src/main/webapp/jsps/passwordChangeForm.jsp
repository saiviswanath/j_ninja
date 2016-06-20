<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@taglib uri="http://www.springframework.org/tags/form" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Password Change Form</title>
<link rel="stylesheet" href="./css/global.css" />
</head>
<body>
	<div id="header">
		<%@ include file="headerinclude.jsp"%>
	</div>
	<h3>
		<c:out value="Enter Password Details: " />
	</h3>
	<div id="body">
		<s:form method="PUT" commandName="updatePasswordBean"
			action="./updatePassword.do">
			<s:errors path="*" cssClass="errorblock" element="div" />
			<table>
				<tr>
					<td><s:label path="oldPassword">Old Password</s:label></td>
					<td><s:password path="oldPassword" /></td>
					<td><s:errors path="oldPassword" cssClass="error" /></td>
				</tr>
				<tr>
					<td><s:label path="newPassword">New Password</s:label></td>
					<td><s:password path="newPassword" /></td>
					<td><s:errors path="newPassword" cssClass="error" /></td>
				</tr>
				<tr>
					<td><s:label path="cfNewPassword">Confirm New Password</s:label></td>
					<td><s:password path="cfNewPassword" /></td>
					<td><s:errors path="cfNewPassword" cssClass="error" /></td>
				</tr>
			</table>
			<input type="hidden" name="_method" value="PUT" />
			<input type="submit" value="Update Password" />
		</s:form>
	</div>
	<div id="footer">
		<%@ include file="footerinclude.jsp"%>
	</div>
</body>
</html>