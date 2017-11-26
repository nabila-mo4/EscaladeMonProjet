<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Création d'un site</title>
		<link type="text/css" rel="stylesheet" href="monStyle.css" />
	</head>
    <body>
    
        <div>
            <form method="get" action="creationSite">
                <fieldset>
                    <legend>Informations du site</legend>
                    
                    <label for="nomSite">Nom <span class="requis">*</span></label>
                    <input type="text" id="nomSite" name="nomSite" value="" size="20" maxlength="20" />
                    <br />
                    
                    <label for="emplacementSite">Emplacement géographique <span class="requis">*</span> </label>
                    <input type="text" id="emplacementSite" name="emplacementSite" value="" size="20" maxlength="20" />
                    <br />
    
                    <label for="typeSite">Type <span class="requis">*</span></label>
                    <input type="text" id="typeSite" name="typeSite" value="" size="20" maxlength="20" />
                    <br />
    
                    <label for="hauteurSite">Hauteur <span class="requis">*</span></label>
                    <input type="text" id="hauteurSite" name="hauteurSite" value="" size="20" maxlength="20" />
                    <br />
                    
                </fieldset>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Effacer" /> <br />
            </form>
        </div>
    </body>
</html>