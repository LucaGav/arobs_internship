<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "com.arobs.model.Product" %>
<%@ page import ="com.arobs.model.User" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>GlobalProducts</title>
<style>
th, td {
  border: 1px solid black;
}
</style>
</head>
<body>
    <h2>List of available products:</h2>
    <table width="300">
    <tr>
        <th>Type</th>
        <th>Amount</th>
        <th>Choose amount</th>
    <c:forEach var="order" items="${listOfProducts}">
    <form action="UserServlet" method="POST">
        <tr>
            <td><c:out value="${order.product.type}"></c:out></td>
            <td><c:out value="${order.storageAmount}"></c:out></td>
            <td><input style = "border:none; outline:none;" type="text" name="amountI" size="9"></td>
            <td style='border-right:none;border-left:none;border-bottom:none;border-top:none'><input style = "background-color:white;" type="submit" value="Add Product"></td>
            <input type="hidden" name="ProductType" value="${order.product.type}">
        </tr>
        </form>
    </c:forEach>
    </table>