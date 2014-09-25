<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<jsp:include page="top.jsp"/>









<div class="span6">

	<ul class="breadcrumb">
		<li><a href="<c:url value="/" />"><i class="icon-home"></i>
				Start</a> <span class="divider">/</span></li>

		<li><a href="<c:url value="/product?search=true" />"> <spring:message
					code="title.products" /></a> <span class="divider">/</span></li>

		<li><spring:message code="title.creatingProduct" /></li>
	</ul>

	<fieldset>
		<legend class="muted">
			<spring:message code="title.creatingProduct" />
		</legend>
	</fieldset>



	<c:url value="/product/new" var="url" />

	<form:form modelAttribute="product" action="${url}" method="post">

		<form:hidden path="manufacturerId" />

		<label><spring:message code="label.name" /></label>
		<form:input path="name" />
		<form:errors path="name" cssClass="alert alert-error display-block" />

		<form:hidden path="categoryId" />

		<label><spring:message code="label.manufacturer" /></label>

		<c:if test="${!(product.manufacturerId==0)}">
			<div class="well padding-small display-inline-block">
				<c:out value="${manufacturerName}" />
			</div>
		</c:if>

		<button type="submit" class="btn btn-link" name="choose_manufacturer">
			<spring:message code="word.choose" />
		</button>

		<form:errors path="manufacturerId"
			cssClass="alert alert-error display-block" />



		<label><spring:message code="label.category" /></label>

		<c:if test="${not empty product.categoryId}">
			<div class="well padding-small display-inline-block">
				<c:out value="${product.categoryId}" />
			</div>

			<button type="submit" class="btn btn-link" name="delete_category">
				<spring:message code="word.delete" />
			</button>

		</c:if>

		<button type="submit" class="btn btn-link" name="choose_category">
			<spring:message code="word.choose" />
		</button>

		<form:errors path="categoryId"
			cssClass="alert alert-error display-block" />



		<label><spring:message code="label.price" /></label>
		<form:input path="price" />
		<form:errors path="price" cssClass="alert alert-error display-block" />



		<label class="margin-top-small"><spring:message
				code="label.description" /></label>
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






