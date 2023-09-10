package Dossier.Model.Entities;


public class Auteur {
	private  int id;
	private String nom;	
    public Auteur(String nom,int id) {
    	this.id = id;
		this.nom = nom;
   }
   public String getNom() {
	   return nom;
   }
   public void setNom(String nom) {
	   this.nom = nom;
   }
  
   public int getId() {
	   return id;
   }
}
