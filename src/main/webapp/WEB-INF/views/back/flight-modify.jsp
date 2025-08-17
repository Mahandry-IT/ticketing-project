<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.ticketingproject.util.FrontUtil" %>
<%@ page import="java.util.Objects" %>
<%@ page import="mg.itu.ticketingproject.entity.*" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifier un Vol - Backoffice</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/main.css">
</head>
<body>
<%@ include file="/WEB-INF/views/fragment/navbar-back.jsp" %>

<div class="container">
    <main class="main-content">
        <div class="content-header">
            <h2>Modifier un Vol</h2>
            <a href="<%= request.getContextPath() %>/back/flights" class="btn btn-secondary">← Retour à la liste</a>
        </div>

        <%
            List<City> cities = (List<City>) request.getAttribute("cities");
            List<Plane> planes = (List<Plane>) request.getAttribute("planes");
            List<SeatType> types = (List<SeatType>) request.getAttribute("seats");
            Flight flight = (Flight) request.getAttribute("flight");
            List<PlaneSeat> planeSeat = (List<PlaneSeat>) request.getAttribute("planeSeats");
        %>

        <form class="form-container" style="max-width: 800px;" action="<%= request.getContextPath() %>/back/modify/flight" method="post">
            <input type="hidden" id="flight_id" name="flight_id" class="form-control"  value="<%= flight.getId() %>" required>
            <div class="form-row">
                <div class="form-group">
                    <label for="departure_city">Ville de départ *</label>
                    <select id="departure_city" name="departure_city" class="form-control" required>
                        <option value="">Sélectionner une ville</option>
                        <%
                            if (cities != null) {
                                for (City city : cities) {
                        %>
                        <% if (Objects.equals(city.getId(), flight.getDepartureCity().getId())) {%>
                            <option value="<%= city.getId() %>" selected><%= city.getName() %></option>
                        <% } else {%>
                            <option value="<%= city.getId() %>"><%= city.getName() %></option>
                        <% } %>
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
                        <% if (Objects.equals(city.getId(), flight.getArrivalCity().getId())) {%>
                        <option value="<%= city.getId() %>" selected><%= city.getName() %></option>
                        <% } else {%>
                        <option value="<%= city.getId() %>"><%= city.getName() %></option>
                        <% } %>
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
                    <input type="datetime-local" id="departure_date" name="departure_date" class="form-control"  value="<%= flight.getDepartureTime()%>" required>
                </div>
                <div class="form-group">
                    <label for="arrival_date">Temps d'arrivée *</label>
                    <input type="datetime-local" id="arrival_date" name="arrival_date" class="form-control" value="<%= flight.getArrivalTime()%>" required>
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
                    <% if (Objects.equals(plane.getId(), flight.getPlane().getId())) {%>
                        <option value="<%= plane.getId() %>" selected>
                            <%= plane.getName() %> - <%= plane.getModel().getName() %>
                        </option>
                    <% } else {%>
                        <option value="<%= plane.getId() %>">
                            <%= plane.getName() %> - <%= plane.getModel().getName() %>
                        </option>
                    <% } %>
                    <%
                            }
                        }
                    %>
                </select>
            </div>

            <h3 class="mt-4 mb-3">Configuration des Sièges et Prix</h3>
                <%
                    if (types != null) {
                        for (int i = 0; i < planeSeat.size(); i++) {
                %>
                <div class="form-row">
                    <input type="hidden" id="seat_id" name="seat_id" class="form-control"  value="<%= types.get(i).getId() %>" required>
                    <input type="hidden" id="plane_seat_id" name="plane_seat_id" class="form-control"  value="<%= planeSeat.get(i).getId() %>" required>
                    <div class="form-group">
                        <label for="seat_count">Nombre de siège <%= FrontUtil.uniformalizedLetter(types.get(i).getName()) %></label>
                        <input type="number" id="seat_count" name="seat_count" class="form-control" min="0" value="<%= planeSeat.get(i).getQuantity()%>" required>
                    </div>

                    <div class="form-group">
                        <label for="seat_price">Prix <%= FrontUtil.uniformalizedLetter(types.get(i).getName()) %></label>
                        <input type="number" id="seat_price" name="seat_price" class="form-control" min="0" step="0.01" value="<%= planeSeat.get(i).getPrice()%>" required>
                    </div>
                </div>
                <%
                        }
                    }
                %>

            <div class="form-group mt-4">
                <button type="submit" class="btn btn-warning">Modifier le Vol</button>
                <a href="<%= request.getContextPath() %>/back/flights" class="btn btn-secondary">Annuler</a>
            </div>
        </form>
    </main>
</div>
</body>
</html>
