<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" 
           uri="http://java.sun.com/jsp/jstl/fmt" %>
           
<jsp:include page="top.jsp"/>




<div class="span6">

	<ul class="breadcrumb">
		<li><a href="<c:url value="/" />"><i class="icon-home"></i>
				Start</a> <span class="divider">/</span></li>
		<li><spring:message code="title.shoppingCart" /></li>

	</ul>







	<fieldset>
		<legend class="muted">
			<spring:message code="title.shoppingCart" />
		</legend>
	</fieldset>





	<c:if test="${not empty isShoppingCartIncorrect}">
		<div class="alert alert-info">
			<spring:message code="error.incorrectShoppingCart" />
		</div>
	</c:if>



	<c:choose>

		<c:when test="${not empty products}">
			<table class="table table-striped table-hover table-middle">
				<thead>
					<tr>
						<th></th>
						<th><spring:message code="label.name" /></th>
						<th>Sztuk</th>
						<th><div class="centered">
								<spring:message code="label.price" />
							</div></th>

					</tr>
				</thead>

				<tbody>

					<c:forEach items="${products}" var="item">
						<tr>
							<td class="small-thumb-cell">

								<div class="centered">


									<a href='<c:url value="/product/${item.productId}"/>'> <img
										class="img-polaroid product-big-thumb"
										<c:choose>

<c:when test="${not empty item.images[0]}">

src="<c:url value="/resources/img/products/${item.images[0].imageId}"/>.jpg"
</c:when>

<c:otherwise>
src="<c:url value="/resources/img/none"/>.jpg"

</c:otherwise>

</c:choose>
										alt="" />

									</a>

								</div>
							</td>



							<td><a href='<c:url value="/product/${item.productId}"/>'><c:out
										value="${item.name}" /></a></td>


							<td>
								<div class="centered">
									<c:set var="pk" value="${item.productId}" />
									<c:out value="${counterMap[pk]}"></c:out>

									<div>
										<a
											href="<c:url value="/cart/add/product/${item.productId}" />"
											class="btn btn-info btn-mini"><i
											class="icon-plus icon-white"></i></a><a
											href="<c:url value="/cart/remove/product/${item.productId}" />"
											class="btn btn-danger btn-mini"><i
											class="icon-minus icon-white"></i></a>

									</div>


								</div>
							</td>
							<td>
								<div class="centered">

									<c:if test="${counterMap[pk]>1}">
										<c:out value="${counterMap[pk]}"></c:out> x 
								<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"
											value="${item.price}" /> zł
								
								
								<br />
									</c:if>

									<span class="badge badge-success price"> <fmt:formatNumber
											maxFractionDigits="2" minFractionDigits="2"
											value="${item.price*counterMap[pk]}" /> zł
									</span>

								</div>

							</td>



						</tr>

					</c:forEach>

				</tbody>
			</table>










			<fieldset>
				<legend class="muted">Forma płatnosci i dostawa</legend>
			</fieldset>

			<label class="radio"> <input type="radio"
				name="optionsRadios" id="optionsRadios1" value="option1" checked>
				Dostarczenie kurierem - płatność za pobraniem - 0 zł
			</label>
			<label class="radio"> <input type="radio"
				name="optionsRadios" id="optionsRadios2" value="option2">
				Dostarczenie kurierem - płatność przelewem - 0 zł
			</label>




			<fieldset>
				<legend class="muted">
					<spring:message code="word.totalPrice" />
				</legend>
				<span class="badge badge-success price pull-right"><fmt:formatNumber
						maxFractionDigits="2" minFractionDigits="2" value="${totalPrice}" />
					zł</span>
			</fieldset>

			<div class="margin-small centered">
				<a href="<c:url value="/cart/validate" />" class="btn btn-primary">
					<i class="icon-circle-arrow-right icon-white"></i>
					<spring:message code="word.advance" />
				</a>
			</div>











		</c:when>
		<c:otherwise>
			<div class="alert alert-info">
				<spring:message code="empty.products" />
			</div>
		</c:otherwise>

	</c:choose>



</div>



<jsp:include page="bottom.jsp"/>



