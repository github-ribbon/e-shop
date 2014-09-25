<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<jsp:include page="top.jsp"/>









<div class="span6">




	<ul class="breadcrumb">

		<li><a href="<c:url value="/" />"><i class="icon-home"></i>
				Start</a> <span class="divider">/</span></li>
		<li><a href="<c:url value="/usr"  />"><spring:message
					code="title.settings" /></a> <span class="divider">/</span></li>
		<li><spring:message code="title.creatingClientAccount" /></li>
	</ul>


	<fieldset>
		<legend class="muted">
			<spring:message code="title.creatingClientAccount" />
		</legend>
	</fieldset>




	<c:url value="/usr/client" var="url" />

	<form:form modelAttribute="client" action="${url}" method="post">

		<form:hidden path="id.login" />

		<label>Login</label>
		<div class="well padding-small display-inline-block">
			<c:out value="${client.id.login}" />
		</div>


		<label><spring:message code="label.givenName" /></label>
		<form:input path="givenName" />
		<form:errors path="givenName"
			cssClass="alert alert-error display-block" />

		<label><spring:message code="label.familyName" /></label>
		<form:input path="familyName" />
		<form:errors path="familyName"
			cssClass="alert alert-error display-block" />

		<label><spring:message code="label.phoneNumber" /></label>
		<form:input path="phoneNumber" />
		<form:errors path="phoneNumber"
			cssClass="alert alert-error display-block" />

		<div class="form-buttons">
			<button type="submit" class="btn btn-primary" name="create">
				<i class="icon-user icon-white"></i>
				<spring:message code="word.becomeClient" />
			</button>
		</div>



	</form:form>









</div>







<jsp:include page="bottom.jsp"/>






