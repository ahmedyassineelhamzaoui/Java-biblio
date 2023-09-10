package Dossier.Controller;

import java.awt.event.KeyEvent;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Dossier.Model.DAO.LivreModel;
import Dossier.Model.Entities.Livre;


public class LivreController {
	
	public static void listerLivreEmprunter(String role,int user_id,DefaultTableModel mod1) {
		LivreModel.listerLivreEmprunter(role,user_id,mod1);
	}
	public static void listerTousLivres(DefaultTableModel mod) {
		LivreModel.listerTousLivres(mod);
	}
	public static ArrayList<Integer> AfficherStatistiques()
	{
		return LivreModel.AfficherStatistiques();
	}
	public static void preventString(KeyEvent e) {
		char k= e.getKeyChar();
		if(!Character.isDigit(k)) {
			e.consume();
		}
	}
	public static void ajouterLivre(DefaultTableModel mod,String isbnField,String titreField,int id,String quantityField,String quantity_dispo,String qauntity_perdu)
	{
		
		if(isbnField.trim().equals("") || titreField.trim().equals("") || quantityField.trim().equals("") || quantity_dispo.trim().equals("") || qauntity_perdu.trim().equals("")) {
			JOptionPane.showMessageDialog(null, "merci de remplire tous les champs","erreur de validation",JOptionPane.ERROR_MESSAGE);
		}else {
			Livre l = new Livre(isbnField.trim(),titreField.trim(),id,Integer.parseInt(quantityField),Integer.parseInt(quantity_dispo),Integer.parseInt(qauntity_perdu));
			LivreModel.ajouterLivre(l,mod);
		}
	}
	public static void supprimerLivre(DefaultTableModel mod,int ligne)
	{
		 if(ligne != -1) {
            	int n = JOptionPane.showConfirmDialog(null, "vous voulez vraiment supprimer ce livre");
            	if(n==0) {	
            		LivreModel.supprimerLivre(mod,ligne);
            	}             	
            }else {
            	JOptionPane.showMessageDialog(null, "selectioner une ligne pour le supprimer","erreur de selection",JOptionPane.ERROR_MESSAGE);
            }	
	}
	public static void chercherLivre(DefaultTableModel mod,String search_name)
	{
		if(search_name.trim().equals("")) {
			JOptionPane.showMessageDialog(null, "s'ils vous plait entrer un nom d'auteur ou un titre de livre valide","erreur de recherche",JOptionPane.ERROR_MESSAGE);
		}else {
    		LivreModel.chercherLivre(mod,search_name); 
		}
	}
	public static void raifrichirTableau(DefaultTableModel mod)
	{
		LivreModel.raifrichirTableau(mod);
	}
	public static void listerLivreDisponible(DefaultTableModel mod)
	{
		LivreModel.listerLivreDisponible(mod);
	}
	public static void modifierLivvre(DefaultTableModel mod,int ligne,String isbnField,String select_auteur,String titreField,String quantityField,String quantity_dispo,String qauntity_perdu)
	{
		if(ligne != -1) {
			if(isbnField.trim().equals("") || titreField.trim().equals("") || quantityField.trim().equals("") || quantity_dispo.trim().equals("") || qauntity_perdu.trim().equals("") ) {
            	JOptionPane.showMessageDialog(null, "merci de remplire tous les champs avant de modifier","erreur de modification",JOptionPane.ERROR_MESSAGE);
			}else {
               
	            LivreModel.modifierLivre(mod,ligne,isbnField.trim(),select_auteur,titreField.trim(),Integer.parseInt(quantityField),Integer.parseInt(quantity_dispo),Integer.parseInt(qauntity_perdu));

			}
        }else {
            	JOptionPane.showMessageDialog(null, "selectioner une ligne pour le modifier","erreur de selection",JOptionPane.ERROR_MESSAGE);
        }
	}
	public static void emprunterLivre(DefaultTableModel mod,DefaultTableModel mod1,int ligne,int user_id)
	{
		if(ligne != -1) {		
			String quantity = JOptionPane.showInputDialog(null, "Veuillez entrer la quantité empruntée","Quantité",JOptionPane.QUESTION_MESSAGE);	
			if(quantity.trim() != null) {
		        LivreModel.emprunterLivre(mod, mod1, ligne,quantity,user_id);
			}else {
	        	JOptionPane.showMessageDialog(null, "merci d'enter une qaunité valide","Message",JOptionPane.ERROR_MESSAGE);
			}
        }else {
        	JOptionPane.showMessageDialog(null, "selectioner un livre pour l'emprunté","Message",JOptionPane.ERROR_MESSAGE);
        }	
	}
	public static void retournerLivre(DefaultTableModel mod,DefaultTableModel mod1,int user_id)
	{
		String ISBN = JOptionPane.showInputDialog(null, "Entrer l'ISBN de livre que vous voulez retourner","Message",JOptionPane.QUESTION_MESSAGE);	
		if(ISBN.trim() != "") {
			LivreModel.retournerLivre(mod,mod1,ISBN,user_id);
		}else {
        	JOptionPane.showMessageDialog(null, "merci d'enter un ISBN valid","Message",JOptionPane.ERROR_MESSAGE);
		}
	}
}
