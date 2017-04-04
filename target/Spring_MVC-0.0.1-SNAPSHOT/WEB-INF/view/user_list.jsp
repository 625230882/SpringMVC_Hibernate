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
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js"></script>
	<title>Insert title here</title>
</head>
<body>
<div class="container">
	<spring:url value="/users/logout" var="userLogOutUrl" />
	<button class="btn btn-primary" onclick="location.href='${userLogOutUrl }'">log out</button>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>#ID</th>
				<th>Username</th>
				<th>Action</th>
			</tr>
		</thead>
		
		<c:if test="${ not empty users }">
			<c:forEach var="user" items="${ users }">
				<spring:url value="/users/showAllUsers/${user.id}" var="userUrl" />
				
				<tr>
				<td>${user.id}</td>
				<td>${user.username}</td>
				<td><button class="btn btn-primary" onclick="location.href='${userUrl}'">Query</button></td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
</div>
</body>
</html>