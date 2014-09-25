<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<%@ taglib prefix="fn" 
           uri="http://java.sun.com/jsp/jstl/functions" %>




<li><c:choose>
		<c:when test="${not empty isChoose}">


			<c:if test="${param.source=='category'}">
				<c:choose>
					<c:when test="${param.action.equals('create')}">
						<c:url value="/category/new" var="actionUrl" />
					</c:when>
					<c:when test="${param.action.equals('view')}">
						<c:url value="/category/${param.category_id}" var="actionUrl" />
					</c:when>
				</c:choose>
			</c:if>


			<c:if test="${param.source=='product'}">
				<c:choose>
					<c:when test="${param.action.equals('create')}">
						<c:url value="/product/new" var="actionUrl" />
					</c:when>
					<c:when test="${param.action.equals('view')}">
						<c:url value="/product/edit/${param.product_id}" var="actionUrl" />
					</c:when>
					<c:when test="${param.action.equals('list')}">
						<c:url value="/product" var="actionUrl" />
					</c:when>
				</c:choose>
			</c:if>



			<form class="margin-none" action="${actionUrl}" method="get">



				<c:if test="${param.action.equals('list')}">
					<input type="hidden" name="search" value="false" />
				</c:if>

				<c:if test="${param.source=='product'}">
					<input type="hidden" name="category_id"
						value='<c:out value="${n.id.categoryId}"></c:out>' />
				</c:if>

				<c:if test="${param.source=='category'}">
					<input type="hidden" name="sup_category_id"
						value='<c:out value="${n.id.categoryId}"></c:out>' />
				</c:if>

				<input type="hidden" name="_params"
					value='<c:out value="${param._params}" />' />

				<button class="btn btn-link" type="submit">
					<c:if test="${fn:length(n.subCategories) gt 0}"> &raquo;</c:if>
					<c:out value="${n.id.categoryId}" />
				</button>
			</form>



		</c:when>
		<c:otherwise>
			<a class="btn btn-link text-left"
				href='<c:url value="/category/${n.id.categoryId}" />'> <c:if
					test="${fn:length(n.subCategories) gt 0}"> &raquo;</c:if>
				${n.id.categoryId}
			</a>
		</c:otherwise>

	</c:choose> <c:if test="${fn:length(n.subCategories) gt 0}">


		<ul>






			<c:forEach var="n" items="${n.subCategories}">
				<c:set var="n" value="${n}" scope="request" />
				<jsp:include page="recursiveCategoryList.jsp" />
			</c:forEach>
		</ul>
	</c:if></li>





