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

		<c:if test="${empty isFeatureIncorrect}">


			<c:forEach items="${supCategories}" var="c">

				<li><a
					href="<c:url value="/product?search=true&category_id=${c}" />"><c:out
							value="${c}" /></a> <span class="divider">/</span></li>

			</c:forEach>


			<c:if test="${not empty originalCategoryId}">

				<li><a
					href="<c:url value="/product?search=true&category_id=${originalCategoryId}" />"><c:out
							value="${originalCategoryId}" /></a> <span class="divider">/</span></li>



			</c:if>




			<li><a href="<c:url value="/product/${feature.id.productId}" />"><c:out
						value="${productName}" /></a> <span class="divider">/</span></li>
		</c:if>
		<li><spring:message code="title.featureDetails" /></li>
	</ul>

	<fieldset>
		<legend class="muted">
			<spring:message code="title.featureDetails" />
		</legend>
	</fieldset>

	<c:choose>
		<c:when test="${empty isFeatureIncorrect}">

			<c:choose>
				<c:when test="${param.created != null}">
					<div class="alert alert-success">
						<spring:message code="success.featureCreated" />
					</div>
				</c:when>

				<c:when test="${param.updated != null}">
					<div class="alert alert-success">
						<spring:message code="success.featureUpdated" />
					</div>
				</c:when>
			</c:choose>



			<c:url value="/feature/" var="url" />

			<form:form modelAttribute="feature" action="${url}" method="post">

				<form:hidden path="id.featureTypeId" />
				<form:hidden path="id.productId" />



				<label><spring:message code="label.product" /></label>
				<div class="well padding-small display-inline-block">
					<c:out value="${productName}" />
				</div>





				<label><spring:message code="label.featureType" /></label>

				<div class="well padding-small display-inline-block">
					<c:out value="${feature.id.featureTypeId}" />
				</div>

				<a class="btn btn-link"
					href='<c:url value="/feature-type/${feature.id.featureTypeId}"/>'><spring:message
						code="word.advance" /></a>



				<label><spring:message code="label.value" /></label>
				<form:textarea path="value" />
				<form:errors path="value" cssClass="alert alert-error display-block" />



				<div class="form-buttons">

					<button type="submit" class="btn btn-primary" name="update">
						<i class="icon-ok icon-white"></i>
						<spring:message code="word.save" />
					</button>

					<button type="submit" class="btn btn-danger" name="delete">
						<i class="icon-remove icon-white"></i>
						<spring:message code="word.delete" />
					</button>

				</div>



			</form:form>




		</c:when>

		<c:otherwise>
			<div class="alert alert-error">
				<spring:message code="error.incorrectFeature" />
			</div>
		</c:otherwise>

	</c:choose>

</div>












<jsp:include page="bottom.jsp"/>






