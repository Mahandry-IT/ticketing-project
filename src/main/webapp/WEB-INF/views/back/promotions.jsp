<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Promotions - Backoffice</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/fragment/navbar.jsp" %>

    <div class="container">
        <main class="main-content">
            <div class="content-header">
                <h2>Gestion des Promotions</h2>
            </div>
            
            <!-- Formulaire d'insertion de promotion -->
            <div class="search-filters">
                <h3>Créer une Nouvelle Promotion</h3>
                <form action="${pageContext.request.contextPath}/WEB-INF/views/back/promotionsjsp" method="post">
                    <div class="form-row">
                        <div class="form-group">
                            <label for="flight">Vol concerné *</label>
                            <select id="flight" name="flight" class="form-control" required>
                                <option value="">Sélectionner un vol</option>
                                <option value="1">Vol #001 - Paris → London (15/01/2024)</option>
                                <option value="2">Vol #002 - London → New York (15/01/2024)</option>
                                <option value="3">Vol #003 - Tokyo → Dubai (16/01/2024)</option>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label for="seat_type">Type de siège *</label>
                            <select id="seat_type" name="seat_type" class="form-control" required>
                                <option value="">Sélectionner un type</option>
                                <option value="1">Économique</option>
                                <option value="2">Business</option>
                                <option value="3">Première</option>
                            </select>
                        </div>
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label for="number_seats">Nombre de sièges en promotion *</label>
                            <input type="number" id="number_seats" name="number_seats" 
                                   class="form-control" min="1" max="50" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="discount">Pourcentage de réduction (%) *</label>
                            <input type="number" id="discount" name="discount" 
                                   class="form-control" min="1" max="90" step="0.01" required>
                        </div>
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label for="start_date">Date de début</label>
                            <input type="date" id="start_date" name="start_date" class="form-control">
                        </div>
                        
                        <div class="form-group">
                            <label for="end_date">Date de fin</label>
                            <input type="date" id="end_date" name="end_date" class="form-control">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="description">Description de la promotion</label>
                        <input type="text" id="description" name="description" 
                               class="form-control" placeholder="Ex: Promotion de lancement, Offre spéciale weekend...">
                    </div>
                    
                    <div class="form-group">
                        <button type="submit" class="btn btn-success">Créer la Promotion</button>
                    </div>
                </form>
            </div>
            
            <!-- Liste des promotions existantes -->
            <div class="table-container">
                <h3>Promotions Actives</h3>
                <table class="table">
                    <thead>
                        <tr>
                            <th>Vol</th>
                            <th>Type de Siège</th>
                            <th>Nb Sièges</th>
                            <th>Réduction</th>
                            <th>Période</th>
                            <th>Description</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Paris → London<br><small>15/01/2024</small></td>
                            <td>Économique</td>
                            <td>20</td>
                            <td>25%</td>
                            <td>10/01 - 20/01</td>
                            <td>Promotion de lancement</td>
                            <td class="actions">
                                <button class="btn btn-warning">Modifier</button>
                                <button class="btn btn-danger">Supprimer</button>
                            </td>
                        </tr>
                        <tr>
                            <td>London → New York<br><small>15/01/2024</small></td>
                            <td>Business</td>
                            <td>5</td>
                            <td>15%</td>
                            <td>12/01 - 18/01</td>
                            <td>Offre spéciale Business</td>
                            <td class="actions">
                                <button class="btn btn-warning">Modifier</button>
                                <button class="btn btn-danger">Supprimer</button>
                            </td>
                        </tr>
                        <tr>
                            <td>Tokyo → Dubai<br><small>16/01/2024</small></td>
                            <td>Première</td>
                            <td>2</td>
                            <td>30%</td>
                            <td>14/01 - 16/01</td>
                            <td>Last minute</td>
                            <td class="actions">
                                <button class="btn btn-warning">Modifier</button>
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
