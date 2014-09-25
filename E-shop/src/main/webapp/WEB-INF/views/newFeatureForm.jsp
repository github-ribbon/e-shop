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

		<c:if test="${empty isProductIncorrect}">


			<c:forEach items="${supCategories}" var="c">

				<li><a
					href="<c:url value="/product?search=true&category_id=${c}" />"><c:out
							value="${c}" /></a> <span class="divider">/</span></li>

			</c:forEach>


			<c:if test="${not empty product.categoryId}">

				<li><a
					href="<c:url value="/product?search=true&category_id=${product.categoryId}" />"><c:out
							value="${product.categoryId}" /></a> <span class="divider">/</span></li>



			</c:if>



			<li><a href="<c:url value="/product/${product.productId}" />"><c:out
						value="${product.name}" /></a> <span class="divider">/</span></li>


		</c:if>


		<li><spring:message code="title.creatingFeature" /></li>
	</ul>

	<fieldset>
		<legend class="muted">
			<spring:message code="title.creatingFeature" />
		</legend>
	</fieldset>


	<c:choose>
		<c:when test="${empty isProductIncorrect}">

			<c:url value="/feature/new" var="url" />

			<form:form modelAttribute="feature" action="${url}" method="post">


				<form:hidden path="id.featureTypeId" />
				<form:hidden path="id.productId" />



				<label><spring:message code="label.product" /></label>
				<div class="well padding-small display-inline-block">
					<c:out value="${product.name}" />
				</div>



				<label><spring:message code="label.featureType" /></label>

				<c:if test="${not empty feature.id.featureTypeId}">
					<div class="well padding-small display-inline-block">
						<c:out value="${feature.id.featureTypeId}" />
					</div>
				</c:if>

				<button type="submit" class="btn btn-link"
					name="choose_feature_type">
					<spring:message code="word.choose" />
				</button>

				<form:errors path="id.featureTypeId"
					cssClass="alert alert-error display-block" />


				<label><spring:message code="label.value" /></label>
				<form:textarea path="value" />
				<form:errors path="value" cssClass="alert alert-error display-block" />





				<div class="form-buttons">
					<button type="submit" class="btn btn-primary" name="create">
						<i class="icon-plus icon-white"></i>
						<spring:message code="word.add" />
					</button>
				</div>



			</form:form>

		</c:when>

		<c:otherwise>
			<div class="alert alert-error">
				<spring:message code="error.incorrectProduct" />
			</div>
		</c:otherwise>

	</c:choose>








</div>




<jsp:include page="bottom.jsp"/>






