<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="top.jsp"/>








<div class="span6">

	<ul class="breadcrumb">
		<li><a href="<c:url value="/" />"><i class="icon-home"></i>
				Start</a> <span class="divider">/</span></li>


		<li><a href="<c:url value="/client" />"><spring:message
					code="title.clients" /></a> <span class="divider">/</span></li>


		<li><spring:message code="title.clientDetails" /><span
			class="divider">/</span></li>


		<c:if test="${empty isClientIncorrect}">

			<li><a href="<c:url value="/order?login=${client.id.login}" />"><spring:message
						code="title.orders" /></a></li>

		</c:if>
	</ul>

	<fieldset>
		<legend class="muted">
			<spring:message code="title.clientDetails" />
		</legend>
	</fieldset>


	<c:if test="${isLogged}">
		<div class="padding-bottom-small">
			<a href="<c:url value="/usr" />" class="btn centered"><i
				class="icon-edit icon-black"></i> <spring:message code="word.edit" /></a>
		</div>
	</c:if>


	<c:choose>
		<c:when test="${empty isClientIncorrect}">






			<form:form modelAttribute="feature" method="post">



				<label>Login</label>
				<div class="well padding-small display-inline-block">
					<c:out value="${client.id.login}" />
				</div>

				<label><spring:message code="label.givenName" /></label>
				<div class="well padding-small display-inline-block">
					<c:out value="${client.givenName}" />
				</div>

				<label><spring:message code="label.familyName" /></label>
				<div class="well padding-small display-inline-block">
					<c:out value="${client.familyName}" />
				</div>

				<label>E-mail</label>
				<div class="well padding-small display-inline-block">
					<c:out value="${client.usr.email}" />
				</div>

				<label><spring:message code="label.phoneNumber" /></label>
				<div class="well padding-small display-inline-block">
					<c:out value="${client.phoneNumber}" />
				</div>



			</form:form>




		</c:when>

		<c:otherwise>
			<div class="alert alert-error">
				<spring:message code="error.incorrectClient" />
			</div>
		</c:otherwise>

	</c:choose>

</div>












<jsp:include page="bottom.jsp"/>






