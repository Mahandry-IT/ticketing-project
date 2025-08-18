<%@ page import="mg.itu.ticketingproject.entity.Flight" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.ticketingproject.data.dto.SeatAvailabilityDTO" %>
<%@ page import="mg.itu.ticketingproject.util.FrontUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Flight flight = (Flight) request.getAttribute("flight");
    List<SeatAvailabilityDTO> list = (List<SeatAvailabilityDTO>) request.getAttribute("planeSeats");
    Long total = (Long) request.getAttribute("total");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Détails du Vol - Backoffice</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/fragment/navbar-front.jsp" %>

    <div class="container">
        <main class="main-content">
            <div class="content-header">
                <h2>Détails du Vol #<%= flight.getId()%></h2>
                <div class="actions">
                    <a href="${pageContext.request.contextPath}/front/flights" class="btn btn-secondary">← Retour</a>
                </div>
            </div>
            
            <div class="interface-selection">
                <div class="interface-card">
                    <h3>Informations Générales</h3>
                    <div style="text-align: left;">
                        <p><strong>Départ:</strong> <%= flight.getDepartureCity().getName()%></p>
                        <p><strong>Arrivée:</strong> <%= flight.getArrivalCity().getName()%></p>
                        <p><strong>Date/Heure Départ:</strong> <%= FrontUtil.formaterDateTime(flight.getDepartureTime(), "dd MMM yyyy HH:mm:ss") %></p>
                        <p><strong>Date/Heure Arrivée:</strong> <%= FrontUtil.formaterDateTime(flight.getArrivalTime(), "dd MMM yyyy HH:mm:ss") %></p>
                        <p><strong>Avion:</strong> <%= flight.getPlane().getName() %></p>
                        <p><strong>Total Réservations:</strong> <%= total%></p>
                    </div>
                </div>
            </div>
                <div class="interface-selection">
                    <% for(SeatAvailabilityDTO element: list) { %>
                        <div class="interface-card">
                            <h3>Sièges <%= FrontUtil.uniformalizedLetter(element.getSeatType()) %></h3>
                            <div style="text-align: left;">
                                <p><strong>Disponible:</strong> <%= element.getAvailableSeats()%>/<%= element.getTotalSeats()%> disponibles</p>
                                <p><strong>Prix:</strong> <%= FrontUtil.formatNumber(element.getPrice().doubleValue(), "#,##0.00")%></p>
                            </div>
                        </div>
                    <% } %>
                </div>
        </main>
    </div>
</body>
</html>
