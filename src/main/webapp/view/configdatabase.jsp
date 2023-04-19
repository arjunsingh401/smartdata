<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="logic"%> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>SDC Home</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
		<link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet">
	</head>
	<body>
		<jsp:include page="header.jsp">
			<jsp:param value="config" name="page"/>
		</jsp:include>
		
		<div class="container-fluid">
			<div class="row">
				<div class="col-lg-12">
					<a href="#" class="btn btn-primary btn-sm" style="float: right;" onclick="addDatabase()"><i class="fa fa-plus"></i> Add</a>
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
		                   <tr>
		                       <td width="20%">
		                        <a href="#" onclick="editDatabase(1)">Oracle</a>
		                       </td>
		                       <td  width="20%">name:.....</td>
		                       <td  width="10%">jdbc:.....</td>
		                       <td  width="20%">org.....</td>
		                       <td  width="15%">abc</td>
		                       <td  width="15%">***</td>
		                   </tr>
		                    <tr>
		                       <td width="20%">
		                        <a href="#" onclick="editDatabase(2)">MYSQL</a>
		                       </td>
		                       <td  width="20%">name:.....</td>
		                       <td  width="10%">jdbc:.....</td>
		                       <td  width="20%">org.....</td>
		                       <td  width="15%">abc</td>
		                       <td  width="15%">***</td>
		                   </tr>
		                    <tr>
		                       <td width="20%">
		                        <a href="#">SQLSERVER</a>
		                       </td>
		                      <td  width="20%">name:.....</td>
		                       <td  width="10%">jdbc:.....</td>
		                       <td  width="20%">org.....</td>
		                       <td  width="15%">abc</td>
		                       <td  width="15%">***</td>
		                   </tr>
		                    <tr>
		                       <td width="20%">
		                        <a href="#">POSTGRES</a>
		                       </td>
		                       <td  width="20%">name:.....</td>
		                       <td  width="10%">jdbc:.....</td>
		                       <td  width="20%">org.....</td>
		                       <td  width="15%">abc</td>
		                       <td  width="15%">***</td>
		                   </tr>
		               </tbody>
		           </table>
		          </div>
	         </div>
		</div>
		
		<!-- MODAL EDIT START-->
		<div id="editModal" class="modal fade" tabindex="-1" role="dialog" data-keyboard="false" data-backdrop="static">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title"><i class="fa fa-edit"></i> Edit Database</h5>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          <span aria-hidden="true">&times;</span>
			        </button>
			      </div>
			      <div class="modal-body">
					 <div class="form-group">
			            <label for="database">Database</label>
			            <input type="text" class="form-control" id="database" placeholder="Database">
			          </div>
			          <div class="form-group">
			            <label for="dbname">DB Name</label>
			            <input type="text" class="form-control" id="dbname" placeholder="DB Name">
			          </div>
			          <div class="form-group">
			            <label for="url">URL</label>
			            <input type="text" class="form-control" id="url" placeholder="URL">
			          </div>
			          <div class="form-group">
			            <label for="drivername">Driver Class Name</label>
			            <input type="text" class="form-control" id="drivername" placeholder="Driver Class Name">
			          </div>
			          <div class="form-group">
			            <label for="username">Username</label>
			            <input type="text" class="form-control" id="username" placeholder="Username">
			          </div>
			          <div class="form-group">
			            <label for="password1">Password</label>
			            <input type="password" class="form-control" id="password" placeholder="Password">
			          </div>
					</div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-primary">Save changes</button>
			        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			      </div>
			    </div>
			  </div>
			</div>
		<!-- MODAL EDIT END-->
		
		<!-- MODAL ADD START-->
		<div id="addModal" class="modal fade" tabindex="-1" role="dialog" data-keyboard="false" data-backdrop="static">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title"><i class="fa fa-plus"></i> Add Database</h5>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          <span aria-hidden="true">&times;</span>
			        </button>
			      </div>
			      <div class="modal-body">
					 <div class="form-group">
			            <label for="database">Database</label>
			            <select  name="database" id="database" class="form-control">
						    <option value="">-- Database --</option>
						    <option value="oracle">Oracle</option>
						    <option value="mysql">MySQL</option>
						    <option value="sqlserver">SQL Server</option>
						</select>
			          </div>
			          <div class="form-group">
			            <label for="dbname">DB Name</label>
			            <input type="text" class="form-control" id="dbname" placeholder="DB Name">
			          </div>
			          <div class="form-group">
			            <label for="url">URL</label>
			            <input type="text" class="form-control" id="url" placeholder="URL">
			          </div>
			          <div class="form-group">
			            <label for="drivername">Driver Class Name</label>
			            <input type="text" class="form-control" id="drivername" placeholder="Driver Class Name">
			          </div>
			          <div class="form-group">
			            <label for="username">Username</label>
			            <input type="text" class="form-control" id="username" placeholder="Username">
			          </div>
			          <div class="form-group">
			            <label for="password1">Password</label>
			            <input type="password" class="form-control" id="password" placeholder="Password">
			          </div>
					</div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-primary">Save changes</button>
			        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			      </div>
			    </div>
			  </div>
			</div>
		<!-- MODAL ADD END-->
		
		<!-- Optional JavaScript -->
	    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
	    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	  
		<script type="text/javascript">
			function editDatabase(id){
				$('#editModal').modal('show');
			}
			
			function addDatabase(){
				$('#addModal').modal('show');
			}
		</script>
		
	</body>
</html>