
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<table>
    <tbody>
    <tr><th>Date/Time</th><th>Description</th><th>Calories</th></tr>
    <c:forEach items="${mealsTo}" var="meal" >
        <jsp:useBean id = "meal" scope="page" type="ru.javawebinar.topjava.model.MealTo"/>
    <tr style="color: ${meal.excess? 'red' : 'green'}">
        <td><fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
            <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}" /></td>
        <td>${meal.description}</td>
        <td>${meal.calories}</td>
        <td><a href="meals?action=edit&id=<c:out value="${meal.id}"/>">Update</a></td>
        <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>">Delete</a></td>
    </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="meals?action=insert">Add Meal</a></p>
</body>
</html>
