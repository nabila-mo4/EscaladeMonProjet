package com.escalade.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.escalade.beans.Voie;
import com.escalade.beans.Secteur;
import com.escalade.dao.DAOFactory;
import com.escalade.dao.SecteurDao;
import com.escalade.dao.VoieDao;

import com.escalade.forms.CreationVoieForm;

public class CreationVoie extends HttpServlet{
	
	 public static final String CONF_DAO_FACTORY = "daofactory";
	    public static final String ATT_VOIE= "voie";
	    public static final String ATT_FORM = "form";
	    public static final String SESSION_SECTEURS= "secteurs";
	    public static final String SESSION_VOIES= "voies";
	    public static final String APPLICATION_SECTEURS = "initSecteurs";
	  

	    public static final String VUE_SUCCES= "/WEB-INF/afficherVoie.jsp";
	    public static final String VUE_FORM = "/WEB-INF/creerVoie.jsp";

	    private SecteurDao secteurDao;
	    private VoieDao voieDao;

	    public void init() throws ServletException {
	      
	        this.secteurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getSecteurDao();
	        this.voieDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getVoieDao();
	    }

	    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
	       
	        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
	    }

	    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
	   
	        CreationVoieForm form = new CreationVoieForm( secteurDao, voieDao );

	        Voie voie = form.createVoie( request );

	       
	        request.setAttribute( ATT_VOIE, voie );
	        request.setAttribute( ATT_FORM, form );

	       
	        if ( form.getErreurs().isEmpty() ) {
	           
	            HttpSession session = request.getSession();
	            Map<Long, Secteur> secteurs = (HashMap<Long, Secteur>) session.getAttribute( SESSION_SECTEURS);
	           
	            if ( secteurs == null ) {
	                secteurs= new HashMap<Long, Secteur>();
	            }
	          
	            secteurs.put( voie.getSecteur().getId(), voie.getSecteur() );
	          
	            session.setAttribute( SESSION_SECTEURS, secteurs);
	            Map<Long, Voie> voies = (HashMap<Long, Voie>) session.getAttribute( SESSION_SECTEURS );
	            
	            if ( voies == null ) {
	                voies = new HashMap<Long, Voie>();
	            }
	          
	            voies.put( voie.getId(), voie);
	            
	            session.setAttribute( SESSION_VOIES, voies);

	           
	            this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
	        } else {
	           
	            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
	        }
	    }

}
