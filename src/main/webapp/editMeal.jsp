
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
    <title>Add/Edit new user</title>
</head>
<body>
<form method="POST" action='editMeal' name="frmAddUser">
    <input type="text" readonly="readonly" hidden="hidden" name="id"
                     value="${meal.id}" /> <br />
    Calories : <input type="number" name="calories"
        value="${meal.calories}" /> <br />
    Description : <input type="text" name="description"
        value="${meal.description}" /> <br />
    Date & Time : <input type="datetime-local" name="dateTime"
        value="${meal.dateTime}" /> <br/>
    <br /> <input type="submit" value="Submit" />
</form>
</body>
</html>