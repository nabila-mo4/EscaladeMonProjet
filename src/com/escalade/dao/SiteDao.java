package com.escalade.dao;

import java.util.List;

import com.escalade.beans.Site;

public interface SiteDao {
	
	void create (Site site) throws DAOException;
	Site find (long id) throws DAOException;
	List<Site> list () throws DAOException;
	void remove(Site site) throws DAOException;

}
