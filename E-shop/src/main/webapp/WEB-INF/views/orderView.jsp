<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" 
           uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
           
<jsp:include page="top.jsp"/>




<div class="span6">

	<ul class="breadcrumb">
		<li><a href="<c:url value="/" />"><i class="icon-home"></i>
				Start</a> <span class="divider">/</span></li>


		<c:choose>


			<c:when test="${empty isOrderIncorrect}">


				<c:choose>
					<c:when test="${(param.own != null)&&(isLogged)}">

						<li><spring:message code="clientAccount" /> <span
							class="divider">/</span></li>
						<li><a
							href="<c:url value="/order?login=${client.id.login}&own" />"><spring:message
									code="title.orders" /></a><span class="divider">/</span></li>
					</c:when>
					<c:otherwise>

						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<li><a href="<c:url value="/client" />"><spring:message
										code="title.clients" /></a> <span class="divider">/</span></li>
							<li><a href="<c:url value="/client/${client.id.login}" />"><spring:message
										code="title.clientDetails" /></a><span class="divider">/</span></li>
							<li><a
								href="<c:url value="/order?login=${client.id.login}" />"><spring:message
										code="title.orders" /></a><span class="divider">/</span></li>
						</sec:authorize>
					</c:otherwise>
				</c:choose>

			</c:when>

			<c:otherwise>
				<sec:authorize
					access="(!hasRole('ROLE_ADMIN'))&&hasRole('ROLE_CLIENT')">
					<li><spring:message code="clientAccount" /> <span
						class="divider">/</span></li>
					<li><a
						href="<c:url value="/order?login=${client.id.login}&own" />"><spring:message
								code="title.orders" /></a><span class="divider">/</span></li>
				</sec:authorize>

				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li><a href="<c:url value="/order" />"><spring:message
								code="title.orders" /></a><span class="divider">/</span></li>
				</sec:authorize>
			</c:otherwise>
		</c:choose>




		<li><spring:message code="title.orderDetails" /></li>

	</ul>







	<fieldset>
		<legend class="muted">
			<spring:message code="title.orderDetails" />
		</legend>
	</fieldset>





	<c:choose>
		<c:when test="${empty isOrderIncorrect}">

			<div>

				<div>



					<label><spring:message code="label.orderId" /></label>

					<div class="well padding-small display-inline-block">
						<c:out value="${order.orderId}"></c:out>
					</div>


					<c:if test="${!((param.own != null)&&(isLogged))}">
						<label><spring:message code="label.client" /></label>

						<div class="well padding-small display-inline-block">
							<c:out value="${client.givenName}"></c:out>
							<c:out value="${client.familyName}"></c:out>
						</div>


						<a href='<c:url value="/client/${client.id.login}"/>'><spring:message
								code="word.advance" /></a>

					</c:if>





				</div>

				<div class="display-inline-block">
					<label><spring:message code="label.created" /></label>

					<div class="well padding-small display-inline-block">
						<fmt:formatDate type="both" pattern="dd-MM-yyyy H:mm:ss "
							value="${order.created}" />
					</div>
				</div>


				<div class="display-inline-block">



					<label><spring:message code="label.modified" /></label>

					<div class="well padding-small display-inline-block">
						<fmt:formatDate type="both" pattern="dd-MM-yyyy H:mm:ss "
							value="${order.modified}" />
					</div>
				</div>
			</div>

			<div class="centered">
				<label>Status</label>

				<c:choose>
					<c:when test="${order.dbStatusId=='C'}">
						<span class="label label-warning padding-small font-medium">
							<spring:message code="word.waiting" />
						</span>
					</c:when>
					<c:when test="${order.dbStatusId=='U'}">
						<span class="label label-info padding-small font-medium"> <spring:message
								code="word.pending" />
						</span>
					</c:when>
					<c:when test="${order.dbStatusId=='F'}">
						<span class="label label-success padding-small font-medium">
							<spring:message code="word.completed" />


						</span>
					</c:when>
					<c:when test="${order.dbStatusId=='D'}">
						<span class="label label-important padding-small font-medium">
							<spring:message code="word.cancelled" />
						</span>
					</c:when>
				</c:choose>





				<c:url value="/order" var="url" />

				<form:form modelAttribute="order" action="${url}" method="post">

					<form:hidden path="orderId" />


					<div class="form-buttons">




						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<c:choose>
								<c:when test="${order.dbStatusId=='C'}">
									<button type="submit" class="btn btn-info" name="send">
										<i class="icon-ok icon-white"></i>
										<spring:message code="word.send" />
									</button>
								</c:when>
								<c:otherwise>
									<span class="disabled btn btn-info"><i
										class="icon-remove icon-white"></i> <spring:message
											code="word.send" /></span>

								</c:otherwise>
							</c:choose>


							<c:choose>

								<c:when test="${order.dbStatusId=='U'}">

									<button type="submit" class="btn btn-success" name="complete">
										<i class="icon-ok icon-white"></i>
										<spring:message code="word.complete" />
									</button>
								</c:when>
								<c:otherwise>
									<span class="disabled btn btn-success"><i
										class="icon-remove icon-white"></i> <spring:message
											code="word.complete" /></span>
								</c:otherwise>
							</c:choose>

						</sec:authorize>










						<sec:authorize access="hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')">
							<c:choose>

								<c:when
									test="${(order.dbStatusId!='F')&&(order.dbStatusId!='D')}">



									<button type="submit" class="btn btn-danger" name="cancel">
										<i class="icon-remove icon-white"></i>
										<spring:message code="word.cancel" />
									</button>

								</c:when>
								<c:otherwise>
									<span class="disabled btn btn-danger"><i
										class="icon-remove icon-white"></i> <spring:message
											code="word.cancel" /></span>
								</c:otherwise>
							</c:choose>
						</sec:authorize>
					</div>

				</form:form>
			</div>





			<table
				class="table table-striped table-hover table-middle margin-top-small">
				<thead>
					<tr>
						<th>ID</th>
						<th></th>
						<th><spring:message code="label.name" /></th>

						<th><div class="centered">
								<spring:message code="label.price" />
							</div></th>

					</tr>
				</thead>

				<tbody>

					<c:forEach items="${productInstances}" var="item">
						<tr>

							<td><sec:authorize access="hasRole('ROLE_ADMIN')">
									<a
										href='<c:url value="/product-instance/${item.productInstanceId}"/>'>
										<c:out value="${item.productInstanceId}"></c:out>
									</a>
								</sec:authorize> <sec:authorize
									access="hasRole('ROLE_CLIENT')&&(!hasRole('ROLE_ADMIN'))">
									<c:out value="${item.productInstanceId}"></c:out>
								</sec:authorize></td>

							<td class="small-thumb-cell">

								<div class="centered">


									<a href='<c:url value="/product/${item.productId}"/>'> <img
										class="img-polaroid product-big-thumb"
										<c:choose>

<c:when test="${not empty item.product.images[0]}">

src="<c:url value="/resources/img/products/${item.product.images[0].imageId}"/>.jpg"
</c:when>

<c:otherwise>
src="<c:url value="/resources/img/none"/>.jpg"

</c:otherwise>

</c:choose>
										alt="" />

									</a>

								</div>
							</td>



							<td><a href='<c:url value="/product/${item.productId}"/>'><c:out
										value="${item.product.name}" /></a></td>



							<td>
								<div class="centered">

									<span class="badge badge-warning price"> <fmt:formatNumber
											maxFractionDigits="2" minFractionDigits="2"
											value="${item.product.price}" /> zł

									</span>

								</div>

							</td>



						</tr>

					</c:forEach>

				</tbody>
			</table>



			<div class="well">Dostarczenie kurierem - płatnosc przelewem -
				0 zł</div>










			<fieldset>
				<legend class="muted">
					<spring:message code="word.totalPrice" />
				</legend>
				<span class="badge badge-warning price pull-right"><fmt:formatNumber
						maxFractionDigits="2" minFractionDigits="2" value="${totalPrice}" />
					zł</span>
			</fieldset>




			<fieldset>
				<legend class="muted">
					<spring:message code="label.deliveryAddress" />
				</legend>
			</fieldset>


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






		</c:when>


		<c:otherwise>

			<div class="alert alert-info">
				<spring:message code="error.incorrectOrder" />
			</div>
		</c:otherwise>
	</c:choose>





</div>



<jsp:include page="bottom.jsp"/>



