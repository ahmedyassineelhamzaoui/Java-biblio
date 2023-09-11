package Dossier.Model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import Dossier.Database.ConnexionDB;

public class AuteurModel {

	public static ArrayList<String> afficherTousAuteur()
	{
		ArrayList<String> nom_auteurs = new ArrayList<>(); 
		PreparedStatement ps;
		ResultSet rs;
		
		String query = "SELECT * FROM auteur";
			
		try {
			ps = ConnexionDB.getConnection().prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				  nom_auteurs.add(rs.getString("name"));
			}
		}catch(Exception e) {
			e.getMessage();
		}
		return nom_auteurs;
		
	}
	public static int trouverIdAuteur(String nom)
	{
		PreparedStatement ps;
		ResultSet rs;
		int id = -1;
		String query = "SELECT * FROM auteur where name=?";
			
		try {
			ps = ConnexionDB.getConnection().prepareStatement(query);
			ps.setString(1, nom);
			rs = ps.executeQuery();
			if(rs.next()) {
				id = rs.getInt("id");
			}
		}catch(Exception e) {
			e.getMessage();
		}
		return id;
	}
	public static String trouverAuteurNom(int id)
	{
		PreparedStatement ps;
		ResultSet rs;
		String name = "";
		String query = "SELECT * FROM auteur where id=?";
			
		try {
			ps = ConnexionDB.getConnection().prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				name = rs.getString("name");
			}
		}catch(Exception e) {
			e.getMessage();
		}
		return name;
	}
	public static void addAuteur(String nom,JComboBox<String> select_auteur)
	{
		PreparedStatement ps;
		String query = "INSERT INTO auteur(name) VALUES(?) ";
			
		try {
			ps = ConnexionDB.getConnection().prepareStatement(query);
			ps.setString(1, nom);
			if(ps.executeUpdate() != 0) {
				JOptionPane.showMessageDialog(null, "l'auteur a été créer avec succes","message",JOptionPane.PLAIN_MESSAGE);
				select_auteur.addItem(nom);
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,"l'auteur que vous avez saisie existe déja","message",JOptionPane.ERROR_MESSAGE);
		}
	}

}
