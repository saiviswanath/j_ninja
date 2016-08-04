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
		var text = $("#deleteResultMessage").text();
		if (text != "") {
			$("#deleteResultMessage").show();
		}

		var text = $("#deleteMessageDisplay").text();
		if (text != "") {
			$("#deleteMessageDisplay").show();
		}

		$("#deleteForm").on("submit", function(event) {
			event.preventDefault();
			$.ajax({
				url : $(this).attr("action"),
				data : $(this).serialize(),
				type : "POST"
			}).done(function(result) {
				$("#deletebody").html(result);
			});
		});
	});
</script>
</head>
<body>
	<div id="deletebody">
		<div id="deleteResultMessage" class="resultBlock"
			style="display: none;">${ResultMessage}</div>
		<div id="deleteMessageDisplay" class="errorblock"
			style="display: none;">${ErrorMessage}</div>
		<s:form id="deleteForm" method="POST" commandName="updateInputBean"
			action="./deleteFormDetails.do">
			<s:errors path="*" cssClass="errorblock" element="div" />
			<h3>
				<c:out value="Enter User Name Details to delete: " />
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
			<input type="submit" value="delete" />
		</s:form>
	</div>
</body>
</html>