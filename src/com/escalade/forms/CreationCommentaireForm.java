package com.escalade.forms;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.escalade.beans.Commentaire;
import com.escalade.beans.Longueur;
import com.escalade.beans.Secteur;
import com.escalade.beans.Site;
import com.escalade.beans.Topo;
import com.escalade.beans.Voie;
import com.escalade.dao.CommentaireDao;
import com.escalade.dao.DAOException;
import com.escalade.dao.LongueurDao;
import com.escalade.dao.SecteurDao;
import com.escalade.dao.SiteDao;
import com.escalade.dao.TopoDao;
import com.escalade.dao.VoieDao;
	public final class CreationCommentaireForm {
	    private static final String CHAMP_CONTENU= "contenuCommentaire";
	    private static final String CHAMP_NOMUTILISATEUR= "nomUtilisateurCommentaire";
	   
	    private String resultat;
	    private Map<String, String> erreurs= new HashMap<String, String>();
	    private SiteDao  siteDao;
	    private SecteurDao secteurDao;
	    private VoieDao voieDao;
	    private LongueurDao longueurDao;
	    private TopoDao topoDao;
	   

	    
	    public Map<String, String> getErreurs() {
	        return erreurs;
	    }

	    public String getResultat() {
	        return resultat;
	    }

	    public Secteur createSecteur( HttpServletRequest request) {
	  
	        DateTime date= new DateTime();
	        CreationSiteForm siteForm = new CreationSiteForm( siteDao );
	        CreationSecteurForm secteurForm = new CreationSecteurForm( secteurDao );
	        CreationVoieForm voieForm = new CreationVoieForm(voieDao );
	        CreationLongueurForm longueurForm = new CreationLongueurForm( longueurDao );
	        CreationTopoForm topoForm = new CreationTopoForm( topoDao );
	        Site site = siteForm.createSite( request);
	        Secteur secteur = secteurForm.createSecteur( request);
	        Voie voie = voieForm.createVoie( request);
	        Longueur longueur= longueurForm.createLongueur( request);
	        Topo topo = topoForm.createTopo( request);
	        erreurs = siteForm.getErreurs();
	        String contenu = getValeurChamp( request, CHAMP_CONTENU );
	        String nomAuteur= getValeurChamp( request, CHAMP_NOMUTILISATEUR);
	        
	  
	        Commentaire commentaire = new Commentaire();
	        

	        try { 
	        	commentaire.setContenu(contenu);
	        	commentaire.setDatePublication(date);
	        	commentaire.setSite(site);
	        	commentaire.setSecteur(secteur);
	        	commentaire.setVoie(voie);
	        	commentaire.setLongueur(longueur);
	        	commentaire.setTopo(topo);

	            if ( erreurs.isEmpty() ) {
	                secteurDao.create(secteur);
	                resultat = "Succès de la création du commentaire.";
	            }
	            else {
	                resultat = "Échec de la création du commentaire.";
	            }
	        } catch ( DAOException e ) {
	            setErreur( "imprévu", "Erreur imprévue lors de la création." );
	            resultat = "Échec de la création du commentaire.";
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