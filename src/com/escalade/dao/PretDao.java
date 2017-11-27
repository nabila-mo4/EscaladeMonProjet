package com.escalade.dao;

import java.util.List;

import com.escalade.beans.Pret;


public interface PretDao {
	
	void create (Pret pret) throws DAOException;
	Pret find (long id) throws DAOException;
	List<Pret> list () throws DAOException;
	void remove(Pret pret) throws DAOException;

}
