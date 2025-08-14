<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nouveau Vol - Backoffice</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/fragment/navbar.jsp" %>

    <div class="container">
        <main class="main-content">
            <div class="content-header">
                <h2>Créer un Nouveau Vol</h2>
                <a href="${pageContext.request.contextPath}/back/flights" class="btn btn-secondary">← Retour à la liste</a>
            </div>
            
            <form class="form-container" style="max-width: 800px;" action="${pageContext.request.contextPath}/back/flights" method="post">
                <div class="form-row">
                    <div class="form-group">
                        <label for="departure_city">Ville de départ *</label>
                        <select id="departure_city" name="departure_city" class="form-control" required>
                            <option value="">Sélectionner une ville</option>
                            <option value="1">Paris</option>
                            <option value="2">London</option>
                            <option value="3">New York</option>
                            <option value="4">Tokyo</option>
                            <option value="5">Dubai</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="arrival_city">Ville d'arrivée *</label>
                        <select id="arrival_city" name="arrival_city" class="form-control" required>
                            <option value="">Sélectionner une ville</option>
                            <option value="1">Paris</option>
                            <option value="2">London</option>
                            <option value="3">New York</option>
                            <option value="4">Tokyo</option>
                            <option value="5">Dubai</option>
                        </select>
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="departure_date">Date de départ *</label>
                        <input type="date" id="departure_date" name="departure_date" class="form-control" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="departure_time">Heure de départ *</label>
                        <input type="time" id="departure_time" name="departure_time" class="form-control" required>
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="arrival_date">Date d'arrivée *</label>
                        <input type="date" id="arrival_date" name="arrival_date" class="form-control" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="arrival_time">Heure d'arrivée *</label>
                        <input type="time" id="arrival_time" name="arrival_time" class="form-control" required>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="plane">Avion *</label>
                    <select id="plane" name="plane" class="form-control" required>
                        <option value="">Sélectionner un avion</option>
                        <option value="1">Boeing 737-800 - Air France 001</option>
                        <option value="2">Airbus A320 - British Airways 002</option>
                        <option value="3">Boeing 777-300ER - Emirates 003</option>
                    </select>
                </div>
                
                <h3 class="mt-4 mb-3">Configuration des Sièges et Prix</h3>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="economy_seats">Sièges Économique</label>
                        <input type="number" id="economy_seats" name="economy_seats" class="form-control" min="0" value="150">
                    </div>
                    
                    <div class="form-group">
                        <label for="economy_price">Prix Économique (€)</label>
                        <input type="number" id="economy_price" name="economy_price" class="form-control" min="0" step="0.01" value="299.99">
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="business_seats">Sièges Business</label>
                        <input type="number" id="business_seats" name="business_seats" class="form-control" min="0" value="30">
                    </div>
                    
                    <div class="form-group">
                        <label for="business_price">Prix Business (€)</label>
                        <input type="number" id="business_price" name="business_price" class="form-control" min="0" step="0.01" value="899.99">
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="first_seats">Sièges Première</label>
                        <input type="number" id="first_seats" name="first_seats" class="form-control" min="0" value="12">
                    </div>
                    
                    <div class="form-group">
                        <label for="first_price">Prix Première (€)</label>
                        <input type="number" id="first_price" name="first_price" class="form-control" min="0" step="0.01" value="1999.99">
                    </div>
                </div>
                
                <div class="form-group mt-4">
                    <button type="submit" class="btn btn-success">Créer le Vol</button>
                    <a href="${pageContext.request.contextPath}/back/flights" class="btn btn-secondary">Annuler</a>
                </div>
            </form>
        </main>
    </div>
</body>
</html>
