package com.escalade.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.escalade.dao.SecteurDao;
import com.escalade.beans.Secteur;
import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;

public class SuppressionSecteur extends HttpServlet {
	    public static final String CONF_DAO_FACTORY  = "daofactory";
	    public static final String PARAM_ID_SECTEUR = "idSecteur";
	    public static final String SESSION_SECTEURS = "secteurs";

	    public static final String VUE = "/listeSecteurs";

	    private SecteurDao secteurDao;

	    public void init() throws ServletException {
	       
	        this.secteurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getSecteurDao();
	    }

	    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
	        
	        String idSecteur = getValeurParametre( request, PARAM_ID_SECTEUR );
	        Long id = Long.parseLong( idSecteur );

	       
	        HttpSession session = request.getSession();
	        @SuppressWarnings("unchecked")
			Map<Long, Secteur> secteurs = (HashMap<Long, Secteur>) session.getAttribute( SESSION_SECTEURS );

	       
	        if ( id != null && secteurs != null ) {
	            try {
	               
	                secteurDao.remove( secteurs.get( id ) );
	               
	                secteurs.remove( id );
	            } catch ( DAOException e ) {
	                e.printStackTrace();
	            }
	            
	            session.setAttribute( SESSION_SECTEURS, secteurs );
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
