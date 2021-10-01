<%@ page import="by.newsportal.news.bean.User" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>List of news offers</title>
    <link rel="stylesheet" href="resources/css/css-style.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.name.site" var="name_site"/>
    <fmt:message bundle="${loc}" key="local.locbutton.name.exit" var="exit_button"/>
    <fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button"/>
    <fmt:message bundle="${loc}" key="local.text.deleteNews" var="delete_news"/>
    <fmt:message bundle="${loc}" key="local.text.hello" var="hello"/>
    <fmt:message bundle="${loc}" key="local.text.toMain" var="add_to_main_page"/>
    <fmt:message bundle="${loc}" key="local.text.updateNews" var="update_news"/>
    <fmt:message bundle="${loc}" key="local.text.addNews" var="add_news"/>
</head>
<body>
<%
    String UserRole = (((User) request.getSession(false).getAttribute("user")).getRole()).toString();
    request.setAttribute("UserRole", UserRole);
%>
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
        <div class="heading-2">
            <form action="Controller" method="post">
                <input type="hidden" name="command" value="GO_TO_MAIN_PAGE"/>
                <input type="submit" class="button" value="${exit_button}"/>
            </form>
            <form action="Controller" method="post">
                <input type="hidden" name="command" value="GO_TO_ADD_NEWS_PAGE"/>
                <input type="submit" class="button" value="${add_news}"/>
            </form>
        </div>
    </div>
</div>

<c:set var="mer" value="${sessionScope.user}"/>
<p class="text"><c:out value="${mer.getName()} ${hello}"/></p>
<HR WIDTH="70%" ALIGN="center" SIZE="1">

<table class="textNews" align="center">
    <c:forEach items="${newses}" var="news">
        <tr ALIGN="center">
            <th ALIGN="center"><c:out value="${news.getTitle()}"/></th>
        </tr>
        <tr ALIGN="center">
            <td ALIGN="center"><p class="textDescription"><c:out value="${news.getDescription()}"/></p>
                <div ALIGN="right">
                    <a href="Controller?command=ADD_NEWS_TO_HOME_PAGE&choosenNewsId=${news.getId()}&currentPage=${currentPage}"
                       style="font-size: 20px;display: inline-block;background: #408080;color: black; padding: 1rem 1.5rem; text-decoration: none; ">
                        <c:out value="${add_to_main_page}"/></a>
                    <a href="Controller?command=UPDATE_NEWS_PAGE&choosenNewsId=${news.getId()}&currentPage=${currentPage}"
                       style="font-size: 20px;display: inline-block;background: #408080;color: black; padding: 1rem 1.5rem; text-decoration: none; ">
                        <c:out value="${update_news}"/></a>
                    <a href="Controller?command=DELETE_NEWS&choosenNewsId=${news.getId()}&currentPage=${currentPage}"
                       style="font-size: 20px;display: inline-block; background: #408080;  color: black; padding: 1rem 1.5rem; text-decoration: none; ">
                        <c:out value="${delete_news}"/></a>
                </div>
                <HR WIDTH="70%" ALIGN="center" SIZE="1">
            </td>
        </tr>
    </c:forEach>
</table>

<div class="pagination" style="width: 50%; margin: 0 auto; text-align: center;">
    <c:if test="${currentPage>1}">
        <a href="Controller?command=GO_TO_LIST_NEWS_OFFER_PAGE&requestCurrentPage=${pageNumList.get(currentPage-2)}">&laquo;</a>
    </c:if> <c:forEach var="page" items="${pageNumList}">

    <c:if test="${page != currentPage}">
        <a href="Controller?command=GO_TO_LIST_NEWS_OFFER_PAGE&requestCurrentPage=${page}">${page}</a>
    </c:if>

    <c:if test="${page == currentPage}">
        <a class="active" href="#">${page}</a>
    </c:if>

</c:forEach> <c:if test="${pageNumList.size()>currentPage}">
    <a href="Controller?command=GO_TO_LIST_NEWS_OFFER_PAGE&requestCurrentPage=${pageNumList.get(currentPage)}">&raquo;</a>
</c:if></div>
</body>
</html>