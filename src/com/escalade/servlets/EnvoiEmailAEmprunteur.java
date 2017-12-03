package com.escalade.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.escalade.beans.Site;
import com.escalade.dao.DAOFactory;
import com.escalade.dao.PretDao;

public class EnvoiEmailAEmprunteur extends HttpServlet {
	
	private String hote;
	private String port;
	private String utilisateur;
	private String motPasse;
	private PretDao pretDao;
	public static final String SESSION_EMAILEMPRUNTEUR = "emailEmprunteur";
	public static final String SESSION_NOMDEMANDEUR ="nomDemandeur";
	public static final String CONF_DAO_FACTORY = "daofactory";
	
	public void init() {
		this.pretDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getPretDao();
		ServletContext context= getServletContext();
		hote =context.getInitParameter("hote");
		port = context.getInitParameter("port");
		utilisateur = context.getInitParameter("utilisateur");
		motPasse = context.getInitParameter("motPasse");
	}
	
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession();
         String emailEmprunteur = (String) session.getAttribute( SESSION_EMAILEMPRUNTEUR);
         String nomDemandeur = (String) session.getAttribute(SESSION_NOMDEMANDEUR);
		
		String objet = "Demande d'emprunt";
		String contenu = "Vous avez reçu une nouvelle demande de la part " +nomDemandeur+
						 "Cliquez sur le lien ci-dessous pour répondre à cette demande"
						 ;
		String message ="";
		try { 
			EmailUtilitaire.envoiEmail (hote, port, utilisateur, motPasse, emailEmprunteur, objet, contenu);
			message = "La demande est prise en compte";
			
		}
		
		catch (Exception e) {
			e.printStackTrace();
			message = "Erreur"+e.getMessage();
			
			
		}
		
		finally {
			request.setAttribute("message", message);
			getServletContext().getRequestDispatcher("/FicheTopo.jsp").forward(request, response);
		}
	}

}
