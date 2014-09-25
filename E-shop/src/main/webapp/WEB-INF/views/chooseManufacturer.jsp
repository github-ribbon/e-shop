<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="top.jsp"/>




<div class="span6">

	<ul class="breadcrumb">
		<li><a href="<c:url value="/" />"><i class="icon-home"></i>
				Start</a> <span class="divider">/</span></li>
		<li><spring:message code="title.choosingManufacturer" /></li>

	</ul>







	<fieldset>
		<legend class="muted">
			<spring:message code="title.choosingManufacturer" />
		</legend>
	</fieldset>



	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<div class="padding-bottom-small">
			<a href="<c:url value="/manufacturer/new" />" class="btn centered"
				target="_blank"><i class="icon-plus icon-black"></i> <spring:message
					code="word.add" /></a>
		</div>
	</sec:authorize>

	<c:choose>

		<c:when test="${!isObjectIncorrect}">


			<!-- Pagination -->
			<c:url var="pag_link" value="${pageContext.request.requestURI}" />
			<jsp:include page="pagination.jsp"></jsp:include>


			<c:choose>

				<c:when test="${not empty manufacturers}">
					<table
						class="table table-striped  table-bordered table-hover table-middle">
						<thead>
							<tr>
								<th><spring:message code="label.name" /></th>
								<th><spring:message code="label.description" /></th>
								<th><spring:message code="label.homePage" /></th>
								<th></th>
							</tr>
						</thead>

						<tbody>

							<c:forEach items="${manufacturers}" var="item">
								<tr>
									<td><c:out value="${item.name}" /></td>
									<td><c:out value="${item.description}" /></td>
									<td><c:out value="${item.homePage}" /></td>
									<td>
										<div class="centered">


											<c:choose>
												<c:when test="${param.action=='create'}">
													<c:url value="/product/new" var="actionUrl" />
												</c:when>

												<c:when test="${param.action=='view'}">
													<c:url value="/product/edit/${param.product_id}"
														var="actionUrl" />
												</c:when>

												<c:when test="${param.action=='list'}">
													<c:url value="/product" var="actionUrl" />
												</c:when>

											</c:choose>

											<form class="display-inline" action="${actionUrl}"
												method="get">

												<input type="hidden" name="manufacturer_id"
													value='<c:out value="${item.manufacturerId}" />' /> <input
													type="hidden" name="_params"
													value='<c:out value="${param._params}" />' />

												<c:if test="${param.action=='list'}">
													<input type="hidden" name="search" value="false" />

												</c:if>


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
						<spring:message code="empty.manufacturers" />
					</div>
				</c:otherwise>

			</c:choose>


			<!-- Pagination -->
			<c:url var="pag_link" value="${pageContext.request.requestURI}" />
			<jsp:include page="pagination.jsp"></jsp:include>






		</c:when>
		<c:otherwise>
			<div class="alert alert-error">
				<spring:message code="error.incorrectObject" />
			</div>

		</c:otherwise>
	</c:choose>












</div>



<jsp:include page="bottom.jsp"/>



