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
    private JTextField quantity_dispo;
    private JTextField qauntity_perdu;
    
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
					Menu frame = new Menu();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 128, 128));
		panel_1.setBounds(0, 0, 258, 661);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		user_image = new JLabel("");
		user_image.setHorizontalAlignment(SwingConstants.CENTER);
		user_image.setBounds(70, 22, 118, 120);
		panel_1.add(user_image);
		
		user_role = new JLabel("role");
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
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setBounds(257, 0, 927, 661);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		// Create a JScrollPane and add the table to it
		JScrollPane scrollPane = new JScrollPane(tableLivre);

		// Add the scroll pane to your panel or frame
		scrollPane.setBounds(10, 292, 892, 358);
		panel_2.add(scrollPane);
		
		isbnField = new JTextField();
		isbnField.setBackground(new Color(255, 255, 224));
		isbnField.setBounds(10, 111, 123, 33);
		panel_2.add(isbnField);
		
		titreField = new JTextField();
		titreField.setBackground(new Color(255, 255, 224));
		titreField.setBounds(316, 111, 230, 33);
		panel_2.add(titreField);
		
		quantityField = new JTextField();
		JLabel lblNewLabel_5 = new JLabel("Quantité disponible");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_5.setBounds(668, 89, 124, 14);
		panel_2.add(lblNewLabel_5);
		
		quantity_dispo = new JTextField();
		quantity_dispo.setBackground(new Color(255, 255, 224));
		quantity_dispo.setBounds(668, 111, 128, 33);
		panel_2.add(quantity_dispo);
		quantity_dispo.setColumns(10);
		
		qauntity_perdu = new JTextField();
		qauntity_perdu.setBackground(new Color(255, 255, 224));
		qauntity_perdu.setBounds(812, 112, 101, 32);
		panel_2.add(qauntity_perdu);
		qauntity_perdu.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Quantié perdu");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6.setBounds(810, 89, 103, 14);
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
		
		
		PreparedStatement ps;
		ResultSet rs;
		
		String quary = "SELECT * FROM auteur";
		JComboBox<String> select_auteur = new JComboBox<String>();
		select_auteur.setBackground(new Color(255, 255, 224));
		select_auteur.setBounds(139, 111, 165, 33);
		panel_2.add(select_auteur);
		
		try {
			ps = ConnexionDB.getConnection().prepareStatement(quary);
			rs = ps.executeQuery();
			while(rs.next()) {
				  select_auteur.addItem(rs.getString("nom")); 
			}
		}catch(Exception e) {
			
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
					if(isbnField.getText().equals("") || titreField.getText().equals("") || quantityField.getText().equals("") || quantity_dispo.getText().equals("") || qauntity_perdu.getText().equals("") ) {
	                	JOptionPane.showMessageDialog(null, "merci de remplire tous les champs avant de modifier","erreur de modification",JOptionPane.ERROR_MESSAGE);
					}else {
						ArrayList<Livre> newLivresList = new ArrayList<>();
						Livre l = new Livre(isbnField.getText(),select_auteur.getSelectedItem().toString(),titreField.getText(),Integer.parseInt(quantityField.getText()),Integer.parseInt(quantity_dispo.getText()),Integer.parseInt(qauntity_perdu.getText()));
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
		                l.modifierLivre(livres,newLivresList,mod,ligne,isbnField.getText(),select_auteur.getSelectedItem().toString(),titreField.getText(),Integer.parseInt(quantityField.getText()),Integer.parseInt(quantity_dispo.getText()),Integer.parseInt(qauntity_perdu.getText()));
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
		if(user_role.getText().equals("Emprunteur")) {
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
		if(user_role.getText().equals("Emprunteur")) {
			btnNewButton_2.setVisible(false);
		}else {
			btnNewButton_2.setVisible(true);
		}
		JButton btnNewButton_3 = new JButton("Emprunter le livre");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_3.setBackground(new Color(255, 215, 0));
		btnNewButton_3.setBounds(27, 420, 207, 49);
		panel_1.add(btnNewButton_3);
		 
		
		
		auth_name = new JLabel("");
		auth_name.setFont(new Font("Tahoma", Font.BOLD, 13));
		auth_name.setBounds(25, 30, 345, 33);
		panel_2.add(auth_name);
		
		
		
		if(user_role.getText().equals("Emprunteur")) {
			btnNewButton_3.setVisible(true);
		}else {
			btnNewButton_3.setVisible(false);
		}
		input_search = new JTextField();
		input_search.setBackground(new Color(253, 245, 230));
		input_search.setFont(new Font("Tahoma", Font.BOLD, 12));
		input_search.setBounds(10, 243, 261, 33);
		panel_2.add(input_search);
		input_search.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("chercher par Titre ou auteur");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 218, 230, 14);
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
		search_button.setBounds(281, 243, 123, 33);
		panel_2.add(search_button);
		
		
		
		

		
		
		quantityField.setBackground(new Color(255, 255, 224));
		quantityField.setBounds(552, 111, 104, 33);
		panel_2.add(quantityField);
		
		
		
		
		
		JLabel lblNewLabel_1 = new JLabel("Code ISBN ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(10, 88, 81, 14);
		panel_2.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Auteur de Livre");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(149, 86, 95, 14);
		panel_2.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Titre de livre");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3.setBounds(349, 88, 95, 14);
		panel_2.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Quantité total");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_4.setBounds(552, 88, 104, 14);
		panel_2.add(lblNewLabel_4);
		
		JButton refresh = new JButton("rafraîchir le tableau");
		refresh.setBackground(new Color(255, 215, 0));
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Livre l = new Livre();
				l.raifrichirTableau(livres,mod);
				
			}
		});
		refresh.setBounds(749, 243, 146, 33);
		panel_2.add(refresh);
		
		
		
		
		JButton btnNewButton = new JButton("Ajouter le livre");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isbnField.getText().equals("") || titreField.getText().equals("") || quantityField.getText().equals("") || quantity_dispo.getText().equals("") || qauntity_perdu.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "merci de remplire tous les champs","erreur de validation",JOptionPane.ERROR_MESSAGE);
				}else {
					Livre l = new Livre(isbnField.getText(),select_auteur.getSelectedItem().toString(),titreField.getText(),Integer.parseInt(quantityField.getText()),Integer.parseInt(quantity_dispo.getText()),Integer.parseInt(qauntity_perdu.getText()));
	                mod = (DefaultTableModel) tableLivre.getModel();
					l.ajouterLivre(l,mod,livres);
				}
			}

			
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setBackground(new Color(255, 215, 0));
		btnNewButton.setBounds(27, 185, 207, 49);
		panel_1.add(btnNewButton);
		
		if(user_role.getText().equals("Emprunteur")) {
			btnNewButton.setVisible(false);
		}else {
			btnNewButton.setVisible(true);
		}
		
		
	}
}
