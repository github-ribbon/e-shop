<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="top.jsp"/>




<div class="span6">

	<ul class="breadcrumb">
		<li><a href="<c:url value="/" />"><i class="icon-home"></i>
				Start</a> <span class="divider">/</span></li>
		<li><spring:message code="title.choosingFeatureType" /></li>

	</ul>







	<fieldset>
		<legend class="muted">
			<spring:message code="title.choosingFeatureType" />
		</legend>
	</fieldset>


	<div class="padding-bottom-small">
		<a href="<c:url value="/feature-type/new" />" class="btn centered"
			target="_blank"><i class="icon-plus icon-black"></i> <spring:message
				code="word.add" /></a>
	</div>


	<!-- Pagination -->
	<c:url var="pag_link" value="${pageContext.request.requestURI}" />
	<jsp:include page="pagination.jsp"></jsp:include>


	<c:choose>

		<c:when test="${not empty featureTypes}">
			<table
				class="table table-striped  table-bordered table-hover table-middle">
				<thead>
					<tr>
						<th><spring:message code="label.name" /></th>
						<th><spring:message code="label.description" /></th>
						<th></th>
					</tr>
				</thead>

				<tbody>

					<c:forEach items="${featureTypes}" var="item">
						<tr>
							<td><c:out value="${item.id.featureTypeId}" /></td>
							<td><c:out value="${item.description}" /></td>
							<td>
								<div class="centered">



									<form class="display-inline"
										action='<c:url value="/feature/new/product/${param.product_id}" />'
										method="get">

										<input type="hidden" name="feature_type_id"
											value='<c:out value="${item.id.featureTypeId}" />' /> <input
											type="hidden" name="_params"
											value='<c:out value="${param._params}" />' />

										<button type="submit" class="btn">
											<i class="icon-circle-arrow-right icon-black"></i>
											<spring:message code="word.choose" />
										</button>
									</form>

								</div>

							</td>

						</tr>

					</c:forEach>

				</tbody>
			</table>

		</c:when>
		<c:otherwise>
			<div class="alert alert-info">
				<spring:message code="empty.featureTypes" />
			</div>
		</c:otherwise>

	</c:choose>

	<!-- Pagination -->
	<c:url var="pag_link" value="${pageContext.request.requestURI}" />
	<jsp:include page="pagination.jsp"></jsp:include>














</div>



<jsp:include page="bottom.jsp"/>



