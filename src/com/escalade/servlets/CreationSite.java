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
import com.escalade.forms.CreationSiteForm;
import com.escalade.beans.Site;
import com.escalade.dao.DAOFactory;

public class CreationSite extends HttpServlet{
		
		    public static final String CONF_DAO_FACTORY = "daofactory";
		    public static final String ATT_SITE= "site";
		    public static final String ATT_FORM = "form";
		    public static final String SESSION_SITES= "sites";
		    public static final String VUE_SUCCES= "/WEB-INF/afficherSite.jsp";
		    public static final String VUE_FORM = "/WEB-INF/creerSite.jsp";

		    private SiteDao  siteDao;

		    public void init() throws ServletException {
		       
		        this.siteDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getSiteDao();
		    }

		    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		        
		        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
		    }

		    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		        
		        CreationSiteForm form = new CreationSiteForm( siteDao );

		        Site site = form.createSite( request);

		        
		        request.setAttribute( ATT_SITE, site );
		        request.setAttribute( ATT_FORM, form );

		        
		        if ( form.getErreurs().isEmpty() ) {
		          
		            HttpSession session = request.getSession();
		            Map<Long, Site> sites = (HashMap<Long, Site>) session.getAttribute( SESSION_SITES );
		           
		            if ( sites == null ) {
		                sites = new HashMap<Long, Site>();
		            }
		            
		            sites.put( site.getId(), site );
		           
		            session.setAttribute( SESSION_SITES, sites );

		           
		            this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
		        } else {
		            
		            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
		        }
		    }
		
		
										 }
