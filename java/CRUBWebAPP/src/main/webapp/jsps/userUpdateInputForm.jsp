<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update User Page Request</title>
<link rel="stylesheet" href="./css/global.css" />
</head>
<body>
	<div id="header">
		<%@ include file="headerinclude.jsp"%>
	</div>
	<div id="body">
		<h3>
			<c:out value="Enter User Name Details to update: " />
		</h3>
		<s:form method="GET" commandName="userUpdateInputBean"
			action="./fetchUserUpdateFormDetails.do">
			<s:errors path="*" cssClass="errorblock" element="div" />
			<table>
				<tr>
					<td><s:label path="userName">User Name</s:label></td>
					<td><s:input path="userName" /></td>
					<td><s:errors path="userName" cssClass="error" /></td>
				</tr>
			</table>
			<input type="submit" value="FetchUserUpdateForm" />
		</s:form>
	</div>
	<div id="messageDisplay">
		${ErrorMessage}
	</div>
	<div id="footer">
		<%@ include file="footerinclude.jsp"%>
	</div>
</body>
</html>