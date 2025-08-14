<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Détails du Vol - Backoffice</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/fragment/navbar.jsp" %>

    <div class="container">
        <main class="main-content">
            <div class="content-header">
                <h2>Détails du Vol #001</h2>
                <div class="actions">
                    <a href="${pageContext.request.contextPath}/back/flights" class="btn btn-secondary">← Retour</a>
                    <a href="${pageContext.request.contextPath}/WEB-INF/views/back/flight-edit.jsp?id=1" class="btn btn-warning">Modifier</a>
                </div>
            </div>
            
            <div class="interface-selection">
                <div class="interface-card">
                    <h3>Informations Générales</h3>
                    <div style="text-align: left;">
                        <p><strong>Départ:</strong> Paris (CDG)</p>
                        <p><strong>Arrivée:</strong> London (LHR)</p>
                        <p><strong>Date/Heure Départ:</strong> 15/01/2024 08:00</p>
                        <p><strong>Date/Heure Arrivée:</strong> 15/01/2024 09:30</p>
                        <p><strong>Durée:</strong> 1h 30min</p>
                        <p><strong>Avion:</strong> Boeing 737-800</p>
                    </div>
                </div>
                
                <div class="interface-card">
                    <h3>Disponibilité des Sièges</h3>
                    <div style="text-align: left;">
                        <p><strong>Économique:</strong> 142/150 disponibles</p>
                        <p><strong>Business:</strong> 28/30 disponibles</p>
                        <p><strong>Première:</strong> 12/12 disponibles</p>
                        <p><strong>Total Réservations:</strong> 10</p>
                    </div>
                </div>
            </div>
            
            <!-- Formulaire d'insertion de détails -->
            <div class="search-filters">
                <h3>Ajouter des Détails au Vol</h3>
                <form class="form-row" action="${pageContext.request.contextPath}/WEB-INF/views/back/flight-details.jsp" method="post">
                    <div class="form-group">
                        <label for="detail_type">Type de détail</label>
                        <select id="detail_type" name="detail_type" class="form-control">
                            <option value="meal">Service de repas</option>
                            <option value="entertainment">Divertissement</option>
                            <option value="wifi">WiFi disponible</option>
                            <option value="baggage">Politique bagages</option>
                            <option value="other">Autre</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="detail_description">Description</label>
                        <input type="text" id="detail_description" name="detail_description" 
                               class="form-control" placeholder="Description du détail">
                    </div>
                    
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">Ajouter</button>
                    </div>
                </form>
            </div>
            
            <!-- Liste des détails existants -->
            <div class="table-container">
                <h3>Détails du Vol</h3>
                <table class="table">
                    <thead>
                        <tr>
                            <th>Type</th>
                            <th>Description</th>
                            <th>Date d'ajout</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Service de repas</td>
                            <td>Repas chaud servi en classe Business et Première</td>
                            <td>10/01/2024</td>
                            <td class="actions">
                                <button class="btn btn-danger">Supprimer</button>
                            </td>
                        </tr>
                        <tr>
                            <td>WiFi disponible</td>
                            <td>WiFi gratuit pour tous les passagers</td>
                            <td>10/01/2024</td>
                            <td class="actions">
                                <button class="btn btn-danger">Supprimer</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </main>
    </div>
</body>
</html>
