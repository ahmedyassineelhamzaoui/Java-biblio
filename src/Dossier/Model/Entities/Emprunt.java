package Dossier.Model.Entities;

import java.util.Date;

public class Emprunt {

	private String ISBN_livre;
	private Date date_emprunt;
	private Date date_retoure;
	private String statut;
	private int quantité_emprunté;
	private String name;
	private String phone;
	private String titre;
	private String nom_auteur;
	
	public Emprunt(String iSBN_livre, String nom_auteur, String titre, int quantité_emprunté, Date date_emprunt, Date date_retoure,String name, String phone, String statut) {
		ISBN_livre = iSBN_livre;
		this.date_emprunt = date_emprunt;
		this.date_retoure = date_retoure;
		this.statut = statut;
		this.quantité_emprunté = quantité_emprunté;
		this.name = name;
		this.phone = phone;
		this.titre = titre;
		this.nom_auteur = nom_auteur;
	}
	public String getISBN_livre() {
		return ISBN_livre;
	}
	public void setISBN_livre(String iSBN_livre) {
		ISBN_livre = iSBN_livre;
	}
	public Date getDate_emprunt() {
		return date_emprunt;
	}
	public void setDate_emprunt(Date date_emprunt) {
		this.date_emprunt = date_emprunt;
	}
	public Date getDate_retoure() {
		return date_retoure;
	}
	public void setDate_retoure(Date date_retoure) {
		this.date_retoure = date_retoure;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public int getQuantité_emprunté() {
		return quantité_emprunté;
	}
	public void setQuantité_emprunté(int quantité_emprunté) {
		this.quantité_emprunté = quantité_emprunté;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getNom_auteur() {
		return nom_auteur;
	}
	public void setNom_auteur(String nom_auteur) {
		this.nom_auteur = nom_auteur;
	}
	
	
	

	
}
