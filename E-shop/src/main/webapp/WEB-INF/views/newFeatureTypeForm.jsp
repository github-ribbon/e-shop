<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<jsp:include page="top.jsp"/>









<div class="span6">

	<ul class="breadcrumb">
		<li><a href="<c:url value="/" />"><i class="icon-home"></i>
				Start</a> <span class="divider">/</span></li>
		<li><a href="<c:url value="/feature-type" />"><spring:message
					code="title.featureTypes" /></a> <span class="divider">/</span></li>
		<li><spring:message code="title.creatingFeatureType" /></li>
	</ul>

	<fieldset>
		<legend class="muted">
			<spring:message code="title.creatingFeatureType" />
		</legend>
	</fieldset>



	<c:url value="/feature-type/new" var="url" />

	<form:form modelAttribute="featureType" action="${url}" method="post">


		<label><spring:message code="label.name" /></label>
		<form:input path="id.featureTypeId" />
		<form:errors path="id.featureTypeId"
			cssClass="alert alert-error display-block" />


		<label><spring:message code="label.description" /></label>
		<form:textarea path="description" />
		<form:errors path="description"
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






