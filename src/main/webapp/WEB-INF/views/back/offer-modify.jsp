<%@ page import="mg.itu.ticketingproject.entity.Flight" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.ticketingproject.entity.Offer" %>
<%@ page import="mg.itu.ticketingproject.entity.SeatType" %>
<%@ page import="mg.itu.ticketingproject.util.FrontUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Flight> flights = (List<Flight>) request.getAttribute("flights");
    Offer offer = (Offer) request.getAttribute("offer");
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
    <title>Modifier des Promotions - Backoffice</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/fragment/navbar-back.jsp" %>

    <div class="container">
        <main class="main-content">
            <div class="content-header">
                <h2>Modifier des Promotions</h2>
            </div>
            
            <!-- Formulaire d'insertion de promotion -->
            <div class="search-filters">
                <h3>Modifier la Promotion</h3>
                <% if(error != null && !error.isEmpty()) { %>
                <div class="alert alert-danger mb-3">
                    <%= error %>
                </div>
                <% } %>
                <form action="${pageContext.request.contextPath}/back/modify/offer" method="post">
                    <input type="hidden" name="id_offer" value="<%= offer.getId() %>">
                    <input type="hidden" name="flight" value="<%= offer.getFlight().getId() %>">
                    <div class="form-row">
                        <div class="form-group">
                            <label for="seat_type">Type de siège *</label>
                            <select id="seat_type" name="seat_type" class="form-control" required>
                                <option value="">Sélectionner un type</option>
                                <% if (seatTypes != null) {
                                    for (SeatType seatType : seatTypes) { %>
                                    <% if (offer.getType().getId().equals(seatType.getId())) { %>
                                        <option value="<%= seatType.getId() %>" selected>
                                            <%= seatType.getName() %>
                                        </option>
                                    <% } else { %>
                                    <option value="<%= seatType.getId() %>">
                                        <%= seatType.getName() %>
                                    </option>
                                <% } } } %>
                            </select>
                        </div>
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label for="number_seats">Nombre de sièges en promotion *</label>
                            <input type="number" id="number_seats" name="number_seats" 
                                   class="form-control" min="0" value="<%= offer.getNumber()%>" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="discount">Pourcentage de réduction (%) *</label>
                            <input type="number" id="discount" name="discount" 
                                   class="form-control" min="0" max="100" step="0.01" value="<%= offer.getOffer().doubleValue()%>" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-warning">Modifier la Promotion</button>
                    </div>
                </form>
            </div>
        </main>
    </div>
</body>
</html>
