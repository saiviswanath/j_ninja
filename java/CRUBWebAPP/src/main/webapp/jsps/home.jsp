<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
       pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="pragma" content="no-cache" />
<title>Home</title>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.0/themes/smoothness/jquery-ui.css" />
    <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.min.js"></script>
<link rel="stylesheet" href="./css/global.css" />
<script>
       $(function() {
              // Tab structure
                       $( "#tabs" ).tabs();

              $("li:nth-child(2) > a").on("click", function(event) {
                     $.ajax({
                           url : "./newStudentPage.do",
                           type : "GET",
                           dataType : "html"
                     }).done(function(result) {
                           $("#tabs-2").html(result);
                     });
              });

              $("li:nth-child(3) > a").on("click", function(event) {
                     $.ajax({
                           url : "./updateInputForm.do",
                           type : "GET",
                           dataType : "html"
                     }).done(function(result) {
                           $("#tabs-3").html(result);
                     });
              });

              $("li:nth-child(4) > a").on("click", function(event) {
                     $.ajax({
                           url : "./deleteInputForm.do",
                           type : "GET",
                           dataType : "html"
                     }).done(function(result) {
                           $("#tabs-4").html(result);
                     });
              });
              
              // Sort and direction
              $("#sortBy").on("change", function(event) {
                     
                     var form = $("#sortBySelectForm");
                     $.ajax({
                           url :  form.attr("action"),
                           data : form.find("input[type='hidden'], :input:not(:hidden)").serialize(),
                           type : "GET"
                     }).done(function(result) {
                           hideStuff();
                           $("#parentDiv").html(result);
                     });

              });
              
              $("#sortDirection").on("change", function(event) {
                     var form = $("#sortDirSelectForm");
                     $.ajax({
                           url :  form.attr("action"),
                           data : form.find("input[type='hidden'], :input:not(:hidden)").serialize(),
                           type : "GET"
                     }).done(function(result) {
                           hideStuff();
                           $("#parentDiv").html(result);
                     });
              });
              
              
              // Sorting and direction buttons
              $("#fnameButton").on("click", function(event) {
                     sortingButtonAjaxCall($(this), "firstname");
              });
              
              $("#lnameButton").on("click", function(event) {
                     sortingButtonAjaxCall($(this), "lastname");
              });
              
              $("#genderButton").on("click", function(event) {
                     sortingButtonAjaxCall($(this), "gender");
              });
              
              $("#dobButton").on("click", function(event) {
                     sortingButtonAjaxCall($(this), "dob");
              });
              
              // Pagination links
              $("#prevButton").on("click", function(event) {
                     var form = $("#prevLink");
                     $.ajax({
                           url :  form.attr("action"),
                           data : form.find("input[type='hidden'], :input:not(:hidden)").serialize(),
                           type : "GET"
                     }).done(function(result) {
                           hideStuff();
                           $("#parentDiv").html(result);
                     });
              });
              
              $("#nextButton").on("click", function(event) {
                     var form = $("#nextLink");
                     $.ajax({
                           url :  form.attr("action"),
                           data : form.find("input[type='hidden'], :input:not(:hidden)").serialize(),
                           type : "GET"
                     }).done(function(result) {
                           hideStuff();
                           $("#parentDiv").html(result);
                     });
              });
              
              
              $.each($("[id^=numButton]"), function() {
                     var num = $(this).val();
                     var formSelector = "#numLink" + num;
                     var buttonSelector = "#numButton" + num;
                     $(buttonSelector).on("click", function(event) {
                           var form = $(formSelector);
                           $.ajax({
                                  url :  form.attr("action"),
                                  data : form.find("input[type='hidden'], :input:not(:hidden)").serialize(),
                                  type : "GET"
                           }).done(function(result) {
                                  hideStuff();
                                  $("#parentDiv").html(result);
                           });
                     });
              });
              
              function hideStuff() {
                     $("body > div[id='header']").hide();
                     $("body > div[id='tabs']").hide();
                     $("body > div[id='footer']").hide();
              }
              
              function getSortDirection() {
                     var sortDirection = '${sortDirField}';
                     if (sortDirection == 'asc') {
                           return 'desc';
                     } else if (sortDirection == 'desc') {
                           return 'asc';
                     }
              }
              
              function changeOnSortDir(button) {
                     var buttonValue = button.val();
                     var sortDirection = '${sortDirField}';
                     if (sortDirection == 'asc') {
                           buttonValue += decodeHtml("&#8593;");
                     } else if (sortDirection == 'desc') {
                           buttonValue += decodeHtml("&#8595;");
                     }
              }
              
              function decodeHtml(html) {
   					 var txt = document.createElement("textarea");
    				 txt.innerHTML = html;
    				 return txt.value;
			  }
              
              function sortingButtonAjaxCall(button, sortBy) {
                     changeOnSortDir(button);
                     $.ajax({
                           url :  "./getHomePage.do",
                           data : {
                                  first : 0,
                                  max : '${maxPageRecords}',
                                  sortBy : sortBy,
                                  sortDirection : getSortDirection()
                           },
                           type : "GET"
                     }).done(function(result) {
                           hideStuff();
                           $("#parentDiv").html(result);
                     });
              }
       });    
</script>
</head>
<body>
       <div id="parentDiv"></div>
       <div id="header">
              <%@ include file="headerinclude.jsp"%>
       </div>
       <div id="tabs">
              <ul>
                     <li><a href="#tabs-1">Home</a></li>
                     <li><a href="#tabs-2">New Student</a></li>
                     <li><a href="#tabs-3">Update Student</a></li>
                     <li><a href="#tabs-4">Delete Student</a></li>
              </ul>
              <div id="tabs-1">
                     <h3>
                           <c:out value="List of active Students" />
                     </h3>

                     <c:set var="sortByField" value="${sortByField}" />
                     <c:set var="sortDirField" value="${sortDirField}" />

                     <div id="body">
                           <%-- <!-- SortBy Form -->
                           <form id="sortBySelectForm" name="sortBySelectForm"
                                  action="./getHomePage.do">
                                  <input type="hidden" name="first" value="0" /> <input
                                         type="hidden" name="max" value="${maxPageRecords}" /> <input
                                         type="hidden" name="sortDirection" value="${sortDirField}" />
                                  
                                  <table>
                                         <tr>
                                                <td><label for="sortBy">SortBy</label></td>
                                                <td><select id="sortBy" name="sortBy">
                                                       onchange="this.form.submit()">
                                                              <option value="select" selected>--Select--</option>
                                                              <option value="firstname">FirstName</option>
                                                              <option value="lastname">LastName</option>
                                                              <option value="gender">Gender</option>
                                                              <option value="dob">DOB</option>
                                                </select></td>
                                                <td><c:out value="${sortByField}" /></td>
                                         </tr>
                                  </table>
                           </form>

                           <!-- SortDir Form -->
                           <form id="sortDirSelectForm" name="sortDirSelectForm"
                                  action="./getHomePage.do">
                                  <input type="hidden" name="first" value="0" /> <input
                                         type="hidden" name="max" value="${maxPageRecords}" /> <input
                                         type="hidden" name="sortBy" value="${sortByField}" />
                                  <table>
                                         <tr>
                                                <td><label for="sortDirection">Sort Direction</label></td>
                                                <td><select id="sortDirection" name="sortDirection">
                                                       <!-- onchange="this.form.submit()"> -->
                                                              <option value="select" selected>--Select--</option>
                                                              <option value="asc">Ascending</option>
                                                              <option value="desc">Descending</option>
                                                </select></td>
                                                <td><c:out value="${sortDirField}" /></td>
                                         </tr>
                                  </table>
                           </form>
--%>
                          <table>
                                  <tr><td>SortBy:&nbsp;</td><td><c:out value="${sortByField}" /></td></tr>
                                  <tr><td>SortDirection:&nbsp;</td><td><c:out value="${sortDirField}" /></td></tr>
                           </table>

                           <table id="student-table" border="1" cellpadding="1" cellspacing="1">
                                  <th><input id="fnameButton" type="button" value="FirstName" class="sortButtons"></th>
                                  <th><input id="lnameButton" type="button" value="LastName" class="sortButtons"></th>
                                  <th><input id="genderButton" type="button" value="Gender" class="sortButtons"></th>
                                  <th><input id="dobButton" type="button" value="DOB" class="sortButtons"></th>
                                  <th><input id="emailButton" type="button" value="Email" class="sortButtons"></th>
                                  <th><input id="mnumButton" type="button" value="MobilNumber" class="sortButtons"></th>
                                  <th><input id="addrButton" type="button" value="Address" class="sortButtons"></th>
                                  <th><input id="coursesButton" type="button" value="Courses" class="sortButtons"></th>
                                  <c:forEach var="student" items="${studentList}">
                                         <tr>
                                                <td>${student.firstName}</td>
                                                <td>${student.lastName}</td>
                                                <td>${student.gender}</td>
                                                <td>${student.DOB}</td>
                                                <td>${student.email}</td>
                                                <td>${student.mobileNumber}</td>
                                                <td>${student.address}</td>
                                                <td><c:forEach var="course" items="${student.courses}">
                                  ${course}&nbsp;
                           </c:forEach></td>
                                         </tr>
                                  </c:forEach>
                           </table>
                           </div>

                           <%--For displaying Previous link except for the 1st page --%>
                           <c:if test="${currentPage != 0}">
                           <form id="prevLink" action="./getHomePage.do">
                                  <input type="hidden" name="first" value="${currentPage - maxPageRecords}" />
                                  <input type="hidden" name="max" value="${maxPageRecords}" />
                                  <input type="hidden" name="sortBy" value="${sortByField}" />
                                  <input type="hidden" name="sortDirection" value="${sortDirField}" />
                                  <input id="prevButton" type="button" value="Previous"/>
                           </form>
                                  <%-- <td><a id="prevLink"
                                         href="./getHomePage.do?first=${currentPage - maxPageRecords}&max=${maxPageRecords}&sortBy=${sortByField}&sortDirection=${sortDirField}">Previous</a></td> --%>
                           </c:if>

                           <%--For displaying Page numbers. 
       The when condition does not display a link for the current page--%>
                           <table id="page-table" border="1" cellpadding="5" cellspacing="5">
                                  <tr>
                                         <c:forEach begin="1" end="${noOfPages}" var="i">
                                                <c:choose>
                                                       <c:when test="${currentPage eq ((i-1) * maxPageRecords)}">
                                                              <td>${i}</td>
                                                       </c:when>
                                                       <c:otherwise>
                                                       <td>
                                                       <form id="numLink${i}" action="./getHomePage.do">
                                                              <input type="hidden" name="first" value="${(i-1) * maxPageRecords}" />
                                                              <input type="hidden" name="max" value="${maxPageRecords}" />
                                                              <input type="hidden" name="sortBy" value="${sortByField}" />
                                                              <input type="hidden" name="sortDirection" value="${sortDirField}" />
                                                              <input id="numButton${i}" type="button" value="${i}"/>
                                                       </form>
                                                       </td>
                                                              <%-- <td><a id="numLink"
                                                                     href="./getHomePage.do?first=${(i-1) * maxPageRecords}&max=${maxPageRecords}&sortBy=${sortByField}&sortDirection=${sortDirField}">${i}</a></td> --%>
                                                       </c:otherwise>
                                                </c:choose>
                                         </c:forEach>
                                  </tr>
                           </table>

                           <%--For displaying Next link --%>
                           <c:if test="${currentPage lt ((noOfPages - 1) * maxPageRecords)}">
                                  <form id="nextLink" action="./getHomePage.do">
                                  <input type="hidden" name="first" value="${currentPage + maxPageRecords}" />
                                  <input type="hidden" name="max" value="${maxPageRecords}" />
                                  <input type="hidden" name="sortBy" value="${sortByField}" />
                                  <input type="hidden" name="sortDirection" value="${sortDirField}" />
                                  <input id="nextButton" type="button" value="Next"/>
                           </form>
                                  <%-- <td><a id="nextLink"
                                         href="./getHomePage.do?first=${currentPage + maxPageRecords}&max=${maxPageRecords}&sortBy=${sortByField}&sortDirection=${sortDirField}">Next</a></td> --%>
                           </c:if>
                           </div>

              <div id="tabs-2"></div>
              <div id="tabs-3"></div>
              <div id="tabs-4"></div>
              
       </div>

       <%--          <table>
                     <tr>
                           <td><a href="<c:url value="./newStudentPage.do"/>">New
                                         Student</a></td>
                           <td><a href="<c:url value="./updateInputForm.do"/>">Update
                                         Student</a></td>
                           <td><a href="<c:url value="./deleteInputForm.do"/>">Delete
                                         Student</a></td>
                     </tr>
              </table> --%>
       <div id="footer">
              <%@ include file="footerinclude.jsp"%>
       </div>
</body>
</html>