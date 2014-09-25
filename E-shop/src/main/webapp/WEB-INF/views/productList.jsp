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


		<li><a href="<c:url value="/product?search=true" />"> <spring:message
					code="title.products" /></a> <span class="divider">/</span></li>

		<c:forEach items="${supCategories}" var="c">

			<li><a
				href="<c:url value="/product?search=true&category_id=${c}" />"><c:out
						value="${c}" /></a> <span class="divider">/</span></li>

		</c:forEach>


		<c:if test="${not empty categoryId}">

			<li><c:out value="${categoryId}" /></li>

		</c:if>



	</ul>







	<fieldset>
		<legend class="muted">
			<spring:message code="title.products" />
		</legend>
	</fieldset>




	<c:if test="${param.deleted != null}">
		<div class="alert alert-success">
			<spring:message code="success.productDeleted" />
		</div>
	</c:if>




	<form action='<c:url value="/product" />' method="get">

		<input type="hidden" name="search" value="true" />




		<div class="centered">


			<input type="text" name="name" value="${name}"
				placeholder="<spring:message code="label.name" />" />




			<button type="submit" class="btn btn-primary display-inline-block"
				style="margin-left: -5px; margin-top: -10px">
				
				<i class="icon-search icon-white"></i>
				<spring:message code="word.search" />
			</button>




		</div>




		<div class="display-inline-block">
			<label><spring:message code="label.manufacturer" /></label>




			<c:if test="${not empty manufacturerId}">
				<input type="hidden" name="manufacturer_id"
					value="${manufacturerId}" />
				<div class="well padding-small display-inline-block">
					<c:out value="${manufacturerName}" />
				</div>
			</c:if>



			<button type="submit" class="btn btn-link" name="choose_manufacturer">
				<spring:message code="word.choose" />
			</button>


		</div>





		<div class="display-inline-block">

			<label><spring:message code="label.category" /></label>


			<c:if test="${not empty categoryId}">
				<input type="hidden" name="category_id" value="${categoryId}" />
				<div class="well padding-small display-inline-block">
					<c:out value="${categoryId}" />
				</div>
			</c:if>




			<button type="submit" class="btn btn-link" name="choose_category">
				<spring:message code="word.choose" />
			</button>




		</div>



		<div class="display-inline-block padding-left-small">

			<label><spring:message code="label.price" /></label> <input
				type="radio" name="ascending" value="true"
				<c:if test="${ascending==true}">
checked="checked"
</c:if>>
			Rosnąco <input type="radio" name="ascending" value="false"
				<c:if test="${ascending==false}">
checked="checked"
</c:if>>
			Malejące

		</div>






	</form>



	<c:if test="${not empty manufacturerId}">

	</c:if>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<div class="padding-bottom-small">

			<c:url value="/product/new?_params" var="addProductUrl">
				<c:if test="${not empty manufacturerId}">
					<c:param name="manufacturer_id">${manufacturerId}</c:param>
				</c:if>
				<c:if test="${not empty categoryId}">
					<c:param name="category_id">${categoryId}</c:param>
				</c:if>
			</c:url>

			<a href="${addProductUrl}" class="btn centered"><i
				class="icon-plus icon-black"></i> <spring:message code="word.add" /></a>
		</div>
	</sec:authorize>

	<c:if test="${param.search==true}">


		<!-- Pagination -->
		<c:url var="pag_link" value="${pageContext.request.requestURI}" />
		<jsp:include page="pagination.jsp"></jsp:include>


		<c:choose>

			<c:when test="${not empty products}">
				<table class="table table-striped table-hover table-middle">
					<thead>
						<tr>
							<th></th>
							<th><spring:message code="label.name" /></th>
							<th><spring:message code="label.price" /></th>

						</tr>
					</thead>

					<tbody>




						<c:forEach items="${products}" var="item">
							<tr>
								<td class="thumb-cell">

									<div class="centered">

										<a href='<c:url value="/product/${item.productId}"/>'> <img
											class="img-polaroid big-product-thumb"
											<c:choose>

<c:when test="${not empty item.images[0]}">

src="<c:url value="/resources/img/products/${item.images[0].imageId}"/>.jpg"
</c:when>

<c:otherwise>
src="<c:url value="/resources/img/none"/>.jpg"

</c:otherwise>

</c:choose>
											title="Lorem ipsum dolor sit amet" />

										</a>

									</div>

								</td>



								<td><a href='<c:url value="/product/${item.productId}"/>'><c:out
											value="${item.name}" /></a></td>
								<td>

									<div class="centered">

										<span class="badge badge-success price"> <fmt:formatNumber
												maxFractionDigits="2" minFractionDigits="2"
												value="${item.price}" /> zł
										</span>




										<sec:authorize access="!hasRole('ROLE_CLIENT')">

											<div class="margin-top-small">
												<form class="form-horizontal">
													<span class="btn btn-info disabled"
														title='<spring:message code="shoppingDenied" />'><i
														class="icon-shopping-cart icon-white"></i> <spring:message
															code="word.add" /></span>
												</form>
											</div>

										</sec:authorize>

										<sec:authorize access="hasRole('ROLE_CLIENT')">
											<div class="margin-top-small">
												<form class="form-horizontal"
													action='<c:url value="/cart/add/product/${item.productId}"/>'
													method="get">
													<button type="submit" class="btn btn-info">
														<i class="icon-shopping-cart icon-white"></i>
														<spring:message code="word.add" />
													</button>
												</form>
											</div>
										</sec:authorize>


									</div>



								</td>


							</tr>

						</c:forEach>

					</tbody>
				</table>

			</c:when>
			<c:otherwise>
				<div class="alert alert-info">
					<spring:message code="empty.products" />
				</div>
			</c:otherwise>

		</c:choose>


		<!-- Pagination -->
		<c:url var="pag_link" value="${pageContext.request.requestURI}" />
		<jsp:include page="pagination.jsp"></jsp:include>




	</c:if>












</div>



<jsp:include page="bottom.jsp"/>



