package Dossier.Controller;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import Dossier.Model.DAO.AuteurModel;
import Dossier.Model.DAO.LivreModel;
import Dossier.Model.Entities.Auteur;

public class AuteurController {

	public static ArrayList<String> afficherTousAutuer()
	{
		return AuteurModel.afficherTousAuteur();
	}
	public static int trouverIDAuteur(String nom) {
		return AuteurModel.trouverIdAuteur(nom);
	}
	public static void addAuteur(JComboBox<String> select_auteur)
	{
		String nom = JOptionPane.showInputDialog(null, "Entrer me nom de l'auteur que vous voulez ajouter","Message",JOptionPane.QUESTION_MESSAGE);	

		if(nom.trim() != "") {
			Auteur a = new Auteur(nom.trim());
            AuteurModel.addAuteur(a.getNom(),select_auteur);
		}else {
        	JOptionPane.showMessageDialog(null, "merci d'enter un nom valid","Message",JOptionPane.ERROR_MESSAGE);
		}
	}
}
