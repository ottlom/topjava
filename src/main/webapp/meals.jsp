<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<jsp:useBean id="listMeals" scope="request" type="java.util.concurrent.CopyOnWriteArrayList"/>
<h3><a href="addMeal.jsp?action=add?id">Add meal</a></h3>
<table border="1" cellspacing="0">
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="meal" items="${listMeals}">
            <c:choose>
                <c:when test="${meal.excess eq true}">
                    <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-DD'T'HH:mm" var="date" type="both"/>
                    <fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${date}" var="parsedDate"/>
                    <tr style="color: red">
                        <td>${parsedDate}</td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
                        <td><a href="addMeal.jsp">Update</a></td>
                        <td><a href="meals?id=${meal.id}&action=delete">Delete</a></td>
                    </tr>
                </c:when>

                <c:otherwise>
                    <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-DD'T'HH:mm" var="date" type="both"/>
                    <fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${date}" var="parsedDate"/>
                    <tr style="color: green">
                        <td>${parsedDate}</td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
                        <td><a href="addMeal.jsp">Update</a></td>
                        <td><a href="meals.jsp">Delete</a></td>
                    </tr>
                </c:otherwise>
            </c:choose>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
