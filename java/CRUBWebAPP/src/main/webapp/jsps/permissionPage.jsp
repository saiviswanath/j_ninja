<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Permission Page</title>
</head>
<body>
	<div id="header">
		<%@ include file="headerinclude.jsp"%>
		<table>
			<tr>
				<td><a href="<c:url value="./newUserPage.do"/>">New User</a></td>
				<td><a href="<c:url value="./updateUserInputForm.do"/>">Update
						User</a></td>
				<td><a href="<c:url value="./deleteUserInputForm.do"/>">Delete
						User</a></td>
			</tr>
		</table>
	</div>
	<h3>
		<c:out value="List of active Users" />
	</h3>
	<div id="body">
		<table id="user-table" border="1" cellpadding="1" cellspacing="1">
			<th>UserName</th>
			<th>Email</th>
			<th>Enabled</th>
			<th>Roles</th>
			<c:forEach var="user" items="${userList}">
				<tr>
					<td>${user.userName}</td>
					<td>${user.email}</td>
					<c:choose>
						<c:when test="${user.enabled == 1}">
							<td>Yes</td>
						</c:when>
						<c:otherwise>
							<td>No</td>
						</c:otherwise>
					</c:choose>
					<td><c:forEach var="role" items="${user.roles}">
					${role}&nbsp;
				</c:forEach></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div id="footer">
		<%@ include file="footerinclude.jsp"%>
	</div>
</body>
</html>