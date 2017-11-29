package com.escalade.dao;

	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.List;
	import static com.escalade.dao.DAOUtilitaire.fermeturesSilencieuses;
	import static com.escalade.dao.DAOUtilitaire.initialisationRequetePreparee;

	import com.escalade.beans.Topo;

	public class TopoDaoImpl implements TopoDao {
		
		private static final String SQL_SELECT        = "SELECT * FROM Topo";
	    private static final String SQL_SELECT_PAR_ID = "SELECT * FROM Topo WHERE idTopo = ?";
	    private static final String SQL_INSERT        = "INSERT INTO Topo (nom, nomAuteur, dateSortie) VALUES (?, ?, ?)";
	    private static final String SQL_DELETE_PAR_ID = "DELETE FROM Topo WHERE idTopo = ?";

	    private DAOFactory daoFactory;

	    TopoDaoImpl( DAOFactory daoFactory ) {
	        this.daoFactory = daoFactory;
	    }

	    
	    @Override
	    public Topo find( long id ) throws DAOException {
	        return find( SQL_SELECT_PAR_ID, id );
	    }

	   
	    @Override
	    public void create ( Topo topo ) throws DAOException {
	        Connection connexion = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet valeursAutoGenerees = null;

	        try {
	            connexion = daoFactory.getConnection();
	            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true,
	                    topo.getNom(), topo.getNomAuteur(),
	                    topo.getDateSortie());
	                    
	            int statut = preparedStatement.executeUpdate();
	            if ( statut == 0 ) {
	                throw new DAOException( "Échec de la création du topo" );
	            }
	            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	            if ( valeursAutoGenerees.next() ) {
	                topo.setId( valeursAutoGenerees.getLong( 1 ) );
	            } else {
	                throw new DAOException( "Échec de la création du topo en base de données" );
	            }
	        } catch ( SQLException e ) {
	            throw new DAOException( e );
	        } finally {
	            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	        }
	    }

	  
	    @Override
	    public List<Topo> list() throws DAOException {
	        Connection connection = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultSet = null;
	        List<Topo> topos = new ArrayList<Topo>();

	        try {
	            connection = daoFactory.getConnection();
	            preparedStatement = connection.prepareStatement( SQL_SELECT );
	            resultSet = preparedStatement.executeQuery();
	            while ( resultSet.next() ) {
	                topos.add( map( resultSet ) );
	            }
	        } catch ( SQLException e ) {
	            throw new DAOException( e );
	        } finally {
	            fermeturesSilencieuses( resultSet, preparedStatement, connection );
	        }

	        return topos;
	    }

	    
	    @Override
	    public void remove( Topo topo ) throws DAOException {
	        Connection connexion = null;
	        PreparedStatement preparedStatement = null;

	        try {
	            connexion = daoFactory.getConnection();
	            preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE_PAR_ID, true, topo.getId() );
	            int statut = preparedStatement.executeUpdate();
	            if ( statut == 0 ) {
	                throw new DAOException( "Échec de la suppression du topo" );
	            } else {
	                topo.setId( null );
	            }
	        } catch ( SQLException e ) {
	            throw new DAOException( e );
	        } finally {
	            fermeturesSilencieuses( preparedStatement, connexion );
	        }
	    }

	    
	    private Topo find( String sql, Object... objets ) throws DAOException {
	        Connection connexion = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultSet = null;
	        Topo topo = null;

	        try {
	          
	            connexion = daoFactory.getConnection();
	           
	            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
	            resultSet = preparedStatement.executeQuery();
	            
	            if ( resultSet.next() ) {
	                topo = map( resultSet );
	            }
	        } catch ( SQLException e ) {
	            throw new DAOException( e );
	        } finally {
	            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	        }

	        return topo;
	    }

	    
	    private static Topo map( ResultSet resultSet ) throws SQLException {
	        Topo topo = new Topo();
	        topo.setId( resultSet.getLong( "id" ) );
	        topo.setNom( resultSet.getString( "nom" ) );
	        topo.setNomAuteur( resultSet.getString( "nomAuteur" ) );
	        topo.setDateSortie( resultSet.getDate( "dateSortie" ) );
	        
	        return topo;
	    }

	}



