<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.ticketingproject.entity.City" %>
<%@ page import="mg.itu.ticketingproject.entity.Plane" %>
<%@ page import="mg.itu.ticketingproject.entity.SeatType" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nouveau Vol - Backoffice</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/main.css">
</head>
<body>
<%@ include file="/WEB-INF/views/fragment/navbar.jsp" %>

<div class="container">
    <main class="main-content">
        <div class="content-header">
            <h2>Créer un Nouveau Vol</h2>
            <a href="<%= request.getContextPath() %>/back/flights" class="btn btn-secondary">← Retour à la liste</a>
        </div>

        <%
            List<City> cities = (List<City>) request.getAttribute("cities");
            List<Plane> planes = (List<Plane>) request.getAttribute("planes");
            List<SeatType> types = (List<SeatType>) request.getAttribute("seats");
        %>

        <form class="form-container" style="max-width: 800px;" action="<%= request.getContextPath() %>/back/add/flight" method="post">
            <div class="form-row">
                <div class="form-group">
                    <label for="departure_city">Ville de départ *</label>
                    <select id="departure_city" name="departure_city" class="form-control" required>
                        <option value="">Sélectionner une ville</option>
                        <%
                            if (cities != null) {
                                for (City city : cities) {
                        %>
                        <option value="<%= city.getId() %>"><%= city.getName() %></option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>

                <div class="form-group">
                    <label for="arrival_city">Ville d'arrivée *</label>
                    <select id="arrival_city" name="arrival_city" class="form-control" required>
                        <option value="">Sélectionner une ville</option>
                        <%
                            if (cities != null) {
                                for (City city : cities) {
                        %>
                        <option value="<%= city.getId() %>"><%= city.getName() %></option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="departure_date">Temps de départ *</label>
                    <input type="datetime-local" id="departure_date" name="departure_date" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="arrival_date">Temps d'arrivée *</label>
                    <input type="datetime-local" id="arrival_date" name="arrival_date" class="form-control" required>
                </div>
            </div>

            <div class="form-group">
                <label for="plane">Avion *</label>
                <select id="plane" name="plane" class="form-control" required>
                    <option value="">Sélectionner un avion</option>
                    <%
                        if (planes != null) {
                            for (Plane plane : planes) {
                    %>
                    <option value="<%= plane.getId() %>">
                        <%= plane.getName() %> - <%= plane.getModel().getName() %>
                    </option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>

            <h3 class="mt-4 mb-3">Configuration des Sièges et Prix</h3>
            <%
                if (types != null) {
                    for (SeatType type : types) {
            %>
            <div class="form-row">
                <div class="form-group">
                    <label for="seat_name"><%= type.getName() %></label>
                    <input type="number" id="seat_name" name="seat_name" class="form-control" min="0" value="0" required>
                </div>

                <div class="form-group">
                    <label for="seat_price">Prix <%= type.getName() %></label>
                    <input type="number" id="seat_price" name="seat_price" class="form-control" min="0" step="0.01" value="0" required>
                </div>
            </div>
            <%
                    }
                }
            %>

            <div class="form-group mt-4">
                <button type="submit" class="btn btn-success">Créer le Vol</button>
                <a href="<%= request.getContextPath() %>/back/add/flights" class="btn btn-secondary">Annuler</a>
            </div>
        </form>
    </main>
</div>
</body>
</html>
