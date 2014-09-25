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





		<c:if test="${not empty product.categoryId}">

			<li><a
				href="<c:url value="/product?search=true&category_id=${product.categoryId}" />"><c:out
						value="${product.categoryId}" /></a> <span class="divider">/</span></li>

		</c:if>

		<li><c:out value="${product.name}" /></li>

	</ul>

	<fieldset>
		<legend class="muted">
			<c:out value="${product.name}" />
		</legend>
	</fieldset>

	<c:choose>
		<c:when test="${empty isProductIncorrect}">

			<c:choose>
				<c:when test="${param.created != null}">
					<div class="alert alert-success">
						<spring:message code="success.productCreated" />
					</div>
				</c:when>


			</c:choose>

















			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<div class="padding-bottom-small">
					<a href='<c:url value="/product/edit/${product.productId}" />'
						class="btn"><i class="icon-pencil"></i> <spring:message
							code="word.edit" /></a>
				</div>
			</sec:authorize>

			<div class="row">
				<div class="span3">

					<a class="fancybox"
						<c:choose>

<c:when test="${not empty images[0]}">

href="<c:url value="/resources/img/products/${images[0].imageId}"/>.jpg"
</c:when>

<c:otherwise>
href="<c:url value="/resources/img/none"/>.jpg"

</c:otherwise>

</c:choose>
						data-fancybox-group="gallery" title="Lorem ipsum dolor sit amet"><img
						class="img-polaroid img-main"
						<c:choose>

<c:when test="${not empty images[0]}">

src="<c:url value="/resources/img/products/${images[0].imageId}"/>.jpg"
</c:when>

<c:otherwise>
src="<c:url value="/resources/img/none"/>.jpg"

</c:otherwise>

</c:choose>
						alt="" /></a>
				</div>

				<div class="span2 centered padding-bottom-small">

					<div class="margin-top-small">


						<span class="badge badge-success price"> <fmt:formatNumber
								maxFractionDigits="2" minFractionDigits="2"
								value="${product.price}" /> zł
						</span>
					</div>


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
								action='<c:url value="/cart/add/product/${product.productId}"/>'
								method="get">
								<button type="submit" class="btn btn-info">
									<i class="icon-shopping-cart icon-white"></i>
									<spring:message code="word.add" />
								</button>
							</form>
						</div>
					</sec:authorize>

					<!--  <span class="label label-success padding-small">Dostepny w magazynie</span>-->
					<!--<span class="label label-important padding-small">Available within 3 weeks</span>-->

					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<div class="padding-bottom-small padding-top-small">
							<a
								href='<c:url value="/product-instance/product/${product.productId}" />'
								class="btn"><i class="icon-th-large"></i> <spring:message
									code="title.productInstances" /> </a>
						</div>
					</sec:authorize>


				</div>

			</div>





			<p>


				<c:choose>

					<c:when test="${not empty images}">
						<c:forEach items="${images}" var="p" begin="1">

							<a class="fancybox"
								href='<c:url value="/resources/img/products/${p.imageId}" />.jpg'
								data-fancybox-group="gallery" title="Lorem ipsum dolor sit amet"><img
								class="img-polaroid product-thumb"
								src="<c:url value="/resources/img/products/${p.imageId}" />.jpg"
								alt="" /></a>


						</c:forEach>


					</c:when>


					<c:otherwise>

						<div class="alert alert-info">
							<spring:message code="empty.images" />
						</div>
					</c:otherwise>


				</c:choose>



			</p>

			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<div class="padding-bottom-small">
					<a href='<c:url value="/image/product/${product.productId}"/>'
						class="btn"><i class="icon-picture"></i> <spring:message
							code="title.images" /></a>
				</div>
			</sec:authorize>

			<div class="row">


				<div class="span6">


					<c:if test="${param.feature_deleted != null}">
						<div class="alert alert-success">
							<spring:message code="success.featureDeleted" />
						</div>
					</c:if>

					<c:if test="${param.opinion_created != null}">
						<div class="alert alert-success">
							<spring:message code="success.opinionCreated" />
						</div>
					</c:if>

					<c:if test="${opinionErrors}">
						<div class="alert alert-error">
							<spring:message code="error.opinion" />
						</div>
					</c:if>



					<div class="tabbable">
						<!-- Only required for left/right tabs -->
						<ul class="nav nav-tabs">
							<li class="active"><a href="#tab1" data-toggle="tab"><i
									class="icon-list-alt icon-black"></i> <spring:message
										code="productSpec" /></a></li>
							<li><a href="#tab2" data-toggle="tab"><i
									class="icon-tags icon-black"></i> Opinie</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab1">

								<c:if test="${not empty product.description}">


									<div class="well padding-small display-inline-block">
										<c:out value="${product.description}" />
									</div>

								</c:if>

								<sec:authorize access="hasRole('ROLE_ADMIN')">
									<div class="padding-bottom-small">
										<a class="btn btn-primary"
											href='<c:url value="/feature/new/product/${product.productId}"/>'>
											<i class="icon-plus icon-white"></i> <spring:message
												code="word.add" />
										</a>
									</div>
								</sec:authorize>


								<c:choose>

									<c:when test="${not empty features}">

										<table
											class="table table-striped table-bordered table-hover table-middle">
											<tbody>
												<c:forEach var="f" items="${features}">

													<tr>
														<td><c:out value="${f.id.featureTypeId}" /></td>
														<td><c:out value="${f.value}" /></td>

														<sec:authorize access="hasRole('ROLE_CLIENT')">
															<td>

																<div class="centered">
																	<a class="btn"
																		href='<c:url value="/feature/product/${product.productId}/feature-type/${f.id.featureTypeId}"/>'>
																		<i class="icon-circle-arrow-right icon-black"></i> <spring:message
																			code="word.advance" />
																	</a>
																</div>
															</td>
														</sec:authorize>
													</tr>
												</c:forEach>
											</tbody>
										</table>

									</c:when>

									<c:otherwise>

										<div class="alert alert-info">
											<spring:message code="empty.features" />
										</div>
									</c:otherwise>
								</c:choose>





							</div>

							<div class="tab-pane" id="tab2">


								<sec:authorize access="hasRole('ROLE_CLIENT')">

									<c:if test="${not empty opinion}">
										<c:url value="/opinion/new" var="url" />

										<form:form modelAttribute="opinion" action="${url}"
											method="post">

											<form:hidden path="productId" />

											<label><spring:message code="label.opinionContent" /></label>
											<form:textarea path="content" />
											<form:errors path="content"
												cssClass="alert alert-error display-block" />

											<div class="form-buttons">
												<button type="submit" class="btn btn-primary" name="create">
													<i class="icon-plus icon-white"></i>
													<spring:message code="word.add" />
												</button>
											</div>



										</form:form>
									</c:if>

								</sec:authorize>



								<c:choose>
									<c:when test="${not empty opinions}">
										<table class="table table-striped table-bordered table-hover">



											<c:forEach items="${opinions}" var="op">

												<tr>
													<td><c:out value="${op.content}" /><small
														class="muted display-block"> <fmt:formatDate
																type="both" pattern="dd-MM-yyyy H:mm:ss "
																value="${op.added}" />
													</small></td>
												</tr>
											</c:forEach>
										</table>
									</c:when>
									<c:otherwise>

										<div class="alert alert-info">
											<spring:message code="empty.opinions" />
										</div>


									</c:otherwise>
								</c:choose>


							</div>
						</div>
					</div>


				</div>



			</div>















		</c:when>

		<c:otherwise>
			<div class="alert alert-error">
				<spring:message code="error.incorrectProduct" />
			</div>
		</c:otherwise>

	</c:choose>

</div>












<jsp:include page="bottom.jsp"/>






