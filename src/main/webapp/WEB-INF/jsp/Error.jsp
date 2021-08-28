<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Error</title>
<link rel="stylesheet" href="resources/css/NewSst.css">
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.name.site" var="name_site" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru"
	var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en"
	var="en_button" />
<fmt:message bundle="${loc}" key="local.text.created" var="created" />
<fmt:message bundle="${loc}" key="local.text.error" var="error" />
<fmt:message bundle="${loc}" key="local.text.error.message" var="error_message" />

</head>
<body>
	<div class="heading">

		<h1 class=headline>
			<c:out value="${name_site}" />
		</h1>
		<div class=heading-1>

			<div class="heading-2">
				<form action="Controller" method="post">
					<input type="hidden" name="local" value="ru" /> 
					<input type="hidden" name="command" value="CHANGE_LOCAL"/>
					<input type="submit" class="button_local" value="${ru_button}" /><br />
				</form>
				<form action="Controller" method="post">
					<input type="hidden" name="local" value="en" /> 
					<input type="hidden" name="command" value="CHANGE_LOCAL"/>
					<input type="submit" class="button_local" value="${en_button}" /><br />
				</form>
			</div>
		</div>
	</div>
	<h1 class="headline"><c:out value="${error}"/></h1>
	<p class="text"><c:out value="${error_message}"/></p>
</body>
</html>