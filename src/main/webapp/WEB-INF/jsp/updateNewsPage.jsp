<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <title>UPDATE NEWS PAGE</title>
    <link rel="stylesheet" href="resources/css/css-style.css" type="text/css">
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="resources.localization.local" var="loc" />


    <fmt:message bundle="${loc}" key="local.locbutton.name.register" var="register_button" />

</head>
<body>
<div class="headline">

    <a href="Controller?commandToController=GO_TO_MAIN_PAGE">
        <h1 style="margin: 20px; background-color: #cd0000">
            <span>News </span>
        </h1>
    </a>

</div>
<div  style="width: 400px; height:510px">
    <h2 style="font-weight: 600;">Update New News</h2>
    <form action="Controller" method="post" enctype="multipart/form-data">
        <textarea name="title"  placeholder="title" style="size: 80px; width: 350px;" >${choosenNews.getTitle()}</textarea>
        <br />
        <br />
        <textarea name="full_text"  placeholder="full text" style="size: 80px; width: 350px; height:200px" >${choosenNews.getDescription()}</textarea>
        <br />
        <br />

        <input type="hidden" name="choosenId" value="${choosenNews.getId()}" />
        <input type="hidden" name="commandToController" value="UPDATE_NEWS" />

        <button>Update</button>
    </form>
</div>


</body>
</html>