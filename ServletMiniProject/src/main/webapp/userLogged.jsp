<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "com.arobs.model.Product" %>
<%@ page import ="com.arobs.model.User" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>UserPage</title>
<style>
table, th, td {
  border: 1px solid black;
}
</style>
</head>
<body>

<%
    User user = (User) session.getAttribute("currentSessionUser");
 %>
 <h1>
    Welcome <%= user.getUsername() %>
    </h1>
    <h2>List of available products</h2>
    <table width="500">
    <tr>
        <th>Type</th>
        <th>Amount</th>
    <c:forEach var="product" items="${listOfProducts}">
        <tr>
            <td><c:out value="${product.type}"></c:out></td>
            <td><c:out value="${product.storageAmount}"></c:out></td>
        </tr>
    </c:forEach>
    </table>
</body>
</html>