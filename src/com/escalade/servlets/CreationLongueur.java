package com.escalade.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.escalade.beans.Longueur;
import com.escalade.beans.Voie;
import com.escalade.dao.DAOFactory;
import com.escalade.dao.LongueurDao;
import com.escalade.dao.VoieDao;
import com.escalade.forms.CreationLongueurForm;

public class CreationLongueur extends HttpServlet{

	 public static final String CONF_DAO_FACTORY = "daofactory";
	    public static final String ATT_LONGUEUR= "longueur";
	    public static final String ATT_FORM = "form";
	    public static final String SESSION_LONGUEURS= "longueurs";
	    public static final String APPLICATION_LONGUEURS = "initLongueurs";
	  

	    public static final String VUE_SUCCES= "/WEB-INF/afficherLongueur.jsp";
	    public static final String VUE_FORM = "/WEB-INF/creerLongueur.jsp";

	    private VoieDao voieDao;
	    private LongueurDao longueurDao;

	    public void init() throws ServletException {
	      
	        this.voieDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getVoieDao();
	        this.longueurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getLongueurDao();
	    }

	    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
	       
	        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
	    }

	    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
	   
	        CreationLongueurForm form = new CreationLongueurForm( voieDao, longueurDao );

	        Longueur longueur = form.createLongueur( request );

	       
	        request.setAttribute( ATT_LONGUEUR, longueur );
	        request.setAttribute( ATT_FORM, form );

	       
	        if ( form.getErreurs().isEmpty() ) {
	           
	            HttpSession session = request.getSession();
	            Map<Long, Voie> voies = (HashMap<Long, Voie>) session.getAttribute( SESSION_LONGUEURS);
	           
	            if ( voies == null ) {
	                voies= new HashMap<Long, Voie>();
	            }
	          
	            voies.put( longueur.getVoie().getId(), longueur.getVoie() );
	          
	            session.setAttribute( SESSION_LONGUEURS, voies);
	            Map<Long, Longueur> longueurs = (HashMap<Long, Longueur>) session.getAttribute( SESSION_LONGUEURS );
	            
	            if ( longueurs == null ) {
	                longueurs = new HashMap<Long, Longueur>();
	            }
	          
	            longueurs.put( longueur.getId(), longueur);
	            
	            session.setAttribute( SESSION_LONGUEURS, longueurs);

	           
	            this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
	        } else {
	           
	            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
	        }
	    }
}