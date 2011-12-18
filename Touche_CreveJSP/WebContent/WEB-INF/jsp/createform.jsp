<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
   
       <title>Touché Crevé - Création d'une partie</title>
	   <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	   <meta name="description" content="Création d'une partie">
	   <link rel="stylesheet" media="screen" type="text/css" title="Design" href="touche_creve.css">
	  
    </head>
	<body>
	
		<div class="container_1st_level">
		
			<h2>Création d'une partie</h2>
			
			<div class="creation_partie_container">
				<form id="creation_partie_form" method="post" action="cree.html">
					<p>
						Nom de la partie : <input type="text" name="nompartie"/>
						<input type="submit" value="Créer la partie"/>
					</p>
				</form>
			</div>
			
			<div class="nom_joueur_container">Connecté en tant que : <span class="nom_joueur"><c:out value="${sessionScope.nom}" /></span></div>
		
		</div>
	
	</body>
</html>