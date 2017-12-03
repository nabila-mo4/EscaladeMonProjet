<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <meta charset="utf-8" />
        <title>Liste des prets</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"></c:url>" />
    </head>
    <body>
    <c:import url="/inc/menu.jsp"></c:import>
        
        <div id="corps">
        <c:choose>
        
        <c:when test="${ empty sessionScope.prets }">
         <p class="erreur">Aucun pret enregistré.</p>
        
        </c:when>
        
        <c:otherwise>
        
        <table>
                <tr>
                    <th>Date de début</th>
                    <th>Date de fin</th>
                    <th>Nom de l'emprunteur du topo</th>
                    <th>Email de l'emprunteur du topo</th>
                    <th>Nom du demandeur </th>
                    <th> Email du demandeur </th>
                    
                    <th class="action">Action</th>                    
                </tr>
        <c:forEach items="${ sessionScope.prets }" var="mapPrets" varStatus="boucle">
        <tr>
        
         <td><c:out value="${ mapPrets.value.topo.nom }"></c:out></td>
        <td> 
        	<c:out value="${ mapPrets.value.dateDebut }"></c:out>
         </td>
         
         <td>
         	<c:out value="${ mapPrets.value.dateFin}"></c:out>
         
         </td>
         
         <td>
         
         <c:out value="${ mapPrets.value.nomEmprunteur }"></c:out>
         
         </td>
         
         <td><c:out value="${ mapPrets.value.emailEmprunteur}"></c:out>
         </td>
         
          <td><c:out value="${ mapPrets.value.nomDemandeur}"></c:out>
         </td>
         
          <td><c:out value="${ mapPrets.value.emailDemandeur}"></c:out>
         </td>
        
        </tr>
        
        </c:forEach>
         
       </table>
                
        </c:otherwise>   
        </c:choose>
        </div>
    </body>
</html>