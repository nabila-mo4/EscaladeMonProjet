package com.escalade.beans;

import java.io.Serializable;


public class Topo implements Serializable {
	
		private String nom;
		private String nomAuteur;
		private String dateSortie;
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
		public String getDateSortie() {
			return dateSortie;
		}
		public void setDateSortie(String dateSortie) {
			this.dateSortie = dateSortie;
		} 
		
		

}
