package Dossier;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import java.awt.TextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends JFrame {

	private JPanel contentPane;
    private JLabel auth_name;
    private JLabel user_role;
    private JLabel user_image;
    private JTextField input_search;
    private JTextField isbnField;
    private JTextField titreField;
    private JTextField quantityField;
    
    ArrayList<Livre> livres = new ArrayList<>();
    DefaultTableModel mod; // on ajoute ce model pour remplire notre tableau
    DefaultTableModel mod1;
    private JTextField quantity_dispo;
    private JTextField qauntity_perdu;
    
    
    private String userRole;
    private JTable table;
//    private JTable tableEmprunts;
    
    public JLabel getAuthname() {
    	return auth_name;
    }
    public JLabel getImage() {
    	return user_image;
    }
    public JLabel getRole() {
    	return user_role;
    }
    public JTextField getIsbnField() {
		return  isbnField;
	}
	public JTextField getTitreField() {
		return  titreField;
	}
	public JTextField getQuantityField() {
		return  quantityField;
	}
	public JTextField getQuantityAvailableField() {
		return  quantity_dispo;
	}
	public JTextField getQuantityLostField() {
		return  qauntity_perdu;
	}
   
	/**
	 * Launch the application.
	 */
    
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login l =new Login();
					l.setVisible(true);
					l.setLocationRelativeTo(null);
					l.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Menu() {
    }
		
    public Menu(String role,int user_id) {
    	
		this.userRole = role;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1370, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 128, 128));
		panel_1.setBounds(0, 0, 258, 711);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		user_image = new JLabel("");
		user_image.setHorizontalAlignment(SwingConstants.CENTER);
		user_image.setBounds(70, 22, 118, 120);
		panel_1.add(user_image);
		
		user_role = new JLabel("");
		user_role.setHorizontalAlignment(SwingConstants.CENTER);
		user_role.setFont(new Font("Tahoma", Font.BOLD, 12));
		user_role.setBounds(70, 153, 118, 14);
		panel_1.add(user_role);
		
		
		
		JTable tableLivre = new JTable();
		
		
		tableLivre.setFont(new Font("Tahoma", Font.BOLD, 12));
		tableLivre.setBorder(UIManager.getBorder("Table.scrollPaneBorder"));
		tableLivre.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ISBN", "nom d'auteur", "Titre de livre", "Quantit\u00E9 total", "Quantit\u00E9 disponible", "Quantit\u00E9 perdue"
			}
		));
		
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
		mod = (DefaultTableModel) tableLivre.getModel();
		for(Livre l:livres) {
	    	mod.addRow(new Object[] {l.getISBN(),l.getAuteur(),l.getTitre(),l.getQtotal(),l.getQdisponible(),l.getQperdus()});
		}
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(245, 255, 250));
		panel_2.setBounds(257, 0, 1097, 711);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		// Create a JScrollPane and add the table to it
		JScrollPane scrollPane = new JScrollPane(tableLivre);

		// Add the scroll pane to your panel or frame
		scrollPane.setBounds(12, 344, 1075, 166);
		panel_2.add(scrollPane);
		

		
		JLabel total_statistique = new JLabel("0");
		total_statistique.setHorizontalAlignment(SwingConstants.CENTER);
		total_statistique.setFont(new Font("Tahoma", Font.BOLD, 14));
		total_statistique.setBounds(89, 118, 46, 14);
		panel_2.add(total_statistique);
		
		JLabel total_disponible = new JLabel("0");
		total_disponible.setHorizontalAlignment(SwingConstants.CENTER);
		total_disponible.setFont(new Font("Tahoma", Font.BOLD, 14));
		total_disponible.setBounds(277, 118, 46, 14);
		panel_2.add(total_disponible);
		
		JLabel total_perdu = new JLabel("0");
		total_perdu.setFont(new Font("Tahoma", Font.BOLD, 14));
		total_perdu.setHorizontalAlignment(SwingConstants.CENTER);
		total_perdu.setBounds(484, 118, 46, 14);
		panel_2.add(total_perdu);
		
		JLabel total_rtard = new JLabel("0");
		total_rtard.setFont(new Font("Tahoma", Font.BOLD, 14));
		total_rtard.setHorizontalAlignment(SwingConstants.CENTER);
		total_rtard.setBounds(700, 118, 46, 14);
		panel_2.add(total_rtard);
		
		JLabel total_emprunté = new JLabel("0");
		total_emprunté.setFont(new Font("Tahoma", Font.BOLD, 14));
		total_emprunté.setHorizontalAlignment(SwingConstants.CENTER);
		total_emprunté.setBounds(930, 118, 46, 14);
		panel_2.add(total_emprunté);
		
		isbnField = new JTextField();
		isbnField.setBackground(new Color(255, 255, 224));
		isbnField.setBounds(12, 192, 123, 33);
		panel_2.add(isbnField);
		
		titreField = new JTextField();
		titreField.setBackground(new Color(255, 255, 224));
		titreField.setBounds(358, 192, 230, 33);
		panel_2.add(titreField);
		
		quantityField = new JTextField();
		JLabel lblNewLabel_5 = new JLabel("Quantité disponible");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_5.setBounds(758, 172, 124, 14);
		panel_2.add(lblNewLabel_5);
		
		quantity_dispo = new JTextField();
		quantity_dispo.setBackground(new Color(255, 255, 224));
		quantity_dispo.setBounds(758, 192, 138, 33);
		panel_2.add(quantity_dispo);
		quantity_dispo.setColumns(10);
		
		qauntity_perdu = new JTextField();
		qauntity_perdu.setBackground(new Color(255, 255, 224));
		qauntity_perdu.setBounds(906, 192, 151, 32);
		panel_2.add(qauntity_perdu);
		qauntity_perdu.setColumns(10);
		
		PreparedStatement prepare;
		ResultSet resul;
		
		String statistiquequery = "SELECT sum(l.q_total) as t1 , sum(l.q_disponible) as t2 , sum(l.q_perdu) as t3   FROM livres l ";
		
		try {
			prepare = ConnexionDB.getConnection().prepareStatement(statistiquequery);
			resul = prepare.executeQuery();
			if(resul.next()) {
				total_statistique.setText(resul.getString(1));
				total_disponible.setText(resul.getString(2));
				total_perdu.setText(resul.getString(3));
			}
		}catch(Exception e) {
			e.getMessage();
		}
		
		PreparedStatement prepared;
		ResultSet results;
		
		String queryRetard = "SELECT count(*) FROM emprunts where statut = 'en retard' ";

		try {
			prepared = ConnexionDB.getConnection().prepareStatement(queryRetard);
			results = prepared.executeQuery();
			if(results.next()) {
				total_rtard.setText(results.getString(1));
			}
		}catch(Exception e) {
			e.getMessage();
		}
		
		PreparedStatement prepareds;
		ResultSet resultss;
		
		String queryemprunt = "SELECT count(*) FROM emprunts where statut = 'emprunté' ";

		try {
			prepareds = ConnexionDB.getConnection().prepareStatement(queryemprunt);
			resultss = prepareds.executeQuery();
			if(resultss.next()) {
				total_emprunté.setText(resultss.getString(1));
			}
		}catch(Exception e) {
			e.getMessage();
		}
		
		JLabel lblNewLabel_6 = new JLabel("Quantié perdu");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6.setBounds(909, 172, 103, 14);
		panel_2.add(lblNewLabel_6);
		quantityField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char k= e.getKeyChar();
				if(!Character.isDigit(k)) {
					e.consume();
				}
			}
		});
		quantity_dispo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char k= e.getKeyChar();
				if(!Character.isDigit(k)) {
					e.consume();
				}
			}
		});
		qauntity_perdu.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char k= e.getKeyChar();
				if(!Character.isDigit(k)) {
					e.consume();
				}
			}
		});
		
		PreparedStatement ps;
		ResultSet rs;
		
		String quary = "SELECT * FROM auteur";
		JComboBox<String> select_auteur = new JComboBox<String>();
		select_auteur.setBackground(new Color(255, 255, 224));
		select_auteur.setBounds(145, 192, 203, 33);
		panel_2.add(select_auteur);
		
		try {
			ps = ConnexionDB.getConnection().prepareStatement(quary);
			rs = ps.executeQuery();
			while(rs.next()) {
				  select_auteur.addItem(rs.getString("nom")); 
			}
		}catch(Exception e) {
			e.getMessage();
		}
		
		tableLivre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mod = (DefaultTableModel) tableLivre.getModel();
				int ligne = tableLivre.getSelectedRow();
				ArrayList<Livre> newLivresList = new ArrayList<>();
                for(int i=0; i<mod.getRowCount(); i++) {
                	  String isbn = (String)mod.getValueAt(i, 0);
                	  String auteur = (String)mod.getValueAt(i, 1);
                	  String titre = (String)mod.getValueAt(i, 2);
                	  int quantite = (int)mod.getValueAt(i, 3);
                	  int quantiteDispo = (int)mod.getValueAt(i, 4);
                	  int quantitePerdu = (int)mod.getValueAt(i, 5);
                	  newLivresList.add(new Livre(isbn, auteur, titre, quantite,quantiteDispo,quantitePerdu));
                }
				if(ligne != -1) {
					isbnField.setText(newLivresList.get(ligne).getISBN());
					select_auteur.setSelectedItem(newLivresList.get(ligne).getAuteur());
					titreField.setText(newLivresList.get(ligne).getTitre());
					quantityField.setText(String.valueOf(newLivresList.get(ligne).getQtotal()));
					quantity_dispo.setText(String.valueOf(newLivresList.get(ligne).getQdisponible()));
					qauntity_perdu.setText(String.valueOf(newLivresList.get(ligne).getQperdus()));
				}
			}
		});
		
		JButton btnNewButton_1 = new JButton("Modifier Le livre");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mod = (DefaultTableModel) tableLivre.getModel();
			    int ligne = tableLivre.getSelectedRow();
				if(ligne != -1) {
					if(isbnField.getText().trim().equals("") || titreField.getText().trim().equals("") || quantityField.getText().trim().equals("") || quantity_dispo.getText().trim().equals("") || qauntity_perdu.getText().trim().equals("") ) {
	                	JOptionPane.showMessageDialog(null, "merci de remplire tous les champs avant de modifier","erreur de modification",JOptionPane.ERROR_MESSAGE);
					}else {
						ArrayList<Livre> newLivresList = new ArrayList<>();
						Livre l = new Livre(isbnField.getText().trim(),select_auteur.getSelectedItem().toString(),titreField.getText().trim(),Integer.parseInt(quantityField.getText()),Integer.parseInt(quantity_dispo.getText()),Integer.parseInt(qauntity_perdu.getText()));
		                mod = (DefaultTableModel) tableLivre.getModel();
		                for(int i=0; i<mod.getRowCount(); i++) {
		                	  String isbn = (String)mod.getValueAt(i, 0);
		                	  String auteur = (String)mod.getValueAt(i, 1);
		                	  String titre = (String)mod.getValueAt(i, 2);
		                	  int quantite = (int)mod.getValueAt(i, 3);
		                	  int quantitedispo = (int)mod.getValueAt(i, 4);
		                	  int quantiteperdu = (int)mod.getValueAt(i, 5);
		                	  newLivresList.add(new Livre(isbn, auteur, titre, quantite,quantitedispo,quantiteperdu)); 
		                }
			            l.modifierLivre(livres,newLivresList,mod,ligne,isbnField.getText().trim(),select_auteur.getSelectedItem().toString(),titreField.getText().trim(),Integer.parseInt(quantityField.getText()),Integer.parseInt(quantity_dispo.getText()),Integer.parseInt(qauntity_perdu.getText()));

					}
	            }else {
	                	JOptionPane.showMessageDialog(null, "selectioner une ligne pour le modifier","erreur de selection",JOptionPane.ERROR_MESSAGE);
	            }
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_1.setBackground(new Color(255, 215, 0));
		btnNewButton_1.setBounds(27, 263, 207, 49);
		panel_1.add(btnNewButton_1);
		
		if(userRole.equals("Emprunteur")) {
			btnNewButton_1.setVisible(false);
		}else {
			btnNewButton_1.setVisible(true);
		}
		JButton btnNewButton_2 = new JButton("Supprimer Le livre");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mod = (DefaultTableModel) tableLivre.getModel();
				int ligne = tableLivre.getSelectedRow();
				 if(ligne != -1) {
	                	int n = JOptionPane.showConfirmDialog(null, "vous voulez vraiment supprimer ce livre");
	                	if(n==0) {	
	                		Livre l = new Livre();
	                		ArrayList<Livre> newLivresList = new ArrayList<>();
			                for(int i=0; i<mod.getRowCount(); i++) {
			                	  String isbn = (String)mod.getValueAt(i, 0);
			                	  String auteur = (String)mod.getValueAt(i, 1);
			                	  String titre = (String)mod.getValueAt(i, 2);
			                	  int quantite = (int)mod.getValueAt(i, 3);
			                	  int quantitedispo = (int)mod.getValueAt(i, 4);
			                	  int quantiteperdu = (int)mod.getValueAt(i, 5);
			                	  newLivresList.add(new Livre(isbn, auteur, titre, quantite,quantitedispo,quantiteperdu));
			                }
	                		l.supprimerLivre(livres,newLivresList,mod,ligne);
	                	}             	
	                }else {
	                	JOptionPane.showMessageDialog(null, "selectioner une ligne pour le supprimer","erreur de selection",JOptionPane.ERROR_MESSAGE);
	                }	
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_2.setBackground(new Color(255, 215, 0));
		btnNewButton_2.setBounds(27, 338, 207, 49);
		panel_1.add(btnNewButton_2);
		if(userRole.equals("Emprunteur")) {
			btnNewButton_2.setVisible(false);
		}else {
			btnNewButton_2.setVisible(true);
		}
		 JTable tableEmprunts = new JTable();

		JButton btnNewButton_3 = new JButton("Emprunter le livre");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mod = (DefaultTableModel) tableLivre.getModel();
				mod1 = (DefaultTableModel) tableEmprunts.getModel();

				int ligne = tableLivre.getSelectedRow();
				
				 if(ligne != -1) {
	                		Livre l = new Livre();
	                		ArrayList<Livre> newLivresList = new ArrayList<>();
			                for(int i=0; i<mod.getRowCount(); i++) {
			                	  String isbn = (String)mod.getValueAt(i, 0);
			                	  String auteur = (String)mod.getValueAt(i, 1);
			                	  String titre = (String)mod.getValueAt(i, 2);
			                	  int quantite = (int)mod.getValueAt(i, 3);
			                	  int quantitedispo = (int)mod.getValueAt(i, 4);
			                	  int quantiteperdu = (int)mod.getValueAt(i, 5);
			                	  newLivresList.add(new Livre(isbn, auteur, titre, quantite,quantitedispo,quantiteperdu));
			                }
	                		l.emprunterLivre(livres,newLivresList,mod,mod1,ligne,user_id);
	                }else {
	                	JOptionPane.showMessageDialog(null, "selectioner un livre pour l'emprunté","erreur de selection",JOptionPane.ERROR_MESSAGE);
	                }	
			}
		});
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_3.setBackground(new Color(255, 215, 0));
		btnNewButton_3.setBounds(27, 420, 207, 49);
		panel_1.add(btnNewButton_3);
		
		if(userRole.equals("Emprunteur")) {
			btnNewButton_3.setVisible(true);
		}else {
			btnNewButton_3.setVisible(false);
		}
		
		auth_name = new JLabel("");
		auth_name.setFont(new Font("Tahoma", Font.BOLD, 13));
		auth_name.setBounds(25, 22, 345, 33);
		panel_2.add(auth_name);
		
	
		input_search = new JTextField();
		input_search.setBackground(new Color(253, 245, 230));
		input_search.setFont(new Font("Tahoma", Font.BOLD, 12));
		input_search.setBounds(11, 275, 261, 33);
		panel_2.add(input_search);
		input_search.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("chercher par Titre ou auteur");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(11, 250, 230, 14);
		panel_2.add(lblNewLabel);
		
		JButton search_button = new JButton("chercher");
		search_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Livre l = new Livre();
				mod = (DefaultTableModel) tableLivre.getModel();
				String search_name = input_search.getText();
				if(search_name.equals("")) {
					JOptionPane.showMessageDialog(null, "s'ils vous plait entrer un nom d'auteur ou un titre de livre","erreur de recherche",JOptionPane.ERROR_MESSAGE);
				}else {
	        		l.chercherLivre(livres,mod,search_name); 
				}
			}
		});
		search_button.setFont(new Font("Tahoma", Font.BOLD, 13));
		search_button.setBackground(new Color(50, 205, 50));
		search_button.setBounds(282, 275, 123, 33);
		panel_2.add(search_button);
		
		
		
		

		
		
		quantityField.setBackground(new Color(255, 255, 224));
		quantityField.setBounds(598, 192, 144, 33);
		panel_2.add(quantityField);
		
		
		
		
		
		JLabel lblNewLabel_1 = new JLabel("Code ISBN ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(11, 172, 81, 14);
		panel_2.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Auteur de Livre");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(144, 172, 95, 14);
		panel_2.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Titre de livre");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3.setBounds(357, 172, 95, 14);
		panel_2.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Quantité total");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_4.setBounds(598, 172, 104, 14);
		panel_2.add(lblNewLabel_4);
		
		JButton refresh = new JButton("rafraîchir le tableau");
		refresh.setBackground(new Color(255, 215, 0));
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mod = (DefaultTableModel) tableLivre.getModel();
				Livre l = new Livre();
				l.raifrichirTableau(livres,mod);
				
			}
		});
		refresh.setBounds(936, 307, 146, 33);
		panel_2.add(refresh);
		
		
		JLabel lblNewLabel_7 = new JLabel("Quantité total des livres");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_7.setBounds(28, 83, 180, 14);
		panel_2.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Quantité des livres disponibles");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_8.setBounds(210, 83, 209, 14);
		panel_2.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("Quantité des livres perdus");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_9.setBounds(429, 83, 209, 14);
		panel_2.add(lblNewLabel_9);
		
		JLabel lblNewLabel_12 = new JLabel("Quantité des livres en retard");
		lblNewLabel_12.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_12.setBounds(623, 83, 209, 14);
		panel_2.add(lblNewLabel_12);
		
		JLabel lblNewLabel_13 = new JLabel("Quantité des livres empruntés");
		lblNewLabel_13.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_13.setBounds(833, 83, 209, 14);
		panel_2.add(lblNewLabel_13);
		
		JLabel lblNewLabel_10 = new JLabel("La liste de tous les livres");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_10.setBounds(11, 319, 180, 14);
		panel_2.add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("La liste des livres empruntés");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_11.setBounds(12, 521, 216, 14);
		panel_2.add(lblNewLabel_11);
	
			
			
           tableEmprunts.setFont(new Font("Tahoma", Font.BOLD, 12));
           tableEmprunts.setBorder(UIManager.getBorder("Table.scrollPaneBorder"));
           tableEmprunts.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"ISBN", "nom d'auteur", "Titre de livre", "Quantit\u00E9 empruntés" ,"date d'emprunt","date de retoure","nom d'emprunteur","Téléphone","statut"
				}
			));
			
			
			
			JScrollPane scrollPane_1 = new JScrollPane(tableEmprunts);
			scrollPane_1.setBounds(12, 546, 1075, 147);
			panel_2.add(scrollPane_1);
			
			JButton btnNewButton_5 = new JButton("lister les livres disponibles");
			btnNewButton_5.setBackground(new Color(255, 215, 0));
			btnNewButton_5.setBounds(748, 307, 178, 33);
			panel_2.add(btnNewButton_5);
			
			JButton btnNewButton_6 = new JButton("Déconexion");
			btnNewButton_6.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					Login l = new Login();
					l.setVisible(true);
					l.setResizable(false);
					l.setLocationRelativeTo(null);
				}
			});
			btnNewButton_6.setBackground(new Color(255, 215, 0));
			btnNewButton_6.setBounds(968, 22, 114, 33);
			panel_2.add(btnNewButton_6);
			btnNewButton_5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mod = (DefaultTableModel) tableLivre.getModel();
					Livre l = new Livre();
					l.afficherLivreDisponible(mod);
				}

				
			});
			

			PreparedStatement pstemprunt ;
			ResultSet rstemprunt;
			String querySelectEmpruntadmin ="SELECT l.ISBN,l.nom_auteur,l.titre,(l.q_total - l.q_disponible - l.q_perdu) as quantité_emmprunté,e.date_emprunt,e.date_retoure,u.name,u.phone,e.statut  FROM livres l JOIN emprunts e on l.ISBN = e.ISBN_livre JOIN users u ON u.id = e.id_emprunteur ";
			String querySelectEmpruntUser ="SELECT l.ISBN,l.nom_auteur,l.titre,(l.q_total - l.q_disponible - l.q_perdu) as quantité_emmprunté,e.date_emprunt,e.date_retoure,u.name,u.phone,e.statut  FROM livres l JOIN emprunts e on l.ISBN = e.ISBN_livre JOIN users u ON u.id = e.id_emprunteur where u.id = ?";

			try {
				mod = (DefaultTableModel) tableEmprunts.getModel();

				if(userRole.equals("Emprunteur")) {
					pstemprunt =  ConnexionDB.getConnection().prepareStatement(querySelectEmpruntUser);
					pstemprunt.setInt(1, user_id);
					rstemprunt = pstemprunt.executeQuery();
					while(rstemprunt.next()) {
						mod.addRow(new Object[] {rstemprunt.getString("ISBN"),rstemprunt.getString("nom_auteur"),rstemprunt.getString("titre"),rstemprunt.getInt("quantité_emmprunté"),rstemprunt.getTimestamp("date_emprunt"),rstemprunt.getTimestamp("date_retoure"),rstemprunt.getString("name"),rstemprunt.getString("phone"),rstemprunt.getString("statut")});
					}
				}else {
					pstemprunt =  ConnexionDB.getConnection().prepareStatement(querySelectEmpruntadmin);
					rstemprunt = pstemprunt.executeQuery();
					while(rstemprunt.next()) {
						mod.addRow(new Object[] {rstemprunt.getString("ISBN"),rstemprunt.getString("nom_auteur"),rstemprunt.getString("titre"),rstemprunt.getInt("quantité_emmprunté"),rstemprunt.getTimestamp("date_emprunt"),rstemprunt.getTimestamp("date_retoure"),rstemprunt.getString("name"),rstemprunt.getString("phone"),rstemprunt.getString("statut")});
					}
				}
			}catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "imposible d'afficher la liste d'emprunts","erreur d'affichage",JOptionPane.ERROR_MESSAGE);
			}
			

		
		
		
		
		
		
		JButton btnNewButton = new JButton("Ajouter le livre");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isbnField.getText().trim().equals("") || titreField.getText().trim().equals("") || quantityField.getText().trim().equals("") || quantity_dispo.getText().trim().equals("") || qauntity_perdu.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "merci de remplire tous les champs","erreur de validation",JOptionPane.ERROR_MESSAGE);
				}else {
					Livre l = new Livre(isbnField.getText().trim(),select_auteur.getSelectedItem().toString(),titreField.getText().trim(),Integer.parseInt(quantityField.getText()),Integer.parseInt(quantity_dispo.getText()),Integer.parseInt(qauntity_perdu.getText()));
	                mod = (DefaultTableModel) tableLivre.getModel();
					l.ajouterLivre(l,mod,livres);
				}
			}

			
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setBackground(new Color(255, 215, 0));
		btnNewButton.setBounds(27, 185, 207, 49);
		panel_1.add(btnNewButton);
		
		
		
		if(userRole.equals("Emprunteur")) {
			btnNewButton.setVisible(false);
		}else {
			btnNewButton.setVisible(true);
		}
		
		
		JPanel panel5 = new JPanel(); 

		// Create the input fields
		JLabel label100 = new JLabel("First Name:");
		JTextField textField100 = new JTextField(10);

		JLabel label101 = new JLabel("Last Name:"); 
		JTextField textField101 = new JTextField(10);

		// Add the components to the panel 
		panel5.add(label100);
		panel5.add(textField100);
		panel5.add(label101); 
		panel5.add(textField101);
		
		
		JButton btnNewButton_4 = new JButton("Retourner un livre");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean exist=false;
				 String ISBNentered = JOptionPane.showInputDialog(null, "Veuillez entrer l'ISBN empruntée","ISBN",JOptionPane.QUESTION_MESSAGE);	
                 PreparedStatement ps;
                 ResultSet rs;
                 String querySelect = "SELECT * FROM emprunts e where e.ISBN_livre = ?";
                 try {
                	 ps =ConnexionDB.getConnection().prepareStatement(querySelect);
                	 ps.setString(1, ISBNentered);
                	 rs = ps.executeQuery();
                	 if(rs.next()) {
                		 exist = true;
                	 }
                 }catch(Exception es) {
                	 JOptionPane.showMessageDialog(null, "un erreur dans la requete","erreur",JOptionPane.ERROR_MESSAGE);
                 }
                 if(ISBNentered == null || !exist) {
                	 JOptionPane.showMessageDialog(null, "s'il vous plait entrer un ISBN d'un livre déja emprunté","erreur",JOptionPane.ERROR_MESSAGE);
                 }else {
                	 boolean in = false;
                	 String CIN = JOptionPane.showInputDialog(null, "Veuillez entrer votre CIN","ISBN",JOptionPane.QUESTION_MESSAGE);	
	                     PreparedStatement p;
	                     ResultSet r;
	                     String checkQuery ="SELECT * FROM emprunts e JOIN users u ON e.id_emprunteur = u.id WHERE u.cin = ?";
	                     try {
	                    	 p = ConnexionDB.getConnection().prepareStatement(checkQuery);
	                    	 p.setString(1, CIN);
	                    	 r = p.executeQuery();
	                    	 if(r.next()) {
	                    		 in = true;
	                    	 }
	                     }catch(Exception ex) {
	                    	 JOptionPane.showMessageDialog(null, "erreur dans LA requete","erreur",JOptionPane.ERROR_MESSAGE);
	                     }
	                 if(CIN.equals("")) {
                     JOptionPane.showMessageDialog(null, "invalid cin","erreur",JOptionPane.ERROR_MESSAGE);
                     }
                     if(!in) {
                    	 JOptionPane.showMessageDialog(null, "le cin que tu as entré ne correspondent a aucune emprunt","erreur",JOptionPane.ERROR_MESSAGE);
                     }else {
                    	 PreparedStatement pst;
	                     ResultSet rst;
	                     String checkISBNCIN ="SELECT * FROM emprunts e JOIN users u ON e.id_emprunteur = u.id WHERE u.cin = ? AND e.ISBN_livre = ?";
	                     try {
	                    	 pst = ConnexionDB.getConnection().prepareStatement(checkISBNCIN);
	                    	 pst.setString(1, CIN);
	                    	 pst.setString(2, ISBNentered);
	                    	 rst = pst.executeQuery();
	                    	 if(rst.next()) {
	                    		 PreparedStatement pstd;
	    	                     String deleteQuery ="DELETE FROM emprunts WHERE ISBN_livre = ? AND id_emprunteur =?";
	    	                     try {
	    	                    	 pstd = ConnexionDB.getConnection().prepareStatement(deleteQuery);
	    	                    	 pstd.setString(1, ISBNentered);
	    	                    	 pstd.setInt(1, user_id);
	    	                    	 pstd.execute();
	    	                    	 int ligne = tableLivre.getSelectedRow();
	    	                    	 mod.setValueAt(livres.get(ligne).getQdisponible() + rst.getInt(7), ligne, 4);
	    	                    	 JOptionPane.showMessageDialog(null, "Le livre a bien été retourné. Merci!","Retour effectué",JOptionPane.INFORMATION_MESSAGE);
	    	                     }catch(Exception ed) {
	    	                    	 JOptionPane.showMessageDialog(null, "errur dans votre requéte delete", "erreur", JOptionPane.ERROR_MESSAGE);
	    	                     }
	                    	 }else {
	                    		 JOptionPane.showMessageDialog(null,"vous n'aver pas emprunté ce livre","erreur",JOptionPane.ERROR_MESSAGE );
	                    	 }
	                     }catch(Exception ex) {
	                    	 JOptionPane.showMessageDialog(null, "erreur dans votre requete","erreur",JOptionPane.ERROR_MESSAGE);
	                     }
	                     
                     }
                 }
				}
		});
		btnNewButton_4.setBackground(new Color(255, 215, 0));
		btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_4.setBounds(27, 496, 207, 49);
		panel_1.add(btnNewButton_4);
		if(userRole.equals("Emprunteur")) {
			btnNewButton_4.setVisible(true);
		}else {
			btnNewButton_4.setVisible(false);
		}
    }
}
