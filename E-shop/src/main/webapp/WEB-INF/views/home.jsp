<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" 
           uri="http://java.sun.com/jsp/jstl/fmt" %>


<jsp:include page="top.jsp"/>



<div class="span6">

	<ul class="breadcrumb">
		<li><i class="icon-home"></i> Start</li>
	</ul>




	<fieldset>
		<legend class="muted">
			<spring:message code="welcome" />
		</legend>
	</fieldset>



	<c:if test="${not empty bestsellers}">

		<div id="myCarousel" class="carousel slide">
			<ol class="carousel-indicators">

				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<c:forEach items="${bestsellers}" var="p" begin="1"
					varStatus="status">

					<li data-target="#myCarousel"
						data-slide-to="<c:out value="${status}"/>"></li>

				</c:forEach>

			</ol>
			<!-- Carousel items -->
			<div class="carousel-inner">


				<div class="active item">


					<a href='<c:url value="/product/${bestsellers[0].productId}"/>'>
						<c:choose>
							<c:when test="${not empty bestsellers[0].images}">
								<img class="img-rounded"
									src="<c:url value="/resources/img/products/${bestsellers[0].images[0].imageId}"/>.jpg"
									alt="" />
							</c:when>
							<c:otherwise>
								<img class="img-rounded"
									src="<c:url value="/resources/img/none"/>.jpg" alt="" />
							</c:otherwise>
						</c:choose>
					</a>


					<div class="carousel-caption">
						<h4>
							<c:out value="${bestsellers[0].name}" />

							<span class="badge badge-success price"> <fmt:formatNumber
									maxFractionDigits="2" minFractionDigits="2"
									value="${bestsellers[0].price}" /> zł
							</span>
						</h4>
						<p>
							<c:out value="${bestsellers[0].description}" />
						</p>
					</div>
				</div>




				<c:forEach items="${bestsellers}" var="item" begin="1">


					<div class="item">

						<a href='<c:url value="/product/${item.productId}"/>'> <c:choose>
								<c:when test="${not empty item.images}">
									<img class="img-rounded"
										src="<c:url value="/resources/img/products/${item.images[0].imageId}"/>.jpg"
										alt="" />
								</c:when>
								<c:otherwise>
									<img class="img-rounded"
										src="<c:url value="/resources/img/none"/>.jpg" alt="" />
								</c:otherwise>
							</c:choose>
						</a>

						<div class="carousel-caption">
							<h4>
								<c:out value="${item.name}" />


								<span class="badge badge-success price"> <fmt:formatNumber
										maxFractionDigits="2" minFractionDigits="2"
										value="${item.price}" /> zł
								</span>
							</h4>
							<p>
								<c:out value="${item.description}" />
							</p>
						</div>

					</div>

				</c:forEach>

			</div>



			<!-- Carousel nav -->
			<a class="carousel-control left" href="#myCarousel" data-slide="prev">&lsaquo;</a>
			<a class="carousel-control right" href="#myCarousel"
				data-slide="next">&rsaquo;</a>
		</div>


	</c:if>



</div>



<jsp:include page="bottom.jsp"/>



