<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register Client</title>
<link rel="icon" href="/UserAccountClient/favicon.ico" type="image/x-icon" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript">
function testService() {
	//http://stackoverflow.com/questions/20035101/no-access-control-allow-origin-header-is-present-on-the-requested-resource
	$.ajax({ 
		dataType: "json",
        type: "GET",
        contentType: "application/json; charSet=UTF-8",
        url: "http://localhost:9090/useraccount/register/doregister?name=142&username=142&password=shanti12345",
        success: function(data) {
                    alert(data);
				 },
		error: function(data) {
                    alert("error");
               }
	});
}
</script>
</head>
<body>
	<input type="button" value="test" onClick="testService()">
</body>
</html>