package com.escalade.dao;

import com.escalade.beans.Commentaire;

public interface CommentaireDao {
	
	  void create(Commentaire commentaire ) throws DAOException;

	    Commentaire find( String email ) throws DAOException;

}
