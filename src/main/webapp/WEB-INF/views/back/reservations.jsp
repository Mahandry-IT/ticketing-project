<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Réservations - Backoffice</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/fragment/navbar.jsp" %>

    <div class="container">
        <main class="main-content">
            <div class="content-header">
                <h2>Gestion des Réservations</h2>
            </div>
            
            <!-- Filtres de recherche -->
            <div class="search-filters">
                <h3>Filtrer les Réservations</h3>
                <form class="form-row">
                    <div class="form-group">
                        <label for="reservation_id">ID Réservation</label>
                        <input type="text" id="reservation_id" name="reservation_id" 
                               class="form-control" placeholder="Ex: RES001">
                    </div>
                    
                    <div class="form-group">
                        <label for="customer_email">Email Client</label>
                        <input type="email" id="customer_email" name="customer_email" 
                               class="form-control" placeholder="client@email.com">
                    </div>
                    
                    <div class="form-group">
                        <label for="flight_filter">Vol</label>
                        <select id="flight_filter" name="flight_filter" class="form-control">
                            <option value="">Tous les vols</option>
                            <option value="1">Paris → London</option>
                            <option value="2">London → New York</option>
                            <option value="3">Tokyo → Dubai</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="status_filter">Statut</label>
                        <select id="status_filter" name="status_filter" class="form-control">
                            <option value="">Tous les statuts</option>
                            <option value="CONFIRMED">Confirmée</option>
                            <option value="CANCELLED">Annulée</option>
                            <option value="PENDING">En attente</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">Filtrer</button>
                    </div>
                </form>
            </div>
            
            <!-- Liste des réservations -->
            <div class="table-container">
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID Réservation</th>
                            <th>Client</th>
                            <th>Vol</th>
                            <th>Date Réservation</th>
                            <th>Passagers</th>
                            <th>Prix Total</th>
                            <th>Statut</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>RES001</td>
                            <td>user@ticketing.com</td>
                            <td>Paris → London<br><small>15/01/2024 08:00</small></td>
                            <td>10/01/2024 14:30</td>
                            <td>2</td>
                            <td>599.98 €</td>
                            <td><span class="alert alert-success" style="padding: 0.25rem 0.5rem; margin: 0;">Confirmée</span></td>
                            <td class="actions">
                                <a href="${pageContext.request.contextPath}/WEB-INF/views/back/reservation-details.jsp?id=1" class="btn btn-secondary">Détails</a>
                                <button class="btn btn-danger">Annuler</button>
                            </td>
                        </tr>
                        <tr>
                            <td>RES002</td>
                            <td>client2@email.com</td>
                            <td>London → New York<br><small>15/01/2024 14:00</small></td>
                            <td>11/01/2024 09:15</td>
                            <td>1</td>
                            <td>899.99 €</td>
                            <td><span class="alert alert-success" style="padding: 0.25rem 0.5rem; margin: 0;">Confirmée</span></td>
                            <td class="actions">
                                <a href="${pageContext.request.contextPath}/WEB-INF/views/back/reservation-details.jsp?id=2" class="btn btn-secondary">Détails</a>
                                <button class="btn btn-danger">Annuler</button>
                            </td>
                        </tr>
                        <tr>
                            <td>RES003</td>
                            <td>client3@email.com</td>
                            <td>Tokyo → Dubai<br><small>16/01/2024 22:00</small></td>
                            <td>12/01/2024 16:45</td>
                            <td>3</td>
                            <td>2399.97 €</td>
                            <td><span class="alert alert-danger" style="padding: 0.25rem 0.5rem; margin: 0;">Annulée</span></td>
                            <td class="actions">
                                <a href="${pageContext.request.contextPath}/WEB-INF/views/back/reservation-details.jsp?id=3" class="btn btn-secondary">Détails</a>
                                <button class="btn btn-secondary" disabled>Déjà annulée</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            
            <!-- Statistiques -->
            <div class="interface-selection mt-4">
                <div class="interface-card">
                    <h3>Statistiques</h3>
                    <div style="text-align: left;">
                        <p><strong>Total Réservations:</strong> 156</p>
                        <p><strong>Confirmées:</strong> 142</p>
                        <p><strong>Annulées:</strong> 14</p>
                        <p><strong>Chiffre d'affaires:</strong> 89,456.78 €</p>
                    </div>
                </div>
            </div>
        </main>
    </div>
</body>
</html>
