package com.escalade.dao;

import static com.escalade.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.escalade.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.escalade.beans.Pret;

public class PretDaoImpl implements PretDao{
	
	private static final String SQL_SELECT        = "SELECT * FROM Pret";
    private static final String SQL_SELECT_PAR_ID = "SELECT * FROM Pret WHERE idPret = ?";
    private static final String SQL_INSERT        = "INSERT INTO Pret (idTopo, dateDebut, dateFin, nomEmprunteur, emailEmprunteur, nomDemandeur, emailDemandeur) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_PAR_ID = "DELETE FROM Pret WHERE idPret = ?";

    private DAOFactory daoFactory;

    PretDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

  
    @Override
    public Pret find( long id ) throws DAOException {
        return find( SQL_SELECT_PAR_ID, id );
    }

   
    @Override
    public void create( Pret pret) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true,
                    pret.getTopo().getId(), 
                    pret.getDateDebut(),
                    pret.getDateFin(),
                    pret.getNomEmprunteur(),
                    pret.getEmailEmprunteur(),
                    pret.getNomDemandeur(),
                    pret.getEmailDemandeur());
                   
                    
                  
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la création du pret" );
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                pret.setId( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DAOException( "Échec de la création du pret en base de données" );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }

   
    @Override
    public List<Pret> list() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Pret> prets = new ArrayList<Pret>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement( SQL_SELECT );
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
                prets.add( map( resultSet ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connection );
        }

        return prets;
    }

    
    @Override
    public void remove( Pret pret ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE_PAR_ID, true, pret.getId() );
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la suppression du pret" );
            } else {
                pret.setId( null );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
        }
    }

   
    private Pret find( String sql, Object... objets ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Pret pret = null;

        try {
           
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            if ( resultSet.next() ) {
                pret = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return pret;
    }

   
    private Pret map( ResultSet resultSet ) throws SQLException {
        Pret pret = new Pret();
        pret.setId( resultSet.getLong( "id" ) );

       
        TopoDao topoDao = daoFactory.getTopoDao();
        pret.setTopo( topoDao.find( resultSet.getLong( "idTopo" ) ) );
        pret.setDateDebut(resultSet.getDate( "dateDebut" ));
        pret.setDateFin(resultSet.getDate ( "dateFin" ));
        pret.setNomEmprunteur(resultSet.getString( "nomEmprunteur" ));
        pret.setEmailEmprunteur(resultSet.getString( "emailEmprunteur" ));
        pret.setNomDemandeur(resultSet.getString( "nomDemandeur" ));
        pret.setEmailDemandeur(resultSet.getString( "emailDemandeur" ));
       
        return pret;
    }

}
