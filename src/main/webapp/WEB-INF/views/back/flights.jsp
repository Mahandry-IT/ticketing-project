<%@ page import="java.util.List" %>
<%@ page import="mg.itu.ticketingproject.entity.Flight" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Flight> flights = (List<Flight>) request.getAttribute("flights");

%>
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
                <a href="${pageContext.request.contextPath}/back/add/flight" class="btn btn-primary">+ Nouveau Vol</a>
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
                        <%
                            if (flights == null || flights.isEmpty()) {
                        %>
                        <tr>
                            <td colspan="7" style="text-align:center; font-style:italic;">
                                Aucun vol trouvé
                            </td>
                        </tr>
                        <%
                        } else {
                            for (Flight f : flights) {
                        %>
                        <tr>
                            <td><%= f.getId() %></td>
                            <td><%= f.getDepartureCity().getName() %></td>
                            <td><%= f.getArrivalCity().getName() %></td>
                            <td><%= f.getDepartureTime() %></td>
                            <td><%= f.getArrivalTime() %></td>
                            <td><%= f.getPlane().getName() %></td>
                            <td class="actions">
                                <a href="${pageContext.request.contextPath}/flight/details?id=<%= f.getId() %>" class="btn btn-secondary">Détails</a>
                                <a href="${pageContext.request.contextPath}/flight/edit?id=<%= f.getId() %>" class="btn btn-warning">Modifier</a>
                                <button class="btn btn-danger">Supprimer</button>
                            </td>
                        </tr>
                        <%
                                }
                            }
                        %>
                    </tbody>

                </table>
            </div>
        </main>
    </div>
</body>
</html>
