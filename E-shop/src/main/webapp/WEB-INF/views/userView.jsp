<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="top.jsp"/>






<div class="span6">




	<ul class="breadcrumb">
		<li><a href="<c:url value="/" />"><i class="icon-home"></i>
				Start</a> <span class="divider">/</span></li>
		<li><spring:message code="title.settings" /></li>
	</ul>


	<fieldset>
		<legend class="muted">
			<spring:message code="title.settings" />
		</legend>
	</fieldset>



	<sec:authorize
		access="hasRole('ROLE_ADMIN')&&(!hasRole('ROLE_CLIENT'))">

		<c:if test="${param.client_account_created == null}">
			<div class="padding-bottom-small">
				<a href="<c:url value="/usr/client" />" class="btn centered"><i
					class="icon-user icon-black"></i> <spring:message
						code="word.becomeClient" /></a>
			</div>
		</c:if>
	</sec:authorize>



	<c:if test="${param.updated != null}">
		<div class="alert alert-success">
			<spring:message code="success.userUpdated" />
		</div>
	</c:if>

	<c:if test="${param.client_account_created != null}">
		<div class="alert alert-success">
			<spring:message code="success.clientAccountCreated" />
		</div>
	</c:if>




	<c:url value="/usr/" var="url" />

	<form:form modelAttribute="usr" action="${url}" method="post">

		<form:hidden path="id.login" />

		<label>Login</label>
		<div class="well padding-small display-inline-block">
			<c:out value="${usr.id.login}" />
		</div>
		<form:errors path="id.login"
			cssClass="alert alert-error display-block" />

		<form:hidden path="email" />

		<label>Email</label>
		<div class="well padding-small display-inline-block">
			<c:out value="${usr.email}" />
		</div>
		<form:errors path="email" cssClass="alert alert-error display-block" />


		<label><spring:message code="label.password" /></label>
		<form:password path="password" />
		<form:errors path="password"
			cssClass="alert alert-error display-block" />
			
		<label><spring:message code="label.password2" /></label>
		<input type="password" name="password2"/>
		

		<c:if test="${not empty usr.client}">
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

		</c:if>


		<div class="form-buttons">
			<button type="submit" class="btn btn-primary" name="update">
				<i class="icon-ok icon-white"></i>
				<spring:message code="word.save" />
			</button>
		</div>



	</form:form>



</div>




<jsp:include page="bottom.jsp"/>






