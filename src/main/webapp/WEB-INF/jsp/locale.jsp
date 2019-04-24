<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<li class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown">${pageContext.response.locale}</a>
    <div class="dropdown-menu">
        <a class="dropdown-item" href="${requestScope['javax.servlet.forward.request_uri']}?language=en">English</a>
        <a class="dropdown-item" href="${requestScope['javax.servlet.forward.request_uri']}?language=ru">Русский</a>
    </div>
</li>
</html>
