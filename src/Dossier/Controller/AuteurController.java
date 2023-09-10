package Dossier.Controller;

import java.util.ArrayList;

import Dossier.Model.DAO.AuteurModel;

public class AuteurController {

	public static ArrayList<String> afficherTousAutuer()
	{
		return AuteurModel.afficherTousAuteur();
	}
	public static int trouverIDAuteur(String nom) {
		return AuteurModel.trouverIdAuteur(nom);
	}
}
