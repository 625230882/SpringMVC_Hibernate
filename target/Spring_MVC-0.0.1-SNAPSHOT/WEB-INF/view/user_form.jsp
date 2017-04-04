<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
	<link rel='stylesheet' href="${pageContext.request.contextPath}/resourses/bootstrap.css">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta HTTP-EQUIV="Pragma" content="no-cache">
	<meta HTTP-EQUIV="Expires" content="-1">
	<script	src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<title>Insert title here</title>

</head>
<body>
<div class="container">

	<c:choose>
		<c:when test="${users['new'] || usersInfo['new']}">
			<h1>Add User</h1>
			<spring:url value="/users/userForm" var="url" />
			<c:set var="formName" value="users"/>
		</c:when>
		<c:otherwise>
			<h1>Update User</h1>
			<spring:url value="${ window.location.href }" var="url" />
			<c:set var="formName" value="usersInfo"/>
		</c:otherwise>
	</c:choose>
	
	
	
	<form:form class="form-horizontal" modelAttribute="${formName }" action="${ url}" method="post" enctype="multipart/form-data">
		
		<form:hidden path="id" />
			<c:choose>
			
				<%-- add new user --%>
				
				<c:when test="${users['new'] || usersInfo['new']}">
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
				</c:when>
			<c:otherwise>
			
				<%-- update form for user --%>
				<form:hidden path="photo" />
				
				<spring:bind path="email">
					<div class="form-group row">
						<label class="col-2 control-label">Email</label>
						<div class="col-10">
							<form:input type="text" path="email" class="form-control" placeholder="Email"></form:input>
							<form:errors path="email"  class="control-label"/>
						</div>
					</div>
				</spring:bind>
				
				
				<spring:bind path="name">
					<div class="form-group row">
						<label class="col-2 control-label">name</label>
						<div class="col-10">
							<form:input type="text" path="name" class="form-control" placeholder="name"></form:input>
							<form:errors path="name"  class="control-label"/>
						</div>
					</div>
				</spring:bind>
				
				
				<spring:bind path="birthdate">
					<div class="form-group row">
						<label class="col-2 control-label">birthdate</label>
						<div class="col-10">
							<form:input type="text" path="birthdate" class="form-control" placeholder="birthdate"></form:input>
							<form:errors path="birthdate"  class="control-label"/>
						</div>
					</div>
				</spring:bind>
				
				
				<spring:bind path="address">
					<div class="form-group row">
						<label class="col-2 control-label">address</label>
						<div class="col-10">
							<form:input type="text" path="address" class="form-control" placeholder="address"></form:input>
							<form:errors path="address"  class="control-label"/>
						</div>
					</div>
				</spring:bind>
				
				
				<spring:bind path="phone">
					<div class="form-group row">
						<label class="col-2 control-label">phone</label>
						<div class="col-10">
							<form:input type="text" path="phone" class="form-control" placeholder="phone"></form:input>
							<form:errors path="phone"  class="control-label"/>
						</div>
					</div>
				</spring:bind>
				
				<div class="form-group row">
					<label class="col-2 control-label">sex</label>
					<div class="col-10">
						<form:select class="form-control col-1"  path="sex">
							<c:choose>
								<c:when test = "${usersInfo.sex == 'female'}">
									<form:option value="male">male</form:option>
			                		<form:option selected="selected" value="female">female</form:option>
								</c:when>
								<c:otherwise>
									<form:option selected="selected" value="male">male</form:option>
				                	<form:option value="female">female</form:option>
								</c:otherwise>
							</c:choose>
						</form:select>
					</div>
				</div>
				
				<input type="file" size="50" name="images" multiple/>
			</c:otherwise>
		</c:choose>
		
		
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button id="addFile" type="submit" class="btn-lg btn-primary pull-right">Add</button>
			</div>
		</div>
	</form:form>
</div>
</body>
</html>