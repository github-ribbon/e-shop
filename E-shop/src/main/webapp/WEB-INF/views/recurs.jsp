<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<%@ taglib prefix="fn" 
           uri="http://java.sun.com/jsp/jstl/functions" %>





<li><a
	href='<c:url value="/product?search=true&category_id=${node.id.categoryId}"/>'><c:if
			test="${fn:length(node.subCategories) gt 0}">
 &raquo;
 </c:if>${node.id.categoryId}</a> <c:if
		test="${fn:length(node.subCategories) gt 0}">


		<ul>






			<c:forEach var="node" items="${node.subCategories}">
				<c:set var="node" value="${node}" scope="request" />
				<jsp:include page="recurs.jsp" />
			</c:forEach>
		</ul>
	</c:if></li>





