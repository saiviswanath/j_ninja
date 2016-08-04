<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Index Page</title>
<script>
	$(function() {
		$("#updateFormBody").on("submit", function(event) {
			event.preventDefault();
			$.ajax({
				url : $(this).attr("action"),
				data : $(this).serialize(),
				type : "POST"
			}).done(function(result) {
				$("#updateFetchbody").html(result);
			});
		});
	});
</script>
</head>
<body>
	<div id="updateFetchbody">
		<h3>
			<c:out value="Update Student Details: " />
		</h3>
		<s:form id="updateFormBody" action="./updateStudentDetails.do"
			method="POST" commandName="student">
			<s:errors path="*" cssClass="errorblock" element="div" />
			<table id="student-table" cellspacing="1" cellpadding="1">
				<tr>
					<td><s:label path="firstName">First Name</s:label></td>
					<td><s:input path="firstName" readonly="true" /></td>
					<td><s:errors path="firstName" cssClass="error" /></td>
				</tr>
				<tr>
					<td><s:label path="lastName">Last Name</s:label></td>
					<td><s:input path="lastName" readonly="true" /></td>
					<td><s:errors path="lastName" cssClass="error" /></td>
				</tr>
				<tr>
					<td><s:label path="gender">Gender</s:label></td>
					<td><s:radiobutton path="gender" value="Male" />Male <s:radiobutton
							path="gender" value="Female" />Female <s:radiobutton
							path="gender" value="Other" />Other</td>
					<td><s:errors path="gender" cssClass="error" /></td>
				</tr>
				<tr>
					<td><s:label path="DOB">DOB</s:label></td>
					<td><s:input path="DOB" id="datepicker" /></td>
					<td><s:errors path="DOB" cssClass="error" /></td>
				</tr>
				<tr>
					<td><s:label path="email">Email</s:label></td>
					<td><s:input path="email" /></td>
					<td><s:errors path="email" cssClass="error" /></td>
				</tr>
				<tr>
					<td><s:label path="mobileNumber">Mobile Number</s:label></td>
					<td><s:input path="mobileNumber" /></td>
					<td><s:errors path="mobileNumber" cssClass="error" /></td>
				</tr>
				<tr>
					<td><s:label path="courses">Courses</s:label></td>
					<td><s:select path="courses" items="${courseList}"
							multiple="true" /></td>
					<td><s:errors path="courses" cssClass="error" /></td>
				</tr>
			</table>
			<h4>Address:</h4>
			<table>
				<tr>
					<td><s:label path="address.houseNo">House No</s:label></td>
					<td><s:input path="address.houseNo" /></td>
					<td><s:errors path="address.houseNo" cssClass="error" /></td>
				</tr>
				<tr>
					<td><s:label path="address.street">Street</s:label></td>
					<td><s:input path="address.street" /></td>
					<td><s:errors path="address.street" cssClass="error" /></td>
				</tr>
				<tr>
					<td><s:label path="address.area">Area</s:label></td>
					<td><s:input path="address.area" /></td>
					<td><s:errors path="address.area" cssClass="error" /></td>
				</tr>
				<tr>
					<td><s:label path="address.city">City</s:label></td>
					<td><s:input path="address.city" /></td>
					<td><s:errors path="address.city" cssClass="error" /></td>
				</tr>
				<tr>
					<td><s:label path="address.pin">Pin</s:label></td>
					<td><s:input path="address.pin" /></td>
					<td><s:errors path="address.pin" cssClass="error" /></td>
				</tr>
			</table>
			<!-- 			<input type="hidden" name="_method" value="PUT" />
 -->
			<input type="submit" value="Update" />
		</s:form>
	</div>
</body>
</html>