<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="top.jsp"/>


<div class="span6">

	<ul class="breadcrumb">
		<li><a href="<c:url value="/" />"><i class="icon-home"></i>
				Start</a> <span class="divider">/</span></li>

		<li><a href="<c:url value="/cart" />"><spring:message
					code="title.shoppingCart" /></a> <span class="divider">/</span></li>
		<li><spring:message code="title.orderFinalizing" /></li>
	</ul>

	<fieldset>
		<legend class="muted">
			<spring:message code="title.orderFinalizing" />
		</legend>
	</fieldset>


	<c:if test="${not empty isDeliveryAddressIncorrect}">
		<div class="alert alert-error">
			<spring:message code="error.incorrectDeliveryAddress" />
		</div>
	</c:if>



	<label><spring:message code="label.deliveryAddress" /></label> <a
		class="btn btn-link" href='<c:url value="/choose/delivery-address"/>'><spring:message
			code="word.choose" /></a>



	<c:if test="${not empty deliveryAddress}">


		<label><spring:message code="label.street" /></label>

		<div class="well padding-small display-inline-block">
			<c:out value="${deliveryAddress.street}" />
		</div>

		<label><spring:message code="label.postCode" /></label>

		<div class="well padding-small display-inline-block">
			<c:out value="${deliveryAddress.postCode}" />
		</div>

		<label><spring:message code="label.city" /></label>

		<div class="well padding-small display-inline-block">
			<c:out value="${deliveryAddress.city}" />
		</div>

	</c:if>


	<c:url value="/order/new" var="url" />

	<form:form modelAttribute="order" action="${url}" method="post">



		<form:hidden path="deliveryAddressId" />



		<div class="form-buttons">

			<button type="submit" class="btn btn-primary" name="create">
				<i class="icon-ok icon-white"></i>
				<spring:message code="word.placeOrder" />
			</button>


		</div>


	</form:form>







</div>












<jsp:include page="bottom.jsp"/>






