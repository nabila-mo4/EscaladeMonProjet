package com.escalade.beans;

public class Site {
	private String nom;
	private String emplacementGeo;
	private String type;
	private int hauteur;
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
	public String getEmplacementGeo() {
		return emplacementGeo;
	}
	public void setEmplacementGeo(String emplacementGeo) {
		this.emplacementGeo = emplacementGeo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getHauteur() {
		return hauteur;
	}
	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}
	
	

}
