<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Création d'un secteur</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"></c:url>" />
    </head>
    <body>
        <c:import url="/inc/menu.jsp" ></c:import>
        <div>
            <form method="post" action="<c:url value="/creationSecteur"> </c:url></form>" enctype="multipart/form-data">
                <fieldset>
                    <legend>Informations du secteur</legend>
                   
  
                    
                    <c:set var="client" value="${ commande.client }" scope="request" ></c:set>
                    <div id="nouveauClient">
                        <c:import url="/inc/inc_client_form.jsp" ></c:import>
                    </div>
                    
                    
                    <c:if test="${ !empty sessionScope.clients }">
                    <div id="ancienClient">
                        <select name="listeSites" id="listeSites">
                            <option value="">Choisissez un site</option>
                            
                            <c:forEach items="${ sessionScope.sites}" var="mapSites">
                           
                            <option value="${ mapSites.key }">${ mapSites.value.nom } </option>
                            </c:forEach>
                        </select>
                    </div>
                    </c:if>
                </fieldset>
                <fieldset>
                    <legend>Informations secteur</legend>
                    
                    <label for="nom">Nom <span class="requis">*</span></label>
                    <input type="text" id="v" name="nom"> 
                    
                    <br />
                    
                    <label for="hauteur">Hauteur <span class="requis">*</span></label>
                    <input type="text" id="v" name="hauteur"> 
                    
                    <br />
                    
                    
                    
                    <p class="info">${ form.resultat }</p>
                </fieldset>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" /> <br />
            </form>
        </div>
      
    </body>
</html>