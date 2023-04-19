<%
	String pageName = request.getParameter("page");
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
        <a class="nav-link <%if(pageName.equalsIgnoreCase("jobs")){out.print("active");} %>" href="#jobs"><i class="fa fa-briefcase"></i> Jobs</a>
      </li>
      <li class="nav-item  <%if(pageName.equalsIgnoreCase("config")){out.print("active");} %>">
        <a class="nav-link <%if(pageName.equalsIgnoreCase("config")){out.print("active");} %>" href="${pageContext.request.contextPath}/getDbConnections"><i class="fa fa-database"></i> Database</a>
      </li>
    </ul>
  </div>
</nav>
<br>