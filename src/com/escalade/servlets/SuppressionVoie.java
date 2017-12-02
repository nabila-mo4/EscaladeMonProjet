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
import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.dao.VoieDao;

public class SuppressionVoie extends HttpServlet {

	public static final String CONF_DAO_FACTORY  = "daofactory";
    public static final String PARAM_ID_VOIE = "idVoie";
    public static final String SESSION_VOIES = "voies";

    public static final String VUE = "/listeVoies";

    private VoieDao voieDao;

    public void init() throws ServletException {
       
        this.voieDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getVoieDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        
        String idVoie = getValeurParametre( request, PARAM_ID_VOIE );
        Long id = Long.parseLong( idVoie );

       
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
		Map<Long, Voie> voies = (HashMap<Long, Voie>) session.getAttribute( SESSION_VOIES );

       
        if ( id != null && voies != null ) {
            try {
               
                voieDao.remove( voies.get( id ) );
               
                voies.remove( id );
            } catch ( DAOException e ) {
                e.printStackTrace();
            }
            
            session.setAttribute( SESSION_VOIES, voies );
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
