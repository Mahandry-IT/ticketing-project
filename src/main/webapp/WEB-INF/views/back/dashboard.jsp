<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Backoffice</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/fragment/navbar.jsp" %>

    <div class="container">
        <main class="main-content">
            <div class="content-header">
                <h2>Dashboard Administrateur</h2>
            </div>
            
            <div class="interface-selection">
                <div class="interface-card">
                    <h3>Gestion des Vols</h3>
                    <p>Créer, modifier et supprimer des vols</p>
                    <a href="${pageContext.request.contextPath}/back/flights" class="btn btn-primary">Gérer les vols</a>
                </div>
                
                <div class="interface-card">
                    <h3>Réservations</h3>
                    <p>Consulter et annuler les réservations</p>
                    <a href="${pageContext.request.contextPath}/WEB-INF/views/back/reservations.jsp" class="btn btn-secondary">Voir les réservations</a>
                </div>
                
                <div class="interface-card">
                    <h3>Promotions</h3>
                    <p>Créer des offres promotionnelles</p>
                    <a href="${pageContext.request.contextPath}/back/offers" class="btn btn-success">Gérer les promotions</a>
                </div>
                
                <div class="interface-card">
                    <h3>Configuration</h3>
                    <p>Paramètres système et délais</p>
                    <a href="${pageContext.request.contextPath}/WEB-INF/views/back/config.jsp" class="btn btn-warning">Configuration</a>
                </div>
            </div>
        </main>
    </div>
</body>
</html>
