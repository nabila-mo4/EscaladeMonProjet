package com.escalade.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.escalade.beans.Topo;
import com.escalade.dao.DAOException;
import com.escalade.dao.TopoDao;


	
	public final class CreationTopoForm {
	    private static final String CHAMP_NOM = "nomTopo";
	    private static final String CHAMP_NOMAUTEUR   = "nomAuteurTopo";
	    private static final String CHAMP_DATESORTIE= "dateSortieTopo";
	            
	    private String resultat;
	    private Map<String, String> erreurs = new HashMap<String, String>();
	    private TopoDao topoDao;

	    public CreationTopoForm( TopoDao topoDao ) {
	        this.topoDao = topoDao;
	    }

	    public Map<String, String> getErreurs() {
	        return erreurs;
	    }

	    public String getResultat() {
	        return resultat;
	    }

	    public Topo createTopo( HttpServletRequest request) {
	        String nom = getValeurChamp( request, CHAMP_NOM );
	        String nomAuteur = getValeurChamp( request, CHAMP_NOMAUTEUR );
	        String dateSortie = getValeurChamp( request, CHAMP_DATESORTIE);
	        
	        
	        Topo topo = new Topo();
	        topo.setNom(nom);
	        topo.setNomAuteur(nomAuteur); 
	        topo.setDateSortie(dateSortie);

	        try {
	            if ( erreurs.isEmpty() ) {
	                topoDao.create( topo );
	                resultat = "Succès de la création du topo.";
	            } else {
	                resultat = "Échec de la création du topo.";
	            }
	        } catch ( DAOException e ) {
	            setErreur( "imprévu", "Erreur imprévue lors de la création." );
	            resultat = "Échec de la création du topo : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
	            e.printStackTrace();
	        }

	        return topo;
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


