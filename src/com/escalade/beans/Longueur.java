package com.escalade.beans;

public class Longueur {
		private Voie voie;
		public Voie getVoie() {
			return voie;
		}
		public void setVoie(Voie voie) {
			this.voie = voie;
		}
		private int hauteur;
		private String cotation;
		private String nomRelais;
		private Long id;
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public int getHauteur() {
			return hauteur;
		}
		public void setHauteur(int hauteur) {
			this.hauteur = hauteur;
		}
		public String getCotation() {
			return cotation;
		}
		public void setCotation(String cotation) {
			this.cotation = cotation;
		}
		public String getNomRelais() {
			return nomRelais;
		}
		public void setNomRelais(String nomRelais) {
			this.nomRelais = nomRelais;
		}
		
		

}
