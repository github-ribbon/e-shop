<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="top.jsp"/>








<div class="span6">

	<ul class="breadcrumb">
		<li><a href="<c:url value="/" />"><i class="icon-home"></i>
				Start</a> <span class="divider">/</span></li>



		<li><a href="<c:url value="/product?search=true" />"> <spring:message
					code="title.products" /></a> <span class="divider">/</span></li>

		<c:if test="${empty isProductInstanceIncorrect}">


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


		<li><spring:message code="title.productInstanceDetails" /></li>




	</ul>





	<fieldset>
		<legend class="muted">
			<spring:message code="title.productInstanceDetails" />
		</legend>
	</fieldset>

	<c:choose>
		<c:when test="${empty isProductInstanceIncorrect}">

			<c:choose>
				<c:when test="${param.created != null}">
					<div class="alert alert-success">
						<spring:message code="success.productInstanceCreated" />
					</div>
				</c:when>

			</c:choose>



			<c:url value="/product-instance/" var="url" />

			<form:form modelAttribute="productInstance" action="${url}"
				method="post">

				<form:hidden path="productInstanceId" />


				<label><spring:message code="label.productInstanceId" /></label>

				<div class="well padding-small display-inline-block">
					<c:out value="${productInstance.productInstanceId}" />
				</div>

				<label><spring:message code="label.product" /></label>

				<div class="well padding-small display-inline-block">
					<c:out value="${product.name}" />
				</div>

				<label><spring:message code="label.added" /></label>

				<div class="well padding-small display-inline-block">
					<fmt:formatDate type="both" pattern="dd-MM-yyyy H:mm:ss "
						value="${productInstance.added}" />
				</div>



				<div class="form-buttons">

					<c:choose>

						<c:when test="${!isDependent}">
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






			<fieldset>
				<legend class="muted">
					<spring:message code="title.orders" />
				</legend>
			</fieldset>





			<c:choose>

				<c:when test="${not empty orders}">
					<table
						class="table table-striped  table-bordered table-hover table-middle">
						<thead>
							<tr>
								<th><spring:message code="label.orderId" /></th>
								<th><spring:message code="label.client" /></th>
								<th><spring:message code="label.modified" /></th>
								<th>Status</th>
								<th></th>
							</tr>
						</thead>

						<tbody>

							<c:forEach items="${orders}" var="item">
								<tr>
									<td><c:out value="${item.orderId}" /></td>
									<td><c:out
											value="${item.deliveryAddress.client.givenName}" /> <c:out
											value="${item.deliveryAddress.client.familyName}" /></td>
									<td><fmt:formatDate type="both"
											pattern="dd-MM-yyyy H:mm:ss " value="${item.modified}" /></td>
									<td><c:choose>
											<c:when test="${item.dbStatusId=='C'}">
												<span class="badge badge-warning padding-small"> <spring:message
														code="status.waiting" /></span>
											</c:when>
											<c:when test="${item.dbStatusId=='U'}">
												<span class="badge badge-info padding-small"> <spring:message
														code="word.pending" />
												</span>
											</c:when>
											<c:when test="${item.dbStatusId=='F'}">
												<span class="badge badge-success padding-small "> <spring:message
														code="word.completed" />


												</span>
											</c:when>
											<c:when test="${item.dbStatusId=='D'}">
												<span class="badge badge-important padding-small"> <spring:message
														code="status.cancelled" /></span>
											</c:when>
										</c:choose></td>

									<td>
										<div class="centered">
											<a href="<c:url value="/order/${item.orderId}" />"
												class="btn"><i
												class="icon-circle-arrow-right icon-black"></i> <spring:message
													code="word.advance" /></a>
										</div>

									</td>

								</tr>

							</c:forEach>

						</tbody>
					</table>

				</c:when>
				<c:otherwise>
					<div class="alert alert-info">
						<spring:message code="empty.orders" />
					</div>
				</c:otherwise>

			</c:choose>








		</c:when>

		<c:otherwise>
			<div class="alert alert-error">
				<spring:message code="error.incorrectProductInstance" />
			</div>
		</c:otherwise>

	</c:choose>

</div>












<jsp:include page="bottom.jsp"/>






