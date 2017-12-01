package com.escalade.forms;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.escalade.beans.Site;
import com.escalade.dao.SiteDao;
import com.escalade.dao.DAOException;

	public final class CreationSiteForm {
	    private static final String CHAMP_NOM = "nomSite";
	    private static final String CHAMP_EMPLACEMENTGEO    = "emplacementGeoSite";
	    private static final String CHAMP_TYPE   = "typeSite";
	    private static final String CHAMP_HAUTEUR   = "hauteurSite";                 
	    private String resultat;
	    private Map<String, String> erreurs = new HashMap<String, String>();
	    private SiteDao siteDao;

	    public CreationSiteForm( SiteDao siteDao ) {
	        this.siteDao = siteDao;
	    }

	    public Map<String, String> getErreurs() {
	        return erreurs;
	    }

	    public String getResultat() {
	        return resultat;
	    }

	    public Site createSite( HttpServletRequest request) {
	        String nom = getValeurChamp( request, CHAMP_NOM );
	        String emplacementGeo = getValeurChamp( request, CHAMP_EMPLACEMENTGEO );
	        String type = getValeurChamp( request, CHAMP_TYPE );
	        String hauteur = getValeurChamp( request, CHAMP_HAUTEUR );
	        int hauteurt= Integer.parseInt(hauteur);
	        
	        Site site = new Site();
	        site.setNom(nom);
	        site.setEmplacementGeo(emplacementGeo);
	        site.setType(type);
	        site.setHauteur(hauteurt);   

	        try {
	            if ( erreurs.isEmpty() ) {
	                siteDao.create( site );
	                resultat = "Succès de la création du site.";
	            } else {
	                resultat = "Échec de la création du site.";
	            }
	        } catch ( DAOException e ) {
	            setErreur( "imprévu", "Erreur imprévue lors de la création." );
	            resultat = "Échec de la création du site : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
	            e.printStackTrace();
	        }

	        return site;
	    }
	    private void setErreur( String champ, String message ) {
	        erreurs.put( champ, message );
	    }

	   
	    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
	        String valeur = request.getParameter( nomChamp );
	        if ( valeur == null || valeur.trim().length() == 0 ) {
	            return null;
	        } else {
	            return valeur;
	        }
	    }        
	    
	}

