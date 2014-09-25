<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<jsp:include page="top.jsp"/>









<div class="span6">




	<ul class="breadcrumb">
		<li><a href="<c:url value="/" />"><i class="icon-home"></i>
				Start</a> <span class="divider">/</span></li>
		<li><spring:message code="title.registration" /></li>
	</ul>


	<fieldset>
		<legend class="muted">
			<spring:message code="title.registration" />
		</legend>
	</fieldset>


	<c:choose>
		<c:when test="${empty completed}">

			<c:url value="/usr/new" var="url" />

			<form:form modelAttribute="usr" action="${url}" method="post">


				<label>Login</label>
				<form:input path="id.login" />
				<form:errors path="id.login"
					cssClass="alert alert-error display-block" />

				<label>Email</label>
				<form:input path="email" />
				<form:errors path="email" cssClass="alert alert-error display-block" />

				<label><spring:message code="label.password" /></label>
				<form:password path="password" />
				<form:errors path="password"
					cssClass="alert alert-error display-block" />

				<label><spring:message code="label.password2" /></label>
				<input type="password" name="password2" />


				<label><spring:message code="label.givenName" /></label>
				<form:input path="client.givenName" />
				<form:errors path="client.givenName"
					cssClass="alert alert-error display-block" />

				<label><spring:message code="label.familyName" /></label>
				<form:input path="client.familyName" />
				<form:errors path="client.familyName"
					cssClass="alert alert-error display-block" />

				<label><spring:message code="label.phoneNumber" /></label>
				<form:input path="client.phoneNumber" />
				<form:errors path="client.phoneNumber"
					cssClass="alert alert-error display-block" />

				<div class="form-buttons">
					<button type="submit" class="btn btn-primary" name="create">
						<i class="icon-ok icon-white"></i>
						<spring:message code="word.register" />
					</button>
				</div>



			</form:form>
		</c:when>
		<c:otherwise>


			<div class="alert alert-success">
				<spring:message code="success.registered" />
			</div>


		</c:otherwise>
	</c:choose>









</div>




<jsp:include page="bottom.jsp"/>






