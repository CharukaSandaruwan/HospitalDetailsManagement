<%@page import="com.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Hospital Details Management</title>

<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/hospital.js"></script>

</head>
<body Style="background-image:url(potos/home_danceschool_pic44.jpg)">
<div class="container">
	<div class="row" >
		<div class="col-6">
			<h1>Hospital Details Management</h1>
			
			<form id="formHospital" name="formHospital" method="post" action="Hospital.jsp">

				Hospital ID:
				<input id="hospital_id" name="hospital_id" type="text" class="form-control form-control-sm">
				<br>
				 
				Hospital name:
				<input id="hospital_name" name="hospital_name" type="text" class="form-control form-control-sm">
				<br>
				
				Hospital Location:
				<input id="hospital_location" name="hospital_location" type="text" class="form-control form-control-sm">
				<br>
				 
				Available Rooms:
				<input id="hospital_availableRooms" name="hospital_availableRooms" type="text" class="form-control form-control-sm">
				<br>
				
				Hospital Labs:
				<input id="hospital_labs" name="hospital_labs" type="text" class="form-control form-control-sm">
				<br>
				
				<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary" >
				<input type="hidden" id="hidhospitalIDSave" name="hidhospitalIDSave" value="">
			
				
			</form>
			
			<div id="alertSuccess" class="alert alert-success"></div>
			<div id="alertError" class="alert alert-danger"></div>
			<br>

			<div id="divHospitalGrid">
				<%
					Hospital hosObj = new Hospital();
					out.print(hosObj.readHospitalDetails());
				%>
			</div>
		</div>
	</div>
</div>
</body>
</html>