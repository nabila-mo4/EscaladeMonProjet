package com.escalade.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.escalade.beans.Secteur;
import com.escalade.beans.Site;
import com.escalade.dao.SecteurDao;
import com.escalade.dao.SiteDao;
import com.escalade.dao.DAOException;

	
	public final class CreationSecteurForm {
	    private static final String CHAMP_NOM             = "nomSecteur";
	    private static final String CHAMP_HAUTEUR        = "hauteurSecteur";

	    private String resultat;
	    private Map<String, String> erreurs= new HashMap<String, String>();
	    private SiteDao  siteDao;
	    private SecteurDao secteurDao;

	    public CreationSecteurForm( SiteDao siteDao, SecteurDao secteurDao ) {
	        this.siteDao = siteDao;
	        this.secteurDao = secteurDao;
	    }

	    public Map<String, String> getErreurs() {
	        return erreurs;
	    }

	    public String getResultat() {
	        return resultat;
	    }

	    public Secteur createSecteur( HttpServletRequest request) {
	  
	        
	        CreationSiteForm siteForm = new CreationSiteForm( siteDao );
	        Site site = siteForm.createSite( request);
	        erreurs = siteForm.getErreurs();
	        String nom = getValeurChamp( request, CHAMP_NOM );
	        String hauteur = getValeurChamp( request, CHAMP_HAUTEUR );
	        int hauteurt= Integer.parseInt(hauteur);
	        

	        Secteur secteur = new Secteur();
	        

	        try { 
	            secteur.setNom(nom);
	            secteur.setHauteur(hauteurt);
	            secteur.setSite(site);

	            if ( erreurs.isEmpty() ) {
	                secteurDao.create(secteur);
	                resultat = "Succès de la création du secteur.";
	            }
	            else {
	                resultat = "Échec de la création du secteur.";
	            }
	        } catch ( DAOException e ) {
	            setErreur( "imprévu", "Erreur imprévue lors de la création." );
	            resultat = "Échec de la création de la commande : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
	            e.printStackTrace();
	        }

	        return secteur;
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


