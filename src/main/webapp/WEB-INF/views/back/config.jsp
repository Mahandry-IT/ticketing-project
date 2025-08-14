<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Configuration - Backoffice</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/fragment/navbar.jsp" %>

    <div class="container">
        <main class="main-content">
            <div class="content-header">
                <h2>Configuration Système</h2>
            </div>
            
            <!-- Configuration des délais -->
            <div class="search-filters">
                <h3>Configuration des Délais de Réservation et Annulation</h3>
                <form action="${pageContext.request.contextPath}/WEB-INF/views/back/config.jsp" method="post">
                    <div class="form-row">
                        <div class="form-group">
                            <label for="cancel_time">Délai minimal d'annulation (heures) *</label>
                            <input type="number" id="cancel_time" name="cancel_time" 
                                   class="form-control" min="1" max="168" value="24" required>
                            <small>Nombre d'heures avant le départ pour pouvoir annuler</small>
                        </div>
                        
                        <div class="form-group">
                            <label for="reservation_time">Délai minimal de réservation (heures) *</label>
                            <input type="number" id="reservation_time" name="reservation_time" 
                                   class="form-control" min="1" max="72" value="2" required>
                            <small>Nombre d'heures avant le départ pour pouvoir réserver</small>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <button type="submit" class="btn btn-success">Sauvegarder les Délais</button>
                    </div>
                </form>
            </div>
            
            <!-- Configuration des catégories d'âge -->
            <div class="search-filters">
                <h3>Configuration des Catégories d'Âge et Promotions</h3>
                <div class="table-container">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Catégorie</th>
                                <th>Âge Min</th>
                                <th>Âge Max</th>
                                <th>Réduction (%)</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>Bébé (INFANT)</td>
                                <td>0</td>
                                <td>2</td>
                                <td>90%</td>
                                <td class="actions">
                                    <button class="btn btn-warning">Modifier</button>
                                </td>
                            </tr>
                            <tr>
                                <td>Enfant (CHILD)</td>
                                <td>3</td>
                                <td>11</td>
                                <td>50%</td>
                                <td class="actions">
                                    <button class="btn btn-warning">Modifier</button>
                                </td>
                            </tr>
                            <tr>
                                <td>Adulte (ADULT)</td>
                                <td>12</td>
                                <td>64</td>
                                <td>0%</td>
                                <td class="actions">
                                    <button class="btn btn-warning">Modifier</button>
                                </td>
                            </tr>
                            <tr>
                                <td>Senior (SENIOR)</td>
                                <td>65</td>
                                <td>120</td>
                                <td>20%</td>
                                <td class="actions">
                                    <button class="btn btn-warning">Modifier</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            
            <!-- Formulaire de modification d'une catégorie -->
            <div class="search-filters">
                <h3>Modifier une Catégorie d'Âge</h3>
                <form action="${pageContext.request.contextPath}/WEB-INF/views/back/config.jsp" method="post">
                    <div class="form-row">
                        <div class="form-group">
                            <label for="category">Catégorie *</label>
                            <select id="category" name="category" class="form-control" required>
                                <option value="">Sélectionner une catégorie</option>
                                <option value="1">Bébé (INFANT)</option>
                                <option value="2">Enfant (CHILD)</option>
                                <option value="3">Adulte (ADULT)</option>
                                <option value="4">Senior (SENIOR)</option>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label for="min_age">Âge minimum *</label>
                            <input type="number" id="min_age" name="min_age" 
                                   class="form-control" min="0" max="120" required>
                        </div>
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label for="max_age">Âge maximum *</label>
                            <input type="number" id="max_age" name="max_age" 
                                   class="form-control" min="0" max="120" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="discount_percent">Pourcentage de réduction (%) *</label>
                            <input type="number" id="discount_percent" name="discount_percent" 
                                   class="form-control" min="0" max="100" step="0.01" required>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <button type="submit" class="btn btn-success">Mettre à Jour la Catégorie</button>
                    </div>
                </form>
            </div>
            
            <!-- Configuration générale -->
            <div class="search-filters">
                <h3>Paramètres Généraux</h3>
                <form action="${pageContext.request.contextPath}/WEB-INF/views/back/configjsp" method="post">
                    <div class="form-row">
                        <div class="form-group">
                            <label for="company_name">Nom de la compagnie</label>
                            <input type="text" id="company_name" name="company_name" 
                                   class="form-control" value="Air Ticketing System">
                        </div>
                        
                        <div class="form-group">
                            <label for="support_email">Email de support</label>
                            <input type="email" id="support_email" name="support_email" 
                                   class="form-control" value="support@ticketing.com">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <button type="submit" class="btn btn-success">Sauvegarder</button>
                    </div>
                </form>
            </div>
        </main>
    </div>
</body>
</html>
