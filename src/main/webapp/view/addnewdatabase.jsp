<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="logic"%> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Add New Database</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
		<link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet">
	</head>
	<body>
		<jsp:include page="header.jsp">
			<jsp:param value="config" name="page"/>
		</jsp:include>
		<div class = "container-fluid">
	         <nav aria-label = "breadcrumb">
	            <ul class = "breadcrumb">
	               <li class = "breadcrumb-item"><a href = "${pageContext.request.contextPath}/home"><i class="fa fa-home"></i> Home</a></li>
	               <li class = "breadcrumb-item"><a href = "${pageContext.request.contextPath}/getDbConnections"><i class="fa fa-list"></i> Database List</a></li>
	               <li class = "breadcrumb-item active" aria-current = "page"><i class="fa fa-plus"></i> Add Database</li>
	            </ul>
	         </nav>
	    </div>
	    <form:form action="${pageContext.request.contextPath}/savenNewDatabase" method="post" modelAttribute="connection">
			<div class="container-fluid">
				<div class="row">
				   	 <div class="col-sm-6">
					 <div class="form-group">
			            <label for="database">Database</label>
			            <form:input path="description" id="description" cssClass="form-control"/>
			          </div>
				      </div>
				      <div class="col-sm-6">    
			          <div class="form-group">
			            <label for="dbname">DB Name</label>
			            <input type="text" class="form-control" id="dbname" placeholder="DB Name">
			          </div>
			           </div>
				      <div class="col-sm-6"> 
			          <div class="form-group">
			            <label for="url">URL</label>
			            <form:input path="url" id="url" cssClass="form-control"/>
			          </div>
			           </div>
				      <div class="col-sm-6"> 
			          <div class="form-group">
			            <label for="drivername">Driver Class Name</label>
			            <input type="text" class="form-control" id="drivername" placeholder="Driver Class Name">
			          </div>
			           </div>
				      <div class="col-sm-6"> 
			          <div class="form-group">
			            <label for="username">Username</label>
			            <input type="text" class="form-control" id="username" placeholder="Username">
			          </div>
			           </div>
				      <div class="col-sm-6"> 
			          <div class="form-group">
			            <label for="password1">Password</label>
			            <input type="password" class="form-control" id="password" placeholder="Password">
			          </div>
			          </div>
		         </div>
		         <div class="row"> 
	                   <div class="col-sm-12 text-center">
	                      <a href="${pageContext.request.contextPath}/services" title="BACK"  class="btn btn-danger cancel"><i class="fa fa-remove"></i> Cancel</a>
	                      <button id="submit-button" class="btn btn-primary cancel" type="submit" data-ng-click="validateForm($event)"><i class="fa fa-save"></i> Save</button>
	                  </div>
	              </div>
			</div>
		</form:form>
		
		<!-- Optional JavaScript -->
	    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
	    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	</body>
</html>