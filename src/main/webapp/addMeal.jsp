<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>add</title>
    <jsp:useBean id="meal" class="ru.javawebinar.topjava.model.MealTo" scope="request"/>
</head>
<body>
<h1>Add meal</h1>
<form method="post" action="meals.jsp" enctype="application/x-www-form-urlencoded" accept-charset="UTF-8">
    Date: <input type="datetime-local" value="${meal.dateTime}"><br>
    Description: <input type="text" placeholder="Введите название пищи" value="${meal.description}"><br>
    Calories: <input type="number" placeholder="Введите количество каллорий" value="${meal.calories}">

    <button type="submit">Сохранить</button>
    <button type="reset" onclick="window.history.back()">Отменить</button>
</form>
</body>
</html>