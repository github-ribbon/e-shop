<%@page import="java.lang.NullPointerException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="span3">



  


	<div class="well padding-horizontal-0">


		<ul class="nav nav-list">



			<li class="nav-header"><spring:message code="title.categories" /></li>

			<sec:authorize access="hasRole('ROLE_ADMIN')">

				<li><div class="padding-small centered">
						<a href="<c:url value="/category" />" class="btn centered"><i
							class="icon-pencil icon-black"></i> <spring:message
								code="word.edit" /></a> <a href="<c:url value="/category/new" />"
							class="btn centered"><i class="icon-plus icon-black"></i> <spring:message
								code="word.add" /></a>
					</div></li>

			</sec:authorize>



			<c:choose>

				<c:when test="${not empty appContextCategories}">
					<c:forEach items="${appContextCategories}" var="nodes">
						<c:set var="level" value="1" scope="request" />
						<c:set var="node" value="${nodes}" scope="request" />

						<jsp:include page="recurs.jsp" />


					</c:forEach>
				</c:when>

				<c:otherwise>
					<div class="alert alert-info">
						<spring:message code="empty.categories" />
					</div>
				</c:otherwise>
			</c:choose>

			<li class="margin-top-small"><a
				href='<c:url value="/product?search=true" />'><spring:message
						code="title.allProducts" /></a></li>

		</ul>








	</div>



</div>	