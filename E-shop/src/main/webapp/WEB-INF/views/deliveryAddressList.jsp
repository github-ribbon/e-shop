<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="top.jsp"/>




<div class="span6">

	<ul class="breadcrumb">
		<li><a href="<c:url value="/" />"><i class="icon-home"></i>
				Start</a> <span class="divider">/</span></li>


		<li><spring:message code="title.deliveryAddresses" /></li>



	</ul>







	<fieldset>
		<legend class="muted">
			<spring:message code="title.deliveryAddresses" />
		</legend>
	</fieldset>



	<c:if test="${param.deleted != null}">
		<div class="alert alert-success">
			<spring:message code="success.deliveryAddressDeleted" />
		</div>
	</c:if>



	<div class="padding-bottom-small">
		<a href="<c:url value="/delivery-address/new" />" class="btn centered"><i
			class="icon-plus icon-black"></i> <spring:message code="word.add" /></a>
	</div>



	<!-- Pagination -->
	<c:url var="pag_link" value="${pageContext.request.requestURI}" />
	<jsp:include page="pagination.jsp"></jsp:include>


	<c:choose>

		<c:when test="${not empty deliveryAddresses}">
			<table
				class="table table-striped  table-bordered table-hover table-middle">
				<thead>
					<tr>
						<th><spring:message code="label.street" /></th>
						<th><spring:message code="label.postCode" /></th>
						<th><spring:message code="label.city" /></th>
						<th></th>
					</tr>
				</thead>

				<tbody>

					<c:forEach items="${deliveryAddresses}" var="item">
						<tr>
							<td><c:out value="${item.street}" /></td>
							<td><c:out value="${item.postCode}" /></td>
							<td><c:out value="${item.city}" /></td>
							<td>
								<div class="centered">
									<a
										href="<c:url value="/delivery-address/${item.deliveryAddressId}" />"
										class="btn"><i class="icon-circle-arrow-right icon-black"></i>
										<spring:message code="word.advance" /></a>
								</div>

							</td>

						</tr>

					</c:forEach>

				</tbody>
			</table>

		</c:when>
		<c:otherwise>
			<div class="alert alert-info">
				<spring:message code="empty.deliveryAddresses" />
			</div>
		</c:otherwise>

	</c:choose>


	<!-- Pagination -->
	<c:url var="pag_link" value="${pageContext.request.requestURI}" />
	<jsp:include page="pagination.jsp"></jsp:include>














</div>



<jsp:include page="bottom.jsp"/>



