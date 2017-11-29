package com.escalade.dao;

import static com.escalade.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.escalade.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.escalade.beans.Commentaire;


public class CommentaireDaoImpl implements CommentaireDao{
	
	 private static final String SQL_SELECT = "SELECT * FROM Commentaire";
	    private static final String SQL_SELECT_PAR_ID = "SELECT * FROM Commentaire WHERE idCommentaire = ?";
	    private static final String SQL_INSERT        = "INSERT INTO Commentaire (idSite, idSecteur, idVoie, idLongueur, idTopo, contenu, nomUtilisateur, datePublication ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	    private static final String SQL_DELETE_PAR_ID = "DELETE FROM Commentaire WHERE idCommentaire = ?";

	    private DAOFactory daoFactory;

	    CommentaireDaoImpl( DAOFactory daoFactory ) {
	        this.daoFactory = daoFactory;
	    }

	  
	    @Override
	    public Commentaire find( long id ) throws DAOException {
	        return find( SQL_SELECT_PAR_ID, id );
	    }

	   
	    @Override
	    public void create( Commentaire commentaire) throws DAOException {
	        Connection connexion = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet valeursAutoGenerees = null;

	        try {
	            connexion = daoFactory.getConnection();
	            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true,
	                    commentaire.getSite().getId(), 
	                    commentaire.getSecteur().getId(), 
	                    commentaire.getVoie().getId(), 
	                    commentaire.getLongueur().getId(), 
	                    commentaire.getTopo().getId(), 
	                    commentaire.getContenu(),
	                    commentaire.getNomUtilisateur(),
	                    commentaire.getDatePublication());
	                    
	                  
	            int statut = preparedStatement.executeUpdate();
	            if ( statut == 0 ) {
	                throw new DAOException( "Échec de la création du commentaire" );
	            }
	            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	            if ( valeursAutoGenerees.next() ) {
	                commentaire.setId( valeursAutoGenerees.getLong( 1 ) );
	            } else {
	                throw new DAOException( "Échec de la création du commentaire en base de données" );
	            }
	        } catch ( SQLException e ) {
	            throw new DAOException( e );
	        } finally {
	            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	        }
	    }

	   
	    @Override
	    public List<Commentaire> list() throws DAOException {
	        Connection connection = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultSet = null;
	        List<Commentaire> commentaires = new ArrayList<Commentaire>();

	        try {
	            connection = daoFactory.getConnection();
	            preparedStatement = connection.prepareStatement( SQL_SELECT );
	            resultSet = preparedStatement.executeQuery();
	            while ( resultSet.next() ) {
	                commentaires.add( map( resultSet ) );
	            }
	        } catch ( SQLException e ) {
	            throw new DAOException( e );
	        } finally {
	            fermeturesSilencieuses( resultSet, preparedStatement, connection );
	        }

	        return commentaires;
	    }

	    
	    @Override
	    public void remove( Commentaire commentaire ) throws DAOException {
	        Connection connexion = null;
	        PreparedStatement preparedStatement = null;

	        try {
	            connexion = daoFactory.getConnection();
	            preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE_PAR_ID, true, commentaire.getId() );
	            int statut = preparedStatement.executeUpdate();
	            if ( statut == 0 ) {
	                throw new DAOException( "Échec de la suppression du commentaire" );
	            } else {
	                commentaire.setId( null );
	            }
	        } catch ( SQLException e ) {
	            throw new DAOException( e );
	        } finally {
	            fermeturesSilencieuses( preparedStatement, connexion );
	        }
	    }

	   
	    private Commentaire find( String sql, Object... objets ) throws DAOException {
	        Connection connexion = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultSet = null;
	        Commentaire commentaire = null;

	        try {
	           
	            connexion = daoFactory.getConnection();
	            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
	            resultSet = preparedStatement.executeQuery();
	            if ( resultSet.next() ) {
	                commentaire = map( resultSet );
	            }
	        } catch ( SQLException e ) {
	            throw new DAOException( e );
	        } finally {
	            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	        }

	        return commentaire;
	    }

	   
	    private Commentaire map( ResultSet resultSet ) throws SQLException {
	        Commentaire commentaire = new Commentaire();
	        commentaire.setId( resultSet.getLong( "id" ) );
	        SiteDao siteDao = daoFactory.getSiteDao();
	        commentaire.setSite( siteDao.find( resultSet.getLong( "idSite" ) ) );
	        SecteurDao secteurDao = daoFactory.getSecteurDao();
	        commentaire.setSecteur( secteurDao.find( resultSet.getLong( "idSecteur" ) ) );
	        VoieDao voieDao = daoFactory.getVoieDao();
	        commentaire.setVoie( voieDao.find( resultSet.getLong( "idVoie" ) ) );
	        LongueurDao longueurDao = daoFactory.getLongueurDao();
	        commentaire.setLongueur( longueurDao.find( resultSet.getLong( "idLongueur" ) ) );
	        TopoDao topoDao = daoFactory.getTopoDao();
	        commentaire.setTopo( topoDao.find( resultSet.getLong( "idTopo" ) ) );
	        commentaire.setContenu( resultSet.getString( "contenu" ) );
	        commentaire.setNomUtilisateur( resultSet.getString( "nomUtilisateur" ) );
	        commentaire.setDatePublication(resultSet.getDate("datePublication"));
	       
	        return commentaire;
	    }
}
