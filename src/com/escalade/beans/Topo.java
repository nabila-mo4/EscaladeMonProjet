package com.escalade.beans;

import java.util.Date;

public class Topo {
	
		private String nom;
		private String nomAuteur;
		private Date dateSortie;
		private Long id;
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getNom() {
			return nom;
		}
		public void setNom(String nom) {
			this.nom = nom;
		}
		public String getNomAuteur() {
			return nomAuteur;
		}
		public void setNomAuteur(String nomAuteur) {
			this.nomAuteur = nomAuteur;
		}
		public Date getDateSortie() {
			return dateSortie;
		}
		public void setDateSortie(Date dateSortie) {
			this.dateSortie = dateSortie;
		} 
		
		

}
