package com.escalade.servlets;

import javax.servlet.http.HttpServlet;
	
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.escalade.dao.SecteurDao;
import com.escalade.dao.SiteDao;
import com.escalade.forms.CreationSecteurForm;
import com.escalade.beans.Secteur;
import com.escalade.beans.Site;
import com.escalade.dao.DAOFactory;
	

	public class CreationSecteur extends HttpServlet {
	    public static final String CONF_DAO_FACTORY = "daofactory";
	    public static final String ATT_SECTEUR= "secteur";
	    public static final String ATT_FORM = "form";
	    public static final String SESSION_SITES= "sites";
	    public static final String SESSION_SECTEURS= "secteurs";
	    public static final String APPLICATION_SITES = "initClients";
	   
	    public static final String VUE_SUCCES= "/WEB-INF/afficherSecteur.jsp";
	    public static final String VUE_FORM = "/WEB-INF/creerSecteur.jsp";

	    private SiteDao  siteDao;
	    private SecteurDao secteurDao;

	    public void init() throws ServletException {
	      
	        this.siteDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getSiteDao();
	        this.secteurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getSecteurDao();
	    }

	    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
	       
	        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
	    }

	    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
	   
	        CreationSecteurForm form = new CreationSecteurForm( siteDao, secteurDao );

	        Secteur secteur = form.createSecteur( request );

	       
	        request.setAttribute( ATT_SECTEUR, secteur );
	        request.setAttribute( ATT_FORM, form );

	       
	        if ( form.getErreurs().isEmpty() ) {
	           
	            HttpSession session = request.getSession();
	            Map<Long, Site> sites = (HashMap<Long, Site>) session.getAttribute( SESSION_SITES );
	           
	            if ( sites == null ) {
	                sites= new HashMap<Long, Site>();
	            }
	          
	            sites.put( secteur.getSite().getId(), secteur.getSite() );
	          
	            session.setAttribute( SESSION_SITES, sites);
	            Map<Long, Secteur> secteurs = (HashMap<Long, Secteur>) session.getAttribute( SESSION_SECTEURS );
	            
	            if ( secteurs == null ) {
	                secteurs = new HashMap<Long, Secteur>();
	            }
	          
	            secteurs.put( secteur.getId(), secteur);
	            
	            session.setAttribute( SESSION_SECTEURS, secteurs);

	           
	            this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
	        } else {
	           
	            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
	        }
	    }
	}

