<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>UserPage</title>
</head>
<body>

<h1> User Login</h1>
 <form action="LoginServlet" method="post">
  Username: <input type="text" name="username">
  <br> <br>

  Password: <input type="password" name="password"><br>

  <br>
  <input type="submit" value="Log In">
   <input type="reset" value="Reset">
 </form>
 <c:out value="${MessageOp}"/>
</body>
</html>