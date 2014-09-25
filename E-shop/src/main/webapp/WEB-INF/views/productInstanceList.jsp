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


		<li><spring:message code="title.productInstances" /></li>



	</ul>







	<fieldset>
		<legend class="muted">
			<spring:message code="title.productInstances" />
		</legend>
	</fieldset>



	<c:if test="${param.deleted != null}">
		<div class="alert alert-success">
			<spring:message code="success.productInstanceDeleted" />
		</div>
	</c:if>




	<c:choose>
		<c:when test="${empty isProductIncorrect}">



			<div class="padding-bottom-small">
				<a
					href="<c:url value="/product-instance/new/product/${product.productId}" />"
					class="btn centered"><i class="icon-plus icon-black"></i> <spring:message
						code="word.add" /></a>
			</div>


			<label><spring:message code="label.product" /></label>

			<div class="well padding-small display-inline-block">
				<c:out value="${product.name}" />
			</div>




			<!-- Pagination -->
			<c:url var="pag_link" value="${pageContext.request.requestURI}" />
			<jsp:include page="pagination.jsp"></jsp:include>



			<c:choose>

				<c:when test="${not empty productInstances}">
					<table
						class="table table-striped  table-bordered table-hover table-middle">
						<thead>
							<tr>
								<th><spring:message code="label.productInstanceId" /></th>
								<th><spring:message code="label.added" /></th>
								<th></th>
							</tr>
						</thead>

						<tbody>

							<c:forEach items="${productInstances}" var="item">
								<tr>
									<td><c:out value="${item.productInstanceId}" /></td>
									<td><fmt:formatDate type="both"
											pattern="dd-MM-yyyy H:mm:ss " value="${item.added}" /></td>
									<td>
										<div class="centered">
											<a
												href="<c:url value="/product-instance/${item.productInstanceId}" />"
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
						<spring:message code="empty.productInstances" />
					</div>
				</c:otherwise>

			</c:choose>


			<!-- Pagination -->
			<c:url var="pag_link" value="${pageContext.request.requestURI}" />
			<jsp:include page="pagination.jsp"></jsp:include>




		</c:when>

		<c:otherwise>
			<div class="alert alert-error">
				<spring:message code="error.incorrectProduct" />
			</div>

		</c:otherwise>

	</c:choose>












</div>



<jsp:include page="bottom.jsp"/>



