<%--
  Created by IntelliJ IDEA.
  User: Mahandry
  Date: 10/08/2025
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BackOffice - Tableau de bord</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body class="page-container">
<nav class="navbar">
    <div class="container">
        <nav>
            <a href="dashboard.html">Accueil</a>
            <a href="flights.html">Vols</a>
            <a href="promotions.html">Promotions</a>
            <a href="settings.html">Paramètres</a>
            <a href="#" onclick="logout()">Déconnexion</a>
        </nav>
    </div>
</nav>

<div class="content">
    <div class="container">
        <h2>Tableau de bord</h2>

        <div class="card">
            <h3>Actions rapides</h3>
            <div style="display: flex; gap: 15px; flex-wrap: wrap; margin-top: 15px;">
                <a href="flights.html" class="btn btn-primary">Gérer les vols</a>
                <a href="promotions.html" class="btn btn-secondary">Gérer les promotions</a>
                <a href="settings.html" class="btn btn-info">Paramètres système</a>
            </div>
        </div>

        <div class="card">
            <h3>Vols récents</h3>
            <table class="table" id="recentFlightsTable">
                <thead>
                <tr>
                    <th>Vol</th>
                    <th>Origine</th>
                    <th>Destination</th>
                    <th>Date</th>
                    <th>Statut</th>
                </tr>
                </thead>
                <tbody id="recentFlightsBody">
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>

