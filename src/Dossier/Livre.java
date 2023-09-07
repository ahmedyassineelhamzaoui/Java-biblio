package Dossier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



public class Livre {
	
	private String ISBN;
	private String nom_auteur;
	private String titre;
	private int Q_total;
	private int Q_dispo;
	private int Q_perdu;
	ArrayList<Livre> livres = new ArrayList<>();
	ArrayList<Livre> livreschercher = new ArrayList<>();
	DefaultTableModel mod; // on ajoute ce model pour remplire notre tableau
	
	private int quantité_disponible;
	public Livre(String ISBN,String nom_auteur,String titre,int Q_total,int Q_dispo,int Q_perdu) {
		this.ISBN = ISBN;
		this.nom_auteur = nom_auteur;
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
	
	public void ajouterLivre(Livre l,DefaultTableModel mod,ArrayList<Livre> originList) {
		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
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
	    		int quantiteDisponible = l.Q_dispo;
	    		int quantitePerdue =  l.Q_perdu;
	    		int quantiteTotale = l.Q_total;
	    		if(l.Q_total< l.Q_dispo + l.Q_perdu) {
	    			  String message = "Attention, la somme de la quantité disponible (" 
	    			             + quantiteDisponible + ") et de la quantité perdue (" 
	    			             + quantitePerdue + ") ne peut pas dépasser la quantité totale ("+ quantiteTotale + ")";
	    			JOptionPane.showMessageDialog(null, message,"donner des donnees valides",JOptionPane.ERROR_MESSAGE);
	    		}else {
	    			PreparedStatement p;
		    		String queryInsert = "INSERT INTO users.livres(ISBN,nom_auteur,titre,q_total,q_disponible,q_perdu) VALUES(?,?,?,?,?,?)";
		    		try {
		    			p = ConnexionDB.getConnection().prepareStatement(queryInsert);
		    			p.setString(1, l.ISBN);
		    			p.setString(2, l.nom_auteur);
		    			p.setString(3, l.titre);
		    			p.setLong(4, Integer.parseInt(String.valueOf(l.Q_total)));
		    			p.setInt(5, Integer.parseInt(String.valueOf(l.Q_dispo)));
		    			p.setInt(6,l.Q_perdu);
		    			if(p.executeUpdate() !=0) {
							JOptionPane.showMessageDialog(null, "Le livre a été créer avec succés","succés",JOptionPane.PLAIN_MESSAGE);
							Livre livre = new Livre(l.ISBN,l.nom_auteur,l.titre,l.Q_total,l.Q_dispo,l.Q_perdu);
							originList.add(livre);
					    	mod.addRow(new Object[] {livre.getISBN(),livre.getAuteur(),livre.getTitre(),livre.getQtotal(),livre.getQdisponible(),livre.getQperdus()});
		    			}
		    		}catch(Exception e) {
		    			JOptionPane.showMessageDialog(null,"imposibe d'inserer les donnes","erreur d'insertion",JOptionPane.ERROR_MESSAGE);
		    		}
	    		}
	    		
	    	}
	    	}catch(Exception e) {
	    		JOptionPane.showMessageDialog(null,"vérification échoué","erreur",JOptionPane.ERROR_MESSAGE);
	    	} 
	}
	
	public void supprimerLivre(ArrayList<Livre> originListe,ArrayList<Livre> ls,DefaultTableModel mod,int ligneSelectionnee) {
		 
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
		 for(int i=0;i<originListe.size();i++) {
			 if(originListe.get(i).ISBN.equals(ls.get(ligneSelectionnee).getISBN())){
				 originListe.remove(i);
				 break;
			 }
		 }
		 mod.removeRow(ligneSelectionnee);
	}
	public void modifierLivre(ArrayList<Livre> originListe,ArrayList<Livre> ls,DefaultTableModel mod,int ligneSelectionnee,String isbn,String auteur,String titre,int quant,int quantdispo,int quantperdu) {
		
   		 PreparedStatement prs;
		 String queryUpdate = "UPDATE users.livres  SET ISBN=?,nom_auteur=?,titre=?,q_total=?,q_disponible=?,q_perdu=?  WHERE ISBN =?";
		 try {		 
			 prs = ConnexionDB.getConnection().prepareStatement(queryUpdate);
			 prs.setString(1, isbn);
			 prs.setString(2,auteur );
			 prs.setString(3,titre );
			 prs.setInt(4,quant);
			 prs.setInt(5, quantdispo);
			 prs.setInt(6, quantperdu);
			 prs.setString(7,ls.get(ligneSelectionnee).getISBN());
			 if(prs.executeUpdate() != 0) {
				 for(int i=0;i<originListe.size();i++) {
					 if(originListe.get(i).ISBN.equals(ls.get(ligneSelectionnee).getISBN())){
						 originListe.get(i).setISBN(isbn);
						 originListe.get(i).setAuteur(auteur);
						 originListe.get(i).setTitre(titre);
						 originListe.get(i).setQtotal(quant);
						 originListe.get(i).setQdisponible(quantdispo);
						 originListe.get(i).setQperdus(quantperdu);
						 break;
					 }
				 }
				 ls.get(ligneSelectionnee).setISBN(isbn);
				 ls.get(ligneSelectionnee).setAuteur(auteur);
				 ls.get(ligneSelectionnee).setTitre(titre);
				 ls.get(ligneSelectionnee).setQtotal(quant);
				 ls.get(ligneSelectionnee).setQdisponible(quantdispo);
				 ls.get(ligneSelectionnee).setQperdus(quantperdu);
				 mod.setValueAt(isbn, ligneSelectionnee, 0);
				 mod.setValueAt(auteur, ligneSelectionnee, 1);
				 mod.setValueAt(titre, ligneSelectionnee, 2);
				 mod.setValueAt(quant, ligneSelectionnee, 3);
				 mod.setValueAt(quantdispo, ligneSelectionnee, 4);
				 mod.setValueAt(quantperdu, ligneSelectionnee, 5);
			 JOptionPane.showMessageDialog(null, "le livre a été bien Modifier","succés",JOptionPane.PLAIN_MESSAGE);
			 }
			 
		 }catch(Exception ex) {
			  JOptionPane.showMessageDialog(null, "impossible de modifier ce livre","erreur de suppresion",JOptionPane.ERROR_MESSAGE);
		 }
		 
	}
	public void emprunterLivre(ArrayList<Livre> originListe,ArrayList<Livre> newLivresList,DefaultTableModel mod,int ligneSelectionnee,int user_id) {
		String quantity = JOptionPane.showInputDialog(null, "Veuillez entrer la quantité empruntée","Quantité",JOptionPane.QUESTION_MESSAGE);	
		if(quantity != null) {
	        PreparedStatement p;
	        ResultSet s;
	        String querySelect = "SELECT q_disponible from livres where isbn=?";
	        try {
	        	p = ConnexionDB.getConnection().prepareStatement(querySelect);
	        	p.setString(1, (String) mod.getValueAt(ligneSelectionnee, 0) );
	        	s = p.executeQuery();
	        	if(s.next()) {
	        		quantité_disponible = s.getInt("q_disponible");
	        	}
 	            int qty = Integer.parseInt(quantity);
	        	if(quantité_disponible> qty) {
	        		  PreparedStatement pserach;
	        		  ResultSet resultSearch;
	   	              String searchsameISBN ="SELECT * FROM emprunts where ISBN_livre= ? AND id_emprunteur = ?";
		   	             try {
		   	            	pserach = ConnexionDB.getConnection().prepareStatement(searchsameISBN);
		   	            	pserach.setString(1, (String) mod.getValueAt(ligneSelectionnee, 0));
		   	            	pserach.setInt(2, user_id);
		   	            	resultSearch = pserach.executeQuery();
		   	            	if(resultSearch.next()) {
		   	            		JOptionPane.showMessageDialog(null, "Merci de retourner la quantité de livre emprunté à temps pour le bénéfice d'un autre.");
		   	            	}else {
		   	            	 try {
		   	   	              PreparedStatement ps;
		   	   	              String Query ="INSERT INTO emprunts(ISBN_livre,date_emprunt,date_retoure,statut,id_emprunteur) VALUES(?,?,?,?,?)";
		   	   	              try {
		   	   	            	  ps = ConnexionDB.getConnection().prepareStatement(Query);
		   	   	            	  ps.setString(1, (String) mod.getValueAt(ligneSelectionnee, 0));
		   		   	            	java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(System.currentTimeMillis());
		   		   	            	ps.setTimestamp(2, currentTimestamp);
		   		
		   		   	            	java.util.Calendar calendar = java.util.Calendar.getInstance();
		   		   	            	calendar.setTime(currentTimestamp);
		   		   	            	calendar.add(java.util.Calendar.DAY_OF_MONTH, 2);
		   		   	            	java.sql.Timestamp returnTimestamp = new java.sql.Timestamp(calendar.getTimeInMillis());
		   		   	            	ps.setTimestamp(3, returnTimestamp);
		   	   	            	    ps.setString(4, "emprunté");
		   	   	            	    ps.setInt(5, user_id);
		   		   	            	if(ps.executeUpdate() != 0) {
		   				   	   				 for(int i=0;i<originListe.size();i++) {
		   				   	   					 if(originListe.get(i).ISBN.equals(newLivresList.get(ligneSelectionnee).getISBN())){
		   				   	   						 originListe.get(i).setQdisponible(quantité_disponible - qty);
		   			   	   						      break;
		   				   	   					 }
		   				   	   				 }
		   			   	   				
		   			   	   			     newLivresList.get(ligneSelectionnee).setQdisponible(quantité_disponible - qty );
		   			   	   				 mod.setValueAt(quantité_disponible - qty, ligneSelectionnee, 4);
		   			   	   				 
		   				   	             JOptionPane.showMessageDialog(null, "Le livre a été bien emprunté");
		   				   	             PreparedStatement pst;
		   				   	             String queryUpdate = "UPDATE livres SET q_disponible = ? WHERE ISBN =? ";
		   				   	             try {
		   				   	            	 pst = ConnexionDB.getConnection().prepareStatement(queryUpdate);
		   					   	             pst.setInt(1,quantité_disponible - qty);
		   					   	             pst.setString(2, (String) mod.getValueAt(ligneSelectionnee, 0));
		   					   	             pst.execute();
		   				   	             }catch(Exception e) {
		   				   	            	  JOptionPane.showMessageDialog(null,"impossible de metre a jour","erreur de modification",JOptionPane.ERROR_MESSAGE);
		   				   	             }
		   	   	            	   }
		   	   	              }catch(Exception ex) {
		   	   	            	  JOptionPane.showMessageDialog(null,"impossible d'emprunté ce livre pour le moment","erreur d'emprunt",JOptionPane.ERROR_MESSAGE);
		   	   	              }
		   	   	              
		   	   	          } catch(NumberFormatException ex) {
		   	   	              JOptionPane.showMessageDialog(null,  "Veuillez entrer un nombre","invalide quanité ",JOptionPane.ERROR_MESSAGE); 
		   	   	          }
		   	            	}
		   	             }catch(Exception e) {
		   	            	 JOptionPane.showMessageDialog(null, "erreur dans votre requéte","erreur",JOptionPane.ERROR_MESSAGE);
		   	             }}else {
	        		JOptionPane.showMessageDialog(null, "la quanté que vous avez entrer n'est plus disponible","impossible d'emprunté ",JOptionPane.ERROR_MESSAGE);
	        	}
	        }catch(Exception ex) {
	        	JOptionPane.showMessageDialog(null, "quantité disponible non trouvable","erreur de selection",JOptionPane.ERROR_MESSAGE);
	        }
	      }
	}
	public void chercherLivre(ArrayList<Livre> li,DefaultTableModel mod,String nom) {
		PreparedStatement ps;
		ResultSet rs;
		String searchQuery = "SELECT * FROM livres l WHERE l.nom_auteur LIKE ? OR l.titre LIKE ?";
		try {
		    ps = ConnexionDB.getConnection().prepareStatement(searchQuery);
		    ps.setString(1, '%' + nom + '%');
		    ps.setString(2, '%' + nom + '%');
		    rs = ps.executeQuery();
		    while (rs.next()) {
		        Livre l = new Livre(rs.getString("ISBN"), rs.getString("nom_auteur"), rs.getString("titre"), rs.getInt("q_total"), rs.getInt("q_disponible"), rs.getInt("q_perdu"));
		        livreschercher.add(l);
		    }
		    while (mod.getRowCount() > 0) {
		        mod.removeRow(0);
		    }
		    if (livreschercher.size() > 0) {
		        System.out.println("kldk");
		        for (Livre livreTrouve : livreschercher) {
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
		    } else {
		        JOptionPane.showMessageDialog(null, "Aucun livre n'a été détecté avec le nom entré", "Aucun livre", JOptionPane.ERROR_MESSAGE);
		    }

		} catch (Exception e) {
		    e.printStackTrace();
		    JOptionPane.showMessageDialog(null, "Une erreur dans la requête", "Erreur", JOptionPane.ERROR_MESSAGE);
		}

	}
	public void listerLivreEmprunter() {
		
	}
	public void afficherLivreDisponible(DefaultTableModel mod) {
		ArrayList<Livre> livres = new ArrayList<>();
		PreparedStatement ps;
		ResultSet rs;
		String query ="SELECT * FROM livres l where l.q_disponible > 0 ";
		try {
			ps = ConnexionDB.getConnection().prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				Livre l = new Livre(rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getInt(7));
				livres.add(l);
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "erreur dans la requéte","erreur",JOptionPane.ERROR_MESSAGE);
		}
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
	public void retournerLLivre() {
		
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
