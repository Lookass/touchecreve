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
	   <script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>
 	   <script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
 	   <script type="text/javascript">
 	  	function joinGame(id) {
 			  var r = confirm("Voulez vous rejoindre cette partie?");
 			  if (r==true) {
 				 $('body').append('<form id="redirectJoin" METHOD="POST" ACTION="join.html"></form>');
				 $('#redirectJoin').append('<input type="hidden" name="gameid" value="'+ id +'" />');
				 $('#redirectJoin').submit();
 			  } 
 	  	}
 	  	function lireJournalGame(id) {
			  var r = confirm("Voulez vous lire le journal de cette partie?");
 			  if (r==true) {
 				 $('body').append('<form id="redirectJoin" METHOD="POST" ACTION="journal.html"></form>');
				 $('#redirectJoin').append('<input type="hidden" name="idpartie" value="'+ id +'" />');
				 $('#redirectJoin').submit();
 			  } 
 	  	}
 	  </script>
    </head>
	<body>
	
		<div class="container_1st_level">
		
			<h2>Lobby</h2>
			
			<div class="action_buttons_container">
				<button id="b_creer_partie" onclick='document.location.href="cree.html";' >Créer une partie</button>
			</div>
			<div class="table_parties_filter_label">Parties en attente de joueur</div>
			<table id="liste_parties">
				<thead>
					<tr>
						<th>#</th>
						<th>Nom</th>
						<th>Joueur Rouge</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="parties" items="${parties}">
						<c:if test='${parties.etat == "EN_ATTENTE"}'>
							<tr onClick='joinGame(${parties.id});'>
								<td>${parties.id}</td>
								<td>${parties.nom}</td>
								<td>${parties.joueurRouge.nom}</td>
							</tr>
						</c:if>
					</c:forEach>
					
					
				</tbody>
			</table>
			<div class="table_parties_filter_label">Parties terminées</div>
						<table id="liste_parties">
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
						<c:if test='${parties.etat == "TERMINEE"}'>
							<tr onClick='lireJournalGame(${parties.id});'>
								<td>${parties.id}</td>
								<td>${parties.nom}</td>
								<td>${parties.joueurRouge.nom}</td>
								<td>${parties.dateDebut}</td>
							</tr>
						</c:if>
					
					</c:forEach>
					
					
				</tbody>
			</table>
			<div class="nom_joueur_container">Connecté en tant que : <span class="nom_joueur"><c:out value="${sessionScope.nom}" /></span></div>
		
		</div>
	
	</body>
	
</html>