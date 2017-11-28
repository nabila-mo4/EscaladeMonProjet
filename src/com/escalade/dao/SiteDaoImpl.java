package com.escalade.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static com.escalade.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.escalade.dao.DAOUtilitaire.initialisationRequetePreparee;

import com.escalade.beans.Site;

public class SiteDaoImpl implements SiteDao {
	
	private static final String SQL_SELECT        = "SELECT * FROM Site";
    private static final String SQL_SELECT_PAR_ID = "SELECT * FROM Site WHERE idSite = ?";
    private static final String SQL_INSERT        = "INSERT INTO Site (nom, emplacementGeo, type, hauteur) VALUES (?, ?, ?, ?)";
    private static final String SQL_DELETE_PAR_ID = "DELETE FROM Site WHERE idSite = ?";

    private DAOFactory daoFactory;

    SiteDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    
    @Override
    public Site find( long id ) throws DAOException {
        return find( SQL_SELECT_PAR_ID, id );
    }

   
    @Override
    public void create ( Site site ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true,
                    site.getNom(), site.getEmplacementGeo(),
                    site.getType(), site.getHauteur());
                    
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la création du site" );
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                site.setId( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DAOException( "Échec de la création du site en base de données" );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }

  
    @Override
    public List<Site> list() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Site> sites = new ArrayList<Site>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement( SQL_SELECT );
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
                sites.add( map( resultSet ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connection );
        }

        return sites;
    }

    
    @Override
    public void remove( Site site ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE_PAR_ID, true, site.getId() );
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la suppression du site" );
            } else {
                site.setId( null );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
        }
    }

    
    private Site find( String sql, Object... objets ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Site site = null;

        try {
          
            connexion = daoFactory.getConnection();
           
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            
            if ( resultSet.next() ) {
                site = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return site;
    }

    
    private static Site map( ResultSet resultSet ) throws SQLException {
        Site site = new Site();
        site.setId( resultSet.getLong( "id" ) );
        site.setNom( resultSet.getString( "nom" ) );
        site.setEmplacementGeo( resultSet.getString( "emplacementGeo" ) );
        site.setType( resultSet.getString( "type" ) );
        site.setHauteur( resultSet.getInt( "hauteur" ) );
        return site;
    }

}
