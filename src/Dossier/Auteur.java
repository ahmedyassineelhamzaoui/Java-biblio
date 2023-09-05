package Dossier;


public class Auteur {
	private static int id;
	private String nom;	
    public Auteur(String nom) {
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
