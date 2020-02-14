<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<style>
h1{font-size:28px;}
form {
  margin: 0 auto;
  display: table;
}

input[type=text]{
  display: table-cell;
  border: 1px solid #ccc;
}

input[type=password] {
  display: table-cell;
  border: 1px solid #ccc;
}


input[type=submit] {
  background-color: #4CAF50;
  color: white;
  padding: 14px 20px;
  display:table-cell;
  border: none;
  cursor: pointer;
  width: 50%;
}

input[type=reset] {
  background-color: #950115;
  color: white;
  padding: 14px 15px;
  border: none;
  display:table-cell;
  cursor: pointer;
  width: 50%;
}
label {
display: table-cell;
}
p { display: table-row;  }

</style>
</head>
<body>
 <form action="LoginServlet" method="post">
  <p>
  </p>
   <p>
  <label for="user">Username:</label>
  <input type="text" name="username">
  <br> <br>
   </p>
   <p>
  <label for="pass">Password:</label>
  <input id="pass" type="password" name="password"><br>
  </p>
  <br>
  <input type="submit" value="Log In">
  <input type="reset" value="Reset">
 </form>
 <c:out value="${MessageOp}"/>
</body>
</html>