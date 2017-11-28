package com.escalade.dao;

import static com.escalade.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.escalade.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.escalade.beans.Voie;

public class VoieDaoImpl implements VoieDao{
	
	private static final String SQL_SELECT        = "SELECT * FROM Voie";
    private static final String SQL_SELECT_PAR_ID = "SELECT * FROM Voie WHERE idVoie = ?";
    private static final String SQL_INSERT        = "INSERT INTO Voie (idSecteur, nom, hauteur, nomHauteurVoie, etat, nbPoints) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_PAR_ID = "DELETE FROM Voie WHERE idVoie = ?";

    private DAOFactory daoFactory;

    VoieDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

  
    @Override
    public Voie find( long id ) throws DAOException {
        return find( SQL_SELECT_PAR_ID, id );
    }

   
    @Override
    public void create( Voie voie) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true,
                    voie.getSecteur().getId(), 
                    voie.getNom(),
                    voie.getHauteur(),
                    voie.getNomHauteurVoie(),
                    voie.getEtat(),
                    voie.getNbPoints());
                  
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la création de la voie" );
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                voie.setId( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DAOException( "Échec de la création de la voie en base de données" );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }

   
    @Override
    public List<Voie> list() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Voie> voies = new ArrayList<Voie>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement( SQL_SELECT );
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
                voies.add( map( resultSet ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connection );
        }

        return voies;
    }

    
    @Override
    public void remove( Voie voie ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE_PAR_ID, true, voie.getId() );
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la suppression de la voie" );
            } else {
                voie.setId( null );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
        }
    }

   
    private Voie find( String sql, Object... objets ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Voie voie = null;

        try {
           
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            if ( resultSet.next() ) {
                voie = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return voie;
    }

   
    private Voie map( ResultSet resultSet ) throws SQLException {
        Voie voie = new Voie();
        voie.setId( resultSet.getLong( "id" ) );

       
        SecteurDao secteurDao = daoFactory.getSecteurDao();
        voie.setSecteur( secteurDao.find( resultSet.getLong( "idSecteur" ) ) );
        voie.setNom( resultSet.getString( "nom" ) );
        voie.setHauteur( resultSet.getInt( "hauteur" ) );
        voie.setEtat( resultSet.getString( "etat" ) );
        voie.setNbPoints( resultSet.getInt( "nbPoints" ) );
        
       
        return voie;
    }

}
