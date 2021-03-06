<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
     <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>BMS</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<style type="text/css">

body{

background-image:url("mv.jpg")

}
td,tr,th,h3
{
color:black;
}

.errors{
color:red;
}

* {
  box-sizing: border-box;
}

.container {
  position: absolute;
  top : 200px;
  right: 350px;
  left:800px;
  margin: 20px;
  max-width: 300px;
  max-height:600px;
  padding: 16px;
  background-color: white;
}

.ali
{

top:30px;
right:10px;
left:1500px;
padding:16px;
position:absolute;

}


.navbar {
      margin-bottom: 0;
      border-radius: 0;
    }
    /* Set height of the grid so .sidenav can be 100% (adjust as needed) */
    .row.content {height: 450px}
    
    /* Set gray background color and 100% height */
    .sidenav {
      padding-top: 20px;
      background-color: #f1f1f1;
      height: 100%;
    }
    
    /* Set black background color, white text and some padding */
    footer {
      background-color: #555;
      color: white;
      padding: 15px;
    }
    
    /* On small screens, set height to 'auto' for sidenav and grid */
    @media screen and (max-width: 767px) {
      .sidenav {
        height: auto;
        padding: 15px;
      }
      .row.content {height:auto;} 
    }
</style>
</head>
<body>
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
     
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <c:if test="${role eq 'Admin'}">
 <li class="active"><a href="/admin">Home</a></li>
</c:if>


       
        
        <c:if test="${role eq 'user'}">
 <li class="active"><a href="/user">Home</a></li>
</c:if>
        <li><a href="#">About</a></li>
        
        <li><a href="#">Contact</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="/home"><span class="glyphicon glyphicon-log-in"></span> Logout</a></li>
      </ul>
    </div>
  </div>
</nav>
<div align="center">
<div class="container">
<h3>Add Movies</h3>
<form:form action="/savmovi" method="get" modelAttribute="movies">
<table>

<tr><td>Movie Name: </td>
<td><form:input path="mName"/></td></tr>
<tr><td></td><td><form:errors path="mName" cssClass="errors"/></td></tr>
<tr><td>Language:</td>
<td><form:input path="lang"/></td></tr>
<tr><td></td><td><form:errors path="lang" cssClass="errors"/></td></tr>
<tr><td>Duration</td>
<td><form:input path="duration"/></td></tr>
<tr><td></td><td><form:errors path="duration" cssClass="errors"/></td></tr>
<tr>
<td>Place</td>

<td><select name="pname">
<option>select place</option>
<c:forEach items="${pall}" var="pnam">
<option>${pnam.pName}</option>
</c:forEach>
</select> </td><%-- <td><form:errors path="place" cssClass="errors"/></td> --%>
</tr>
<tr></tr>
<tr><td></td><td><input type="submit" class="btn btn-success" value="Add Movies"></td></tr>





</table>


</form:form></div>
</div>

</body>
</html>