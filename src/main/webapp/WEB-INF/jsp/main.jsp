<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>News portal main page</title>
    <link rel="stylesheet" href="resources/css/css-style.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.name.site" var="name_site"/>
    <fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button"/>
    <fmt:message bundle="${loc}" key="local.locbutton.name.login" var="login_button"/>
    <fmt:message bundle="${loc}" key="local.locbutton.name.register" var="register_button"/>
</head>
<body>
<div class="heading">
    <h1 class=headline><c:out value="${name_site}"/></h1>
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

        <div class="heading-2" style="">

            <form action="Controller" method="post">
                <input type="hidden" name="command" value="AUTHORIZATION_PAGE"/>
                <input type="submit" class="button" value="${login_button}"/>
            </form>

            <form action="Controller" method="post">
                <input type="hidden" name="command" value="REGISTRATION_PAGE"/>
                <input type="submit" class="button" value="${register_button}"/>
            </form>

        </div>
    </div>
</div>

<HR WIDTH="70%" ALIGN="center" SIZE="1">

<table class="textNews" align="center">
    <c:forEach items="${newses}" var="clip">
        <tr ALIGN="center">
            <th ALIGN="center"><c:out value="${clip.getTitle()}"/></th>
        </tr>
        <tr ALIGN="center">
            <td ALIGN="center"><p class="textDescription"><c:out value="${clip.getDescription()}"/></p>
                <HR WIDTH="70%" ALIGN="center" SIZE="1">
            </td>
        </tr>
    </c:forEach>
</table>

<div class="pagination" style="width: 50%; margin: 0 auto; text-align: center;">
    <c:if test="${currentPage>1}">
        <a href="Controller?command=GO_TO_MAIN_PAGE&requestCurrentPage=${pageNumList.get(currentPage-2)}">&laquo;</a>
    </c:if>

    <c:forEach var="page" items="${pageNumList}">
        <c:if test="${page != currentPage}">
            <a href="Controller?command=GO_TO_MAIN_PAGE&requestCurrentPage=${page}">${page}</a>
        </c:if>

        <c:if test="${page == currentPage}">
            <a class="active" href="#">${page}</a>
        </c:if>
    </c:forEach>

    <c:if test="${pageNumList.size()>currentPage}">
        <a href="Controller?command=GO_TO_MAIN_PAGE&requestCurrentPage=${pageNumList.get(currentPage)}">&raquo;</a>
    </c:if>
</div>

</body>
</html>