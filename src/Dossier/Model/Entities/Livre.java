package Dossier.Model.Entities;

import javax.swing.table.DefaultTableModel;



public class Livre {
	
	private String ISBN;
	private int id_auteur;
	private String titre;
	private int Q_total;
	private int Q_dispo;
	private int Q_perdu;
	DefaultTableModel mod; 
	
	public Livre(String ISBN,String titre,int id_auteur,int Q_total,int Q_dispo,int Q_perdu) {
		this.ISBN = ISBN;
		this.id_auteur = id_auteur;
		this.titre = titre;
		this.Q_dispo = Q_dispo;
		this.Q_perdu = Q_perdu;
		this.Q_total = Q_total;
	}
	public Livre() {}
	
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}
	public int getAuteur() {
		return id_auteur;
	}
	public void setAuteur(int id_auteur) {
		this.id_auteur = id_auteur;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public int getQtotal() {
		return Q_total;
	}
	public void setQtotal(int Q_total) {
		this.Q_total = Q_total;
	}
	public int getQdisponible() {
		return Q_dispo;
	}
	public void setQdisponible(int Q_dispo) {
		this.Q_dispo = Q_dispo;
	}
	public int getQperdus() {
		return Q_perdu;
	}
	public void setQperdus(int Q_perdu) {
		this.Q_perdu = Q_perdu;
	}

}
