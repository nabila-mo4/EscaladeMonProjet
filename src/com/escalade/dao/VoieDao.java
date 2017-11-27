package com.escalade.dao;

import java.util.List;


import com.escalade.beans.Voie;

public interface VoieDao {
	
	void create (Voie voie) throws DAOException;
	Voie find (long id) throws DAOException;
	List<Voie> list () throws DAOException;
	void remove(Voie voie) throws DAOException;

}
