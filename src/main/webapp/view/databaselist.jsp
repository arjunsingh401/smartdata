<%@page import="com.sdc.model.DbConnection"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="logic"%> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%
	List connections = (List)request.getAttribute("connections");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Database List</title>
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
	               <li class = "breadcrumb-item active" aria-current = "page"><i class="fa fa-list"></i> Database List</li>
	            </ul>
	         </nav>
	    </div>
		<div class="container-fluid">
			<div class="row">
				<div class="col-lg-12">
					<a class="btn btn-primary btn-sm" style="float: right;" href="${pageContext.request.contextPath}/addnNewDatabase"><i class="fa fa-plus"></i> Add</a>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-lg-12">
					<table class="table" cellspacing="0">
		               <thead>
		                   <tr>
		                       <th>Database</th>
		                       <th>DB Name</th>
		                       <th>URL </th>
		                       <th>Driver Class Name </th>
		                       <th>Username </th>
		                       <th>Password </th>
		                   </tr>
		               </thead>
		               <tbody>
		               		<%
		               		 	for(int a=0;a<connections.size();a++){
		               		 	DbConnection db = (DbConnection)connections.get(a);
		               		 %>
		               			 <tr>
		                       <td width="20%">
		                        <a href="${pageContext.request.contextPath}/getDbConnection/<%=db.getId()%>"><%=db.getDescription()%></a>
		                       </td>
		                       <td  width="20%"><%=db.getName()%></td>
		                       <td  width="10%"><%=db.getUrl()%></td>
		                       <td  width="20%"><%=db.getDriverclass()%></td>
		                       <td  width="15%"><%=db.getUsername()%></td>
		                       <td  width="15%"><%=db.getPassword()%></td>
		                   </tr> 
		               		<%  } %>
		               </tbody>
		           </table>
		          </div>
	         </div>
		</div>
		
		<!-- Optional JavaScript -->
	    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
	    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	</body>
</html>