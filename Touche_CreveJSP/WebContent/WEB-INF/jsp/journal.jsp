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
			<c:set var="couleurCount" value="0" />
			<c:forEach var="mouvements" items="${mouvements}">
				<c:if test='${mouvements.etatTentative == 0}'>
    				<c:set var="etat" value="Raté" />
				</c:if>
				<c:if test='${mouvements.etatTentative == 1}'>
    				<c:set var="etat" value="Touché" />
				</c:if>
				<c:if test='${mouvements.etatTentative == 2}'>
    				<c:set var="etat" value="Crevé" />
				</c:if>
				
				
				<c:if test='${mouvements.ligne == 0}'>
    				<c:set var="ligne" value="A" />
				</c:if>
				<c:if test='${mouvements.ligne == 1}'>
    				<c:set var="ligne" value="B" />
				</c:if>
				<c:if test='${mouvements.ligne == 2}'>
    				<c:set var="ligne" value="C" />
				</c:if>
				<c:if test='${mouvements.ligne == 3}'>
    				<c:set var="ligne" value="D" />
				</c:if>
				<c:if test='${mouvements.ligne == 4}'>
    				<c:set var="ligne" value="E" />
				</c:if>
				<c:if test='${mouvements.ligne == 5}'>
    				<c:set var="ligne" value="F" />
				</c:if>
				<c:if test='${mouvements.ligne == 6}'>
    				<c:set var="ligne" value="G" />
				</c:if>
				<c:if test='${mouvements.ligne == 7}'>
    				<c:set var="ligne" value="H" />
				</c:if>
				<c:if test='${mouvements.ligne == 8}'>
    				<c:set var="ligne" value="I" />
				</c:if>
				<c:if test='${mouvements.ligne == 9}'>
    				<c:set var="ligne" value="J" />
				</c:if>
				
				<c:choose>
					 <c:when test='${couleurCount mod 2 == 0}'>
	        			<p><span class="joueur_rouge">${partie.joueurRouge.nom}</span> tente de crever un pneu en <span class="case_jeu">${ligne}:${mouvements.colonne+1}</span> -> <span class="tentative_rate">${etat}</span></p>
			    	</c:when>
			    	<c:otherwise>
	        			<p><span class="joueur_bleu">${partie.joueurBleu.nom}</span> tente de crever un pneu en <span class="case_jeu">${ligne}:${mouvements.colonne+1}</span> -> <span class="tentative_rate">${etat}</span></p>
	    			</c:otherwise>
				</c:choose>
				<c:set var="couleurCount" value="${couleurCount + 1}" />
			</c:forEach>
				
		
		
			</div>
			
			<div class="nom_joueur_container">Connecté en tant que : <span class="nom_joueur"><c:out value="${sessionScope.nom}" /></span></div>
		
		</div>
	
	</body>
	
</html>