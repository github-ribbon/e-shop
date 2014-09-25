<%@page import="java.lang.NullPointerException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<div class="container well footer-font padding-small">
	<div class="row">
		<div class="span4">
			<ul class="nav nav-list">
				<li class="nav-header">Informacje o sklepie</li>
				<li><a href="#">Kontakt</a></li>
				<li><a href="#">Historia</a></li>
				<li><a href="#">Serwisy partnerskie</a></li>
				<li><a href="#">Nagrody i wyróżnienia</a></li>

			</ul>
		</div>

		<div class="span4">
			<ul class="nav nav-list">
				<li class="nav-header">Dla klientów</li>
				<li><a href="#">Rodzaje dostawy</a></li>
				<li><a href="#">Formy płatności</a></li>
				<li><a href="#">Warunki gwarancji</a></li>
				<li><a href="#">Koszty transportu</a></li>
				<li><a href="#">Regulamin sklepu</a></li>
			</ul>
		</div>


		<div class="span4">
			<div class="nav-header">Dołącz do nas</div>



			<div id="share-buttons">

				<!-- Facebook -->
				<a href="http://www.facebook.com" target="_blank"><img
					src='<c:url value="/resources/img/facebook.png"/>' alt="Facebook" /></a>

				<!-- Twitter -->
				<a href="http://twitter.com" target="_blank"><img
					src="<c:url value="/resources/img/twitter.png"/>" alt="Twitter" /></a>

				<!-- Google+ -->
				<a href="https://plus.google.com" target="_blank"><img
					src="<c:url value="/resources/img/google.png"/>" alt="Google" /></a>

				<!-- Digg -->
				<a href="http://www.digg.com" target="_blank"><img
					src="<c:url value="/resources/img/diggit.png" />" alt="Digg" /></a>

				<!-- Reddit -->
				<a href="http://reddit.com" target="_blank"><img
					src="<c:url value="/resources/img/reddit.png" />" alt="Reddit" /></a>

				<!-- LinkedIn -->
				<a href="http://www.linkedin.com" target="_blank"><img
					src="<c:url value="/resources/img/linkedin.png" />" alt="LinkedIn" /></a>

				<!-- StumbleUpon-->
				<a href="http://www.stumbleupon.com" target="_blank"><img
					src="<c:url value="/resources/img/stumbleupon.png" />"
					alt="StumbleUpon" /></a>

			</div>
		</div>

	</div>



	<div class="row">
	     
		<div class="span4">&copy; Copyright by e-shop 2013</div>

		<div class="span4 offset4">
			Projekt i wykonanie: <a href="#">http://andrzejstazecki.cba.pl</a>
		</div>

	</div>

</div>



</body>
</html>
