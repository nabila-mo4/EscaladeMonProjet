package com.escalade.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.escalade.beans.Secteur;
import com.escalade.beans.Voie;
import com.escalade.dao.DAOException;
import com.escalade.dao.SecteurDao;
import com.escalade.dao.VoieDao;

public class CreationVoieForm {
	
	    private static final String CHAMP_NOM = "nomVoie";
	    private static final String CHAMP_HAUTEUR = "hauteurVoie";
	    private static final String CHAMP_NOMHAUTEURVOIE = "nomHauteurVoie";
	    private static final String CHAMP_NBPOINTS = "nbPointsVoie";
	    private static final String CHAMP_ETAT = "etatVoie";

	    private String resultat;
	    private Map<String, String> erreurs= new HashMap<String, String>();
	    private VoieDao voieDao;
	    private SecteurDao secteurDao;
	    
	    public CreationVoieForm(VoieDao voieDao)
	    {
	    	this.voieDao= voieDao;
	    }

	    public CreationVoieForm( SecteurDao secteurDao, VoieDao voieDao ) {
	        this.secteurDao = secteurDao;
	        this.voieDao = voieDao;
	    }

	    public Map<String, String> getErreurs() {
	        return erreurs;
	    }

	    public String getResultat() {
	        return resultat;
	    }

	    public Voie createVoie( HttpServletRequest request) {
	  
	        
	        CreationSecteurForm secteurForm = new CreationSecteurForm(secteurDao );
	        Secteur secteur = secteurForm.createSecteur( request);
	        erreurs = secteurForm.getErreurs();
	        String nom = getValeurChamp( request, CHAMP_NOM );
	        String hauteur = getValeurChamp( request, CHAMP_HAUTEUR );
	        String nomHauteurVoie = getValeurChamp( request, CHAMP_NOMHAUTEURVOIE );
	        String nbPoints = getValeurChamp( request, CHAMP_NBPOINTS );
	        String etat = getValeurChamp( request, CHAMP_ETAT );
	        int hauteurt= Integer.parseInt(hauteur);
	        int nbPointst = Integer.parseInt(nbPoints);
	        

	        Voie voie = new Voie();
	        

	        try { 
	            voie.setNom(nom);
	            voie.setHauteur(hauteurt);
	            voie.setNomHauteurVoie(nomHauteurVoie);
	            voie.setNbPoints(nbPointst);
	            voie.setEtat(etat);
	            voie.setSecteur(secteur);

	            if ( erreurs.isEmpty() ) {
	                voieDao.create(voie);
	                resultat = "Succès de la création de la voie.";
	            }
	            else {
	                resultat = "Échec de la création de la voie.";
	            }
	        } catch ( DAOException e ) {
	            setErreur( "imprévu", "Erreur imprévue lors de la création." );
	            resultat = "Échec de la création de la voie : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
	            e.printStackTrace();
	        }

	        return voie;
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
