package com.escalade.beans;

import java.util.Date;

public class Pret {
	
		private Topo topo;
		private Date dateDebut;
		private Date dateFin;
		private String nomEmprunteur;
		private String emailEmprunteur;
		private Long id;
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Date getDateDebut() {
			return dateDebut;
		}
		public void setDateDebut(Date dateDebut) {
			this.dateDebut = dateDebut;
		}
		public Date getDateFin() {
			return dateFin;
		}
		public void setDateFin(Date dateFin) {
			this.dateFin = dateFin;
		}
		public String getNomEmprunteur() {
			return nomEmprunteur;
		}
		public void setNomEmprunteur(String nomEmprunteur) {
			this.nomEmprunteur = nomEmprunteur;
		}
		public String getEmailEmprunteur() {
			return emailEmprunteur;
		}
		public void setEmailEmprunteur(String emailEmprunteur) {
			this.emailEmprunteur = emailEmprunteur;
		}
		public Topo getTopo() {
			return topo;
		}
		public void setTopo(Topo topo) {
			this.topo = topo;
		}
		
		
		

}
