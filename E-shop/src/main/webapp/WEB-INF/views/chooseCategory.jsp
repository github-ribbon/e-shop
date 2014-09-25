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
		<li><spring:message code="title.choosingCategory" /></li>
	</ul>

	<fieldset>
		<legend class="muted">
			<spring:message code="title.choosingCategory" />
		</legend>
	</fieldset>






	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<div class="padding-bottom-small">
			<a href="<c:url value="/category/new" />" class="btn centered"
				target="_blank"><i class="icon-plus icon-black"></i> <spring:message
					code="word.add" /></a>
		</div>
	</sec:authorize>



	<c:choose>
		<c:when test="${!isObjectIncorrect}">


			<ul class="nav nav-list padding-small">

				<c:choose>

					<c:when test="${not empty appContextCategories}">


						<c:forEach items="${appContextCategories}" var="ac">

							<c:set var="n" value="${ac}" scope="request" />

							<c:set var="isChoose" value="true" scope="request" />

							<jsp:include page="recursiveCategoryList.jsp" />


						</c:forEach>

					</c:when>

					<c:otherwise>
						<div class="alert alert-info">
							<spring:message code="empty.categories" />
						</div>
					</c:otherwise>
				</c:choose>

			</ul>



		</c:when>
		<c:otherwise>
			<div class="alert alert-error">
				<spring:message code="error.incorrectObject" />
			</div>

		</c:otherwise>
	</c:choose>



</div>


<jsp:include page="bottom.jsp"/>



		
		
		