package com.escalade.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.escalade.dao.SiteDao;
import com.escalade.beans.Site;
import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;

public class SuppressionSite extends HttpServlet{
	
	
	    public static final String CONF_DAO_FACTORY = "daofactory";
	    public static final String PARAM_ID_SITE  = "idSite";
	    public static final String SESSION_SITES = "sites";

	    public static final String VUE = "/listeSites";

	    private SiteDao  siteDao;

	    public void init() throws ServletException {
	        
	        this.siteDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getSiteDao();
	    }

	    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
	      
	        String idSite = getValeurParametre( request, PARAM_ID_SITE );
	        Long id = Long.parseLong( idSite );

	       
	        HttpSession session = request.getSession();
	        @SuppressWarnings("unchecked")
			Map<Long, Site> sites = (HashMap<Long, Site>) session.getAttribute( SESSION_SITES );

	        
	        if ( id != null && sites != null ) {
	            try {
	                
	                siteDao.remove( sites.get( id ) );
	               
	               sites.remove( id );
	            } catch ( DAOException e ) {
	                e.printStackTrace();
	            }
	           
	            session.setAttribute( SESSION_SITES, sites );
	        }

	       
	        response.sendRedirect( request.getContextPath() + VUE );
	    }

	    
	    private static String getValeurParametre( HttpServletRequest request, String nomChamp ) {
	        String valeur = request.getParameter( nomChamp );
	        if ( valeur == null || valeur.trim().length() == 0 ) {
	            return null;
	        } else {
	            return valeur;
	        }
	    }
	}
