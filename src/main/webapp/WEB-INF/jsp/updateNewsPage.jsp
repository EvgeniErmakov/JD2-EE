<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>UPDATE NEWS PAGE</title>
    <link rel="stylesheet" href="resources/css/css-style.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.name.site" var="name_site"/>
    <fmt:message bundle="${loc}" key="local.locbutton.name.exit" var="exit_button"/>
    <fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button"/>
    <fmt:message bundle="${loc}" key="local.text.hello" var="hello"/>
    <fmt:message bundle="${loc}" key="local.text.updateNews" var="update_news"/>
    <fmt:message bundle="${loc}" key="local.text.buttonUpdate" var="button_update"/>
</head>
<body>

<div class="heading">
    <h1><a href="Controller?command=AFTER_AUTHORIZATION" style="color : black;
			font-family:serif; font-size: 75px;margin: 50px;text-align: center;">
        <c:out value="${name_site}"/></a>
    </h1>
    <div class=heading-1>
        <div class="heading-2">
            <form action="Controller" method="post">
                <input type="hidden" name="local" value="ru"/>
                <input type="hidden" name="command" value="CHANGE_LOCAL"/>
                <input type="submit" class="button_local" value="${ru_button}"/><br/>
            </form>
            <form action="Controller" method="post">
                <input type="hidden" name="local" value="en"/>
                <input type="hidden" name="command" value="CHANGE_LOCAL"/>
                <input type="submit" class="button_local" value="${en_button}"/><br/>
            </form>
        </div>
        <div>
            <form class="heading-2" action="Controller" method="post">
                <input type="hidden" name="command" value="GO_TO_MAIN_PAGE"/>
                <input type="submit" class="button" value="${exit_button}"/>
            </form>
        </div>
    </div>
</div>

<div style="width: 50%; margin: 0 auto; text-align: center;">
    <h2 style="font-weight: 600;"><c:out value="${update_news}"/></h2>
    <form action="Controller" method="post">
        <textarea name="title" placeholder="title"
                  style="size: 80px; width: 350px;">${choosenNews.getTitle()}</textarea>
        <br/>
        <br/>
        <textarea name="description" placeholder="description"
                  style="size: 80px; width: 350px; height:200px">${choosenNews.getDescription()}</textarea>
        <br/>
        <br/>
        <input type="hidden" name="choosenId" value="${choosenNews.getId()}"/>
        <input type="hidden" name="command" value="UPDATE_NEWS"/>
        <button id="update-button" style="background-color: #408080; color: black;
   justify-content: center;    align-items: center;   margin-right: 20px;
    margin-left: 20px; font-size: 20px;   justify-content: space-between; width: 150px;height:50px"><c:out value="${button_update}"/></button>
    </form>
</div>
</body>
</html>