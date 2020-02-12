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
th, td {
  border: 1px solid black;
}
</style>
</head>
<body>

 <div style="float: right;">
    <br> <br>
     <h2>Your shopping cart:</h2>
         <table width="500">
         <tr>
             <th>Type</th>
             <th>Amount</th>
         <c:forEach var="order" items="${listOfCartProducts}">
             <tr>
                 <td><c:out value="${order.product.type}"></c:out></td>
                 <td><c:out value="${order.storageAmount}"></c:out></td>
                 <td td style='border-right:none;border-left:none;border-bottom:none;border-top:none'><a data-confirm="Are you sure?" href="${pageContext.request.contextPath }/CartServlet?action=delete&ProductType=${order.product.type}">Remove</a>
             </tr>
         </c:forEach>
         </table>
      </div>

    <%
    User user = (User) session.getAttribute("currentSessionUser");
     %>
    <h1>
    Welcome <%= user.getUsername() %>
    </h1>
    <jsp:include page = 'globalProducts.jsp'/>

     <br><br>
     <c:out value="${MessageOp}"/>




</body>
</html>