<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	 <script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>
	 <script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
	<script type="text/javascript">
	var idPartie = <c:out value="${partie.id}" />;
	</script>
	<script src="js/attente.js" type="text/javascript"></script>
	<title>En attente d'un adversaire..</title>
	<link rel="stylesheet" media="screen" type="text/css" title="Design" href="touche_creve.css">
	</head>
	<body>
		<div class="container_1st_level">
			<h2>${partie.nom}</h2>
			<p>En attente d'un adversaire..</p>
			<div class="nom_joueur_container">Connecté en tant que : <span class="nom_joueur"><c:out value="${sessionScope.nom}" /></span></div>
		</div>
	<FORM id="redirectPlacement" METHOD="POST" ACTION="join.html"></FORM>
	</body>
</html>