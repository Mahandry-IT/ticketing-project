<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.ticketingproject.entity.SeatType" %>
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
  <title>Modification Réservation - SkyBooking</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">

</head>
<body>
<%@ include file="/WEB-INF/views/fragment/navbar-front.jsp" %>
<div class="container">
  <div class="content-header">
    <h2>Modification Réservation</h2>
    <a href="${pageContext.request.contextPath}/front/reservations" class="btn btn-secondary">← Retour aux résultats</a>
  </div>

  <!-- Résumé du vol -->
  <form id="reservation-form" method="post" action="${pageContext.request.contextPath}/front/reservation/add" enctype="multipart/form-data">
  <div class="form-container" style="max-width: 800px;">
    <h3 class="form-title">Réservation </h3>
    <div class="form-row">
      <div class="form-group">
        <input type="hidden" id="flight_id" name="flight_id" value="<%= reservation.getFlight().getId()%>" class="form-control" required>
        <input type="hidden" name="reservation_id" value="<%= reservation.getId()%>">
        <label for="reservation-time">Temps de réservations</label>
        <input type="datetime-local" name="reservation_time"  id="reservation-time" class="form-control" value="<%= reservation.getReservationTime()%>" required>
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
      <div class="passenger-block" style="border:1px solid #ccc; padding:1rem; margin-bottom:1rem; border-radius:8px;">
        <h4 class="passenger-title">Passager <%= i+1 %></h4>
        <input type="hidden" name="detail_id" value="<%= reservation.getReservationDetails().get(i).getId()%>">

        <div class="form-row">
          <div class="form-group">
            <label>Passeport</label>
            <div class="avatar-container">
              <img src="<%= reservation.getReservationDetails().get(i).getPassport() %>" width="80px" height="80px">
            </div>
          </div>
          <div class="form-group">
            <label>Nom</label>
            <input type="text" name="name" class="form-control" value="<%= reservation.getReservationDetails().get(i).getPassengerName() %>" required>
          </div>
        </div>
        <div class="form-row">
          <div class="form-group">
            <label>Type de siège</label>
            <select name="seat_type" class="seat-type form-control" required>
              <option value="">Sélectionner...</option>
              <% for (SeatType st : seatTypes) { %>
              <option value="<%= st.getId() %>" <%= st.getId().equals(reservation.getReservationDetails().get(i).getSeatType().getId()) ? "selected" : "" %>>
                <%= st.getName() %>
              </option>
              <% } %>
            </select>
          </div>

          <div class="form-group">
            <label>Âge</label>
            <input type="number" name="age" class="age form-control" value="<%= reservation.getReservationDetails().get(i).getAge() %>" required>
          </div>
        </div>
      </div>
      <div class="form-row">
        <div class="form-group">
          <label for="promotion">Promotion</label>
          <input type="number" id="promotion" name="promotion" value="<%= reservation.getReservationDetails().get(i).getPrice() %>" class="form-control" disabled>
        </div>
        <div class="form-group">
          <label for="price">Prix</label>
          <input type="number" id="price" name="price" value="<%= reservation.getReservationDetails().get(i).getPrice() %>" class="form-control">
        </div>
      </div>
      <% } %>
    </div>
    <div class="form-group">
      <button type="submit" class="btn btn-success">Confirmer Modification</button>
    </div>
  </div>
  </form>
</div>

<script>
  document.addEventListener("DOMContentLoaded", function () {
    const passengersContainer = document.getElementById("passengers-container");
    const addPassengerBtn = document.getElementById("add-passenger");
    const removeLastBtn = document.getElementById("remove-last");
    const resetFormBtn = document.getElementById("reset-form");

    const flightId = <%= reservation.getFlight().getId() %>;

    // ---- Fonction pour attacher les events à un bloc passager ----
    function attachEventsToPassenger(block) {
      const seatTypeSelect = block.querySelector(".seat-type");
      const ageInput = block.querySelector(".age");
      const priceInput = div.querySelector("input[name='price']");
      const promotionValue = div.querySelector("input[name='promotion']");

      async function updatePrice() {
        const seatTypeId = parseInt(seatTypeSelect.value); // <-- force number
        const age = parseInt(ageInput.value);

        if (seatTypeId && !isNaN(age)) {
          try {
            const price = await getSeatPrice(flightId, seatTypeId, age);
            promotionValue.value = price.promotionPrice;
            priceInput.value = price.price;
          } catch (err) {
            console.error(err);
            promotionValue.value = 0;
            priceInput.value = 0;
          }
        } else {
          promotionValue.value = 0;
          priceInput.value = 0;
        }
      }

      seatTypeSelect.addEventListener("change", updatePrice);
      ageInput.addEventListener("input", updatePrice);
    }

    // ---- Appliquer aux passagers déjà existants ----
    document.querySelectorAll(".passenger-block").forEach(block => {
      attachEventsToPassenger(block);
    });

    // ---- API prix siège ----
    async function getSeatPrice(flightId, seatTypeId, age) {
      const requestBody = { idFlight: flightId, idSeatType: seatTypeId, age: age };
      const response = await fetch(`${pageContext.request.contextPath}/front/price/flight`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(requestBody)
      });
      if (!response.ok) throw new Error('Erreur lors de la récupération du prix');
      return await response.json();
    }
  });
</script>

</body>
</html>

