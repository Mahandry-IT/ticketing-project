<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="mg.itu.ticketingproject.entity.ReservationParam" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Paramètres de Réservation - Backoffice</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/main.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            max-width: 800px;
            margin-top: 2rem;
        }
        .main-content {
            background: white;
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        .content-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1.5rem;
        }
        .form-container {
            margin-top: 1rem;
        }
        .form-group {
            margin-bottom: 1.5rem;
        }
        .form-control {
            border-radius: 5px;
        }
        .btn-success {
            background-color: #28a745;
            border: none;
        }
        .btn-success:hover {
            background-color: #218838;
        }
        .btn-secondary {
            background-color: #6c757d;
            border: none;
        }
        .btn-secondary:hover {
            background-color: #5a6268;
        }
        .current-params {
            background-color: #e9ecef;
            padding: 1rem;
            border-radius: 5px;
            margin-bottom: 1.5rem;
            font-size: 1.1rem;
        }
        .alert {
            margin-bottom: 1.5rem;
        }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/views/fragment/navbar-back.jsp" %>

<div class="container">
    <main class="main-content">
        <div class="content-header">
            <h2 class="mb-0">Configurer les Paramètres de Réservation</h2>
            <a href="<%= request.getContextPath() %>/back/dashboard" class="btn btn-secondary">← Retour au tableau de bord</a>
        </div>

        <%
            ReservationParam param = (ReservationParam) request.getAttribute("param");
            String successMessage = (String) request.getAttribute("successMessage");
            String errorMessage = (String) request.getAttribute("errorMessage");
        %>

        <!-- Display current parameters -->
        <div class="current-params">
            <% if (param != null && param.getCancelTime() != null && param.getReservationTime() != null) { %>
            <p><strong>Temps d'annulation actuel :</strong> <%= param.getCancelTime() %> heures</p>
            <p><strong>Temps de réservation actuel :</strong> <%= param.getReservationTime() %> heures</p>
            <% } else { %>
            <p>Il n'y a ni temps d'annulation ni temps de réservation actuellement.</p>
            <% } %>
        </div>

        <!-- Display success or error messages -->
        <% if (successMessage != null) { %>
        <div class="alert alert-success" role="alert">
            <%= successMessage %>
        </div>
        <% } %>
        <% if (errorMessage != null) { %>
        <div class="alert alert-danger" role="alert">
            <%= errorMessage %>
        </div>
        <% } %>

        <!-- Form for updating parameters -->
        <form class="form-container" action="<%= request.getContextPath() %>/back/add/reservation-param" method="post">
            <div class="row">
                <div class="col-md-6 form-group">
                    <label for="cancel_time" class="form-label">Temps d'annulation (heures) *</label>
                    <input type="number" id="cancel_time" name="cancel_time" class="form-control" min="0" step="1"
                           value="<%= (param != null && param.getCancelTime() != null) ? param.getCancelTime() : 0 %>"
                           required aria-describedby="cancelTimeHelp">
                    <div id="cancelTimeHelp" class="form-text">Durée en heures avant laquelle une réservation peut être annulée.</div>
                </div>
                <div class="col-md-6 form-group">
                    <label for="reservation_time" class="form-label">Temps de réservation (heures) *</label>
                    <input type="number" id="reservation_time" name="reservation_time" class="form-control" min="0" step="1"
                           value="<%= (param != null && param.getReservationTime() != null) ? param.getReservationTime() : 0 %>"
                           required aria-describedby="reservationTimeHelp">
                    <div id="reservationTimeHelp" class="form-text">Durée en heures pendant laquelle une réservation est valide.</div>
                </div>
            </div>

            <div class="form-group mt-4">
                <button type="submit" class="btn btn-success">Enregistrer les Paramètres</button>
                <a href="<%= request.getContextPath() %>/back/dashboard" class="btn btn-secondary">Annuler</a>
            </div>
        </form>
    </main>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>