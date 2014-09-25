<%@page import="java.lang.NullPointerException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Sklep internetowy</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="<c:url value="/resources/css/custom.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/bootstrap.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/bootstrap-responsive.css" />"
	rel="stylesheet">

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="js/html5shiv.js"></script>
    <![endif]-->


<!-- Add jQuery library -->
<script type="text/javascript"
	src="<c:url value="/resources/js/fancybox/lib/jquery-1.8.2.min.js" />"></script>

<!-- Add mousewheel plugin (this is optional) -->
<script type="text/javascript"
	src="<c:url value="/resources/js/fancybox/lib/jquery.mousewheel-3.0.6.pack.js" />"></script>

<!-- Add fancyBox main JS and CSS files -->
<script type="text/javascript"
	src="<c:url value="/resources/js/fancybox/source/jquery.fancybox.js?v=2.1.3" />"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/js/fancybox/source/jquery.fancybox.css?v=2.1.2" />"
	media="screen" />

<!-- Add Button helper (this is optional) -->
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/js/fancybox/source/helpers/jquery.fancybox-buttons.css?v=1.0.5" />" />
<script type="text/javascript"
	src="<c:url value="/resources/js/fancybox/source/helpers/jquery.fancybox-buttons.js?v=1.0.5" />"></script>

<!-- Add Thumbnail helper (this is optional) -->
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/js/fancybox/source/helpers/jquery.fancybox-thumbs.css?v=1.0.7" />" />
<script type="text/javascript"
	src="<c:url value="/resources/js/fancybox/source/helpers/jquery.fancybox-thumbs.js?v=1.0.7" />"></script>

<!-- Add Media helper (this is optional) -->
<script type="text/javascript"
	src="<c:url value="/resources/js/fancybox/source/helpers/jquery.fancybox-media.js?v=1.0.5" />"></script>

<script type="text/javascript">
		$(document).ready(function() {
			/*
			 *  Simple image gallery. Uses default settings
			 */

			$('.fancybox').fancybox();
$().tooltip('hover');

$('.carousel').carousel({
	  interval: 5000
	});

$('.dropdown-toggle').dropdown();



jQuery('ul.nav li.dropdown').hover(function() {
	  jQuery(this).find('.dropdown-menu').stop(true, true).delay(100).fadeIn();
	}, function() {
	  jQuery(this).find('.dropdown-menu').stop(true, true).delay(100).fadeOut();
	});
			});
			
			
			
	</script>

<script src="<c:url value="/resources/js/bootstrap-transition.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-alert.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-modal.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-dropdown.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-scrollspy.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-tab.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-tooltip.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-popover.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-button.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-collapse.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-carousel.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-typeahead.js" />"></script>
</head>

<body>

	<!-- Fixed navbar -->
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<button type="button" class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="brand"><i class="icon-shopping-cart icon-white"
					style="vertical-align: middle"></i> e-shop</a>
				<div class="nav-collapse collapse">
					<ul class="nav">
						<li><a href="<c:url value="/" />"><i
								class="icon-home icon-white"></i></a></li>



						<sec:authorize
							access="(hasRole('ROLE_ADMIN')&&(!hasRole('ROLE_CLIENT')))||(!hasRole('ROLE_ADMIN')&&(hasRole('ROLE_CLIENT')))">
							<li><a href="<c:url value="/usr" />"> <i class="icon-wrench icon-white"></i> <spring:message
										code="title.settings" /></a></li>



						</sec:authorize>


						<sec:authorize
							access="!hasRole('ROLE_ADMIN')&&hasRole('ROLE_CLIENT')">
							<li><a
								href="<c:url value="/order?login=${sessionScope.usr.id.login}" />"><i
									class="icon-th-large icon-white"></i> <spring:message
										code="title.orders" /></a></li>
							<li><a href="<c:url value="/delivery-address" />"><i
									class="icon-th-large icon-white"></i> <spring:message
										code="title.deliveryAddresses" /></a></li>



						</sec:authorize>




						<sec:authorize
							access="hasRole('ROLE_ADMIN')&&hasRole('ROLE_CLIENT')">

							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown"><i class="icon-user icon-white"></i>
									<spring:message code="clientAccount" /> <b class="caret"></b></a>
								<ul class="dropdown-menu">
									<li><a href="<c:url value="/usr" />"><i class="icon-wrench icon-black"></i> <spring:message
												code="title.settings" /></a></li>
									<li><a
										href="<c:url value="/order?login=${sessionScope.usr.id.login}&own" />"><i
									class="icon-th-large icon-black"></i> <spring:message
												code="title.orders" /></a></li>
									<li><a href="<c:url value="/delivery-address" />"><i
									class="icon-th-large icon-black"></i> <spring:message
												code="title.deliveryAddresses" /></a></li>
								</ul></li>


						</sec:authorize>






						<sec:authorize access="!hasRole('ROLE_ADMIN')">
							<li><a href="#"><i class="icon-map-marker icon-white"></i>
									Kontakt</a></li>

						</sec:authorize>


						<sec:authorize access="hasRole('ROLE_ADMIN')">

							<li><a href="<c:url value="/feature-type" />"><i
									class="icon-th-large icon-white"></i> <spring:message
										code="title.featureTypes" /></a></li>

							<li><a href="<c:url value="/manufacturer" />"><i
									class="icon-th-large icon-white"></i> <spring:message
										code="title.manufacturers" /></a></li>
							<!-- <li class="divider-vertical"></li> -->
							<li><a href="<c:url value="/client" />"><i
									class="icon-th-large icon-white"></i> <spring:message
										code="title.clients" /></a></li>

							<li><a href="<c:url value="/order" />"><i
									class="icon-th-large icon-white"></i> <spring:message
										code="title.orders" /></a></li>
							<!-- 
										<li><a href="<c:url value="/stats" />"><i
									class="icon-map-marker icon-white"></i> Statystyki</a></li>
										-->

						</sec:authorize>



						<li>



							<div class="navbar-form">
								&nbsp;
								<c:choose>

									<c:when test="${not empty sessionScope.cart}">
										<a href='<c:url value="/cart" />' class="btn btn-info"><i
											class=" icon-shopping-cart icon-white"></i> <spring:message
												code="word.cart" /> <span class="badge badge-info"
											style="background: white; color: #0044cc"><c:out
													value="${fn:length(sessionScope.cart)}" /></span> </a>
									</c:when>

									<c:otherwise>

										<sec:authorize
											access="!hasRole('ROLE_CLIENT')&&(!hasRole('ROLE_ADMIN'))">
											<span class="btn disabled"><i
												class="icon-shopping-cart"></i> <spring:message
													code="word.cart" /></span>

										</sec:authorize>

										<sec:authorize access="hasRole('ROLE_CLIENT')">
											<a href='<c:url value="/cart" />' class="btn"><i
												class="icon-shopping-cart"></i> <spring:message
													code="word.cart" /></a>
										</sec:authorize>

									</c:otherwise>
								</c:choose>

							</div>
						</li>


					</ul>








				</div>
				<!--/.nav-collapse -->



			</div>
		</div>
	</div>














	<div class="container padding-top">


		<div class="row">