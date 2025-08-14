<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Vols - Backoffice</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/fragment/navbar.jsp" %>

    <div class="container">
        <main class="main-content">
            <div class="content-header">
                <h2>Gestion des Vols</h2>
                <a href="${pageContext.request.contextPath}/WEB-INF/views/back/flight-add.jsp" class="btn btn-primary">+ Nouveau Vol</a>
            </div>
            
            <!-- Filtres de recherche -->
            <div class="search-filters">
                <h3>Recherche Multi-critères</h3>
                <form class="form-row">
                    <div class="form-group">
                        <label for="departure">Ville de départ</label>
                        <select id="departure" name="departure" class="form-control">
                            <option value="">Toutes les villes</option>
                            <option value="1">Paris</option>
                            <option value="2">London</option>
                            <option value="3">New York</option>
                            <option value="4">Tokyo</option>
                            <option value="5">Dubai</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="arrival">Ville d'arrivée</label>
                        <select id="arrival" name="arrival" class="form-control">
                            <option value="">Toutes les villes</option>
                            <option value="1">Paris</option>
                            <option value="2">London</option>
                            <option value="3">New York</option>
                            <option value="4">Tokyo</option>
                            <option value="5">Dubai</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="date">Date de départ</label>
                        <input type="date" id="date" name="date" class="form-control">
                    </div>
                    
                    <div class="form-group">
                        <label for="plane">Avion</label>
                        <select id="plane" name="plane" class="form-control">
                            <option value="">Tous les avions</option>
                            <option value="1">Boeing 737-800</option>
                            <option value="2">Airbus A320</option>
                            <option value="3">Boeing 777-300ER</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">Rechercher</button>
                    </div>
                </form>
            </div>
            
            <!-- Liste des vols -->
            <div class="table-container">
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Départ</th>
                            <th>Arrivée</th>
                            <th>Date/Heure Départ</th>
                            <th>Date/Heure Arrivée</th>
                            <th>Avion</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>001</td>
                            <td>Paris</td>
                            <td>London</td>
                            <td>2024-01-15 08:00</td>
                            <td>2024-01-15 09:30</td>
                            <td>Boeing 737-800</td>
                            <td class="actions">
                                <a href="${pageContext.request.contextPath}/WEB-INF/views/back/flight-details.jsp?id=1" class="btn btn-secondary">Détails</a>
                                <a href="${pageContext.request.contextPath}/WEB-INF/views/back/flight-edit.jsp?id=1" class="btn btn-warning">Modifier</a>
                                <button class="btn btn-danger">Supprimer</button>
                            </td>
                        </tr>
                        <tr>
                            <td>002</td>
                            <td>London</td>
                            <td>New York</td>
                            <td>2024-01-15 14:00</td>
                            <td>2024-01-15 17:00</td>
                            <td>Boeing 777-300ER</td>
                            <td class="actions">
                                <a href="${pageContext.request.contextPath}/WEB-INF/views/back/flight-details.jsp?id=2" class="btn btn-secondary">Détails</a>
                                <a href="${pageContext.request.contextPath}/WEB-INF/views/back/flight-edit.jsp?id=2" class="btn btn-warning">Modifier</a>
                                <button class="btn btn-danger">Supprimer</button>
                            </td>
                        </tr>
                        <tr>
                            <td>003</td>
                            <td>Tokyo</td>
                            <td>Dubai</td>
                            <td>2024-01-16 22:00</td>
                            <td>2024-01-17 04:30</td>
                            <td>Airbus A320</td>
                            <td class="actions">
                                <a href="${pageContext.request.contextPath}/WEB-INF/views/back/flight-details.jsp?id=3" class="btn btn-secondary">Détails</a>
                                <a href="${pageContext.request.contextPath}/WEB-INF/views/back/flight-edit.jsp?id=3" class="btn btn-warning">Modifier</a>
                                <button class="btn btn-danger">Supprimer</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </main>
    </div>
</body>
</html>
