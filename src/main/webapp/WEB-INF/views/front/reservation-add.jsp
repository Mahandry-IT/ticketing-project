<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.ticketingproject.entity.SeatType" %>
<%@ page import="mg.itu.ticketingproject.entity.Flight" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  LocalDateTime now = LocalDateTime.now();
  String defaultDateTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
  List<SeatType> seatTypes = (List<SeatType>) request.getAttribute("seatTypes");
  Flight flight = (Flight) request.getAttribute("flight");
  String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Nouvelle Réservation - SkyBooking</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">

</head>
<body>
<%@ include file="/WEB-INF/views/fragment/navbar-front.jsp" %>
<div class="container">
  <div class="content-header">
    <h2>Nouvelle Réservation</h2>
    <a href="${pageContext.request.contextPath}/front/reservations" class="btn btn-secondary">← Retour aux résultats</a>
  </div>

  <!-- Résumé du vol -->
  <div class="form-container" style="max-width: 800px;">
    <h3 class="form-title">Détails du Vol</h3>
    <div class="form-row">
      <div class="form-group">
        <label for="vol">Vol</label>
        <input type="text" id="vol" class="form-control" value="<%= flight.getPlane().getName()%>" disabled>
      </div>
      <div class="form-group">
        <label for="destination">Destination</label>
        <input type="text" id="destination" class="form-control" value="<%= flight.getDepartureCity().getName() + " -> " + flight.getArrivalCity().getName()%>" disabled>
      </div>
    </div>
    <div class="form-row">
      <div class="form-group">
        <label for="departure-time">Temps de départ</label>
        <input type="datetime-local" id="departure-time" class="form-control" value="<%= flight.getDepartureTime()%>" disabled>
      </div>
      <div class="form-group">
        <label for="arrival-time">Temps d'arrivé</label>
        <input type="datetime-local" id="arrival-time" class="form-control" value="<%= flight.getArrivalTime()%>" disabled>
      </div>
    </div>
    <h3 class="form-title">Informations Global de Réservation</h3>
    <div class="form-group">
      <label for="display-unit-price">Total de la Réservation</label>
      <input type="number" value="150000" id="display-unit-price" class="form-control" disabled>
    </div>
  </div>

  <form id="reservation-form" method="post" action="${pageContext.request.contextPath}/front/reservation/add" enctype="multipart/form-data">

    <!-- Section des passagers -->
    <div class="form-container" style="max-width: 800px;">
      <h3 class="form-title">Informations de Réservation</h3>
      <% if(error != null && !error.isEmpty()) { %>
        <div class="alert alert-danger mb-3">
          <%= error %>
        </div>
      <% } %>
      <div class="form-row">
        <div class="form-group">
          <label for="reservation-date">Date de Réservation</label>
          <input type="datetime-local" id="reservation-date" name="reservation_time" value="<%= defaultDateTime%>" class="form-control" required>
          <input type="hidden" id="flight_id" name="flight_id" value="<%= flight.getId()%>" class="form-control" required>
        </div>
      </div>
      <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.5rem;">
        <h3 class="form-title">Détails des Passagers</h3>
        <button type="button" id="add-passenger" class="btn btn-primary">+ Ajouter Passager</button>
      </div>

      <div id="passengers-container">
        <!-- Le premier passager sera ajouté automatiquement -->
      </div>
      <div class="form-group">
        <button type="button" id="reset-form" class="btn btn-warning">Réinitialiser</button>
        <button type="button" id="remove-last" class="btn btn-danger">Supprimer Dernier</button>
        <button type="submit" class="btn btn-success">Confirmer Réservation</button>
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

    const flightId = <%= flight.getId()%>; // Remplace par l'ID du vol réel ou récupère dynamiquement
    const seatTypeOptions = `
        <option value="">Sélectionner...</option>
        <% for(SeatType st : seatTypes) { %>
            <option value="<%= st.getId() %>"><%= st.getName() %></option>
        <% } %>
    `;
    function createPassenger(index) {
      const div = document.createElement("div");
      div.classList.add("passenger-block");
      div.style.border = "1px solid #ccc";
      div.style.padding = "1rem";
      div.style.marginBottom = "1rem";
      div.style.borderRadius = "8px";

      div.innerHTML = `
      <h4 class="passenger-title">Passager \${index + 1}</h4>
      <div class="form-row">
        <div class="form-group">
          <label for="passport">Passeport</label>
          <input type="file" id="passport" name="passport" class="form-control" accept="image/*" required>
        </div>
        <div class="form-group">
          <label for="name">Name</label>
          <input type="text" id="name" name="name" class="form-control" required>
        </div>
      </div>
      <div class="form-row">
        <div class="form-group">
          <label for="seat-type">Type de siège</label>
          <select id="seat-type" name="seat_type" class="form-control" required>
              \${seatTypeOptions}
          </select>
        </div>
        <div class="form-group">
          <label for="age">Âge</label>
          <input type="number" id="age" name="age" class="form-control" required>
        </div>
      </div>
      <div class="form-row">
        <div class="form-group">
          <label for="promotion">Promotion</label>
          <input type="number" id="promotion" name="promotion" class="form-control" disabled>
        </div>
        <div class="form-group">
          <label for="price">Prix</label>
          <input type="number" id="price" name="price" class="form-control">
        </div>
      </div>
    `;

      // Ajouter les écouteurs pour le seatType et l'age
      const seatTypeSelect = div.querySelector("select[name='seat_type']");
      const ageInput = div.querySelector("input[name='age']");
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

      return div;
    }

    function reindexPassengers() {
      Array.from(passengersContainer.children).forEach(function (block, index) {
        block.querySelector(".passenger-title").textContent = "Passager " + (index + 1);
      });
    }

    function initFirstPassenger() {
      passengersContainer.innerHTML = "";
      passengersContainer.appendChild(createPassenger(0));
    }

    addPassengerBtn.addEventListener("click", function () {
      const index = passengersContainer.children.length;
      passengersContainer.appendChild(createPassenger(index));
      reindexPassengers();
    });

    removeLastBtn.addEventListener("click", function () {
      if (passengersContainer.children.length > 1) {
        passengersContainer.removeChild(passengersContainer.lastChild);
        reindexPassengers();
      } else {
        alert("Au moins un passager est requis.");
      }
    });

    resetFormBtn.addEventListener("click", function () {
      document.getElementById("reservation-form").reset();
      initFirstPassenger();
    });

    async function getSeatPrice(flightId, seatTypeId, age) {
      const requestBody = {
        idFlight: flightId,
        idSeatType: seatTypeId,
        age: age
      };

      const response = await fetch(`${pageContext.request.contextPath}/front/price/flight`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
      });

      if (!response.ok) {
        throw new Error('Erreur lors de la récupération du prix');
      }

      return await response.json();
    }

    initFirstPassenger();
  });

</script>


</body>
</html>

