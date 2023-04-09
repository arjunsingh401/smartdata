<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="logic"%> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<!DOCTYPE html> 
<html lang="en"> 
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>SDC Login Page </title>
<style> 
Body {
  font-family: Calibri, Helvetica, sans-serif;
 /*  background-color: pink; */
}
button { 
       background-color: #4CAF50; 
       width: 100%;
        color: orange; 
        padding: 15px; 
        margin: 10px 0px; 
        border: none; 
        cursor: pointer; 
         } 
 form { 
        border: 3px solid #f1f1f1; 
    } 
 input[type=text], input[type=password] { 
        width: 100%; 
        margin: 8px 0;
        padding: 12px 20px; 
        display: inline-block; 
        border: 2px solid green; 
        box-sizing: border-box; 
    }
 button:hover { 
        opacity: 0.7; 
    } 
  .cancelbtn { 
        width: auto; 
        padding: 10px 18px;
        margin: 10px 5px;
    } 
      
   
 .container { 
        padding: 25px; 
        background-color: lightblue;
    } 
</style> 
</head>  

<script>

function validateForm(){
 		   	if(document.forms[0].vendorId.value.trim()==""){
 		      alert("Please enter vendor id");
 		      document.forms[0].vendorId.focus();
 	    	  return false;
 	    	}
 		   	if(document.forms[0].userName.value.trim()==""){
 		      alert("Please enter userName");
 		      document.forms[0].userName.focus();
 	    	  return false;
 		    } 
 			if(document.forms[0].password.value.trim()==""){
 	 		      alert("Please enter password");
 	 		      document.forms[0].password.focus();
 	 	    	  return false;
 	 		} 
         	document.forms[0].action="${pageContext.request.contextPath}/login";
 	   		document.forms[0].submit();
 	   		return true;

 }
</script>


<body >  <center>
     <h1> Smart Data Connector </h1> 
      <font color="red" >&nbsp;${loginForm.displayErrorMsg}</font><br> 
    <html:form  style="width: 620px;"  name="indexform"  modelAttribute="user" method="post">
	   
        <div class="container" align="center" >
       
            <label ><font size="5px;" > <b>Vendor Id  </b></font></label><br> 
            <input type="text" placeholder="Vendor Id" onkeypress='return event.charCode >= 48 && event.charCode <= 57' name="vendorId" style="width: 320px;"  required><br>
            <label ><font size="5px;"> <b>Username  </b></font></label><br> 
            <input type="text" placeholder="Enter Username" name="userName" style="width: 320px;"  required><br>
            <label ><font size="5px;"><b>Password </b></font> </label> <br>
            <input type="password" placeholder="Enter Password" name="password" style="width: 320px;" required>
            <button type="submit" style="width: 320px;" onclick="validateForm()"><font size="5px;"><b>Login</b></font></button> <br>
           <!--  <input type="checkbox" checked="checked"> Remember me 
            <button type="button" class="cancelbtn"> Cancel</button> 
            Forgot <a href="#"> password? </a>  -->
        </div> 
    </html:form>   
</body>
</center>   
</html>

 
