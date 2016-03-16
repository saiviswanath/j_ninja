<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register Client</title>
<link rel="icon" href="/UserAccountClient/favicon.ico" type="image/x-icon" />
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript">
	function registerUser() {
		// http://stackoverflow.com/questions/20035101/no-access-control-allow-origin-header-is-present-on-the-requested-resource
		$.ajax({
			dataType : "json",
			type : "GET",
			contentType : "application/json; charSet=UTF-8",
			url : "http://localhost:9090/useraccount/register/doregister?name=" + $("#Name").val() + "&username=" + $("#UserName").val() + "&password=" + $("#Password").val(),
			success : function(response) {
				var tag = response.tag;
				var status = response.status;
				var errmsg = response.error_msg;
				var result = null;
				if (errmsg == null) {
					result = "tag: " + tag + ", status: " + status;
				} else {
					result = "tag: " + tag + ", status: " + status
							+ ", error_msg: " + errmsg;
				}
				alert(result);
			},
			error : function(e) {
				alert("Error: " + e);
			}
		});
	}
</script>
</head>
<body>
	<form id="form1" name="form1" method="get">
		<table id="table1">
			<tr>
				<td><label for="Name">Name</label> <input id="Name" name="Name"
					type="text" /></td>
			</tr>
			<tr>
				<td><label for="UserName">UserName</label> <input id="UserName"
					name="UserName" type="text" /></td>
			</tr>
			<tr>
				<td><label for="Password">Password</label> <input id="Password"
					name="Password" type="password" /></td>
			</tr>
			<tr>
				<td><input type="button" value="register"
					onclick="registerUser()" /></td>
			</tr>
		</table>
	</form>
</body>
</html>