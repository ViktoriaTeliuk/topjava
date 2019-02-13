
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
    <title>Add/Edit new user</title>
</head>
<body>
<!-- jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>!-->
<form method="POST" action='meals' name="frmAddUser">
    <input type="hidden" readonly="readonly" name="id"
                     value="${meal.id}" /> <br />
    Calories : <input type="number" name="calories" required
        value="${meal.calories}" /> <br />
    Description : <input type="text" name="description" required
        value="${meal.description}" /> <br />
    Date & Time : <input type="datetime-local" name="dateTime"
        value="${meal.dateTime}" /> <br/>
    <br /> <input type="submit" value="Submit" />
</form>
</body>
</html>