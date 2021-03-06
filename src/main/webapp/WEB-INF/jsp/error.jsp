<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>ErrorPage</title>
    <link rel="stylesheet" href="resources/css/css-style.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.name.site" var="name_site"/>
    <fmt:message bundle="${loc}" key="local.locbutton.name.ru"
                 var="ru_button"/>
    <fmt:message bundle="${loc}" key="local.locbutton.name.en"
                 var="en_button"/>
    <fmt:message bundle="${loc}" key="local.text.error" var="error"/>
    <fmt:message bundle="${loc}" key="local.text.error.message" var="error_message"/>
</head>
<body>

<div class="heading">
    <h1><a href="Controller?command=go_to_main_page" style="color : black;
			font-family:serif; font-size: 75px;margin: 50px;text-align: center;">
        <c:out value="${name_site}"/></a>
    </h1>
</div>

<h1 class="headline"><c:out value="${error}"/></h1>
<p class="text"><c:out value="${error_message}"/></p>
</body>
</html>