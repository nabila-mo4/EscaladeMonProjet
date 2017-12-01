package com.escalade.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.escalade.beans.Longueur;
import com.escalade.beans.Voie;
import com.escalade.dao.DAOException;
import com.escalade.dao.LongueurDao;
import com.escalade.dao.VoieDao;

	
	public final class CreationLongueurForm {
	    private static final String CHAMP_COTATION= "cotationLongueur";
	    private static final String CHAMP_HAUTEUR = "hauteurLongueur";
	    private static final String CHAMP_NOMRELAIS = "nomRelaisLongueur";
	    private String resultat;
	    private Map<String, String> erreurs= new HashMap<String, String>();
	    private VoieDao  voieDao;
	    private LongueurDao longueurDao;
	    
	    public CreationLongueurForm(LongueurDao longueurDao)
	    {
	    	this.longueurDao = longueurDao;
	    }

	    public CreationLongueurForm( VoieDao voieDao, LongueurDao longueurDao ) {
	        this.voieDao = voieDao;
	        this.longueurDao = longueurDao;
	    }

	    public Map<String, String> getErreurs() {
	        return erreurs;
	    }

	    public String getResultat() {
	        return resultat;
	    }

	    public Longueur createLongueur( HttpServletRequest request) {
	  
	        
	        CreationVoieForm voieForm = new CreationVoieForm( voieDao );
	        Voie voie = voieForm.createVoie( request);
	        erreurs = voieForm.getErreurs();
	   
	        String hauteur = getValeurChamp( request, CHAMP_HAUTEUR );
	        String cotation = getValeurChamp( request, CHAMP_COTATION );
	        String nomRelais = getValeurChamp( request, CHAMP_NOMRELAIS );
	        int hauteurt= Integer.parseInt(hauteur);
	        

	        Longueur longueur = new Longueur();
	        

	        try { 
	            longueur.setHauteur(hauteurt);
	            longueur.setCotation(cotation);
	            longueur.setNomRelais(nomRelais);
	            longueur.setVoie(voie);

	            if ( erreurs.isEmpty() ) {
	                longueurDao.create(longueur);
	                resultat = "Succès de la création de la longueur.";
	            }
	            else {
	                resultat = "Échec de la création de la longueur.";
	            }
	        } catch ( DAOException e ) {
	            setErreur( "imprévu", "Erreur imprévue lors de la création." );
	            resultat = "Échec de la création de la longueur : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
	            e.printStackTrace();
	        }

	        return longueur;
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