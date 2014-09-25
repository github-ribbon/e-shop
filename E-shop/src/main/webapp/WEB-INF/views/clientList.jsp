<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="top.jsp"/>




<div class="span6">

	<ul class="breadcrumb">
		<li><a href="<c:url value="/" />"><i class="icon-home"></i>
				Start</a> <span class="divider">/</span></li>


		<li><spring:message code="title.clients" /></li>



	</ul>







	<fieldset>
		<legend class="muted">
			<spring:message code="title.clients" />
		</legend>
	</fieldset>




	<form action='<c:url value="/client" />' method="get">

		<div class="centered">

			<div class="display-inline-block">
				<label><spring:message code="label.givenName" /></label> <input
					type="text" name="given_name" value="${param.given_name}" />
			</div>

			<div class="display-inline-block">
				<label><spring:message code="label.familyName" /></label> <input
					type="text" name="family_name" value="${param.family_name}" />
			</div>

			<div class="centered">
				<button type="submit" class="btn btn-primary display-inline-block">
					<spring:message code="word.search" />
				</button>
			</div>

		</div>

	</form>





	<!-- Pagination -->
	<c:url var="pag_link" value="${pageContext.request.requestURI}" />
	<jsp:include page="pagination.jsp"></jsp:include>


	<c:choose>

		<c:when test="${not empty clients}">
			<table
				class="table table-striped  table-bordered table-hover table-middle">
				<thead>
					<tr>
						<th>Login</th>
						<th><spring:message code="label.givenName" /></th>
						<th><spring:message code="label.familyName" />
						<th></th>
					</tr>
				</thead>

				<tbody>

					<c:forEach items="${clients}" var="item">
						<tr>
							<td><c:out value="${item.id.login}" /></td>
							<td><c:out value="${item.givenName}" /></td>
							<td><c:out value="${item.familyName}" /></td>

							<td>
								<div class="centered">
									<a href="<c:url value="/client/${item.id.login}" />"
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
				<spring:message code="empty.clients" />
			</div>
		</c:otherwise>

	</c:choose>



	<!-- Pagination -->
	<c:url var="pag_link" value="${pageContext.request.requestURI}" />
	<jsp:include page="pagination.jsp"></jsp:include>













</div>



<jsp:include page="bottom.jsp"/>



