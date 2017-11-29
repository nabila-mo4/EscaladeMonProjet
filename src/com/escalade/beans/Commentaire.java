package com.escalade.beans;

import java.util.Date;

public class Commentaire {
		private Long id;
		private String contenu;
		private String nomUtilisateur;
		private Site site;
		private Secteur secteur;
		private Voie voie;
		private Longueur longueur;
		private Topo topo;
	
		public String getContenu() {
			return contenu;
		}

		public void setContenu(String contenu) {
			this.contenu = contenu;
		}

		public String getNomUtilisateur() {
			return nomUtilisateur;
		}

		public void setNomUtilisateur(String nomUtilisateur) {
			this.nomUtilisateur = nomUtilisateur;
		}

		public Site getSite() {
			return site;
		}

		public void setSite(Site site) {
			this.site = site;
		}

		public Secteur getSecteur() {
			return secteur;
		}

		public void setSecteur(Secteur secteur) {
			this.secteur = secteur;
		}

		public Voie getVoie() {
			return voie;
		}

		public void setVoie(Voie voie) {
			this.voie = voie;
		}

		public Longueur getLongueur() {
			return longueur;
		}

		public void setLongueur(Longueur longueur) {
			this.longueur = longueur;
		}

		public Topo getTopo() {
			return topo;
		}

		public void setTopo(Topo topo) {
			this.topo = topo;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		private Date datePublication;

		public Date getDatePublication() {
			return datePublication;
		}

		public void setDatePublication(Date datePublication) {
			this.datePublication = datePublication;
		}
		
		

}
