<%--
  Created by IntelliJ IDEA.
  User: Mahandry
  Date: 10/08/2025
  Time: 14:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Système de Ticketing - Accueil</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
<div class="container">
  <header class="header">
    <h1>Système de Ticketing Aérien</h1>
    <p>Choisissez votre interface</p>
  </header>

  <main class="main-content">
    <div class="interface-selection">
      <div class="interface-card">
        <h2>BackOffice</h2>
        <p>Interface d'administration pour gérer les vols, promotions et paramètres</p>
        <a href="${pageContext.request.contextPath}/login/back" class="btn btn-primary">Accéder au BackOffice</a>
      </div>

      <div class="interface-card">
        <h2>FrontOffice</h2>
        <p>Interface client pour rechercher des vols et gérer les réservations</p>
        <a href="${pageContext.request.contextPath}/login/front" class="btn btn-secondary">Accéder au FrontOffice</a>
      </div>
    </div>
  </main>

  <footer class="footer">
    <p>&copy; 2025 Système de Ticketing. Tous droits réservés.</p>
  </footer>
</div>
</body>
</html>
