<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Système de Gestion de Vols</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
<div class="container">
  <header class="header">
    <h1>Système de Gestion de Vols</h1>
    <p>Choisissez votre interface</p>
  </header>

  <main class="main-content">
    <div class="interface-selection">
      <div class="interface-card">
        <h2>Backoffice</h2>
        <p>Interface d'administration</p>
        <a href="${pageContext.request.contextPath}/back/login" class="btn btn-primary">Accéder au Backoffice</a>
      </div>

      <div class="interface-card">
        <h2>Frontoffice</h2>
        <p>Interface client</p>
        <a href="frontoffice/loginjsp" class="btn btn-secondary">Accéder au Frontoffice</a>
      </div>
    </div>
  </main>
</div>
</body>
</html>

