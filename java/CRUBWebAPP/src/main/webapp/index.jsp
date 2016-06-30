<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Index Page</title>
</head>
<body>
	<!-- Jsp pagination sample http://theopentutorials.com/examples/java-ee/jsp/pagination-in-servlet-and-jsp/ -->
	<!-- Session scoped var for max records in a page during pagination -->
	<c:set var="maxPageRecords" value="5" scope="session"/>

	<c:url value="./getHomePage.do" var="homeUrl">
		<c:param name="first" value="0" />
		<c:param name="max" value="${maxPageRecords}" />
		<c:param name="sortBy" value="firstname" />
		<c:param name="sortDirection" value="asc" />
	</c:url>
	<jsp:forward page="${homeUrl}" />
</body>
</html>