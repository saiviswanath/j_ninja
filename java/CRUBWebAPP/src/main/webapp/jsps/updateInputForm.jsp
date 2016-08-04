<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Student Page Request</title>
<script type="text/javascript">
$(function() {		
	var text = $("#updateResultMessage").text();
	if (text != "") {
		$("#updateResultMessage").show();
	}
	
	var text = $("#updateMessageDisplay").text();
	if (text != "") {
		$("#updateMessageDisplay").show();
	}
	
	$("#updateForm").on("submit", function(event) {
		event.preventDefault();
		$.ajax({
			url : $(this).attr("action"),
			data : $(this).serialize(),
			type : "GET"
		}).done(function(result) {
			$("#updateBody").html(result);
		});
	});
});
</script>
</head>
<body>
	<div id="updateBody">
		<div id="updateResultMessage" class="resultBlock" style="display:none;">${ResultMessage}</div>
		<div id="updateMessageDisplay" class="errorblock" style="display:none;">${ErrorMessage}</div>

		<s:form id="updateForm" method="GET" commandName="updateInputBean"
			action="./fetchUpdateFormDetails.do">
			<s:errors path="*" cssClass="errorblock" element="div" />
			<h3>
				<c:out value="Enter User Name Details to update: " />
			</h3>
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
			<input id="updateSubmitButton" type="submit" value="FetchUpdateForm" />
		</s:form>
	</div>
</body>
</html>