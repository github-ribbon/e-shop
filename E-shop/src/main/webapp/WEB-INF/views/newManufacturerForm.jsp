<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<jsp:include page="top.jsp"/>









<div class="span6">

	<ul class="breadcrumb">
		<li><a href="<c:url value="/" />"><i class="icon-home"></i>
				Start</a> <span class="divider">/</span></li>
		<li><a href="<c:url value="/manufacturer" />"><spring:message
					code="title.manufacturers" /></a> <span class="divider">/</span></li>
		<li><spring:message code="title.creatingManufacturer" /></li>
	</ul>

	<fieldset>
		<legend class="muted">
			<spring:message code="title.creatingManufacturer" />
		</legend>
	</fieldset>



	<c:url value="/manufacturer/new" var="url" />

	<form:form modelAttribute="manufacturer" action="${url}" method="post">


		<label><spring:message code="label.name" /></label>
		<form:input path="name" />
		<form:errors path="name" cssClass="alert alert-error display-block" />


		<label><spring:message code="label.description" /></label>
		<form:textarea path="description" />
		<form:errors path="description"
			cssClass="alert alert-error display-block" />


		<label><spring:message code="label.homePage" /></label>
		<form:input path="homePage" />
		<form:errors path="homePage"
			cssClass="alert alert-error display-block" />


		<div class="form-buttons">
			<button type="submit" class="btn btn-primary" name="create">
				<i class="icon-plus icon-white"></i>
				<spring:message code="word.add" />
			</button>
		</div>



	</form:form>




</div>


<jsp:include page="bottom.jsp"/>






