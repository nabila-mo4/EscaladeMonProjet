<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta charset="utf-8" />
        <title>Fiche du topo</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"></c:url>" />
    </head>
    <body>
        <c:import url="/inc/menu.jsp" ></c:import>
        <div id="corps">
            <p class="info">${form.resultat }</p>
            <p>Informations du topo</p>
            <p>Nom du topo: <c:out value="${ topo.nom }"></c:out></p>
            <p>Auteur du topo: <c:out value="${ topo.nomAuteur}"></c:out></p>
            <p>Date de sortie du topo: <c:out value="${ topo.dateSortie }"></c:out></p>
            
            
           
        </div>
            <a href="<c:url value="/EnvoiEmailAEmprunteur"> </c:url>">
                 Voulez-vous emprunter ce topo?
            </a>
            
    </body>
</html>