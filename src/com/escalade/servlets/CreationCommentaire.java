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
import com.escalade.beans.Longueur;
import com.escalade.beans.Secteur;
import com.escalade.beans.Site;
import com.escalade.beans.Topo;
import com.escalade.beans.Voie;
import com.escalade.dao.DAOFactory;
import com.escalade.dao.LongueurDao;
import com.escalade.dao.SecteurDao;
import com.escalade.dao.CommentaireDao;
import com.escalade.dao.SiteDao;
import com.escalade.dao.TopoDao;
import com.escalade.dao.VoieDao;
import com.escalade.forms.CreationCommentaireForm;

public class CreationCommentaire extends HttpServlet{
	
	 public static final String CONF_DAO_FACTORY = "daofactory";
	    public static final String ATT_COMMENTAIRE= "commentaire";
	    public static final String ATT_FORM = "form";
	    public static final String SESSION_SITES= "sites";
	    public static final String SESSION_SECTEURS = "secteurs";
	    public static final String SESSION_VOIES = "voies";
	    public static final String SESSION_LONGUEURS = "longueurs";
	    public static final String SESSION_TOPOS = "topos";
	    public static final String SESSION_COMMENTAIRES= "commentaires";
	    public static final String APPLICATION_SITES = "initClients";
	   
	    public static final String VUE_SUCCES= "/WEB-INF/afficherCommentaire.jsp";
	    public static final String VUE_FORM = "/WEB-INF/creerCommentaire.jsp";

	    private SiteDao  siteDao;
	    private SecteurDao secteurDao;
	    private VoieDao voieDao;
	    private LongueurDao longueurDao;
	    private TopoDao topoDao;
	    private CommentaireDao commentaireDao;

	    public void init() throws ServletException {
	      
	        this.siteDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getSiteDao();
	        this.secteurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getSecteurDao();
	        this.voieDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getVoieDao();
	        this.longueurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getLongueurDao();
	        this.topoDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getTopoDao();
	        this.commentaireDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCommentaireDao();
	    											}

	    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
	       
	        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
	    }

	    @SuppressWarnings({ "unchecked", "unchecked", "unchecked" })
		public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
	    	CreationCommentaireForm form = new CreationCommentaireForm( siteDao, secteurDao, voieDao,
	        longueurDao, topoDao, commentaireDao );

	        Commentaire commentaire = form.createCommentaire( request );

	       
	        request.setAttribute( ATT_COMMENTAIRE, commentaire );
	        request.setAttribute( ATT_FORM, form );

	       
	        if ( form.getErreurs().isEmpty() ) {
	           
	            HttpSession session = request.getSession();
	            Map<Long, Site> sites = (HashMap<Long, Site>) session.getAttribute( SESSION_SITES ); 
	           
	            if ( sites == null ) {
	                sites= new HashMap<Long, Site>();
	            }
	          
	            sites.put( commentaire.getSite().getId(), commentaire.getSite() );
	          
	            session.setAttribute( SESSION_SITES, sites);
	            
	            Map<Long, Secteur> secteurs = (HashMap<Long, Secteur>) session.getAttribute( SESSION_SECTEURS );
		           
	            if ( secteurs == null ) {
	                secteurs= new HashMap<Long, Secteur>();
	            }
	          
	            secteurs.put( commentaire.getSecteur().getId(), commentaire.getSecteur() );
	          
	            session.setAttribute( SESSION_SECTEURS, secteurs);
	           
	            
	            Map<Long, Voie> voies = (HashMap<Long, Voie>) session.getAttribute( SESSION_VOIES );
	            
	            if ( voies == null ) {
	                voies= new HashMap<Long, Voie>();
	            }
	          
	            voies.put( commentaire.getVoie().getId(), commentaire.getVoie() );
	          
	            session.setAttribute( SESSION_VOIES, voies);
	            
	            Map<Long, Longueur> longueurs = (HashMap<Long, Longueur>) session.getAttribute( SESSION_LONGUEURS );
	            
	            if ( longueurs == null ) {
	                longueurs= new HashMap<Long, Longueur>();
	            }
	          
	            longueurs.put( commentaire.getLongueur().getId(), commentaire.getLongueur() );
	          
	            session.setAttribute( SESSION_LONGUEURS, longueurs);
	            
	            Map<Long, Topo> topos = (HashMap<Long, Topo>) session.getAttribute( SESSION_TOPOS );
	            if (topos == null ) {
	                topos= new HashMap<Long, Topo>();
	            }
	          
	            topos.put( commentaire.getTopo().getId(), commentaire.getTopo() );
	          
	            session.setAttribute( SESSION_TOPOS, topos);
	            
	            Map<Long, Commentaire> commentaires= (HashMap<Long, Commentaire>) session.getAttribute( SESSION_COMMENTAIRES );
	            
	            if ( commentaires == null ) {
	                commentaires = new HashMap<Long, Commentaire>();
	            }
	          
	            commentaires.put( commentaire.getId(), commentaire);
	            
	            session.setAttribute( SESSION_COMMENTAIRES, commentaires);

	           
	            this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
	        } else {
	           
	            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
	        }
	    }

}
