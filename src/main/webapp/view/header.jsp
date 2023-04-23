<%
	String pageName = request.getParameter("page");
	String userName = (String)session.getAttribute("userName");
%>
<nav class="navbar navbar-expand-lg navbar-dark bg-darknavbar navbar-expand-lg navbar-dark topnav">
  <a class="navbar-brand" href="#">Smart Data Connector</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNav">
    <ul class="navbar-nav">
      <li class="nav-item <%if(pageName.equalsIgnoreCase("home")){out.print("active");} %>">
        <a class="nav-link <%if(pageName.equalsIgnoreCase("home")){out.print("active");} %>" href="${pageContext.request.contextPath}/home"><i class="fa fa-home"></i> Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item <%if(pageName.equalsIgnoreCase("jobs")){out.print("active");} %>">
        <a class="nav-link <%if(pageName.equalsIgnoreCase("jobs")){out.print("active");} %>" href="${pageContext.request.contextPath}/getJobs"><i class="fa fa-briefcase"></i> Jobs</a>
      </li>
      <li class="nav-item  <%if(pageName.equalsIgnoreCase("config")){out.print("active");} %>">
        <a class="nav-link <%if(pageName.equalsIgnoreCase("config")){out.print("active");} %>" href="${pageContext.request.contextPath}/getDbConnections"><i class="fa fa-database"></i> Database</a>
      </li>
      <li class="nav-item dropdown">
	      <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
	        <i class="fa fa-user"></i> <%=userName.toUpperCase() %>
	      </a>
	      <div class="dropdown-menu" style="background: #4b52a4;">
	        <a class="nav-link" href="${pageContext.request.contextPath}/logout"><i class="fa fa-sign-out"></i> Logout</a>
	      </div>
	    </li>
    </ul>
  </div>
</nav>
<br>