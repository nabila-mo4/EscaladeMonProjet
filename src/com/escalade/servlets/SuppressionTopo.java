package com.escalade.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.escalade.beans.Topo;
import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.dao.TopoDao;

public class SuppressionTopo extends HttpServlet{
	
	public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String PARAM_ID_TOPO  = "idTopo";
    public static final String SESSION_TOPOS = "topos";

    public static final String VUE = "/listeTopos";

    private TopoDao  topoDao;

    public void init() throws ServletException {
        
        this.topoDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getTopoDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
      
        String idTopo = getValeurParametre( request, PARAM_ID_TOPO );
        Long id = Long.parseLong( idTopo );

       
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
		Map<Long, Topo> topos = (HashMap<Long, Topo>) session.getAttribute( SESSION_TOPOS );

        
        if ( id != null && topos != null ) {
            try {
                
                topoDao.remove( topos.get( id ) );
               
               topos.remove( id );
            } catch ( DAOException e ) {
                e.printStackTrace();
            }
           
            session.setAttribute( SESSION_TOPOS, topos );
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
