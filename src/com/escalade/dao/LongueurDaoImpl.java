package com.escalade.dao;

import static com.escalade.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.escalade.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.escalade.beans.Longueur;

public class LongueurDaoImpl implements LongueurDao {
	
	private static final String SQL_SELECT        = "SELECT * FROM Longueur";
    private static final String SQL_SELECT_PAR_ID = "SELECT * FROM Longueur WHERE idLongueur = ?";
    private static final String SQL_INSERT        = "INSERT INTO Longueur (idVoie, hauteur, cotation, nomRelais) VALUES (?, ?, ?, ?)";
    private static final String SQL_DELETE_PAR_ID = "DELETE FROM Longueur WHERE idLongueur = ?";

    private DAOFactory daoFactory;

    LongueurDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

  
    @Override
    public Longueur find( long id ) throws DAOException {
        return find( SQL_SELECT_PAR_ID, id );
    }

   
    @Override
    public void create( Longueur longueur) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true,
                    longueur.getVoie().getId(), 
                    longueur.getHauteur(),
                    longueur.getCotation(),
                    longueur.getNomRelais());
                  
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la création de la longueur" );
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                longueur.setId( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DAOException( "Échec de la création de la longueur en base de données" );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }

   
    @Override
    public List<Longueur> list() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Longueur> longueurs = new ArrayList<Longueur>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement( SQL_SELECT );
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
                longueurs.add( map( resultSet ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connection );
        }

        return longueurs;
    }

    
    @Override
    public void remove( Longueur longueur ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE_PAR_ID, true, longueur.getId() );
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la suppression de la longueur" );
            } else {
                longueur.setId( null );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
        }
    }

   
    private Longueur find( String sql, Object... objets ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Longueur longueur = null;

        try {
           
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            if ( resultSet.next() ) {
                longueur = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return longueur;
    }

   
    private Longueur map( ResultSet resultSet ) throws SQLException {
        Longueur longueur = new Longueur();
        longueur.setId( resultSet.getLong( "id" ) );
        VoieDao voieDao = daoFactory.getVoieDao();
        longueur.setVoie( voieDao.find( resultSet.getLong( "idVoie" ) ) );
        longueur.setCotation ( resultSet.getString( "cotation" ) );
        longueur.setNomRelais( resultSet.getString( "nomRelais" ) );
      
        return longueur;
    }

}
