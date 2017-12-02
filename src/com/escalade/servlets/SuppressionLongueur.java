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
import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.dao.LongueurDao;


public class SuppressionLongueur extends HttpServlet{
	
	public static final String CONF_DAO_FACTORY  = "daofactory";
    public static final String PARAM_ID_LONGUEUR = "idLongueur";
    public static final String SESSION_LONGUEURS = "longueurs";

    public static final String VUE = "/listeLongueurs";

    private LongueurDao longueurDao;

    public void init() throws ServletException {
       
        this.longueurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getLongueurDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        
        String idLongueur = getValeurParametre( request, PARAM_ID_LONGUEUR );
        Long id = Long.parseLong( idLongueur );

       
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
		Map<Long, Longueur> longueurs = (HashMap<Long, Longueur>) session.getAttribute( SESSION_LONGUEURS );

       
        if ( id != null && longueurs != null ) {
            try {
               
                longueurDao.remove( longueurs.get( id ) );
               
                longueurs.remove( id );
            } catch ( DAOException e ) {
                e.printStackTrace();
            }
            
            session.setAttribute( SESSION_LONGUEURS, longueurs );
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
