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






			<li><a
				href="<c:url value="/product-instance/product/${product.productId}" />">
					<spring:message code="title.productInstances" />
			</a> <span class="divider">/</span></li>



		</c:if>


		<li><spring:message code="title.creatingProductInstance" /></li>





	</ul>

	<fieldset>
		<legend class="muted">
			<spring:message code="title.creatingProductInstance" />
		</legend>
	</fieldset>


	<c:choose>
		<c:when test="${empty isProductIncorrect}">

			<c:url value="/product-instance/new" var="url" />

			<form:form modelAttribute="productInstance" action="${url}"
				method="post">

				<form:hidden path="productId" />

				<label><spring:message code="label.product" /></label>

				<c:if test="${not empty product}">
					<div class="well padding-small display-inline-block">
						<c:out value="${product.name}" />
					</div>
				</c:if>

				<form:errors path="productId"
					cssClass="alert alert-error display-block" />


				<div class="form-buttons">

					<c:if test="${not empty product}">
						<button type="submit" class="btn btn-primary" name="create">
							<i class="icon-plus icon-white"></i>
							<spring:message code="word.add" />
						</button>
					</c:if>
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






