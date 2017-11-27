package com.escalade.dao;

import java.util.List;

import com.escalade.beans.Secteur;

public interface SecteurDao {
	
	void create (Secteur secteur) throws DAOException;
	Secteur find (long id) throws DAOException;
	List<Secteur> list () throws DAOException;
	void remove(Secteur secteur) throws DAOException;

}
