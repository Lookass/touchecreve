<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
   
       <title>Touché Crevé - Lobby</title>
	   <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	   <meta name="description" content="Liste des parties">
	   <link rel="stylesheet" media="screen" type="text/css" title="Design" href="touche_creve.css">
	  
    </head>
	<body>
	
		<div class="container_1st_level">
		
			<h2>Lobby</h2>
			
			<div class="table_filter_container">
				<input type="radio" name="filtre_parties" value="0" id="rb_en_attente"/><label for="rb_en_attente">Parties en attente</label>
				<input type="radio" name="filtre_parties" value="1" id="rb_terminees"/><label for="rb_terminees">Parties terminées</label>
			</div>
			
			<div class="action_buttons_container">
				<button id="b_creer_partie" disabled="disabled">Créer une partie</button>
				<button id="b_rejoindre_partie">Rejoindre la partie</button>
				<button id="b_voir_journal" disabled="disabled">Voir le journal de la partie</button>
			</div>
			
			<table>
				<thead>
					<tr>
						<th>#</th>
						<th>Nom</th>
						<th>Joueur Rouge</th>
						<th>Date</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="parties" items="${parties}">
					<tr>
						<td>${parties.id}</td>
						<td>${parties.nom}</td>
						<td>${parties.joueurRouge.nom}</td>
						<td>${parties.dateDebut}</td>
					</tr>
				
					</c:forEach>
					
					
				</tbody>
			</table>
			
			<div class="nom_joueur_container">Connecté en tant que : <span class="nom_joueur"><c:out value="${sessionScope.nom}" /></span></div>
		
		</div>
	
	</body>
	
</html>