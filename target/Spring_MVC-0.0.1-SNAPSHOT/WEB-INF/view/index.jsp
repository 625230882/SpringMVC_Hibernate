<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
	<link rel='stylesheet' href="${pageContext.request.contextPath}/resourses/bootstrap.css">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<META Http-Equiv="Cache-Control" Content="no-cache">
	<META Http-Equiv="Pragma" Content="no-cache">
	<META Http-Equiv="Expires" Content="0">
	<title>Insert title here</title>
</head>
<body>

<div class="container">
	<spring:url value="/users/showAllUsers" var="usersUrl" />
	<spring:url value="/users/userForm" var="addUserUrl" />
	
	
	<form:form class="form-horizontal" modelAttribute="loginForm" action="${pageContext.request.contextPath}/users/login" method="post">
		
		<form:hidden path="id" />
		
		<div class="form-group row">
			<label class="col-2 control-label"></label>
			<div class="col-10">
				<div style="color: red">${error}</div>
			</div>
		</div>
		<spring:bind path="username">
			<div class="form-group row">
				<label class="col-2 control-label">Username</label>
				<div class="col-10">
					<form:input type="text" path="username" class="form-control" placeholder="Name"></form:input>
					<form:errors path="username"  class="control-label"/>
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="password">
			<div class="form-group row">
				<label class="col-2 control-label">Password</label>
				<div class="col-10">
					<form:input type="text" path="password" class="form-control" placeholder="Password"></form:input>
					<form:errors path="password"  class="control-label"/>
				</div>
			</div>
		</spring:bind>
		
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn-lg btn-primary pull-right">Login</button>
			</div>
		</div>
	</form:form>
	
	<button onclick="location.href='${usersUrl}'"  class="btn-lg btn-primary pull-right">Show All Users</button>
	
	<button onclick="location.href='${addUserUrl}'"  class="btn-lg btn-primary pull-right">Add User</button>
</div>
	
</body>
</html>