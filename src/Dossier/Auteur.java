package Dossier;

import java.util.Date;

public class Auteur {
	private static int id;
	private String nom;
	private Date dateNaissance;
	
   public Auteur(String nom,Date dateNaissance) {
		this.nom = nom;
		this.dateNaissance = dateNaissance;
   }
   public String getNom() {
	   return nom;
   }
   public void setNom(String nom) {
	   this.nom = nom;
   }
   public Date getDate() {
	   return dateNaissance;
   }
   public void setDate(Date dateNaissance) {
	   this.dateNaissance  = dateNaissance;
   }
   public int getId() {
	   return id;
   }
}
