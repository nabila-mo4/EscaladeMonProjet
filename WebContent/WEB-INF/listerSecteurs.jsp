<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <meta charset="utf-8" />
        <title>Liste des secteurs</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"></c:url>" />
    </head>
    <body>
    <c:import url="/inc/menu.jsp"></c:import>
        
        <div id="corps">
        <c:choose>
        
        <c:when test="${ empty sessionScope.secteurs }">
         <p class="erreur">Aucun secteur enregistré.</p>
        
        </c:when>
        
        <c:otherwise>
        
        <table>
                <tr>
                    <th>Nom</th>
            
                    <th>Hauteur</th>
                    
                    <th class="action">Action</th>                    
                </tr>
        <c:forEach items="${ sessionScope.secteurs }" var="mapSecteurs" varStatus="boucle">
        <tr>
         <td><c:out value="${ mapSecteurs.value.site.nom }"></c:out></td>
        <td> 
        	<c:out value="${ mapSecteurs.value.nom }"></c:out>
         </td>
         
         <td>
         	<c:out value="${ mapSecteurs.value.hauteur}"></c:out>
         
         </td>
         
        
        
        </tr>
        
        </c:forEach>
         
       </table>
                
        </c:otherwise>   
        </c:choose>
        </div>
    </body>
</html>