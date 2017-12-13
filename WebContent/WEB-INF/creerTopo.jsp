<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Création du topo</title>
	<link type="text/css" rel="stylesheet" href="<c:url value="/style/bootstrap.css"></c:url>" />
	<link type="text/css" rel="stylesheet" href="<c:url value="/style/style.css"></c:url>" />
	
</head>
<body>
     
     <div class="container">
    	<div class="row">
			<div class="col-md-12 ">
				<div class="panel panel-login">
					<div class="panel-heading">
						<div class="col-md-12">
							<h3> AJOUTER UN NOUVEAU TOPO </h3>
						</div>
						<hr>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
     	
					     		<form method="post" action="<c:url value="/creationTopo"/>"
					     		enctype="multipart/form-data"
					            role="form" style="display: block;">
					       			<div class="form-group">
					       				<input type="text" name="nomTopo" id="nomTopo" value="<c:out value="${topo.nom}"></c:out>" tabindex="1" class="form-control" placeholder="Titre du topo" />
					       			</div>
									
								
									
									<div class="form-group">
										<input type="text" name="nomAuteurTopo" id="nomAuteurTopo" value="<c:out value="${topo.nomAuteur}"></c:out>" tabindex="1" class="form-control" placeholder="Auteur du topo" />
					       			</div>
					       			
					       			<div class="form-group">
					       				<input type="text" name="dateSortieTopo" id="dateSortieTopo" value="<c:out value="${topo.dateSortie}"></c:out>" tabindex="1" class="form-control" placeholder="Date de sortie du topo" />
					       			</div>
					       			
					       			<p class="info">${ form.resultat }</p>
					       			
					       			<div class="form-group">
										<div class="row">
											<div class="col-md-12">
												<input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="VALIDER"/>
											</div>
										</div>
								    </div>
					            </form>
					       </div>
					    </div>
					</div>
			    </div>
			</div>
	    </div>
	  </div>

        <script src="<c:url value="/style/jquery-3.2.1.min.js"></c:url>"> </script>
		<script src="<c:url value="/style/bootstrap.min.js"> </c:url>"> </script>
    </body>
</html>

