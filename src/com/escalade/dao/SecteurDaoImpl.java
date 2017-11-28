package com.escalade.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static com.escalade.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.escalade.dao.DAOUtilitaire.initialisationRequetePreparee;
import com.escalade.beans.Secteur;


public class SecteurDaoImpl implements SecteurDao {
	
	    private static final String SQL_SELECT        = "SELECT * FROM Secteur";
	    private static final String SQL_SELECT_PAR_ID = "SELECT * FROM Secteur WHERE idSecteur = ?";
	    private static final String SQL_INSERT        = "INSERT INTO Secteur (idSite, nom, hauteur) VALUES (?, ?, ?)";
	    private static final String SQL_DELETE_PAR_ID = "DELETE FROM Secteur WHERE idSecteur = ?";

	    private DAOFactory          daoFactory;

	    SecteurDaoImpl( DAOFactory daoFactory ) {
	        this.daoFactory = daoFactory;
	    }

	  
	    @Override
	    public Secteur find( long id ) throws DAOException {
	        return find( SQL_SELECT_PAR_ID, id );
	    }

	   
	    @Override
	    public void create( Secteur secteur) throws DAOException {
	        Connection connexion = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet valeursAutoGenerees = null;

	        try {
	            connexion = daoFactory.getConnection();
	            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true,
	                    secteur.getSite().getId(), 
	                    secteur.getNom(),
	                    secteur.getHauteur());
	                  
	            int statut = preparedStatement.executeUpdate();
	            if ( statut == 0 ) {
	                throw new DAOException( "Échec de la création du secteur" );
	            }
	            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	            if ( valeursAutoGenerees.next() ) {
	                secteur.setId( valeursAutoGenerees.getLong( 1 ) );
	            } else {
	                throw new DAOException( "Échec de la création du secteur en base de données" );
	            }
	        } catch ( SQLException e ) {
	            throw new DAOException( e );
	        } finally {
	            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	        }
	    }

	   
	    @Override
	    public List<Secteur> list() throws DAOException {
	        Connection connection = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultSet = null;
	        List<Secteur> secteurs = new ArrayList<Secteur>();

	        try {
	            connection = daoFactory.getConnection();
	            preparedStatement = connection.prepareStatement( SQL_SELECT );
	            resultSet = preparedStatement.executeQuery();
	            while ( resultSet.next() ) {
	                secteurs.add( map( resultSet ) );
	            }
	        } catch ( SQLException e ) {
	            throw new DAOException( e );
	        } finally {
	            fermeturesSilencieuses( resultSet, preparedStatement, connection );
	        }

	        return secteurs;
	    }

	    
	    @Override
	    public void remove( Secteur secteur ) throws DAOException {
	        Connection connexion = null;
	        PreparedStatement preparedStatement = null;

	        try {
	            connexion = daoFactory.getConnection();
	            preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE_PAR_ID, true, secteur.getId() );
	            int statut = preparedStatement.executeUpdate();
	            if ( statut == 0 ) {
	                throw new DAOException( "Échec de la suppression du secteur" );
	            } else {
	                secteur.setId( null );
	            }
	        } catch ( SQLException e ) {
	            throw new DAOException( e );
	        } finally {
	            fermeturesSilencieuses( preparedStatement, connexion );
	        }
	    }

	   
	    private Secteur find( String sql, Object... objets ) throws DAOException {
	        Connection connexion = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultSet = null;
	        Secteur secteur = null;

	        try {
	           
	            connexion = daoFactory.getConnection();
	            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
	            resultSet = preparedStatement.executeQuery();
	            if ( resultSet.next() ) {
	                secteur = map( resultSet );
	            }
	        } catch ( SQLException e ) {
	            throw new DAOException( e );
	        } finally {
	            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	        }

	        return secteur;
	    }

	   
	    private Secteur map( ResultSet resultSet ) throws SQLException {
	        Secteur secteur = new Secteur();
	        secteur.setId( resultSet.getLong( "id" ) );

	       
	        SiteDao siteDao = daoFactory.getSiteDao();
	        secteur.setSite( siteDao.find( resultSet.getLong( "idSite" ) ) );
	        secteur.setNom( resultSet.getString( "nom" ) );
	        secteur.setHauteur( resultSet.getInt( "hauteur" ) );
	       
	        return secteur;
	    }

	}

	


