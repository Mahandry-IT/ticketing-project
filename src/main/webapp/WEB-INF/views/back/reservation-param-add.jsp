<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="mg.itu.ticketingproject.entity.ReservationParam" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Paramètres de Réservation - Backoffice</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/main.css">
</head>
<body>
<%@ include file="/WEB-INF/views/fragment/navbar.jsp" %>

<div class="container">
    <main class="main-content">
        <div class="content-header">
            <h2>Configurer les Paramètres de Réservation</h2>
            <a href="<%= request.getContextPath() %>/back/flights" class="btn btn-secondary">← Retour à la liste des vols</a>
        </div>

        <%
            ReservationParam param = (ReservationParam) request.getAttribute("param");
            Integer cancelTime = (param != null && param.getCancelTime() != null) ? param.getCancelTime() : 0;
            Integer reservationTime = (param != null && param.getReservationTime() != null) ? param.getReservationTime() : 0;
        %>

        <form class="form-container" style="max-width: 600px;" action="<%= request.getContextPath() %>/back/add/reservation-param" method="post">
            <div class="form-row">
                <div class="form-group">
                    <label for="cancel_time">Temps d'annulation (heures) *</label>
                    <input type="number" id="cancel_time" name="cancel_time" class="form-control" min="0" value="<%= cancelTime %>" required>
                </div>
                <div class="form-group">
                    <label for="reservation_time">Temps de réservation (heures) *</label>
                    <input type="number" id="reservation_time" name="reservation_time" class="form-control" min="0" value="<%= reservationTime %>" required>
                </div>
            </div>

            <div class="form-group mt-4">
                <button type="submit" class="btn btn-success">Enregistrer les Paramètres</button>
                <a href="<%= request.getContextPath() %>/back/flights" class="btn btn-secondary">Annuler</a>
            </div>
        </form>
    </main>
</div>
</body>
</html>