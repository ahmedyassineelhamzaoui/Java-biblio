package Dossier;

import java.util.Date;

public class Emprunt {

	private static int id;
	private Livre livre;
	private Date date_emprunt;
	private Date date_retoure;
	private boolean statut;
	private Emprunteur emprunteur;
	
	public Emprunt(Livre livre,Date date_emprunt,Date date_retourne,boolean statut,Emprunteur emprunteur) {
		this.date_emprunt = date_emprunt;
		this.date_retoure = date_retourne;
		this.emprunteur = emprunteur;
		this.statut = statut;
		this.livre = livre;
		id++;
	}

	public Livre getLivre() {
		return livre;
	}

	public void setLivre(Livre livre) {
		this.livre = livre;
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

	public boolean isStatut() {
		return statut;
	}

	public void setStatut(boolean statut) {
		this.statut = statut;
	}

	public Emprunteur getEmprunteur() {
		return emprunteur;
	}

	public void setEmprunteur(Emprunteur emprunteur) {
		this.emprunteur = emprunteur;
	}

	public static int getId() {
		return id;
	}
	
}
