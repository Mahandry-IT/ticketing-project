<%@ page import="mg.itu.ticketingproject.entity.Reservation" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.ticketingproject.util.FrontUtil" %>
<%@ page import="mg.itu.ticketingproject.enums.ReservationStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
  String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Liste des réservations</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
<%@ include file="/WEB-INF/views/fragment/navbar-front.jsp" %>

<div class="container">
  <main class="main-content">
    <div class="content-header">
      <h2>Liste des réservations</h2>
    </div>

    <!-- Dernières réservations -->
    <div class="search-filters">
      <h3>Vos Réservations</h3>
      <% if(error != null && !error.isEmpty()) { %>
        <div class="alert alert-danger mb-3">
          <%= error %>
        </div>
      <% } %>
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
                  <a href="${pageContext.request.contextPath}/front/detail/reservation?id=<%= reservation.getId()%>" class="btn btn-secondary">Détails</a>
                  <% if (reservation.getStatus().equals(ReservationStatus.PENDING.getStatus())) {%>
                    <a href="${pageContext.request.contextPath}/front/modify/reservation?id=<%= reservation.getId()%>" class="btn btn-warning">Modifer</a>
                    <a href="${pageContext.request.contextPath}/front/cancel/reservation?id=<%= reservation.getId()%>" class="btn btn-danger">Annuler</a>
                  <% } %>
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
