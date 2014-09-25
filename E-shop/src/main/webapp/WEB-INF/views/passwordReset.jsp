<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="top.jsp"/>








<div class="span6">

	<ul class="breadcrumb">
		<li><a href="<c:url value="/" />"><i class="icon-home"></i>
				Start</a> <span class="divider">/</span></li>
		<li><spring:message code="title.passwordReset" /></li>
	</ul>





	<fieldset>
		<legend class="muted">
			<spring:message code="title.passwordReset" />
		</legend>
	</fieldset>

	<c:choose>

		<c:when test="${success}">

			<div class="alert alert-success">
				<spring:message code="success.passwordReset" />
			</div>
		</c:when>
		<c:otherwise>

			<c:if test="${not empty failure}">
				<div class="alert alert-error">
					<spring:message code="error.passwordReset" />
				</div>
			</c:if>

			<c:url value="/usr/reset-password" var="url" />

			<form action="${url}" method="post">

				<label>Login</label> <input type="text" name="login"
					value="<c:out value="${login}"></c:out>" /> <label>E-mail</label>
				<input type="text" name="email"
					value="<c:out value="${email}"></c:out>" />

				<div class="form-buttons">
					<button type="submit" class="btn btn-primary">
						<i class="icon-ok icon-white"></i>
						<spring:message code="word.resetPassword" />
					</button>
				</div>

			</form>


		</c:otherwise>
	</c:choose>













</div>


<jsp:include page="bottom.jsp"/>



