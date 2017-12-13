package com.escalade.servlets;

import java.io.IOException;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.escalade.dao.DAOFactory;
import com.escalade.dao.PretDao;

public class EnvoiEmailADemandeurPos extends HttpServlet{
		
		private String hote;
		private String port;
		private String utilisateur;
		private String motPasse;
		private PretDao pretDao;
		public static final String SESSION_EMAILDEMANDEUR = "emailDemandeur";
		public static final String SESSION_DATEDEBUT ="dateDebut";
		public static final String SESSION_DATEFIN = "dateFin";
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
	         String emailDemandeur = (String) session.getAttribute( SESSION_EMAILDEMANDEUR);
	         String dateDebut = (String) session.getAttribute( SESSION_DATEDEBUT);
	         String dateFin = (String) session.getAttribute( SESSION_DATEFIN);
	         
			String objet = "Réponse à votre demande";
			String contenu = "Votre demande d'emprunt est acceptée, l'emprunt est valable entre les dates"
					+dateDebut+"et"+dateFin;
						 
			String message ="";
			try { 
				EmailUtilitaire.envoiEmail (hote, port, utilisateur, motPasse, emailDemandeur, objet, contenu);
				message = "Votre accord est bien transmis";
				
			}
			
			catch (Exception e) {
				e.printStackTrace();
				message = "Erreur"+e.getMessage();
				
				
			}
			
			finally {
				request.setAttribute("message", message);
				getServletContext().getRequestDispatcher("/listerSites.jsp").forward(request, response);
			}
		}

	}


