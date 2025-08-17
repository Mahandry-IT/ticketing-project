<%@ page import="mg.itu.ticketingproject.entity.Flight" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.ticketingproject.entity.Offer" %>
<%@ page import="mg.itu.ticketingproject.entity.SeatType" %>
<%@ page import="mg.itu.ticketingproject.util.FrontUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Flight> flights = (List<Flight>) request.getAttribute("flights");
    List<Offer> offers = (List<Offer>) request.getAttribute("offers");
    List<SeatType> seatTypes = (List<SeatType>) request.getAttribute("seatTypes");
%>
<%
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Promotions - Backoffice</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/fragment/navbar-back.jsp" %>

    <div class="container">
        <main class="main-content">
            <div class="content-header">
                <h2>Gestion des Promotions</h2>
            </div>
            
            <!-- Formulaire d'insertion de promotion -->
            <div class="search-filters">
                <h3>Créer une Nouvelle Promotion</h3>
                <% if(error != null && !error.isEmpty()) { %>
                <div class="alert alert-danger mb-3">
                    <%= error %>
                </div>
                <% } %>
                <form action="${pageContext.request.contextPath}/back/add/offer" method="post">
                    <div class="form-row">
                        <div class="form-group">
                            <label for="flight">Vol concerné *</label>
                            <select id="flight" name="flight" class="form-control" required>
                                <option value="">Sélectionner un vol</option>
                                <% if (flights != null) {
                                    for (Flight flight : flights) { %>
                                    <option value="<%= flight.getId() %>">
                                        <%= flight.getDepartureCity().getName() %> →
                                        <%= flight.getArrivalCity().getName() %>
                                    </option>
                                <% } } %>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label for="seat_type">Type de siège *</label>
                            <select id="seat_type" name="seat_type" class="form-control" required>
                                <option value="">Sélectionner un type</option>
                                <% if (seatTypes != null) {
                                    for (SeatType seatType : seatTypes) { %>
                                    <option value="<%= seatType.getId() %>">
                                        <%= seatType.getName() %>
                                    </option>
                                <% } } %>
                            </select>
                        </div>
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label for="number_seats">Nombre de sièges en promotion *</label>
                            <input type="number" id="number_seats" name="number_seats" 
                                   class="form-control" min="1" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="discount">Pourcentage de réduction (%) *</label>
                            <input type="number" id="discount" name="discount" 
                                   class="form-control" min="1" max="100" step="0.01" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-success">Créer la Promotion</button>
                    </div>
                </form>
            </div>
            
            <!-- Liste des promotions existantes -->
            <div class="table-container">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Vol</th>
                            <th>Type de Siège</th>
                            <th>Nb Sièges</th>
                            <th>Réduction</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Offer offer : offers) { %>
                            <tr>
                                <td>
                                    <%= offer.getFlight().getDepartureCity().getName() %> →
                                    <%= offer.getFlight().getArrivalCity().getName() %><br>
                                </td>
                                <td>
                                    <%= offer.getType().getName() %>
                                </td>
                                <td>
                                    <%= offer.getNumber() %>
                                </td>
                                <td>
                                    <%= FrontUtil.formatNumber(offer.getOffer().doubleValue(), "#,##0.00") %>%
                                </td>
                                <td class="actions">
                                    <a href="${pageContext.request.contextPath}/back/modify/offer?id=<%= offer.getId() %>"><button class="btn btn-warning">Modifier</button></a>
                                    <a href="${pageContext.request.contextPath}/back/delete/offer?id=<%= offer.getId() %>"><button class="btn btn-danger">Supprimer</button></a>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </main>
    </div>
</body>
</html>
