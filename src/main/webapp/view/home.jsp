<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="logic"%> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>
<%int userId = (Integer)session.getAttribute("userId"); %>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>SDC Home</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
	<link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet">
</head>
	<body>
		<jsp:include page="header.jsp">
				<jsp:param value="home" name="page"/>
		</jsp:include>
		<div class="container-fluid">
		      <table class="table" cellspacing="0">
	               <thead>
	                   <tr>
	                       <th>Database </th>
	                       <th>Test Connection</th>
	                       <th>Schema</th>
	                       <th>Table</th>
	                   </tr>
	               </thead>
	               <tbody>
	                   <tr>
	                       <td>
	                       	<select  name="sourceDatabase" id="sourceDatabase" class="form-control">
							    <option value="">-- Source Database --</option>
							    <!-- <option value="oracle">Oracle</option>
							    <option value="mysql">MySQL</option>
							    <option value="sqlserver">SQL Server</option> -->
							</select>
	                       </td>
	                       <td>
	                       	<a href="#" class="btn btn-success btn-sm" onclick="testSourceConnection()"><i class="fa fa-database"></i> Source</a>
	                       </td>
	                       <td>
	                       	<select  name="sourceSchema" id="schema" class="form-control">
							    <option value="">-- Select Schema --</option>
							</select>
	                       </td>
	                       <td>
	                       	<select name="" id="table" class="form-control">
							    <option value="">-- Select Table --</option>
							</select>
	                       </td>
	                   </tr>
	                   <tr>
	                       <td> 
	                       	<select  name="targetDatabase" id="targetDatabase" class="form-control">
							    <option value="">-- Target Database --</option>
							  <!--   <option value="oracle">Oracle</option>
							    <option value="mysql">MySQL</option>
							    <option value="sqlserver">SQL Server</option> -->
							</select>
	                       
	                       </td>
	                       <td>
	                       	<a href="#" class="btn btn-success btn-sm" onclick="testDestinationConnection()"><i class="fa fa-database"></i> Target</a>
	                       </td>
	                       <td>
	                       	<select name="targetSchema" id="targetSchema" class="form-control">
							    <option value="">-- Select Schema --</option>
							</select>
	                       </td>
	                       <td>
	                       	<select name="" id="targetTable" class="form-control">
							    <option value="">-- Select Table --</option>
							</select>
	                       </td>
	                   </tr>
	               </tbody>
	           </table>
			    <div class="row">
				  	<!-- <div class="col-lg-2" style="text-align: right;">
				  		<button id="createMapping"  style="display: none;" type="button" class="btn btn-success" onclick="createMapping()">Create Mapping</button>
				  	</div> -->
				  	<div class="col-lg-2" style="text-align: left;">
	                	<button id="saveJob"  style="display: none;" type="button" class="btn btn-success" onclick="saveJob()">Save Job</button>
	                </div>
				  	<div class="col-lg-8" style="text-align: right;">
				  	
				  		<button type="button" class="btn btn-primary" onclick="loadFields()">Load Fields</button>
				  		<button type="button" class="btn btn-danger" onclick="resetFields()">Reset</button>
				  		<button id="createMapping"  style="display: none;" type="button" class="btn btn-success" onclick="createMapping()">Create Mapping</button>
				  	</div>
				 </div>
				 <br/>
				 <table id="mappingTable" class="table" cellspacing="0" style="display: none;">
		               <thead>
		                   <tr>
		                       <th colspan="4">Fields</th>
		                   </tr>
		               </thead>
		                <thead>
		                   <tr>
		                       <th colspan="2" style="text-align: center;">Source</th>
		                       <th colspan="2" style="text-align: center;">Destination</th>
		                   </tr>
		               </thead>
		               <tbody>
		                   <tr>
		                   		<td colspan="2" width="50%" style="padding: 0px;"><table class="table table-bordered table-hover" id="mySoureTable" ></table></td>
		                   		<td colspan="2" width="50%" style="padding: 0px;"><table class="table table-bordered table-hover" id="myTargetTable" ></table></td>
		                   </tr>
		               </tbody>
		            </table> 
			</div>
		
			<div id="loadingModal" class="modal fade" role="dialog" style="top: 35%;" data-backdrop="true">
				<div class="modal-dialog" style="width: 5%">
					<div class="modal-content" style="border: 0px;">
						<div class="modal-body">
							<img alt="" src="${pageContext.request.contextPath}/loading3.gif" height="40px" width="40px">
							<!-- <h4>Loading...</h4> -->
						</div>
					</div>
				</div>
			</div>
			
			<!-- Optional JavaScript -->
		    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
		    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
		    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
		    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
		  
		  	<script>
		  	$(document).ready(function(){
			    document.getElementById('schema').disabled = true;
		        document.getElementById('table').disabled = true ;
		        document.getElementById('targetSchema').disabled = true;
		        document.getElementById('targetTable').disabled = true;
		        dbConnectionNames();
			});
		  	

			function dbConnectionNames(){
		 		 var dbData = $.ajax({
		 		    type: 'GET',       
		 		    url: "${pageContext.request.contextPath}/getDbConnectionNames",	    
		 		   	contentType: "application/json",
		 		    context: document.body,
		 		    global: false,
		 		    async:false,
		 		    success: function(data) {
		 		    	//alert('Source connection successfull'+data);
		 		        return data;
		 		    },
					error : function(xhr, ajaxOptions, thrownError) {
						alert('Unable to load Data Bases.');
					}
		 		}).responseText;
		 		 
		         sourceDB(dbData);
		 		
		         targetDB(dbData);
		 			
		  	}
		  	
		  	function sourceDB(dbData){
		  		const sourcedbConnection = document.getElementById('sourceDatabase'),
			        selects = document.querySelectorAll('select')
			        selects.forEach(select => {
			            if(select.disabled == true){
			                select.style.cursor = "auto"
			            }
			            else{
			                select.style.cursor = "pointer"
			            }
			        })
			        dbData = JSON.parse(dbData);
					dbData.forEach(function(element){
						sourcedbConnection.options[sourcedbConnection.options.length] = new Option(element, element);
					});
		  	}
		  	function targetDB(dbData){
		  		const targetdbConnection = document.getElementById('targetDatabase'),
			        selects = document.querySelectorAll('select')
			        selects.forEach(select => {
			            if(select.disabled == true){
			                select.style.cursor = "auto"
			            }
			            else{
			                select.style.cursor = "pointer"
			            }
			        })
			        dbData = JSON.parse(dbData);
				//const targetdbConnection = document.getElementById('targetSchema');
					//selectSchema.options[0] = new Option("-- Select Table --", "");
					dbData.forEach(function(element){
						targetdbConnection.options[targetdbConnection.options.length] = new Option(element, element);
					});
		  	}
		  	
		 	function Column(userId,schema, table, name, dataType, length, database, t_schema, t_table, t_name, t_dataType, t_length, t_database) {
				this.userId=userId;
		 		this.schema = schema;
				this.table = table;
				this.name = name;
				this.dataType = dataType;
				this.length = length;
				this.database = database;
				
				this.t_schema = t_schema;
				this.t_table = t_table;
				this.t_name = t_name;
				this.t_dataType = t_dataType;
				this.t_length = t_length;
				this.t_database = t_database;
			}
			
		 	function loadFields(){
		        var sourceDatabase = $('#sourceDatabase').val();
		        var schema = $('#schema').val();
		        var table = $('#table').val();
		        
		        var targetDatabase = $('#targetDatabase').val();
		        var targetSchema = $('#targetSchema').val();
		        var targetTable = $('#targetTable').val();
		        
		        if(sourceDatabase == ''){
		        	alert('Select Source database');
		        	$('#sourceDatabase').focus();
		        	return false;
		        } else if(schema == ''){
		        	alert('Select Source schema');
		        	$('#schema').focus();
		        	return false;
		        }else if(table == ''){
		        	alert('Select Source table');
		        	$('#table').focus();
		        	return false;
		        } else if(targetDatabase == ''){
		        	alert('Select Target database');
		        	$('#targetDatabase').focus();
		        	return false;
		        } else if(targetSchema == ''){
		        	alert('Select Target schema');
		        	$('#targetSchema').focus();
		        	return false;
		        }else if(targetTable == ''){
		        	alert('Select Target table');
		        	$('#targetTable').focus();
		        	return false;
		        }
		 		
		 		$('#mappingTable').show();
		 		$('#createMapping').show();
		 		$('#saveJob').show();
		 		
		 		var totalSourceRows = $('#mySoureTable tr').length;
		 		var totalDestinationRows = $('#myTargetTable tr').length;
				
				var total = totalSourceRows;
				while(total != totalDestinationRows){
					total -- ;
					document.getElementById("mySoureTable").deleteRow(total);
				}
		 		
		        document.getElementById('sourceDatabase').disabled = true;
		        document.getElementById('schema').disabled = true;
		        document.getElementById('table').disabled = true ;
		        
		        document.getElementById('targetDatabase').disabled = true;
		        document.getElementById('targetSchema').disabled = true;
		        document.getElementById('targetTable').disabled = true;
		 	}
		 	
		 	function resetFields(){
		 		$('#mappingTable').hide();
		 		$('#createMapping').hide();
		 		$('#saveJob').hide();
		 		
		 		$("#mySoureTable").empty();
		        $('#myTargetTable').empty();
		        
		 		$('#targetSchema').val('');
			    $('#targetTable').val('');
				$('#schema').val('');
			    $('#table').val('');
		
		        document.getElementById('table').disabled = true 
		        document.getElementById('targetTable').disabled = true
		        
		        //document.getElementById('sourceDatabase').disabled = false;
		        document.getElementById('schema').disabled = false;
		        
		       // document.getElementById('targetDatabase').disabled = false;
		        document.getElementById('targetSchema').disabled = false;
		        
		 	}
		 	
		 	function createMapping(){
		 		var totalSourceRows = $('#mySoureTable tr').length;
		 		var totalDestinationRows = $('#myTargetTable tr').length;
				var arr = [];
				var userId=<%=userId%>;
		 		$('#myTargetTable tr').each(function(row) {
		 		    var rowTRID = $(this).closest('tr').attr('id')
		 		    var rowSplit = rowTRID.split('::');
		 		    
		 		    var sourceID = rowSplit[0]+'_sname';
		 		    var destinationID = rowSplit[0]+'_tname';
		 		   
		 		    var sorValue = $('#'+sourceID).find(":selected").val();
		 		    var tarValue = $('#'+destinationID).find(":selected").val();
		 		  
		 		    var destinationSchema = $('#targetSchema').find(":selected").val();
				    var destinationTable = $('#targetTable').find(":selected").val();
				    var destinationName = tarValue;
				    var destinationType = $('#id_'+row+'_tdataType').val();
				    var destinationLength = $('#id_'+row+'_tlength').val();
		 		   	
		 		    var sourceSchema = $('#schema').find(":selected").val();
		 		    var sourceTable = $('#table').find(":selected").val();
		 		    var sourceName = sorValue;
		 		    var sourceType =  $('#id_'+row+'_sdataType').val();
				    var sourceLength = $('#id_'+row+'_slength').val();
				    
		 			var data = new Column(userId,sourceSchema,sourceTable,sourceName,sourceType,sourceLength, '', destinationSchema,destinationTable,destinationName,destinationType,destinationLength, '');
		 			arr.push(data);
		 		});
		
		 		console.log(arr);
		 		
		 		$.ajaxSetup({
		 			async : false
		 		});
		 		
		 		$.ajax({
		 			url: "${pageContext.request.contextPath}/createMapping",	   
		 			type : "POST",
		 			contentType: "application/json",
		    		data: JSON.stringify(arr),
		 			success : function(result) {
						//handle api output
					}
					
				});
		 		$.ajaxSetup({
		 			async : true
		 		});
		 		
		 		
			}
		
			function saveJob() {
		        var totalSourceRows = $('#mySoureTable tr').length;
		        var totalDestinationRows = $('#myTargetTable tr').length;
		        var arr = [];
				var userId=<%=userId%>;
		       
		        $('#myTargetTable tr').each(function(row) {
		            var rowTRID = $(this).closest('tr').attr('id')
		            var rowSplit = rowTRID.split('::');
		
		            var sourceID = rowSplit[0]+'_sname';
		            var destinationID = rowSplit[0]+'_tname';
		
		            var sorValue = $('#'+sourceID).find(":selected").val();
		            var tarValue = $('#'+destinationID).find(":selected").val();

		            var destinationDatabaseId = $('#targetDatabase').val();
		            var destinationSchema = $('#targetSchema').find(":selected").val();
		            var destinationTable = $('#targetTable').find(":selected").val();
		            var destinationName = tarValue;
		            var destinationType = $('#id_'+row+'_tdataType').val();
		            var destinationLength = $('#id_'+row+'_tlength').val();

		            var sourceDatabaseId = $('#sourceDatabase').val();
		            var sourceSchema = $('#schema').find(":selected").val();
		            var sourceTable = $('#table').find(":selected").val();
		            var sourceName = sorValue;
		            var sourceType =  $('#id_'+row+'_sdataType').val();
		            var sourceLength = $('#id_'+row+'_slength').val();
					
		            var data = new Column(userId,sourceSchema,sourceTable,sourceName,sourceType,sourceLength, sourceDatabaseId, destinationSchema,destinationTable,destinationName,destinationType,destinationLength, destinationDatabaseId);
		            arr.push(data);
		        });
		
		        console.log(arr);
		
		        $.ajaxSetup({
		            async : false
		        });
		
		        $.ajax({
		            url: "${pageContext.request.contextPath}/saveDataTransferJob",
		            type : "POST",
		            contentType: "application/json",
		            data: JSON.stringify(arr),
		            success : function(result) {
		                //handle api output
		                alert(result);
		                parent.location.href="${pageContext.request.contextPath}/getJobs";
		            }
		
		        });
		        $.ajaxSetup({
		            async : true
		        });
		    }
		
		 	function testSourceConnection(){
		 		$('#loadingModal').modal('show');
		 		setTimeout(function(){
		 			sourceConnection();
		 		}, 1000);
		 	}
		 	
		 	function sourceConnection(){
		 		var sourceDatabase = $('#sourceDatabase').val();
		
		 		if(sourceDatabase ==''){
		 			alert('Select Source database');
		        	$('#sourceDatabase').focus();
		        	return false;
		 		}
		 		
		 		var schemaData = $.ajax({
		 		    type: 'GET',       
		 		    url: "${pageContext.request.contextPath}/getSourceFields/"+sourceDatabase,	    
		 		    dataType: 'json',
		 		    context: document.body,
		 		    global: false,
		 		    async:false,
		 		    success: function(data) {
		 		    	$('#loadingModal').modal('hide');
		 		    	if(data==''){
		 		    	alert('Unable to Connect Source DB.');	
		 		    	}else{
		 		    	alert('Source connection successfull');
		 		        $('#targetDatabase').find('option[value='+sourceDatabase+']').remove();

		 		    	document.getElementById('sourceDatabase').disabled = true;
		 		    	}
		 		       	
		 		        return data;
		 		    },
					error : function(xhr, ajaxOptions, thrownError) {
						$('#loadingModal').modal('hide');
						alert('Unable to Connect Source DB.');
					}
		 		}).responseText;
		 		
		 		document.getElementById('schema').disabled = false;
		 		
		 		$('#schema').empty();
		 		
		 		const selectSchema = document.getElementById('schema'),
		 	        selectTable = document.getElementById('table'),
		 	        
		 	        selects = document.querySelectorAll('select')
		
		 	        selectTable.disabled = true        
		
		 	        selects.forEach(select => {
		 	            if(select.disabled == true){
		 	                select.style.cursor = "auto"
		 	            }
		 	            else{
		 	                select.style.cursor = "pointer"
		 	            }
		 	        })
		 	        
		 	        schemaData = JSON.parse(schemaData);
		 			selectSchema.options[0] = new Option("-- Select Table --", "");
		 			schemaData.forEach(function(element){		    
		 			    selectSchema.options[selectSchema.options.length] = new Option(element.schemaName, element.schemaName);
		 			});
		 	        
		 	       selectSchema.onchange = (e) =>{            
		 	            selectTable.disabled = false;
		 	            $('#table').empty();
		 	            selectTable.options[0] = new Option("-- Select Table --", "");
		 	            schemaData.forEach(function(element){	    
		 			    var tableNames = element.tables;		    
		 			    tableNames.forEach(function(element){		    	
		 			    	selectTable.options[selectTable.options.length] = new Option(element.name, element.name);
		 			    });		    
		 			    
		 			});       
		 	       }
		 	       
		 	       selectTable.onchange = (e) =>{ 
		 	    	$("#mySoureTable").empty();
		 	        console.log("element = "+e.target.value);                
		 	            schemaData.forEach(function(element){	    
		 			    var tableNames = element.tables;
		 			    //console.log("tableNames = "+tableNames);		    
		 			    tableNames.forEach(function(element){
		 			    	//console.log("table name = "+element.name);
		 			    	if(e.target.value == element.name){
		 				    	var tableColumns = element.columns;
		 				    	var index = 0;
		 				    	var columns = [];
		 				    	tableColumns.forEach(function(element){
		 				    		console.log("column name = "+element.name);
		 				    		var table = document.getElementById("mySoureTable");
		 				    		var row = table.insertRow(-1);
		 				    		var cell = row.insertCell(0);
		 				    		
		 				    		row.id = 'id_'+index+'::s';
		 				    		cell.className="sourceCell_"+index;
		 				    		
		 				    		var content = '<select id="id_'+index+'_sname" class="sourceTableFields form-control"><option value="">-- Select Field --</option></select>';
		 				    		content = content +'<input type="hidden" id="id_'+index+'_sdataType" value="'+element.dataType+'">';
		 				    		content = content +'<input type="hidden" id="id_'+index+'_slength"  value="'+element.length+'">';
		 				    		$(".sourceCell_"+index).html(content);
		 				    		columns.push(element.name);
		 				    		index ++;
		 				    	});
		 				    	
		 				    	columns.forEach(function(element){	
		 				    		$('.sourceTableFields').append(new Option(element, element))
		 				    	});
		 			    	}
		 			    });		    
		 			    
		 			});       
		 	       }
		 		
		 		//$('#errorSourceBody').empty();
		 		//$('#errorSourceBody').append('<div class="alert alert-danger" role="alert">Unable to connect to source database.</div>');
		 	}
		 	function testDestinationConnection(){
		 		$('#loadingModal').modal('show');
		 		setTimeout(function(){
		 			targetConnection();
		 		}, 1000);
		 	}
		
		 	
		 	function targetConnection(){
		        document.getElementById('targetSchema').disabled = false;
		        
		        var targetDatabase = $('#targetDatabase').val();
		
		 		if(targetDatabase ==''){
		 			alert('Select Target database');
		        	$('#targetDatabase').focus();
		        	return false;
		 		}
		        
		        var schemaTargetData = $.ajax({
		 		    type: 'GET',       
		 		    url: "${pageContext.request.contextPath}/getTargetFields/"+targetDatabase,	    
		 		    dataType: 'json',
		 		    context: document.body,
		 		    global: false,
		 		    async:false,
		 		    success: function(data) {
		 		    	$('#loadingModal').modal('hide');
		 		    	if(data==''){
			 		    	alert('Unable to Connect Source DB.');	
			 		    }else{
		 		    	alert('Target Connection successfull');
			 		    }
		 		        document.getElementById('targetDatabase').disabled = true;
		 		        return data;
		 		    },
					error : function(xhr, ajaxOptions, thrownError) {
						$('#loadingModal').modal('hide');
						alert('Unable to Connect Target DB.');
					}
		 		}).responseText;
		 		
		        $('#targetSchema').empty();
		        
		 		//console.log(schemaTargetData);
		 		const selectTargetSchema = document.getElementById('targetSchema'),
		 	        selectTargetTable = document.getElementById('targetTable'),
		 	        
		 	        selects = document.querySelectorAll('select')
		
		 	        selectTargetTable.disabled = true        
		
		 	        selects.forEach(select => {
		 	            if(select.disabled == true){
		 	                select.style.cursor = "auto"
		 	            }
		 	            else{
		 	                select.style.cursor = "pointer"
		 	            }
		 	        })
		 	        
		 	        schemaTargetData = JSON.parse(schemaTargetData);
		 			selectTargetSchema.options[0] = new Option("-- Select Table --", "");
		 			schemaTargetData.forEach(function(element){		    
		 			    selectTargetSchema.options[selectTargetSchema.options.length] = new Option(element.schemaName, element.schemaName);
		 			});
		 	        
		 	       selectTargetSchema.onchange = (e) =>{   
		 	    	   
		 	            selectTargetTable.disabled = false;
		 	            $('#targetTable').empty();
		 	            selectTargetTable.options[0] = new Option("-- Select Table --", "");
		 	            
		 	            schemaTargetData.forEach(function(element){	    
		 			    var tableNames = element.tables;		    
		 			    tableNames.forEach(function(element){		    	
		 			    	selectTargetTable.options[selectTargetTable.options.length] = new Option(element.name, element.name);
		 			    });		    
		 			    
		 			});       
		 	       }
		 	       
		 	       selectTargetTable.onchange = (e) =>{       
		 	    	  $("#myTargetTable").empty();
		 	        console.log("element = "+e.target.value);                
		 	            schemaTargetData.forEach(function(element){	    
		 			    var tableNames = element.tables;
		 			    //console.log("tableNames = "+tableNames);		    
		 			    tableNames.forEach(function(element){
		 			    	//console.log("table name = "+element);
		 			    	if(e.target.value == element.name){
		 				    	var tableColumns = element.columns;
		 				    	var columns = [];
		 				    	var index = 0 ;
		 				    	tableColumns.forEach(function(element){
		 				    	   console.log("column name = "+element.name);
		 				    		var table = document.getElementById("myTargetTable");
		 				    		var row = table.insertRow(-1);
		 				    		var cell = row.insertCell(0);
		 				    		//cell.innerText = element.name; 
		 				    		//$("#targetColumn").text("Was empty");
		 				    		//cell.innerText = element.name;
		 				    		row.id = 'id_'+index+"::t";
		 				    		cell.className="ttthirdRowCell_"+index
		 				    		
		 				    		var content = '<select id="id_'+index+'_tname" class="targetTableFields form-control"><option value="">-- Select Field --</option></select>';
		 				    		content = content +'<input type="hidden" id="id_'+index+'_tdataType" value="'+element.dataType+'">';
		 				    		content = content +'<input type="hidden" id="id_'+index+'_tlength"  value="'+element.length+'">';
		 				    		$(".ttthirdRowCell_"+index).html(content);
		 				    		columns.push(element.name);
		 				    		index++;
		 				    	});
		 			    		
		 				    	columns.forEach(function(element){	
		 				    		$('.targetTableFields').append(new Option(element, element))
		 				    	});	
		 			    	}
		 			    });		    
		 			});       
		 	       } 		
		 		//$('#errorDestinationBody').empty();
		 		//$('#errorDestinationBody').append('<div class="alert alert-danger" role="alert">Unable to connect to destination database.</div>');
		 		
		 	}
		  	</script>
	</body>
</html>
