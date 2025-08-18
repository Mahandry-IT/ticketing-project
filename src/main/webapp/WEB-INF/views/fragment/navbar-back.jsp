<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
<nav class="nav">
    <div class="nav-content">
        <a href="${pageContext.request.contextPath}/back/dashboard" class="nav-brand">Backoffice</a>
        <ul class="nav-menu">
            <li><a href="${pageContext.request.contextPath}/back/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/back/flights">Gestion Vols</a></li>
            <li><a href="${pageContext.request.contextPath}/WEB-INF/views/back/reservations.jsp">Réservations</a></li>
            <li><a href="${pageContext.request.contextPath}/back/offers">Promotions</a></li>
            <li><a href="${pageContext.request.contextPath}/WEB-INF/views/back/config.jsp" class="active">Configuration</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Déconnexion</a></li>
        </ul>
    </div>
</nav>
