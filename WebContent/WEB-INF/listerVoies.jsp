<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <meta charset="utf-8" />
        <title>Liste des voies</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"></c:url>" />
    </head>
    <body>
    <c:import url="/inc/menu.jsp"></c:import>
        
        <div id="corps">
        <c:choose>
        
        <c:when test="${ empty sessionScope.voies }">
         <p class="erreur">Aucune voie enregistrée.</p>
        
        </c:when>
        
        <c:otherwise>
        
        <table>
                <tr>
                    <th>Nom</th>
                    <th>Hauteur</th>
                    <th>Nom de l'hauteur de la voie</th>
                    <th>Hauteur</th>
                    
                    <th class="action">Action</th>                    
                </tr>
        <c:forEach items="${ sessionScope.voies }" var="mapVoies" varStatus="boucle">
        <tr>
        <td> 
        	<c:out value="${ mapVoies.value.nom }"></c:out>
         </td>
         
         <td>
         	<c:out value="${ mapVoies.value.hauteur}"></c:out>
         
         </td>
         
         <td>
         
         <c:out value="${ mapVoies.value.nomHauteurVoie }"></c:out>
         
         </td>
         
        
        
        </tr>
        
        </c:forEach>
         
       </table>
                
        </c:otherwise>   
        </c:choose>
        </div>
    </body>
</html>