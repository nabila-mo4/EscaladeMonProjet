package com.escalade.dao;

import java.util.List;


import com.escalade.beans.Topo;

public interface TopoDao {
	
	void create (Topo topo) throws DAOException;
	Topo find (long id) throws DAOException;
	List<Topo> list () throws DAOException;
	void remove(Topo topo) throws DAOException;

}
