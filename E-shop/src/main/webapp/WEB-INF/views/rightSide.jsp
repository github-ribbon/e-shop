<%@page import="java.lang.NullPointerException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>




<div class="span3">


	<sec:authorize access="!authenticated">
		<div class="well">



			<c:url value="/j_spring_security_check" var="url" />

			<form class="form-vertical" action="${url}" method="post">

				<h4 class="muted">
					<spring:message code="title.logging" />
				</h4>

				<div class="field">
					<label>Login</label> <input id="j_username" name="j_username"
						type="text" class="control-wide" placeholder="login" />
				</div>

				<div class="field">
					<label><spring:message code="label.password" /></label> <input
						id="j_password" name="j_password" type="password"
						class="control-wide" placeholder="********" />
				</div>







				<div class="button_container">
					<button type="submit" class="btn btn-primary">
						<i class="icon-circle-arrow-right icon-white"></i>
						<spring:message code="word.logIn" />
					</button>

					<a href='<c:url value="/usr/register" />' class="btn"> <i
						class="icon-pencil"></i> <spring:message code="word.register" />
					</a>
				</div>


				<label class="checkbox margin-top-small"> <input
					id="j_remember" name="_spring_security_remember_me" type="checkbox" />
					<spring:message code="rememberMe" />
				</label>

			</form>

			<a href="<c:url value="/usr/reset-password" />"><spring:message
					code="word.resetPassword" /></a>



			<c:choose>
				<c:when test="${param.failure!=null}">
					<div class="alert alert-error padding-small margin-small">
						<spring:message code="authenticationError" />
					</div>
				</c:when>
				<c:when test="${param.logged_out!=null}">
					<div class="alert alert-success padding-small margin-small">
						<spring:message code="logoutSuccess" />
					</div>
				</c:when>
			</c:choose>

		</div>

	</sec:authorize>



	<sec:authorize access="authenticated">

		<div class="well">


			<div class="form-horizontal centered margin-none">


				<c:choose>
					<c:when test="${not empty sessionScope.usr.client}">
						<c:set var="userDetails"
							value="${sessionScope.usr.client.givenName} ${sessionScope.usr.client.familyName}" />
					</c:when>
					<c:otherwise>
						<c:set var="userDetails" value="${usr.id.login}" />

					</c:otherwise>


				</c:choose>

				<spring:message code="logInfo" />
				<div class="bold">
					<c:out value="${userDetails}" />
				</div>



				<a href='<c:url value="/logout" />' class="btn btn-inverse"> <i
					class="icon-off icon-white"></i> <spring:message code="word.logout" />
				</a>





			</div>



		</div>

	</sec:authorize>







	<div class="well">
		<h4 class="muted">Wyszukaj</h4>

		<form action='<c:url value="/product?search=true" />' method="get">
			<input type="hidden" name="search" value="true" /> <input
				name="name" class="control-wide" type="text"
				placeholder="<spring:message code="word.productName" />" />
			<button class="btn btn-primary control-medium" type="submit">
				<i class="icon-search icon-white"></i>
				<spring:message code="word.search" />
			</button>
		</form>




	</div>






</div>

</div>
	
	
	
	
	
	
	</div>
      
	