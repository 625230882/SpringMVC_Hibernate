<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<c:if test="${not empty user }">
		<p>
			<label>id:</label>${user.id }
		</p>
		<p>
			<label>name:</label>${user.email }
		</p>
		
		<spring:url value="/users/${user.id }/update" var="updateUserUrl" />
	
		<button onclick="location.href='${updateUserUrl}'">update</button>
	</c:if>
	
</body>
</html>