<%@page import="mg.itu.error.ErrorHandler"%>
<%
    ErrorHandler ex = (ErrorHandler) request.getAttribute("exception");
    java.io.StringWriter sw = new java.io.StringWriter();
    java.io.PrintWriter pw = new java.io.PrintWriter(sw);
    if (ex != null) {
        ex.getException().printStackTrace(pw);
    }
    String stackTrace = sw.toString();
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Erreur - Système de Gestion de Vols</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <style>
        /* Styles spécifiques pour la page d'erreur */

    </style>
</head>
<body>
<div class="container">
    <div class="error-container">
        <!-- En-tête d'erreur -->
        <div class="error-header">
            <div class="error-code"><%= ex != null ? ex.getCode() : 500 %></div>
            <div class="error-title"><%= ex != null ? ex.getTitre() : "Erreur interne du serveur" %></div>
            <div class="error-subtitle"><%= ex != null ? ex.getException().getMessage() : "Une erreur inattendue s'est produite lors du traitement de votre requête. Notre équipe technique a été notifiée et travaille à résoudre le problème." %></div>
        </div>

        <!-- Contenu de l'erreur -->
        <div class="error-content">
            <!-- Stack trace -->
            <div class="error-section">
                <h3>Stack Trace</h3>
                <% if (ex != null && stackTrace != null && !stackTrace.isEmpty()) { %>
                <div class="stacktrace-container">
                    <%= stackTrace.replace(System.getProperty("line.separator"), "<br>").replace(" ", "&nbsp;") %>
                </div>
                <% } %>
            </div>
        </div>

        <!-- Actions -->
        <div class="error-actions">
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Accueil</a>
        </div>
    </div>
</div>
</body>
</html>