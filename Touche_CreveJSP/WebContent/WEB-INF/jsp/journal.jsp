<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
   
       <title>Touché Crevé - Journal de la partie : ${partie.nom}</title>
	   <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	   <meta name="description" content="Journal d'une partie">
	   <link rel="stylesheet" media="screen" type="text/css" title="Design" href="touche_creve.css">
	  
    </head>
	<body>

	
		<div class="container_1st_level">
		
			<h2>Journal <br/>${partie.nom}</h2>
			
			<div class="journal_meta_info_container">
				<div>Début de la partie : <b>${partie.dateDebut}</b></div>
				<div>Joueurs : <span class="joueur_rouge">${partie.joueurRouge.nom}</span> vs. <span class="joueur_bleu">${partie.joueurBleu.nom}</span></div>

			</div>
			
			<div class="journal_container">
				<c:forEach var="mouvements" items="${mouvements}">
		
		<p><span class="joueur_rouge">${partie.joueurRouge.nom}</span> tente de crever un pneu en <span class="case_jeu">${mouvements.ligne}:${mouvements.colonne}</span> -> <span class="tentative_rate">${mouvements.etatTentative}</span></p>
	</c:forEach>
				
		
		
			</div>
			
			<div class="nom_joueur_container">Connecté en tant que : <span class="nom_joueur"><c:out value="${sessionScope.nom}" /></span></div>
		
		</div>
	
	</body>
	
</html>