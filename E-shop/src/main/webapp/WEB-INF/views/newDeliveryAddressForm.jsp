<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<jsp:include page="top.jsp"/>









<div class="span6">

	<ul class="breadcrumb">
		<li><a href="<c:url value="/" />"><i class="icon-home"></i>
				Start</a> <span class="divider">/</span></li>



		<li><a href="<c:url value="/delivery-address" />"><spring:message
					code="title.deliveryAddresses" /></a><span class="divider">/</span></li>



		<li><spring:message code="title.creatingDeliveryAddress" /></li>


	</ul>

	<fieldset>
		<legend class="muted">
			<spring:message code="title.creatingDeliveryAddress" />
		</legend>
	</fieldset>






	<c:url value="/delivery-address/new" var="url" />

	<form:form modelAttribute="deliveryAddress" action="${url}"
		method="post">

		<form:hidden path="login" />

		<label><spring:message code="label.street" /></label>
		<form:input path="street" />
		<form:errors path="street" cssClass="alert alert-error display-block" />

		<label><spring:message code="label.postCode" /></label>
		<form:input path="postCode" />
		<form:errors path="postCode"
			cssClass="alert alert-error display-block" />


		<label><spring:message code="label.city" /></label>
		<form:input path="city" />
		<form:errors path="city" cssClass="alert alert-error display-block" />


		<div class="form-buttons">
			<button type="submit" class="btn btn-primary" name="create">
				<i class="icon-plus icon-white"></i>
				<spring:message code="word.add" />
			</button>
		</div>



	</form:form>







</div>





<jsp:include page="bottom.jsp"/>






