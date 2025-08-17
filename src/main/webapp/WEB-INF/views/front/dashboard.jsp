<%@ page import="mg.itu.ticketingproject.entity.Reservation" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.ticketingproject.util.FrontUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard Client</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
<%@ include file="/WEB-INF/views/fragment/navbar-front.jsp" %>

<div class="container">
  <main class="main-content">
    <div class="content-header">
      <h2>Bienvenue dans votre espace client</h2>
    </div>

    <div class="interface-selection">
      <div class="interface-card">
        <h3>Rechercher un Vol</h3>
        <p>Trouvez et réservez votre prochain voyage</p>
        <a href="${pageContext.request.contextPath}/front/flights" class="btn btn-primary">Vols</a>
      </div>

      <div class="interface-card">
        <h3>Mes Réservations</h3>
        <p>Consultez et gérez vos réservations</p>
        <a href="${pageContext.request.contextPath}/front/reservations" class="btn btn-secondary">Voir mes réservations</a>
      </div>

      <div class="interface-card">
        <h3>Promotions</h3>
        <p>Découvrez nos offres spéciales</p>
        <a href="${pageContext.request.contextPath}/front/offer" class="btn btn-success">Voir les promotions</a>
      </div>
    </div>

    <!-- Dernières réservations -->
    <div class="search-filters">
      <h3>Vos Dernières Réservations</h3>
      <div class="table-container">
        <table class="table">
          <thead>
          <tr>
            <th>Réservation</th>
            <th>Vol</th>
            <th>Date</th>
            <th>Actions</th>
          </tr>
          </thead>
          <tbody>
          <% for (Reservation reservation : reservations) { %>
            <tr>
                <td><%= reservation.getId() %></td>
                <td><%= reservation.getFlight().getDepartureCity().getName() + " → " + reservation.getFlight().getArrivalCity().getName() %></td>
                <td><%= FrontUtil.formaterDateTime(reservation.getReservationTime(), "dd MMM yyyy HH:mm:ss") %></td>
                <td class="actions">
                  <a href="${pageContext.request.contextPath}/front/reservation-detail?id=1" class="btn btn-secondary">Détails</a>
                </td>
            </tr>
          <% } %>
          </tbody>
        </table>
      </div>
    </div>
  </main>
</div>
</body>
</html>
