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
import com.escalade.dao.DAOFactory;
import com.escalade.dao.TopoDao;
import com.escalade.forms.CreationTopoForm;

public class CreationTopo extends HttpServlet {
	public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_TOPO= "topo";
    public static final String ATT_FORM = "form";
    public static final String SESSION_TOPOS= "topos";
    public static final String VUE_SUCCES= "/WEB-INF/afficherTopo.jsp";
    public static final String VUE_FORM = "/WEB-INF/creerTopo.jsp";

    private TopoDao  topoDao;

    public void init() throws ServletException {
       
        this.topoDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getTopoDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        
        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        
        CreationTopoForm form = new CreationTopoForm( topoDao );

        Topo topo = form.createTopo( request);

        
        request.setAttribute( ATT_TOPO, topo );
        request.setAttribute( ATT_FORM, form );

        
        if ( form.getErreurs().isEmpty() ) {
          
            HttpSession session = request.getSession();
            Map<Long, Topo> topos = (HashMap<Long, Topo>) session.getAttribute( SESSION_TOPOS );
           
            if ( topos == null ) {
                topos = new HashMap<Long, Topo>();
            }
            
            topos.put( topo.getId(), topo );
           
            session.setAttribute( SESSION_TOPOS, topos );

           
            this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
        } else {
            
            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
        }
    }



}
