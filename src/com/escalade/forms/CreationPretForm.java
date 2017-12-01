package com.escalade.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.escalade.beans.Pret;
import com.escalade.beans.Topo;
import com.escalade.dao.DAOException;
import com.escalade.dao.PretDao;
import com.escalade.dao.TopoDao;

	public final class CreationPretForm {
	    private static final String CHAMP_DATEDEBUT= "dateDebutPret";
	    private static final String CHAMP_DATEFIN= "dateFinPret";
	    private static final String CHAMP_NOMEMPRUNTEUR= "nomEmprunteurPret";
	    private static final String CHAMP_EMAILEMPRUNTEUR= "emailEmprunteurPret";
	    private static final String CHAMP_NOMDEMANDEUR= "nomDemandeurPret";
	    private static final String CHAMP_EMAILDEMANDEUR= "emailDemandeurPret";
	   

	    private String resultat;
	    private Map<String, String> erreurs= new HashMap<String, String>();
	    private TopoDao  topoDao;
	    private PretDao pretDao;
	    
	    public CreationPretForm(PretDao pretDao)
	    {
	    	this.pretDao = pretDao;
	    }

	    public CreationPretForm( TopoDao topoDao, PretDao pretDao ) {
	        this.topoDao = topoDao;
	        this.pretDao = pretDao;
	    }

	    public Map<String, String> getErreurs() {
	        return erreurs;
	    }

	    public String getResultat() {
	        return resultat;
	    }

	    public Pret createPret( HttpServletRequest request) {
	  
	        
	        CreationTopoForm topoForm = new CreationTopoForm( topoDao );
	        Topo topo = topoForm.createTopo( request);
	        erreurs = topoForm.getErreurs();
	        String dateDebut = getValeurChamp( request, CHAMP_DATEDEBUT );
	        String dateFin = getValeurChamp( request, CHAMP_DATEFIN );
	        String nomDemandeur = getValeurChamp( request, CHAMP_NOMDEMANDEUR );
	        String emailDemandeur= getValeurChamp( request, CHAMP_EMAILDEMANDEUR );
	        String nomEmprunteur = getValeurChamp( request, CHAMP_NOMEMPRUNTEUR);
	        String emailEmprunteur = getValeurChamp( request, CHAMP_EMAILEMPRUNTEUR );
	    
	        Pret pret= new Pret();
	        

	        try { 
	            pret.setDateDebut(dateDebut);
	            pret.setDateFin(dateFin);
	            pret.setNomDemandeur(nomDemandeur);
	            pret.setEmailDemandeur(emailDemandeur);
	            pret.setNomEmprunteur(nomEmprunteur);
	            pret.setEmailEmprunteur(emailEmprunteur);
	           
	            pret.setTopo(topo);

	            if ( erreurs.isEmpty() ) {
	                pretDao.create(pret);
	                resultat = "Succès de la création du pret.";
	            }
	            else {
	                resultat = "Échec de la création du pret.";
	            }
	        } catch ( DAOException e ) {
	            setErreur( "imprévu", "Erreur imprévue lors de la création." );
	            resultat = "Échec de la création du pret.";
	            e.printStackTrace();
	        }

	        return pret;
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
