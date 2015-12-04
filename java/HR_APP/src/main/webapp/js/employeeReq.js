/**
 * Employee Requests
 */
var xmlhttp;
function employeeJSPReq() {
	document.getElementById("image").innerHTML = "<image src='/HR_APP/ajax-loader.gif'/>";
	document.getElementById("empDetails").style = "display: none";

	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	var parameters = "firstName=" + document.getElementById("firstName");
	xmlhttp.onreadystatechange = handleResponse;
	xmlhttp.open("POST", "employee.jsp", true);
	xmlhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlhttp.send(parameters);
}

function handleResponse() {
}
