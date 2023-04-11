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

<style>
body {
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
}

.topnav {
  overflow: hidden;
  background-color: #010860;
}

.topnav a {
  float: left;
  color: #f2f2f2;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  font-size: 17px;
}

.topnav a:hover {
  background-color: #ddd;
  color: blue;
}

.topnav a.active {
  background-color: #04AA6D;
  color: white;
}

.table {
		    width: 65%;
		    max-width: 65%;
		    margin-bottom: 1rem;
		    background-color: transparent;
		}
.float-container {
    border: 3px solid #fff;
    padding: 20px;
}

.float-child {
    width: 50%;
    float: left;
    padding: 20px;
    border: 2px solid red;
}  		
</style>
</head>
<body>
<div class="topnav">
<a href="#" class="logo">Smart Data Connector</a>
<div  align="right">
  <a class="active"  href="#home">Home</a>
  <a href="#news">Jobs</a>
</div>
</div>

<form:form  style="width: 620px;"  name="homeform" modelAttribute="schemaForm" action="#" method="post">

  <%--   <table class="table table-bordered table-hover" id="myTable" >
  <thead>
    <tr>      
      <th scope="col">Source</th>
      <th scope="col">Destination</th>      
    </tr>
  </thead>
  <tbody>
    <tr>      
      <td>Please select Schema:
      		<select  name="sourceSchema" id="schema">
                <option value="">-- Select Schema --</option>
            </select>
           
      </td>
      <td>Please select Schema:
      		<select name="targetSchema" id="targetSchema">
                <option value="">-- Select Schema --</option>
            </select>
      </td>      
    </tr>
    <tr>      
      <td>Please select Table:
      		<select name="" id="table">
                <option value="">-- Select Table --</option>
            </select>
      </td>
      <td>Please select Table:
      		<select name="" id="targetTable">
                <option value="">-- Select Table --</option>
            </select>
      </td>      
    </tr>      
     
     <tr id="targetColumn">      
      <td id="sourceColumn">Please select Column:
      		Source Column  ${schemas}
      </td>
      <td id="targetColumntd">Please select Column:
      		Target Column
      </td>      
    </tr>    
     
  </tbody>
  </table> --%>
  <div class="float-container">
  <div class="float-child" align="right">
    <!-- <div class="green">Float Column 1</div> -->
      <table class="table table-bordered table-hover" id="myTable" >
  <thead>
    <tr>      
      <th scope="col">Source</th>
      <!-- <th scope="col">Destination</th>  -->     
    </tr>
  </thead>
  <tbody>
    <tr>      
      <td>Please select Schema:
      		<select  name="sourceSchema" id="schema">
                <option value="">-- Select Schema --</option>
            </select>
           
      </td>
      <!-- <td>Please select Schema:
      		<select name="targetSchema" id="targetSchema">
                <option value="">-- Select Schema --</option>
            </select>
      </td> -->      
    </tr>
    <tr>      
      <td>Please select Table:
      		<select name="" id="table">
                <option value="">-- Select Table --</option>
            </select>
      </td>
    <!--   <td>Please select Table:
      		<select name="" id="targetTable">
                <option value="">-- Select Table --</option>
            </select>
      </td> -->      
    </tr>      
     
     <tr id="targetColumn">      
      <td id="sourceColumn"><b> Please select Source Column</b>
      </td>
      <!-- <td id="targetColumntd">Please select Column:
      		Target Column
      </td>  -->     
    </tr>    
     
  </tbody>
  </table>
  </div>
  <div class="float-child">
    <!-- <div class="blue">Float Column 2</div> -->
      <table class="table table-bordered table-hover" id="myTargetTable" >
  <thead>
    <tr>      
      <!-- <th scope="col">Source</th> -->
      <th scope="col">Destination</th>      
    </tr>
  </thead>
  <tbody>
    <tr>      
     <%--  <td>Please select Schema:
      		<select  name="sourceSchema" id="schema">
                <option  value="">select</option>
                <logic:forEach items="${schemas}" var="schema">
                <option value="${schema.schemaName}">${schema.schemaName}</option>
                </logic:forEach>
      
            </select>
           
      </td> --%>
      <td>Please select Schema:
      		<select name="targetSchema" id="targetSchema">
                <option value="">-- Select Schema --</option>
            </select>
      </td>      
    </tr>
    <tr>      
      <!-- <td>Please select Table:
      		<select name="" id="table">
                <option value="">-- Select Table --</option>
            </select>
      </td> -->
      <td>Please select Table:
      		<select name="" id="targetTable">
                <option value="">-- Select Table --</option>
            </select>
      </td>      
    </tr>      
     
     <tr id="targetColumn">      
      <%-- <td id="sourceColumn">Please select Column:
      		Source Column  ${schemas}
      </td> --%>
      <td id="targetColumntd"><b> Please select Target Column</b>
      </td>      
    </tr>    
     
  </tbody>
  </table>
  </div>
  </div>
  <button type="button" class="btn btn-primary" style="margin-left:750px;" onclick="submitData()">Primary</button>
</form:form>

 <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
  
  	<script>
  	
	
	 $(document).ready(function(){
		var schemaData = $.ajax({
	    type: 'GET',       
	    url: "http://localhost:8080/smartdata/getSourceFields/sql",	    
	    dataType: 'json',
	    context: document.body,
	    global: false,
	    async:false,
	    success: function(data) {
	        return data;
	    }
	}).responseText;
	
	console.log(schemaData);
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
		schemaData.forEach(function(element){		    
		    selectSchema.options[selectSchema.options.length] = new Option(element.schemaName, element.schemaName);
		});
        
       selectSchema.onchange = (e) =>{            
            selectTable.disabled = false;
            
            schemaData.forEach(function(element){	    
		    var tableNames = element.tables;		    
		    tableNames.forEach(function(element){		    	
		    	selectTable.options[selectTable.options.length] = new Option(element.name, element.name);
		    });		    
		    
		});       
       }
       
       selectTable.onchange = (e) =>{       
        console.log("element = "+e.target.value);                
            schemaData.forEach(function(element){	    
		    var tableNames = element.tables;
		    //console.log("tableNames = "+tableNames);		    
		    tableNames.forEach(function(element){
		    	//console.log("table name = "+element.name);
		    	if(e.target.value == element.name){
			    	var tableColumns = element.columns;
			    	
			    	tableColumns.forEach(function(element){
			    		console.log("column name = "+element.name);
			    		var table = document.getElementById("myTable");
			    		var row = table.insertRow(-1);
			    		row.id = 'id_'+element.name;
			    		var cell = row.insertCell(0);
			    		
			    		//$("td:sourceColumn").append("stuff you want to append");
			    		//$("#sourceColumn").text("Was empty");
						//var cell1 = row.insertCell(1);
			    		cell.innerText = element.name;
			    		//cell1.className="thirdRowCell";
			    	});
		    	}
		    });		    
		    
		});       
       }
       
        
	
	});
	
	//Target details
	
 	$(document).ready(function(){
		var schemaTargetData = $.ajax({
	    type: 'GET',       
	    url: "http://localhost:8080/smartdata/getTargetFields/sql",	    
	    dataType: 'json',
	    context: document.body,
	    global: false,
	    async:false,
	    success: function(data) {
	        return data;
	    }
	}).responseText;
	
	console.log(schemaTargetData);
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
		schemaTargetData.forEach(function(element){		    
		    selectTargetSchema.options[selectTargetSchema.options.length] = new Option(element.schemaName, element.schemaName);
		});
        
       selectTargetSchema.onchange = (e) =>{            
            selectTargetTable.disabled = false;
            
            schemaTargetData.forEach(function(element){	    
		    var tableNames = element.tables;		    
		    tableNames.forEach(function(element){		    	
		    	selectTargetTable.options[selectTargetTable.options.length] = new Option(element.name, element.name);
		    });		    
		    
		});       
       }
       
       selectTargetTable.onchange = (e) =>{       
        console.log("element = "+e.target.value);                
            schemaTargetData.forEach(function(element){	    
		    var tableNames = element.tables;
		    //console.log("tableNames = "+tableNames);		    
		    tableNames.forEach(function(element){
		    	//console.log("table name = "+element.name);
		    	if(e.target.value == element.name){
			    	var tableColumns = element.columns;
			    	var columns = [];
			    	tableColumns.forEach(function(element){
			    	   console.log("column name = "+element.name);
			    		var table = document.getElementById("myTargetTable");
			    		var row = table.insertRow(-1);
			    		var cell = row.insertCell(0);
			    		//cell.innerText = element.name; 
			    		//$("#targetColumn").text("Was empty");
			    		//cell.innerText = element.name;
			    		row.id = 'id_'+element.name;
			    		cell.id = 'td_'+element.name;
			    		cell.className="ttthirdRowCell";
			    		$(".ttthirdRowCell").html('<select name="'+element.name+'" class="targetTableFields"><option value="">-- Select Field --</option></select>');
			    		columns.push(element.name);	
			    		
			    	});
			    	
			    	
		    		
			    	//var targetColumns = document.getElementByClassName('targetTableFields');
			    	columns.forEach(function(element){	
			    		//console.log("column = "+element);
			    		//$(".targetTableFields").options[$(".targetTableFields").options.length] = new Option(element, element);
			    		
			    		$('.targetTableFields').append(new Option(element, element))
			    	});	
			    	// Remove the third row
			    	$('td#td_LAST_NAME').find('select').find('option[value]').remove();

			       // $('td#td_LAST_NAME').remove();
		    	}
		    });		    
		    
		});       
       }
       
	});
 
 	function submitData(){
		console.log("calling function..."+$("#myTargetTable").find("tr"));
		
		var $row = $(this).closest('table').children('tr:first');
		$tds = $row.find("td");             
		//console.log("calling function..."+$row.find("td").value);
		$.each($tds, function() {              
		    console.log($(this).text());        
		});
		
		/* var $row = $('#myTable').closest("tr"),       // Finds the closest row <tr> 
	    $tds = $row.find("td");             // Finds all children <td> elements

		$.each($tds, function() {               // Visits every single <td> element
		    console.log($(this).text());        // Prints out the text within the <td>
		}); */
	}

  	</script>
</body>
</html>
