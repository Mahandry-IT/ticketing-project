<%--
  Created by IntelliJ IDEA.
  User: Mahandry
  Date: 10/08/2025
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BackOffice - Connexion</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
<div class="login-container">
    <form class="login-form" id="loginForm" method="post" action="${pageContext.request.contextPath}/login/back">
        <h2 style="text-align: center; margin-bottom: 30px; color: #333;">BackOffice - Connexion</h2>

        <c:if test="${not empty error}">
            <div id="alertContainer">
                <div class="alert alert-danger">${error}</div>
            </div>
        </c:if>


        <div class="form-group">
            <label for="username">Nom d'utilisateur</label>
            <input type="text" id="username" name="username" required>
        </div>

        <div class="form-group">
            <label for="password">Mot de passe</label>
            <input type="password" id="password" name="password" required>
        </div>

        <button type="submit" class="btn btn-primary" style="width: 100%; margin-bottom: 20px;">Se connecter</button>

        <div style="text-align: center;">
            <a href="${pageContext.request.contextPath}/" style="color: #667eea; text-decoration: none;">← Retour à l'accueil</a>
        </div>

        <div style="margin-top: 20px; padding: 15px; background: #f8f9fa; border-radius: 5px; font-size: 0.9rem;">
            <strong>Compte de test:</strong><br>
            Utilisateur: admin<br>
            Mot de passe: admin123
        </div>
    </form>
</div>
</body>
</html>

