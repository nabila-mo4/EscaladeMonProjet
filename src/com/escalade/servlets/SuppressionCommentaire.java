package com.escalade.servlets;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.escalade.beans.Commentaire;
import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.dao.CommentaireDao;

public class SuppressionCommentaire extends HttpServlet{

	public static final String CONF_DAO_FACTORY  = "daofactory";
    public static final String PARAM_ID_COMMENTAIRE = "idCommentaire";
    public static final String SESSION_COMMENTAIRES = "commentaires";

    public static final String VUE = "/listeCommentaires";

    private CommentaireDao commentaireDao;

    public void init() throws ServletException {
       
        this.commentaireDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCommentaireDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        
        String idCommentaire = getValeurParametre( request, PARAM_ID_COMMENTAIRE);
        Long id = Long.parseLong( idCommentaire );

       
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
		Map<Long, Commentaire> commentaires = (HashMap<Long, Commentaire>) session.getAttribute( SESSION_COMMENTAIRES );

       
        if ( id != null && commentaires != null ) {
            try {
               
                commentaireDao.remove( commentaires.get( id ) );
               
                commentaires.remove( id );
            } catch ( DAOException e ) {
                e.printStackTrace();
            }
            
            session.setAttribute( SESSION_COMMENTAIRES, commentaires );
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
