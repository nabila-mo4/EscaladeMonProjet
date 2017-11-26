package com.escalade.dao;

import com.escalade.beans.Commentaire;

public class CommentaireDaoImpl implements CommentaireDao{
	
	private static final String SQL_SELECT_PAR_EMAIL = "SELECT id, email, nom, mot_de_passe, date_inscription FROM Utilisateur WHERE email = ?";
	
	
	 private DAOFactory daoFactory;
	 CommentaireDaoImpl( DAOFactory daoFactory ) {
	        this.daoFactory = daoFactory;
	    }


	 @Override
	 private Commentaire find( String email ) throws DAOException {
	     Connection connexion = null;
	     PreparedStatement preparedStatement = null;
	     ResultSet resultSet = null;
	     Utilisateur utilisateur = null;

	     try {
	         /* Récupération d'une connexion depuis la Factory */
	         connexion = daoFactory.getConnection();
	         preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_EMAIL, false, email );
	         resultSet = preparedStatement.executeQuery();
	         /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	         if ( resultSet.next() ) {
	             utilisateur = map( resultSet );
	         }
	     } catch ( SQLException e ) {
	         throw new DAOException( e );
	     } finally {
	         fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	     }

	     return utilisateur;
	   

}
	 
	 private static final String SQL_INSERT = "INSERT INTO Utilisateur (email, mot_de_passe, nom, date_inscription) VALUES (?, ?, ?, NOW())";

	 
	 @Override
	 public void create( Commentaire commentaire ) throws IllegalArgumentException,DAOException {
	     Connection connexion = null;
	     PreparedStatement preparedStatement = null;
	     ResultSet valeursAutoGenerees = null;

	     try {
	         /* Récupération d'une connexion depuis la Factory */
	         connexion = daoFactory.getConnection();
	         preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, utilisateur.getEmail(), utilisateur.getMotDePasse(), utilisateur.getNom() );
	         int statut = preparedStatement.executeUpdate();
	         /* Analyse du statut retourné par la requête d'insertion */
	         if ( statut == 0 ) {
	             throw new DAOException( "Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table." );
	         }
	         /* Récupération de l'id auto-généré par la requête d'insertion */
	         valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	         if ( valeursAutoGenerees.next() ) {
	             /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
	             utilisateur.setId( valeursAutoGenerees.getLong( 1 ) );
	         } else {
	             throw new DAOException( "Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné." );
	         }
	     } catch ( SQLException e ) {
	         throw new DAOException( e );
	     } finally {
	         fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	     }
	 }
}
