<%@ page import="java.util.List" %>
<%@ page import="mg.itu.ticketingproject.entity.Flight" %>
<%@ page import="mg.itu.ticketingproject.util.FrontUtil" %>
<%@ page import="mg.itu.ticketingproject.entity.Plane" %>
<%@ page import="mg.itu.ticketingproject.entity.City" %>
<%@ page import="mg.itu.ticketingproject.entity.SeatType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Flight> flights = (List<Flight>) request.getAttribute("flights");
    List<Plane> planes = (List<Plane>) request.getAttribute("planes");
    List<City> cities = (List<City>) request.getAttribute("cities");
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
    <%@ include file="/WEB-INF/views/fragment/navbar-back.jsp" %>

    <div class="container">
        <main class="main-content">
            <div class="content-header">
                <h2>Gestion des Vols</h2>
                <a href="${pageContext.request.contextPath}/back/add/flight" class="btn btn-primary">+ Nouveau Vol</a>
            </div>
            
            <!-- Filtres de recherche -->
            <div class="search-filters">
                <h3>Recherche Multi-critères</h3>
                <form class="form-row" action="<%= request.getContextPath() %>/back/search/flight" method="post">
                    <div class="form-row">
                        <div class="form-group">
                            <label for="departure">Ville de départ</label>
                            <select id="departure" name="departure_city" class="form-control">
                                <option value="">Toutes les villes</option>
                                <%
                                    if (cities != null) {
                                        for (City city : cities) {
                                %>
                                <option value="<%= city.getName() %>"><%= city.getName() %></option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="arrival">Ville d'arrivée</label>
                            <select id="arrival" name="arrival_city" class="form-control">
                                <option value="">Toutes les villes</option>
                                <%
                                    if (cities != null) {
                                        for (City city : cities) {
                                %>
                                <option value="<%= city.getName() %>"><%= city.getName() %></option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="date">Date de départ</label>
                            <input type="datetime-local" id="date" name="departure_date" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="date">Date de d'arrivée</label>
                            <input type="datetime-local" id="date" name="arrival_date" class="form-control">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="plane">Avion</label>
                            <select id="plane" name="plane" class="form-control">
                                <option value="">Tous les avions</option>
                                <%
                                    if (planes != null) {
                                        for (Plane plane : planes) {
                                %>
                                <option value="<%= plane.getName() %>">
                                    <%= plane.getName() %> - <%= plane.getModel().getName() %>
                                </option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                    </div>

                    <div class="form-group mt-4">
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
                            <td><%= FrontUtil.formaterDateTime(f.getDepartureTime(), "dd MMM yyyy HH:mm:ss") %></td>
                            <td><%= FrontUtil.formaterDateTime(f.getArrivalTime(), "dd MMM yyyy HH:mm:ss") %></td>
                            <td><%= f.getPlane().getName() %></td>
                            <td class="actions">
                                <a href="${pageContext.request.contextPath}/back/detail/flight?id=<%= f.getId() %>" class="btn btn-secondary">Détails</a>
                                <a href="${pageContext.request.contextPath}/back/modify/flight?id=<%= f.getId() %>" class="btn btn-warning">Modifier</a>
                                <a href="${pageContext.request.contextPath}/back/delete/flight?id=<%= f.getId() %>" class="btn btn-danger">Supprimer</a>
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
