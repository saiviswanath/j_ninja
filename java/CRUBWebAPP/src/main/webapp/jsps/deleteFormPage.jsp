<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Student Page Request</title>
<link rel="stylesheet" href="./css/global.css" />
</head>
<body>
	<div id="header">
		<%@ include file="headerinclude.jsp"%>
	</div>
	<div id="body">
		<h3>
			<c:out value="Enter User Name Details to delete: " />
		</h3>
		<s:form method="DELETE" commandName="updateInputBean"
			action="./deleteFormDetails.do">
			<s:errors path="*" cssClass="errorblock" element="div" />
			<table>
				<tr>
					<td><s:label path="firstName">First Name</s:label></td>
					<td><s:input path="firstName" /></td>
					<td><s:errors path="firstName" cssClass="error" /></td>
				</tr>
				<tr>
					<td><s:label path="lastName">Last Name</s:label></td>
					<td><s:input path="lastName" /></td>
					<td><s:errors path="lastName" cssClass="error" /></td>
				</tr>
			</table>
			<input type="hidden" name="_method" value="DELETE" />
			<input type="submit" value="delete" />
		</s:form>
	</div>
	<div id="messageDisplay">${ErrorMessage}</div>
	<div id="footer">
		<%@ include file="footerinclude.jsp"%>
	</div>
</body>
</html>