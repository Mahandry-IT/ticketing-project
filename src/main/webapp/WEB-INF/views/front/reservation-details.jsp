<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.ticketingproject.entity.SeatType" %>
<%@ page import="mg.itu.ticketingproject.entity.Flight" %>
<%@ page import="mg.itu.ticketingproject.entity.Reservation" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  List<SeatType> seatTypes = (List<SeatType>) request.getAttribute("seatTypes");
  Reservation reservation = (Reservation) request.getAttribute("reservation");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Détails Réservation - SkyBooking</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">

</head>
<body>
<%@ include file="/WEB-INF/views/fragment/navbar-front.jsp" %>
<div class="container">
  <div class="content-header">
    <h2>Détails Réservation</h2>
    <a href="${pageContext.request.contextPath}/front/reservations" class="btn btn-secondary">← Retour aux résultats</a>
  </div>

  <!-- Résumé du vol -->
  <div class="form-container" style="max-width: 800px;">
    <h3 class="form-title">Réservation </h3>
    <div class="form-row">
      <div class="form-group">
        <label for="reservation-time">Temps de réservations</label>
        <input type="datetime-local" id="reservation-time" class="form-control" value="<%= reservation.getReservationTime()%>" disabled>
      </div>
      <div class="form-group">
        <label for="vol">Vol</label>
        <input type="text" id="vol" class="form-control" value="<%= reservation.getFlight().getDepartureCity().getName() + " -> " + reservation.getFlight().getArrivalCity().getName()%>" disabled>
      </div>
    </div>
    <div class="form-row">
      <div class="form-group">
        <label for="total-price">Prix Total</label>
        <input type="number" id="total-price" class="form-control" value="<%= reservation.getTotalPrice()%>" disabled>
      </div>
      <div class="form-group">
        <label for="passenger-count">Total de personnes</label>
        <input type="number" id="passenger-count" class="form-control" value="<%= reservation.getPassengerCount()%>" disabled>
      </div>
    </div>
  </div>

  <div class="form-container" style="max-width: 800px;">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.5rem;">
      <h3 class="form-title">Détails de la réservation</h3>
    </div>
    <div id="passengers-container">
    <% for (int i = 0; i < reservation.getReservationDetails().size(); i++) { %>
      <div class="passenger-block" style="border: 1px solid rgb(204, 204, 204); padding: 1rem; margin-bottom: 1rem; border-radius: 8px;">
        <h4 class="passenger-title">Passager <%= i+1%></h4>
        <div class="form-row">
          <div class="form-group">
            <label for="passport">Passeport</label>
            <div class="avatar-container">
              <img src="<%= reservation.getReservationDetails().get(i).getPassport()%>" class="img-sm" width="80px" height="80px">
            </div>
          </div>
          <div class="form-group">
            <label for="name">Name</label>
            <input type="text" id="name" name="name" class="form-control" value="<%= reservation.getReservationDetails().get(i).getPassengerName()%>" disabled>
          </div>
        </div>
        <div class="form-row">
          <div class="form-group">
            <label for="seat-type">Type de siège</label>
            <select id="seat-type" name="seat_type" class="form-control" disabled><option value="">Sélectionner...</option>
              <% for(SeatType st : seatTypes) { %>
                <% if(st.getId().equals(reservation.getReservationDetails().get(i).getSeatType().getId())) { %>
                  <option value="<%= st.getId() %>" selected><%= st.getName() %></option>
                <% } else { %>
                  <option value="<%= st.getId() %>"><%= st.getName() %></option>
                <% } %>
              <% } %>
            </select>
          </div>
          <div class="form-group">
            <label for="age">Âge</label>
            <input type="number" id="age" name="age" class="form-control" value="<%= reservation.getReservationDetails().get(i).getAge()%>" disabled>
          </div>
          <div class="form-group">
            <label for="prix">Prix</label>
            <input type="number" id="prix" name="prix" class="form-control" value="<%= reservation.getReservationDetails().get(i).getPrice()%>" disabled>
          </div>
          <input type="hidden" id="price" name="price" class="form-control" value="<%= reservation.getReservationDetails().get(i).getPrice()%>" disabled>
        </div>
      </div>
    <% } %>
    </div>
  </div>
</div>

</body>
</html>

