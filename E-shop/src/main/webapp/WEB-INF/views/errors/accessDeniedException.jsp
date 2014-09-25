<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>



<jsp:include page="../top.jsp"/>



<div class="span6">

	<ul class="breadcrumb">
		<li><a href="<c:url value="/" />"><i class="icon-home"></i>
				Start</a> <span class="divider">/</span></li>
		<li><spring:message code="appError" /></li>
	</ul>




	<fieldset>
		<legend class="muted">
			<spring:message code="appError" />
		</legend>
	</fieldset>


		
		<div class="alert alert-error">
			<spring:message code="accessDenied" />
			</div>
			
			
	


	</div>



<jsp:include page="../bottom.jsp">
	<jsp:param value="value" name="paramName"/>
</jsp:include>

