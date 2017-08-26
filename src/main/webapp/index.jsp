<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Birt ajax</title>

<script src="http://code.jquery.com/jquery-1.10.2.js" type="text/javascript"></script>
<script src="js/app-ajax.js" type="text/javascript"></script>
</head>
<body>


		Enter Report Name: <input type="text" id="ReportName" />
		<br>
		<br>
		<div>
		Enter from Date: <input type="date" id="startDate" />
		Enter to Date: <input type="date" id="endDate" />
		<button id="button" onclick="show()">generate Report</button>
		</div>

	
	<br>
	<br>

	<strong>Ajax Response</strong>:
	<div id="ajaxGetUserServletResponse"></div>
</body>
</html>
