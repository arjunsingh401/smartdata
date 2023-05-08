<%@page import="com.sdc.BatchStatus"%>
<%@page import="com.sdc.model.Jobs"%>
<%@page import="com.sdc.model.DbConnection"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="logic"%> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%
	List jobs = (List)request.getAttribute("jobs");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Jobs List</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
		<link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	</head>
	<body>
		<jsp:include page="header.jsp">
			<jsp:param value="jobs" name="page"/>
		</jsp:include>
		
		<div class = "container-fluid">
	         <nav aria-label = "breadcrumb">
	            <ul class = "breadcrumb">
	               <li class = "breadcrumb-item"><a href = "${pageContext.request.contextPath}/home"><i class="fa fa-home"></i> Home</a></li>
	               <li class = "breadcrumb-item active" aria-current = "page"><i class="fa fa-list"></i> Jobs</li>
	            </ul>
	         </nav>
	    </div>
		<div class="container-fluid">
			<div class="row">
				<div class="col-lg-12">
				    <form action="">
                        <table id="dataTable" class="table" cellspacing="0">
                           <thead>
                               <tr>
                                   <th>Job Id</th>
                                   <th>Description</th>
                                   <th>Total Records</th>
                                   <th>Pending Records</th>
                                   <th>Failed Records</th>
                                   <th>Last Updated</th>
                                   <th>Status</th>
                                   <th>Action</th>
                               </tr>
                           </thead>
                           <tbody id ="body">
                                <%
                                    for(int a=0;a<jobs.size();a++){
                                    Jobs job = (Jobs)jobs.get(a);
                                    String isDisabled = job.getStatus().equalsIgnoreCase("RUNNING")
                                                        || job.getStatus().equalsIgnoreCase("COMPLETED")
                                                        || job.getStatus().equalsIgnoreCase("TERMINATED") ? "disabled" : "";
                                    String isVisible = job.getFailedRecords() <= 0 ? "hidden" : "";
                                 %>
                                <tr>
                                   <td  width="5%"><%=job.getId()%></td>
                                   <td  width="25%"><%=job.getDescription()%></td>
                                   <td  width="10%"><%=job.getTotalRows()%></td>
                                   <td  width="10%"><%=job.getPendingRows()%></td>
                                   <td  width="10%"><%=job.getFailedRecords()%>&nbsp;<a href="${pageContext.request.contextPath}/getErrorFile/<%=job.getErrorFileName()%>" <%=isVisible%>>(View Errors)</a></td>
                                   <td  width="10%"><%=job.getUpdated()%></td>
                                   <td  width="10%"><%=job.getStatus()%></td>
                                   <td  width="10%">
                                   <%if(job.getStatus().equalsIgnoreCase(BatchStatus.CREATED.toString())){ %>
                                        <button type="button" class="btn btn-success" <%=isDisabled%> data-toggle="tooltip" data-placement="bottom" title="Run Job" onclick="runJob(<%=job.getId()%>)"><i class="fas fa-play"></i></button>
                                    <%}if(job.getStatus().equalsIgnoreCase(BatchStatus.RUNNING.toString())){ %>
                                        <button type="button" class="btn btn-danger" data-toggle="tooltip" data-placement="bottom" title="Terminate Job" onclick="stopJob(<%=job.getId()%>)"><i class="fas fa-stop-circle"></i></button>
                                     <%} %>
                                   </td>
                               </tr>
                                <%  } %>
                           </tbody>
                       </table>
                    </form>
		          </div>
	         </div>
		</div>
		
		<!-- Optional JavaScript -->
	    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
	    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

	    <script>
	        function stopJob(jobId) {
	            $.ajax({
	                type: 'POST',
                    url: "${pageContext.request.contextPath}/stop/" + jobId,
                    success: function(data) {
                        alert('Job terminated successfully!!');
                        console.log(data);
                        $('#body').empty();
                        $.each(data, function (index, value) {
                        var isDisabled = '';
                        if (value.status === 'RUNNING' || value.status === 'COMPLETED' || value.status === 'TERMINATED') {
                            isDisabled = 'disabled';
                        }
                        var tr = '<tr>'
                                + '<td  width="5%">' + value.id + '</td>'
                                + '<td  width="25%">' + value.description + '</td>'
                                + '<td  width="10%">' + value.totalRows + '</td>'
                                + '<td  width="10%">' + value.pendingRows + '</td>'
                                + '<td  width="10%">' + value.failedRecords + '&nbsp;<a href="">(View Errors)</a></td>'
                                + '<td  width="10%">' + value.updated + '</td>'
                                + '<td  width="10%">' + value.status + '</td>'
                                + '<td  width="10%">'
                                + '<button type="button" class="btn btn-success" ' + isDisabled + ' data-toggle="tooltip" data-placement="bottom" title="Run Job" onclick="runJob(' + value.id +')"><i class="fas fa-play"></i></button>'
                                + '<button type="button" class="btn btn-danger" data-toggle="tooltip" data-placement="bottom" title="Terminate Job" onclick="stopJob(' + value.id +')"><i class="fas fa-stop-circle"></i></button>'
                                + '</td>'
                                + '</tr>'
                            $('#body').append(tr);
                        });

                    },
                    error : function(xhr, ajaxOptions, thrownError) {
                        alert('Unable to terminate the job.');
                    }
                });
            }

            function runJob(jobId) {
                $.ajax({
                    type: 'POST',
                    url: "${pageContext.request.contextPath}/startDataTransfer/" + jobId,
                    success: function(data) {
                        alert('Data transfered successfully!!');
                        parent.location.href="${pageContext.request.contextPath}/getJobs";
                        /* $('#body').empty();
                        $.each(data, function (index, value) {
                        var isDisabled = '';
                        if (value.status === 'RUNNING' || value.status === 'COMPLETED' || value.status === 'TERMINATED') {
                            isDisabled = 'disabled';
                        }
                        var tr = '<tr>'
                                + '<td  width="5%">' + value.id + '</td>'
                                + '<td  width="25%">' + value.description + '</td>'
                                + '<td  width="10%">' + value.totalRows + '</td>'
                                + '<td  width="10%">' + value.pendingRows + '</td>'
                                + '<td  width="10%">' + value.failedRecords + '&nbsp;<a href="">(View Errors)</a></td>'
                                + '<td  width="10%">' + value.updated + '</td>'
                                + '<td  width="10%">' + value.status + '</td>'
                                + '<td  width="10%">'
                                + '<button type="button" class="btn btn-success" ' + isDisabled + ' data-toggle="tooltip" data-placement="bottom" title="Run Job" onclick="runJob(' + value.id +')"><i class="fas fa-play"></i></button>'
                                + '<button type="button" class="btn btn-danger" data-toggle="tooltip" data-placement="bottom" title="Terminate Job" onclick="stopJob(' + value.id +')"><i class="fas fa-stop-circle"></i></button>'
                                + '</td>'
                                + '</tr>'
                            $('#body').append(tr);
                        }); */

                    },
                    error : function(xhr, ajaxOptions, thrownError) {
                        alert('Unable to terminate the job.');
                    }
                });
            }

	    </script>
	</body>
</html>