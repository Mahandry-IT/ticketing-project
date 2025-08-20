<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Réservations</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
<%@ include file="/WEB-INF/views/fragment/navbar-back.jsp" %>

<div class="container">
    <main class="main-content">

        <div class="container">

            <form action="${pageContext.request.contextPath}/back/alea/date" method="get" style="max-width: 800px" class="form-container">
                <label for="date">Date :</label>
                <input type="date" id="date" name="date" class="form-control">
                <button type="submit" class="btn btn-primary mt-2">Rechercher</button>
            </form>
        </div>
    </main>
</div>
</body>
</html>