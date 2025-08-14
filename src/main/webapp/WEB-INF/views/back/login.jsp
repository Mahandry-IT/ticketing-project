<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion Backoffice</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
    <div class="container">
        <header class="header">
            <h1>Backoffice - Connexion</h1>
            <p>Interface d'administration</p>
        </header>
        
        <main class="main-content">
            <form class="form-container" action="${pageContext.request.contextPath}/back/login" method="post">
                <h2 class="text-center mb-4">Connexion Administrateur</h2>
                <% if(error != null && !error.isEmpty()) { %>
                    <div class="alert alert-danger mb-3">
                        <%= error %>
                    </div>
                <% } %>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" class="form-control" required 
                           placeholder="admin@ticketing.com">
                </div>
                
                <div class="form-group">
                    <label for="password">Mot de passe</label>
                    <input type="password" id="password" name="password" class="form-control" required 
                           placeholder="Votre mot de passe">
                </div>
                
                <div class="form-group">
                    <button type="submit" class="btn btn-primary" style="width: 100%;">
                        Se connecter
                    </button>
                </div>
                
                <div class="text-center">
                    <a href="${pageContext.request.contextPath}/index.jsp">← Retour à l'accueil</a>
                </div>
            </form>
        </main>
    </div>
</body>
</html>
