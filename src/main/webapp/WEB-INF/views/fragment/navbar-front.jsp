<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
<nav class="nav">
    <div class="nav-content">
        <a href="${pageContext.request.contextPath}/front/dashboard" class="nav-brand">Air Ticketing</a>
        <ul class="nav-menu">
            <li><a href="${pageContext.request.contextPath}/front/dashboard" class="active">Accueil</a></li>
            <li><a href="${pageContext.request.contextPath}/front/flights">Vols</a></li>
            <li><a href="${pageContext.request.contextPath}/front/reservations">Mes Réservations</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Déconnexion</a></li>
        </ul>
    </div>
</nav>