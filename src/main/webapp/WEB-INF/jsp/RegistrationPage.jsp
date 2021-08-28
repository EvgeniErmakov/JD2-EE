<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Registration Page</title>
<link rel="stylesheet" href="resources/css/css-style.css">

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.name.site" var="name_site"/>
<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button"/>
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.register" var="Register_button" />
<fmt:message bundle="${loc}" key="local.user.name" var="name"/>
<fmt:message bundle="${loc}" key="local.user.surname" var="surname"/>
<fmt:message bundle="${loc}" key="local.user.email" var="email"/>
<fmt:message bundle="${loc}" key="local.user.enteredPassword" var="enteredPassword"/>
<fmt:message bundle="${loc}" key="local.user.repeatedPassword" var="repeatedPassword"/>
<fmt:message bundle="${loc}" key="local.text.incorrectData" var="incorrect_data_message"/>
<fmt:message bundle="${loc}" key="local.text.already_exist" var="already_exist"/>

<style>
.form input {
	font-family: "Roboto", sans-serif;
	outline: 0;
	background: #f2f2f2;
	width: 100%;
	border: 0;
	margin: 0 0 15px;
	padding: 15px;
	box-sizing: border-box;
	font-size: 14px;
}

.form {
	position: relative;
	z-index: 1;
	background: #FFFFFF;
	max-width: 360px;
	margin: 0 auto 100px;
	padding: 45px;
	text-align: center;
	box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0
		rgba(0, 0, 0, 0.24);
}

</style>
</head>
<body>
		<div class="heading">
		<h1 class=headline><c:out value="${name_site}" /></h1>
		<div class=heading-1>
		<div class="heading-2">
			<form action="Controller" method="post">
				<input type="hidden" name="local" value="ru" /> 
				<input type="hidden" name="command" value="CHANGE_LOCAL" /> 
				<input type="submit" class="button_local" value="${ru_button}" /><br />
			</form>

			<form action="Controller" method="post">
				<input type="hidden" name="local" value="en" /> 
				<input type="hidden" name="command" value="CHANGE_LOCAL" /> 
				<input type="submit" class="button_local" value="${en_button}" /><br />
			</form>
		</div>
			
		</div>
	</div>
	<div class=form>
	
		<font color="red" size="3">
			<c:out value="${param.incorrect_data_message}"/>
			<c:out value="${param.email_is_busy}"/>
		</font>
		
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="Registration_new_user" />
			<input type="text" name="name" placeholder="${name}" value="" /> 
			<input type="text" name="surname" placeholder="${surname}" value="" />
			<input type="text" name="email" placeholder="${email}" value="" /> 
			<input type="password" name="enteredPassword" placeholder="${enteredPassword}" value="" />
			<input type="password" name="repeatedPassword" placeholder="${repeatedPassword}" value="" />

			<input style="background: #408080; width: 50%;" type="submit"
				class="button" value="${Register_button}" />
		</form>
	</div>
</body>
</html>