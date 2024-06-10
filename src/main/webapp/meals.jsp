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
<jsp:useBean id="listMeals" scope="request" type="java.util.List"/>
<c:forEach var="meal" items="${listMeals}">
    <table>
        <c:choose>
            <c:when test="${meal.excess eq true}">
                <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-DD'T'HH:mm" var="date" type="both"/>
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${date}" var="parsedDate"/>
                <tr style="color: red">
                    <td>
                            ${parsedDate}
                            ${meal.description}
                            ${meal.calories}
                            ${meal.excess}
                    </td>
                </tr>
            </c:when>

            <c:otherwise>
                <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-DD'T'HH:mm" var="date" type="both"/>
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${date}" var="parsedDate"/>
                <tr style="color: green">
                    <td>
                            ${parsedDate}
                            ${meal.description}
                            ${meal.calories}
                            ${meal.excess}
                    </td>
                </tr>
            </c:otherwise>
        </c:choose>
    </table>
</c:forEach>
</body>
</html>
