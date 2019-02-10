
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
    <tr style="color: ${meal.excess? 'red' : 'green'}">
        <td><fmt:formatDate type = "both" value = "${localDateTimeFormat.parse(meal.dateTime)}"/> </td>
        <td>${meal.description}</td>
        <td>${meal.calories}</td>
    </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
