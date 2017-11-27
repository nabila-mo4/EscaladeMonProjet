package com.escalade.dao;

import java.util.List;

import com.escalade.beans.Commentaire;


public interface CommentaireDao {
	
	  
	    void create (Commentaire commentaire) throws DAOException;
		Commentaire find (long id) throws DAOException;
		List<Commentaire> list () throws DAOException;
		void remove(Commentaire commentaire) throws DAOException;

}
