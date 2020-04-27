 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>BMS</title>
<style>
* {
  box-sizing: border-box;
}
.container {
  position: absolute;
  top : 200px;
  right: 350px;
  left:800px;
  margin: 20px;
  max-width: 400px;
  max-height:600px;
  padding: 16px;
  background-color: white;
}


body{

background-image:url("logh.png");
}

h2
{
color:green;
}
h1
{
color:red;}



 .error {color:red;}  
 
 h4
 {
 
 color:red;
 
 }
 
 
</style>
</head>
<body >
<div align="center"><h1>..........Welcome to Book My Show :) ...........</h1></div>



<div class="container">
<h2>Login</h2>
<!-- Internet Connection Check -->
<c:if test="${netcheck eq 'icheck'}">

<table>

<tr><td>Internet Not Connected,Please connect to Internet and try again !</td></tr>

<tr><td><a href="/logi">Login</a></td></tr>
</table>


</c:if>
<c:if test="${netcheck ne 'icheck'}">

<!-- UserId sent to mail-->

<c:if test="${env eq 'UserIdSent'}">

<table>
<tr><td>User Id Sent to your Mail Id .</td></tr>
<tr><td>Login Here -> <a href="/logi">Login</a></td></tr>

</table>

</c:if>
<c:if test="${env ne 'UserIdSent'}">
<!-- type mail id for user Id sending -->
<c:if test="${sv eq 'userId'}">

<form:form action="/useridemail"  modelAttribute="login">
<table>
<tr><td>Email</td><td><input type = "email" name="email" placeholder="Enter Email" required="required"/></td></tr>
<tr><td></td><td><font color="red">${notexist}</font><td></tr>

<tr><td></td><td><input type="submit"></td><td><button><a href="/createuser" style="color:black">SignUp</a></button></td></tr>
</table>
</form:form>

</c:if>
<c:if test="${sv ne 'userId'}">
<!-- password sent to mail - confirmation -->
<c:if test="${emailsent eq 'EmailSent'}">
<table>

<tr><td>Password has been sent to your Mail ID.</td></tr>

<tr><td>Login here -> <a href="/logi">login</a></td></tr>


</table>


</c:if>
<c:if test="${emailsent ne 'EmailSent'}">
<!-- Enter User name for identifying user-->
<c:if test= "${eql eq 'vale'}">
<form:form action="/sendmail"  modelAttribute="login">
<table>
<tr><td>UserName</td><td><form:input path="usName" name="uName" placeholder="username" required="required"/></td></tr>
<tr><td></td><td><font color="red">${notexist}</font><td></tr>

<tr><td></td><td><input type="submit"></td><td><button><a href="/createuser" style="color:black">SignUp</a></button></td></tr>
</table>
</form:form>
</c:if>
<!-- Login Page -->
<form:form action="/verify"  modelAttribute="login">
<c:if test= "${eql ne 'vale'}">
<table>
<tr><td>UserName</td><td><form:input path="usName" name="uName" placeholder="username"/></td><td></td></tr>
<tr><td></td><td><form:errors path="usName" cssClass="error"/><font color="red">${username}</font></td><td></td></tr>
<tr></tr>
<tr></tr>
<tr><td>Password</td><td><form:input path="passwrd" name="pass" type="password" placeholder="password"/></td><td></td></tr>
<tr><td></td><td><form:errors path="passwrd" cssClass="error"/><font color="red">${password}</font></td><td></td></tr>

<tr></tr>
<tr><td></td><td><input type ="submit"></td><td><button><a href="/createuser">SignUp</a></button></td></tr>
<tr><td></td><td><a href="/forgotpassword">Forgot password?</a></td><td><a href="/fogotuserid">Forgot UserId</a></td></tr>

</table>
</c:if>

</form:form></c:if></c:if></c:if></c:if>
</div>


</body>
</html>