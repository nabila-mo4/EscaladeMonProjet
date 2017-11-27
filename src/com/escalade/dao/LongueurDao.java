package com.escalade.dao;

import java.util.List;

import com.escalade.beans.Longueur;


public interface LongueurDao {
	
	void create (Longueur longueur) throws DAOException;
	Longueur find (long id) throws DAOException;
	List<Longueur> list () throws DAOException;
	void remove(Longueur longueur) throws DAOException;

}
