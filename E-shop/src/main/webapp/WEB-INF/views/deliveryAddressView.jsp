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



		<li><spring:message code="title.deliveryAddressDetails" /></li>
	</ul>





	<fieldset>
		<legend class="muted">
			<spring:message code="title.deliveryAddressDetails" />
		</legend>
	</fieldset>

	<c:choose>
		<c:when test="${empty isDeliveryAddressIncorrect}">

			<c:choose>
				<c:when test="${param.created != null}">
					<div class="alert alert-success">
						<spring:message code="success.deliveryAddressCreated" />
					</div>
				</c:when>

				<c:when test="${param.updated != null}">
					<div class="alert alert-success">
						<spring:message code="success.deliveryAddressUpdated" />
					</div>
				</c:when>
			</c:choose>



			<c:url value="/delivery-address/" var="url" />

			<form:form modelAttribute="deliveryAddress" action="${url}"
				method="post">

				<form:hidden path="deliveryAddressId" />
				<form:hidden path="login" />

				<label><spring:message code="label.street" /></label>
				<form:input path="street" />
				<form:errors path="street"
					cssClass="alert alert-error display-block" />

				<label><spring:message code="label.postCode" /></label>
				<form:input path="postCode" />
				<form:errors path="postCode"
					cssClass="alert alert-error display-block" />


				<label><spring:message code="label.city" /></label>
				<form:input path="city" />
				<form:errors path="city" cssClass="alert alert-error display-block" />








				<div class="form-buttons">
					<button type="submit" class="btn btn-primary" name="update">
						<i class="icon-ok icon-white"></i>
						<spring:message code="word.save" />
					</button>


					<c:choose>

						<c:when test="${isDependent==false}">
							<button type="submit" class="btn btn-danger" name="delete">
								<i class="icon-remove icon-white"></i>
								<spring:message code="word.delete" />
							</button>

						</c:when>
						<c:otherwise>
							<span class="disabled btn btn-danger"><i
								class="icon-remove icon-white"></i> <spring:message
									code="word.delete" /></span>
						</c:otherwise>
					</c:choose>




				</div>



			</form:form>




		</c:when>

		<c:otherwise>
			<div class="alert alert-error">
				<spring:message code="error.incorrectDeliveryAddress" />
			</div>
		</c:otherwise>

	</c:choose>

</div>












<jsp:include page="bottom.jsp"/>






