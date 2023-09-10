package Dossier.Model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Dossier.Database.ConnexionDB;
import Dossier.Model.Entities.Livre;

public class LivreModel {

	public static void listerLivreEmprunter(String role,int user_id,DefaultTableModel mod1)
	{
		PreparedStatement ps;
		ResultSet rs;
		String querySelectEmpruntadmin ="SELECT l.ISBN,a.name as nom,l.titre,e.quantité_emprunté,e.date_emprunt,e.date_retour,u.name,u.phone,e.statut  FROM  auteur a JOIN livres l ON l.id_auteur = a.id  JOIN  emprunts e on l.ISBN = e.ISBN_livre JOIN users u ON u.id = e.id_emprunteur  ";
		String querySelectEmpruntUser ="SELECT l.ISBN,a.name as nom,l.titre,e.quantité_emprunté,e.date_emprunt,e.date_retour,u.name,u.phone,e.statut  FROM  auteur a JOIN livres l ON l.id_auteur = a.id  JOIN  emprunts e on l.ISBN = e.ISBN_livre JOIN users u ON u.id = e.id_emprunteur  where u.id = ?";

		try {
			if(role.equals("Emprunteur")) {
				ps =  ConnexionDB.getConnection().prepareStatement(querySelectEmpruntUser);
				ps.setInt(1, user_id);
				rs = ps.executeQuery();
				while(rs.next()) {
					mod1.addRow(new Object[] {rs.getString("ISBN"),rs.getString("nom"),rs.getString("titre"),rs.getInt("quantité_emprunté"),rs.getTimestamp("date_emprunt"),rs.getTimestamp("date_retour"),rs.getString("name"),rs.getString("phone"),rs.getString("statut")});
				}
			}else {
				ps =  ConnexionDB.getConnection().prepareStatement(querySelectEmpruntadmin);
				rs = ps.executeQuery();
				while(rs.next()) {
					mod1.addRow(new Object[] {rs.getString("ISBN"),rs.getString("nom"),rs.getString("titre"),rs.getInt("quantité_emprunté"),rs.getTimestamp("date_emprunt"),rs.getTimestamp("date_retour"),rs.getString("name"),rs.getString("phone"),rs.getString("statut")});
				}
			}
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(),"erreur d'affichage",JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void listerTousLivres(DefaultTableModel mod)
	{
		PreparedStatement pst ;
		ResultSet rst;
		String querySelect ="SELECT * FROM livres";
		try {
			pst =  ConnexionDB.getConnection().prepareStatement(querySelect);
			rst = pst.executeQuery();
			while(rst.next()) {
				String auteur_nom = AuteurModel.trouverAuteurNom(rst.getInt("id_auteur"));
		    	mod.addRow(new Object[] {rst.getString("ISBN"),auteur_nom,rst.getString("titre"),rst.getInt("q_total"),rst.getInt("q_disponible"),rst.getInt("q_perdu")});
			}
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "imposible d'afficher la liste","erreur d'affichage",JOptionPane.ERROR_MESSAGE);
		}
	}
	public static ArrayList<Integer> AfficherStatistiques()
	{
		int s1,s2,s3,s4,s5;
        ArrayList<Integer> numbers = new ArrayList<>();
		PreparedStatement ps1;
		ResultSet rs1;
        String statistiquequery1 = "SELECT sum(l.q_total) as s1 , sum(l.q_disponible) as s2 , sum(l.q_perdu) as s3   FROM livres l ";
		try {
			ps1 = ConnexionDB.getConnection().prepareStatement(statistiquequery1);
			rs1 = ps1.executeQuery();
			if(rs1.next()) {
				s1 = rs1.getInt(1);
				s2 = rs1.getInt(2);
				s3 = rs1.getInt(3);
				numbers.add(s1);
				numbers.add(s2);
				numbers.add(s3);
			}
		}catch(Exception e) {
			e.getMessage();
		}
		
		PreparedStatement ps2;
		ResultSet rs2;
        String statistiquequery2 = " SELECT\r\n"
        		+ "    SUM(CASE WHEN statut ='emprunté' then quantité_emprunté end ) as total_emprunté,\r\n"
        		+ "    SUM(CASE WHEN statut = 'en retard' then quantité_emprunté end) as quantité_retard\r\n"
        		+ "FROM emprunts";
		try {
			ps2 = ConnexionDB.getConnection().prepareStatement(statistiquequery2);
			rs2 = ps2.executeQuery();
			if(rs2.next()) {
				s4 = rs2.getInt(1);
				s5 = rs2.getInt(2);
				numbers.add(s4);
				numbers.add(s5);
			}
		}catch(Exception e) {
			e.getMessage();
		}
		
		 return numbers;
	}
	public static void ajouterLivre(Livre l,DefaultTableModel mod)
	{
		PreparedStatement ps;
    	ResultSet rs;
    	String query = "SELECT * FROM  livres where ISBN=? or titre=? ";
    	try {
    	ps = ConnexionDB.getConnection().prepareStatement(query);
    	ps.setString(1,l.getISBN() );
    	ps.setString(2,l.getTitre() );
    	rs = ps.executeQuery();
    	if(rs.next()) {
    		JOptionPane.showMessageDialog(null,"ISBN ou titre de livre est déja existe","insertion refusé",JOptionPane.ERROR_MESSAGE);
    	}else {
    		int quantiteDisponible = l.getQdisponible();
    		int quantitePerdue =  l.getQperdus();
    		int quantiteTotale = l.getQtotal();
    		if(l.getQtotal() != l.getQdisponible() + l.getQperdus()) {
    			  String message = "Attention, la somme de la quantité disponible (" 
    			             + quantiteDisponible + ") et de la quantité perdue (" 
    			             + quantitePerdue + ") sont défferent à la quantité totale ("+ quantiteTotale + ")";
    			JOptionPane.showMessageDialog(null, message,"donner des donnees valides",JOptionPane.ERROR_MESSAGE);
    		}
    		else {
    			PreparedStatement p;
	    		String queryInsert = "INSERT INTO livres(ISBN,titre,id_auteur,q_total,q_disponible,q_perdu) VALUES(?,?,?,?,?,?)";
	    		try {
	    			p = ConnexionDB.getConnection().prepareStatement(queryInsert);
	    			p.setString(1, l.getISBN());
	    			p.setString(2, l.getTitre());
	    			p.setInt(3,l.getAuteur());
	    			p.setInt(4, l.getQtotal());
	    			p.setInt(5, l.getQdisponible());
	    			p.setInt(6,l.getQperdus());
	    			if(p.executeUpdate() !=0) {
						JOptionPane.showMessageDialog(null, "Le livre a été créer avec succés","succés",JOptionPane.PLAIN_MESSAGE);
						String auteur_nom = AuteurModel.trouverAuteurNom(l.getAuteur());
				    	mod.addRow(new Object[] {l.getISBN(),l.getTitre(),auteur_nom,l.getQtotal(),l.getQdisponible(),l.getQperdus()});
	    			}
	    		}catch(Exception e) {
	    			JOptionPane.showMessageDialog(null,"imposibe d'inserer les donnes","erreur d'insertion",JOptionPane.ERROR_MESSAGE);
	    		}
    		}
    		
    	}
    	}catch(Exception e) {
    		JOptionPane.showMessageDialog(null,"ISBN ou titre de livre est déja existe","erreur",JOptionPane.ERROR_MESSAGE);
    	} 
	}
	public static void supprimerLivre(DefaultTableModel mod,int ligne)
	{
		 PreparedStatement ps;
		 ResultSet rs;
		 String queryAfiche = "SELECT * FROM emprunts WHERE ISBN_livre=?";
         try {
        	 ps = ConnexionDB.getConnection().prepareStatement(queryAfiche);
        	 ps.setString(1,(String)mod.getValueAt(ligne, 0));
        	 rs = ps.executeQuery();
        	 if(rs.next()) {
        		 JOptionPane.showMessageDialog(null, "vous ne pouvez pas supprimer ce livre car il est emprunté","erreur de supperssion",JOptionPane.ERROR_MESSAGE);
        	 }else {
        		 PreparedStatement prs;
        		 String queryDelete = "DELETE FROM livres WHERE ISBN=?";
        		 try {
        			 prs = ConnexionDB.getConnection().prepareStatement(queryDelete);
        			 prs.setString(1, (String)mod.getValueAt(ligne, 0));
        			 if(prs.executeUpdate() != 0) {
        				 JOptionPane.showMessageDialog(null, "le livre a été bien supprimer","succés",JOptionPane.PLAIN_MESSAGE);
        			 }
        		 }catch(Exception ex) {
        			  JOptionPane.showMessageDialog(null, "impossible de supprimer ce livre","erreur de suppresion",JOptionPane.ERROR_MESSAGE);
        		 }
        		 mod.removeRow(ligne);
        	 }
         }catch(Exception e) {
        	 JOptionPane.showMessageDialog(null, "une Erreur dans le code","erreur",JOptionPane.ERROR_MESSAGE);
         }
	}
	public static void chercherLivre(DefaultTableModel mod,String nom)
	{
		PreparedStatement ps;
		ResultSet rs;
		boolean trouvé = false;
		String searchQuery = "SELECT * FROM livres l JOIN auteur a ON l.id_auteur = a.id WHERE a.name LIKE ? OR l.titre LIKE ?";
		try {
		    ps = ConnexionDB.getConnection().prepareStatement(searchQuery);
		    ps.setString(1, '%' + nom + '%');
		    ps.setString(2, '%' + nom + '%');
		    rs = ps.executeQuery();
		    while (mod.getRowCount() > 0) {
		        mod.removeRow(0);
		    }
		    while (rs.next()) {
		    	Object[] data = {
	                    rs.getString("ISBN"),
	                    rs.getString("name"),
	                    rs.getString("titre"),
	                    rs.getString("q_total"),
	                    rs.getString("q_disponible"),
	                    rs.getString("q_perdu")
	            };
	            mod.addRow(data);
	            trouvé = true;
		    }
		    if(!trouvé) JOptionPane.showMessageDialog(null, "Désolé, aucun livre ne correspond à la valeur saisie. Veuillez vérifier l'ISBN ou le titre du livre et réessayer","info",JOptionPane.PLAIN_MESSAGE);
		    
		} catch (Exception e) {
		    JOptionPane.showMessageDialog(null, "Une erreur dans le traitement", "Erreur", JOptionPane.ERROR_MESSAGE);
		}

	}
	public static void raifrichirTableau(DefaultTableModel mod)
	{
		while(mod.getRowCount() > 0) {
		    mod.removeRow(0); 
		}
		listerTousLivres(mod);
	}
	public static void listerLivreDisponible(DefaultTableModel mod)
	{
		PreparedStatement ps;
		ResultSet rs;
		String query ="SELECT * FROM livres l JOIN auteur a ON l.id_auteur = a.id where l.q_disponible > 0 ";
		while(mod.getRowCount() > 0) {
		    mod.removeRow(0); 
		}
		try {
			ps = ConnexionDB.getConnection().prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				Object[] data = {
					      rs.getString("ISBN"), 
					      rs.getString("name"),
					      rs.getString("titre"),
					      rs.getInt("q_total"),
					      rs.getInt("q_disponible"),
					      rs.getInt("q_perdu")
					    };
				    mod.addRow(data);
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Imposible d'afficher les livres disponibles","Info",JOptionPane.ERROR_MESSAGE);
		}
		
	
	}
	public static void modifierLivre(DefaultTableModel mod,int ligne,String isbnField,String select_auteur,String titreField,int quantityField,int quantity_dispo,int qauntity_perdu)
	{
		PreparedStatement p;
		ResultSet r;
		String querys = "SELECT e.quantité_emprunté from  emprunts e where ISBN_livre = ?";
		try {
			p = ConnexionDB.getConnection().prepareStatement(querys);
			p.setString(1,isbnField );
			r = p.executeQuery();
			if(r.next()) {
				JOptionPane.showMessageDialog(null, "impossible de modifier ce livre car il est emprunté","Message",JOptionPane.ERROR_MESSAGE);
			}else {
				if(quantityField != quantity_dispo + qauntity_perdu ) {
					JOptionPane.showMessageDialog(null, "impossible de metre a jour ce livre\nla quantité total # quantité disponible + quantité perdu ","erreur de modificatiin",JOptionPane.ERROR_MESSAGE);
				}else {
					 int id_auteur = AuteurModel.trouverIdAuteur(select_auteur);
					 PreparedStatement prs;
					 String queryUpdate = "UPDATE livres  SET ISBN=?,id_auteur=?,titre=?,q_total=?,q_disponible=?,q_perdu=?  WHERE ISBN =?";
					 try {		 
						 prs = ConnexionDB.getConnection().prepareStatement(queryUpdate);
						 prs.setString(1, isbnField);
						 prs.setInt(2,id_auteur );
						 prs.setString(3,titreField);
						 prs.setInt(4, quantityField);
						 prs.setInt(5, quantity_dispo);
						 prs.setInt(6, qauntity_perdu);
						 prs.setString(7,(String) mod.getValueAt(ligne, 0));
						 
						 if(prs.executeUpdate() != 0) {
							 mod.setValueAt(isbnField, ligne, 0);
							 mod.setValueAt(select_auteur, ligne, 1);
							 mod.setValueAt(titreField, ligne, 2);
							 mod.setValueAt(quantityField, ligne, 3);
							 mod.setValueAt(quantity_dispo , ligne, 4);
							 mod.setValueAt(qauntity_perdu, ligne, 5);
						 JOptionPane.showMessageDialog(null, "le livre a été bien Modifier","succés",JOptionPane.PLAIN_MESSAGE);
						 }
						 
					 }catch(Exception ex) {
						  JOptionPane.showMessageDialog(null, "impossible de modifier ce livre\n"
						  		+ "ISBN ou titre existe déja","erreur de suppresion",JOptionPane.ERROR_MESSAGE);
					 }
				}
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "erreur","erreur",JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void emprunterLivre(DefaultTableModel mod,DefaultTableModel mod1,int ligne,String quantity,int user_id)
	{
		        PreparedStatement p;
		        ResultSet s;
		        String querySelect = "SELECT q_disponible from livres where ISBN=?";
		        try {
		        	p = ConnexionDB.getConnection().prepareStatement(querySelect);
		        	p.setString(1, (String) mod.getValueAt(ligne, 0) );
		        	s = p.executeQuery();
		        	if(s.next()) {
		        	   int quantité_disponible = s.getInt("q_disponible");
		 	           int qty = Integer.parseInt(quantity);
		 	           if(quantité_disponible >= qty) {
			        		  PreparedStatement pserach;
			        		  ResultSet resultSearch;
			   	              String searchsameISBN ="SELECT * FROM   emprunts e JOIN livres l ON e.ISBN_livre = l.ISBN JOIN users u ON u.id = e.id_emprunteur  where e.ISBN_livre= ? AND e.id_emprunteur = ?";
				   	             try {
				   	            	pserach = ConnexionDB.getConnection().prepareStatement(searchsameISBN);
				   	            	pserach.setString(1, (String) mod.getValueAt(ligne, 0));
				   	            	pserach.setInt(2, user_id);
				   	            	resultSearch = pserach.executeQuery();
				   	            	if(resultSearch.next()) {
				   	            		JOptionPane.showMessageDialog(null, "Merci de retourner la quantité de livre emprunté à temps pour le bénéfice d'un autre.");
				   	            	}else {
				   	   	              PreparedStatement ps;
				   	   	              String Query ="INSERT INTO emprunts(date_emprunt,date_retour,ISBN_livre,statut,id_emprunteur,quantité_emprunté)  VALUES(?,?,?,?,?,?)";
				   	   	              try {
				   	   	            	  ps = ConnexionDB.getConnection().prepareStatement(Query);
				   	   	            	  ps.setString(3, (String) mod.getValueAt(ligne, 0));
				   		   	              java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(System.currentTimeMillis());
				   		   	              ps.setTimestamp(1, currentTimestamp);
		
				   		   	              java.util.Calendar calendar = java.util.Calendar.getInstance();
				   		   	              calendar.setTime(currentTimestamp);
				   		   	              calendar.add(java.util.Calendar.DAY_OF_MONTH, 2);
				   		   	              java.sql.Timestamp returnTimestamp = new java.sql.Timestamp(calendar.getTimeInMillis());
				   		   	              ps.setTimestamp(2, returnTimestamp);
				   	   	            	  ps.setString(4, "emprunté");
				   	   	            	  ps.setInt(5, user_id);
				   	   	            	  ps.setInt(6, qty);
				   		   	              if(ps.executeUpdate() != 0) {
			   			   	   				 mod.setValueAt(quantité_disponible - qty, ligne, 4);
					   			   	   		 PreparedStatement pserIn;
					   		        		 ResultSet resulIn;
					   		   	             String searchI ="SELECT l.ISBN,l.titre,e.date_emprunt,e.date_retour,e.quantité_emprunté,e.statut,u.phone,u.name,a.name nom_auteur FROM auteur a JOIN livres l on a.id = l.id_auteur Join emprunts e on l.ISBN = e.ISBN_livre join users u on e.id_emprunteur = u.id  where l.ISBN = ? AND e.id_emprunteur = ?";
							   		   	     try {
							   		   	        pserIn = ConnexionDB.getConnection().prepareStatement(searchI);
							   		   	        pserIn.setString(1, (String) mod.getValueAt(ligne, 0));
							   		   	        pserIn.setInt(2, user_id);
							   		   	        resulIn = pserIn.executeQuery();
							   		   	        if(resulIn.next()) {
					   				   	           mod1.addRow(new Object[] {resulIn.getString("ISBN"),resulIn.getString("nom_auteur"),resulIn.getString("titre"),resulIn.getInt("quantité_emprunté"),resulIn.getTimestamp("date_emprunt"),resulIn.getTimestamp("date_retour"),resulIn.getString("name"),resulIn.getString("phone"),resulIn.getString("statut")});
					   				   	           JOptionPane.showMessageDialog(null, "Le livre a été bien emprunté");
							   		   	        }
				   				   	         }catch(Exception e) {
				   				   	            JOptionPane.showMessageDialog(null,"impossible de metre a jour","Message",JOptionPane.ERROR_MESSAGE);
				   				   	         }
				   	   	            	   }
				   	   	              }catch(Exception ex) {
				   	   	            	  JOptionPane.showMessageDialog(null,"impossible d'emprunté ce livre pour le moment","erreur d'emprunt",JOptionPane.ERROR_MESSAGE);
				   	   	              }
				   	            }
				   	            }catch(Exception e) {
				   	            	 JOptionPane.showMessageDialog(null, "erreur dans votre requéte","erreur",JOptionPane.ERROR_MESSAGE);
				   	            }
				   	            }else 
				   	            {
			        		       JOptionPane.showMessageDialog(null, "la quanté que vous avez entrer n'est plus disponible","impossible d'emprunté ",JOptionPane.ERROR_MESSAGE);
			        	        }
		        	}
		        	
		        }catch(Exception ex) {
		        	JOptionPane.showMessageDialog(null, "quantité disponible non trouvable","message",JOptionPane.ERROR_MESSAGE);
		        }
		}
	public static void retournerLivre(DefaultTableModel mod,DefaultTableModel mod1,String ISBN,int user_id)
	{
		PreparedStatement ps;
		ResultSet rs;
		String query = "SELECT * from emprunts where ISBN_livre = ? and id_emprunteur = ?";
		try {
			ps = ConnexionDB.getConnection().prepareStatement(query);
			ps.setString(1, ISBN);
			ps.setInt(2, user_id);
			rs = ps.executeQuery();
			if(rs.next()) {
				PreparedStatement psdelete;
				String queryDelete = "DELETE  from emprunts where ISBN_livre = ? and id_emprunteur = ?";
				try {
					psdelete = ConnexionDB.getConnection().prepareStatement(queryDelete);
					psdelete.setString(1, ISBN);
					psdelete.setInt(2, user_id);
					if(psdelete.executeUpdate()!=0) {
						while(mod.getRowCount() >0) {
							mod.removeRow(0);
						}
						raifrichirTableau(mod);
						while(mod1.getRowCount() >0) {
							mod1.removeRow(0);
						}
						listerLivreEmprunter("Emprunteur",user_id,mod1);
						JOptionPane.showMessageDialog(null, "Merci Le livre a été retourné avec succès","message",JOptionPane.PLAIN_MESSAGE);
					}
				}catch(Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(),"Message",JOptionPane.ERROR_MESSAGE);
				}
			}else {
				JOptionPane.showMessageDialog(null, "vous n'avez pas emprunté ce livre","message",JOptionPane.ERROR_MESSAGE);
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),"message",JOptionPane.ERROR_MESSAGE);
		}
	}
}
