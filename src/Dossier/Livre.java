package Dossier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



public class Livre {
	
	private String ISBN;
	private String nom_auteur;
	private String titre;
	private int Q_total;
	private int Q_dispo;
	private int Q_perdu;
	private DefaultTableModel modelTable;
	ArrayList<Livre> livres = new ArrayList<>();
	ArrayList<Livre> livreschercher = new ArrayList<>();
	DefaultTableModel mod; // on ajoute ce model pour remplire notre tableau
	
	
	public Livre(String ISBN,String nom_auteur,String titre,int Q_total,int Q_dispo,int Q_perdu) {
		this.ISBN = ISBN;
		this.nom_auteur = nom_auteur;
		this.titre = titre;
		this.Q_dispo = Q_dispo;
		this.Q_perdu = Q_perdu;
		this.Q_total = Q_total;
	}
	public Livre() {}
	public DefaultTableModel getModelTable() {
		  return modelTable;
	}
	public void setModelTable(DefaultTableModel model) {
		  this.modelTable = model;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}
	public String getAuteur() {
		return nom_auteur;
	}
	public void setAuteur(String nom_auteur) {
		this.nom_auteur = nom_auteur;
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
	
	public void ajouterLivre(Livre l) {
		Menu m = new Menu();
	    	PreparedStatement ps;
	    	ResultSet rs;
	    	String query = "SELECT * FROM users.livres where ISBN=? or titre=? ";
	    	try {
	    	ps = ConnexionDB.getConnection().prepareStatement(query);
	    	ps.setString(1,l.ISBN );
	    	ps.setString(2,l.titre );
	    	rs = ps.executeQuery();
	    	if(rs.next()) {
	    		JOptionPane.showMessageDialog(null,"ISBN ou titre de livre est déja existe","insertion refusé",JOptionPane.ERROR_MESSAGE);
	    	}else {
	    		PreparedStatement p;
	    		String queryInsert = "INSERT INTO users.livres(ISBN,nom_auteur,titre,q_total,q_disponible,q_perdu) VALUES(?,?,?,?,?,?)";
	    		try {
	    			p = ConnexionDB.getConnection().prepareStatement(queryInsert);
	    			p.setString(1, l.ISBN);
	    			p.setString(2, l.nom_auteur);
	    			p.setString(3, l.titre);
	    			p.setLong(4, Integer.parseInt(String.valueOf(l.Q_total)));
	    			p.setInt(5, Integer.parseInt(String.valueOf(l.Q_total)));
	    			p.setInt(6,0);
	    			if(p.executeUpdate() !=0) {
						JOptionPane.showMessageDialog(null, "Le livre a été créer avec succés","succés",JOptionPane.PLAIN_MESSAGE);
						Livre livre = new Livre(l.ISBN,l.nom_auteur,l.titre,l.Q_total,l.Q_total,0);
				    	livres.add(livre);
				    	modelTable.addRow(new Object[] {livre.getISBN(),livre.getAuteur(),livre.getTitre(),livre.getQtotal(),livre.getQdisponible(),livre.getQperdus()});
	    			}
	    		}catch(Exception e) {
	    			JOptionPane.showMessageDialog(null,"imposibe d'inserer les donnes","erreur d'insertion",JOptionPane.ERROR_MESSAGE);
	    		}
	    	}
	    	}catch(Exception e) {
	    		JOptionPane.showMessageDialog(null,"vérification échoué","erreur",JOptionPane.ERROR_MESSAGE);
	    	} 
	}
	
	public void supprimerLivre(ArrayList<Livre> ls,DefaultTableModel mod,int ligneSelectionnee) {
		 
		 PreparedStatement prs;
		 String queryDelete = "DELETE FROM users.livres WHERE ISBN=?";
		 try {
			 prs = ConnexionDB.getConnection().prepareStatement(queryDelete);
			 prs.setString(1, ls.get(ligneSelectionnee).getISBN());
			 if(prs.executeUpdate() != 0) {
				 JOptionPane.showMessageDialog(null, "le livre a été bien supprimer","succés",JOptionPane.PLAIN_MESSAGE);
			 }
		 }catch(Exception ex) {
			  JOptionPane.showMessageDialog(null, "impossible de supprimer ce livre","erreur de suppresion",JOptionPane.ERROR_MESSAGE);
		 }
		 ls.remove(ligneSelectionnee);
		 mod.removeRow(ligneSelectionnee);
	}
	public void modifierLivre(ArrayList<Livre> ls,DefaultTableModel mod,int ligneSelectionnee,String isbn,String auteur,String titre,int quant) {
		
   		 PreparedStatement prs;
		 String queryUpdate = "UPDATE users.livres  SET ISBN=?,nom_auteur=?,titre=?,q_total=?,q_disponible=?,q_perdu=?  WHERE ISBN =?";
		 try {		 
			 prs = ConnexionDB.getConnection().prepareStatement(queryUpdate);
			 prs.setString(1, isbn);
			 prs.setString(2,auteur );
			 prs.setString(3,titre );
			 prs.setInt(4,quant);
			 prs.setInt(5, quant);
			 prs.setInt(6, 0);
			 prs.setString(7,ls.get(ligneSelectionnee).getISBN() );
			 if(prs.executeUpdate() != 0) {
				 ls.get(ligneSelectionnee).setISBN(isbn);
				 ls.get(ligneSelectionnee).setAuteur(auteur);
				 ls.get(ligneSelectionnee).setTitre(titre);
				 ls.get(ligneSelectionnee).setQtotal(quant);
				 mod.setValueAt(isbn, ligneSelectionnee, 0);
				 mod.setValueAt(auteur, ligneSelectionnee, 1);
				 mod.setValueAt(titre, ligneSelectionnee, 2);
				 mod.setValueAt(quant, ligneSelectionnee, 3);
			 JOptionPane.showMessageDialog(null, "le livre a été bien Modifier","succés",JOptionPane.PLAIN_MESSAGE);
			 }
			 
		 }catch(Exception ex) {
			  JOptionPane.showMessageDialog(null, "impossible de modifier ce livre","erreur de suppresion",JOptionPane.ERROR_MESSAGE);
		 }
		 
	}
	public void emprunterLivre() {
		
	}
	public void chercherLivre(ArrayList<Livre> li,DefaultTableModel mod,String nom) {
		boolean trouvé = false;
		for(Livre livre:li) {
			if(nom.equals(livre.titre) || nom.equals(nom_auteur)) {
				livreschercher.add(livre);
				trouvé = true;
			}
		}
		if(trouvé) {
			while(mod.getRowCount() > 0) {
			    mod.removeRow(0); 
			  }
			 for(Livre livreTrouve : livreschercher) {
				    Object[] data = {
				      livreTrouve.getISBN(), 
				      livreTrouve.getAuteur(),
				      livreTrouve.getTitre(),
				      livreTrouve.getQtotal(),
				      livreTrouve.getQdisponible(),
				      livreTrouve.getQperdus(),
				    };
			    mod.addRow(data);
			}
			
		}
	}
	public void listerLivreEmprunter() {
		
	}
	public void afficherLivreDisponible() {
		
	}
	public void livreStatistiques() {
		
	}
	public void raifrichirTableau(ArrayList<Livre> livres,DefaultTableModel mod){
		while(mod.getRowCount() > 0) {
		    mod.removeRow(0); 
		}
		 for(Livre livre : livres) {
			    Object[] data = {
			      livre.getISBN(), 
			      livre.getAuteur(),
			      livre.getTitre(),
			      livre.getQtotal(),
			      livre.getQdisponible(),
			      livre.getQperdus(),
			    };
		    mod.addRow(data);
		}
	}
	public void displayAll() {
		PreparedStatement pst ;
		ResultSet rst;
		String querySelect ="SELECT * FROM users.livres";
		try {
			pst =  ConnexionDB.getConnection().prepareStatement(querySelect);
			rst = pst.executeQuery();
			while(rst.next()) {
				Livre l = new Livre(rst.getString("ISBN"),rst.getString("nom_auteur"),rst.getString("titre"),rst.getInt("q_total"),rst.getInt("q_disponible"),rst.getInt("q_perdu"));
				livres.add(l);
			}
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "imposible d'afficher la liste","erreur d'affichage",JOptionPane.ERROR_MESSAGE);
		}
		for(Livre l:livres) {
	    	mod.addRow(new Object[] {l.getISBN(),l.getAuteur(),l.getTitre(),l.getQtotal(),l.getQdisponible(),l.getQperdus()});
		}
	}
	
	
}
