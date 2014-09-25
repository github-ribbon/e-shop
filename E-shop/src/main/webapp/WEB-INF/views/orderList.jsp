<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="top.jsp"/>




<div class="span6">

	<ul class="breadcrumb">
		<li><a href="<c:url value="/" />"><i class="icon-home"></i>
				Start</a><span class="divider">/</span></li>


		<c:choose>
			<c:when test="${(param.own != null)&&(isLogged)}">

				<li><spring:message code="clientAccount" /> <span
					class="divider">/</span></li>
			</c:when>
			<c:otherwise>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<c:if test="${not empty login}">
						<li><a href="<c:url value="/client" />"><spring:message
									code="title.clients" /></a> <span class="divider">/</span></li>
						<li><a href="<c:url value="/client/${login}" />"><spring:message
									code="title.clientDetails" /></a><span class="divider">/</span></li>
					</c:if>
				</sec:authorize>
			</c:otherwise>
		</c:choose>

		<li><spring:message code="title.orders" /></li>

	</ul>







	<fieldset>
		<legend class="muted">
			<spring:message code="title.orders" />
		</legend>
	</fieldset>


	<c:choose>
		<c:when test="${empty isClientIncorrect}">


			<form action='<c:url value="/order" />' method="get">


				<label>Status</label> <label class="radio"><input
					type="radio" name="status" value=""
					<c:if test="${param.status!='C'&&param.status!='U'&&param.status!='F'&&param.status!='D'}"> checked="checked"</c:if>>
				<spring:message code="word.all" /></label> <label class="radio"><input
					type="radio" name="status" value="C"
					<c:if test="${param.status=='C'}"> checked="checked"</c:if>>
				<spring:message code="status.waiting" /></label> <label class="radio"><input
					type="radio" name="status" value="U"
					<c:if test="${param.status=='U'}"> checked="checked"</c:if>>
				<spring:message code="word.pending" /></label> <label class="radio"><input
					type="radio" name="status" value="F"
					<c:if test="${param.status=='F'}"> checked="checked"</c:if>>
				<spring:message code="word.completed" /></label> <label class="radio"><input
					type="radio" name="status" value="D"
					<c:if test="${param.status=='D'}"> checked="checked"</c:if>>
				<spring:message code="status.cancelled" /></label>


				<c:if test="${not empty login}">
					<input type="hidden" name="login"
						value='<c:out value="${login}"></c:out>' />

					<c:if test="${(param.own != null)&&(isLogged)}">
						<input type="hidden" name="own" value="" />
					</c:if>


				</c:if>

				<div class="centered">




					<button type="submit" class="btn btn-primary display-inline-block">
						<i class="icon-search icon-white"></i>
						<spring:message code="word.search" />
					</button>


				</div>

			</form>




			<!-- Pagination -->
			<c:url var="pag_link" value="${pageContext.request.requestURI}" />
			<jsp:include page="pagination.jsp"></jsp:include>


			<c:choose>

				<c:when test="${not empty orders}">
					<table
						class="table table-striped  table-bordered table-hover table-middle">
						<thead>
							<tr>
								<th><spring:message code="label.orderId" /></th>


								<c:if test="${!((param.own != null)&&(isLogged))}">
									<th><spring:message code="label.client" /></th>
								</c:if>





								<th><spring:message code="label.modified" />
								<th>Status</th>
								<th></th>
							</tr>
						</thead>

						<tbody>

							<c:forEach items="${orders}" var="item">
								<tr>
									<td><c:out value="${item.orderId}" /></td>

									<c:if test="${!((param.own != null)&&(isLogged))}">

										<td><c:out
												value="${item.deliveryAddress.client.givenName}" /> <c:out
												value="${item.deliveryAddress.client.familyName}" /></td>
									</c:if>



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
											<a
												href="<c:url value="/order/${item.orderId}" />
					
					<c:if test="${(param.own != null)&&(isLogged)}">
					?own
					</c:if>"
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


			<!-- Pagination -->
			<c:url var="pag_link" value="${pageContext.request.requestURI}" />
			<jsp:include page="pagination.jsp"></jsp:include>





		</c:when>
		<c:otherwise>
			<div class="alert alert-error">
				<spring:message code="error.incorrectClient" />
			</div>
		</c:otherwise>

	</c:choose>










</div>



<jsp:include page="bottom.jsp"/>



