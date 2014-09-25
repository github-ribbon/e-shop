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

		<li><spring:message code="title.productImages" /></li>

	</ul>







	<fieldset>
		<legend class="muted">
			<spring:message code="title.productImages" />
		</legend>
	</fieldset>



	<c:choose>

		<c:when test="${empty isProductIncorrect}">


			<c:if test="${param.deleted != null}">
				<div class="alert alert-success">
					<spring:message code="success.imageDeleted" />
				</div>
			</c:if>

			<c:if test="${param.created != null}">
				<div class="alert alert-success">
					<spring:message code="success.imageCreated" />
				</div>
			</c:if>


			<c:if test="${param.deleting_error != null}">
				<div class="alert alert-error">
					<spring:message code="error.imageDeleting" />
				</div>
			</c:if>

			<div class="padding-bottom-small">



				<c:url value="/image/new" var="url" />
				<form:form action="${url}" modelAttribute="newImage"
					enctype="multipart/form-data" method="post">
					<form:hidden path="productId" />

					<label><spring:message code="label.image" /></label>
					<input type="file" name="binaryContent" />
					<form:errors path="*" cssClass="alert alert-error display-block" />

					<button name="create" type="submit"
						class="btn btn-primary display-block">
						<i class="icon-upload icon-white"></i>
						<spring:message code="word.upload" />
					</button>
				</form:form>

			</div>

			<c:choose>

				<c:when test="${not empty images}">
					<table class="table table-striped table-hover table-middle">




						<c:forEach items="${images}" var="item">
							<tr>
								<td>

									<div class="centered">
										<a class="fancybox" data-fancybox-group="gallery"
											href="<c:url value="/resources/img/products/${item.imageId}"/>.jpg"
											title="Lorem ipsum dolor sit amet"><img
											class="img-polaroid product-big-thumb"
											src="<c:url value="/resources/img/products/${item.imageId}"/>.jpg"
											alt="" /></a>

									</div>

								</td>
								<td>

									<div class="centered">
										<c:url value="/image/" var="url" />
										<form action="${url}" method="post">

											<input type="hidden" name="image_id"
												value="<c:out value="${item.imageId}" />" /> <input
												type="hidden" name="product_id"
												value="<c:out value="${product.productId}"/>" />

											<button type="submit" name="delete" class="btn btn-danger">
												<i class="icon-remove icon-white"></i>
												<spring:message code="word.delete" />
											</button>

										</form>

									</div>

								</td>

							</tr>

						</c:forEach>


					</table>

				</c:when>
				<c:otherwise>
					<div class="alert alert-info">
						<spring:message code="empty.images" />
					</div>
				</c:otherwise>

			</c:choose>



		</c:when>
		<c:otherwise>
			<div class="alert alert-error">
				<spring:message code="error.incorrectProduct" />
			</div>

		</c:otherwise>
	</c:choose>














</div>



<jsp:include page="bottom.jsp"/>



