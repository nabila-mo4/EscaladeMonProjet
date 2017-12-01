package com.escalade.beans;

import java.io.Serializable;
import java.util.Date;

public class Pret implements Serializable {
	
		private Topo topo;
		private String dateDebut;
		private String dateFin;
		private String nomEmprunteur;
		private String emailEmprunteur;
		private String nomDemandeur;
		private String emailDemandeur;
		public String getNomDemandeur() {
			return nomDemandeur;
		}
		public void setNomDemandeur(String nomDemandeur) {
			this.nomDemandeur = nomDemandeur;
		}
		public String getEmailDemandeur() {
			return emailDemandeur;
		}
		public void setEmailDemandeur(String emailDemandeur) {
			this.emailDemandeur = emailDemandeur;
		}
		private Long id;
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getDateDebut() {
			return dateDebut;
		}
		public void setDateDebut(String dateDebut) {
			this.dateDebut = dateDebut;
		}
		public String getDateFin() {
			return dateFin;
		}
		public void setDateFin(String dateFin) {
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
