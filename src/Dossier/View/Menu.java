package Dossier.View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Dossier.Controller.AuteurController;
import Dossier.Controller.LivreController;
import Dossier.Model.Entities.Livre;


import javax.swing.JComboBox;
import java.awt.event.ActionListener;

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


	public Menu() {
    }
		
    public Menu(String role,int user_id) {
    	
		this.userRole = role;

		// table de livres
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
		//fin
		// table des livre emprutés
    	JTable tableEmprunts = new JTable();
		mod1 = (DefaultTableModel) tableEmprunts.getModel();
		tableEmprunts.setFont(new Font("Tahoma", Font.BOLD, 12));
        tableEmprunts.setBorder(UIManager.getBorder("Table.scrollPaneBorder"));
        tableEmprunts.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"ISBN", "nom d'auteur", "Titre de livre", "Quantit\u00E9 empruntés" ,"date d'emprunt","date de retoure","nom d'emprunteur","Téléphone","statut"
				}
		));
        //fin
		
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
		
		
		// affichage des livres empruntés
		mod1 = (DefaultTableModel) tableEmprunts.getModel();
		LivreController.listerLivreEmprunter(role,user_id,mod1);
		// fin
		
		// affichage de tous les livres
		mod = (DefaultTableModel) tableLivre.getModel();
		LivreController.listerTousLivres(mod);
		// fin 
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(245, 255, 250));
		panel_2.setBounds(257, 0, 1097, 711);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		JScrollPane scrollPane = new JScrollPane(tableLivre);
		scrollPane.setBounds(12, 344, 1075, 166);
		panel_2.add(scrollPane);
		

		
		
		// les champs de l'application 
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
		
		// fin des champs
		

		
		
		JLabel lblNewLabel_6 = new JLabel("Quantié perdu");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6.setBounds(909, 172, 103, 14);
		panel_2.add(lblNewLabel_6);
		quantityField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				LivreController.preventString(e);
			}
		});
		quantity_dispo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				LivreController.preventString(e);
			}
		});
		qauntity_perdu.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				LivreController.preventString(e);
			}
		});
		
		// affichage de tous les auteur
		JComboBox<String> select_auteur = new JComboBox<String>();
		select_auteur.setBackground(new Color(255, 255, 224));
		select_auteur.setBounds(145, 192, 203, 33);	
		ArrayList<String> auteur = new ArrayList<>();
		auteur = AuteurController.afficherTousAutuer();
		for(String a:auteur) {
			select_auteur.addItem(a); 
		}
		panel_2.add(select_auteur);
        // fin

		// selectioner une ligne
		tableLivre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mod = (DefaultTableModel) tableLivre.getModel();
				int ligne = tableLivre.getSelectedRow();
				if(ligne != -1) {
					isbnField.setText((String)mod.getValueAt(ligne, 0));
					select_auteur.setSelectedItem((String)mod.getValueAt(ligne, 1));
					titreField.setText((String)mod.getValueAt(ligne, 2));
					quantityField.setText( mod.getValueAt(ligne, 3).toString());
					quantity_dispo.setText( mod.getValueAt(ligne, 4).toString());
					qauntity_perdu.setText( mod.getValueAt(ligne, 5).toString());
				}
			}
		});
		//fin
		// ajouter livre
		JButton btnNewButton = new JButton("Ajouter le livre");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 int id = AuteurController.trouverIDAuteur(select_auteur.getSelectedItem().toString());
				 LivreController.ajouterLivre(mod,isbnField.getText(),titreField.getText(),id,quantityField.getText(),quantity_dispo.getText(),qauntity_perdu.getText());
			}

			
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setBackground(new Color(255, 215, 0));
		btnNewButton.setBounds(27, 185, 207, 49);
		panel_1.add(btnNewButton);
		// fin
		// suprimer un livre
		JButton btnNewButton_2 = new JButton("Supprimer Le livre");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ligne = tableLivre.getSelectedRow();
				LivreController.supprimerLivre(mod,ligne);
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_2.setBackground(new Color(255, 215, 0));
		btnNewButton_2.setBounds(27, 338, 207, 49);
		panel_1.add(btnNewButton_2);
		// fin
		// modfier un livre
		JButton btnNewButton_1 = new JButton("Modifier Le livre");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			   int ligne = tableLivre.getSelectedRow(); 
               LivreController.modifierLivvre(mod,ligne,isbnField.getText(),select_auteur.getSelectedItem().toString(),titreField.getText(),quantityField.getText(),quantity_dispo.getText(),qauntity_perdu.getText());
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_1.setBackground(new Color(255, 215, 0));
		btnNewButton_1.setBounds(27, 263, 207, 49);
		panel_1.add(btnNewButton_1);
		// fin

		JButton btnNewButton_3 = new JButton("Emprunter le livre");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ligne = tableLivre.getSelectedRow();
				LivreController.emprunterLivre(mod,mod1,ligne,user_id);
			}
		});
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_3.setBackground(new Color(255, 215, 0));
		btnNewButton_3.setBounds(27, 420, 207, 49);
		panel_1.add(btnNewButton_3);
		
		
		
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
				String search_name = input_search.getText();
				LivreController.chercherLivre(mod,search_name);
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
				LivreController.raifrichirTableau(mod);				
			}
		});
		refresh.setBounds(936, 307, 146, 33);
		panel_2.add(refresh);
		
		
		
		
		
	
			
			
           
			
			
			
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
					LivreController.listerLivreDisponible(mod);
				}

				
			});
			

			
			

		
		
		
		
		
		
		
		
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
			   LivreController.retournerLivre(mod,mod1,user_id);
			}
		});
		btnNewButton_4.setBackground(new Color(255, 215, 0));
		btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_4.setBounds(27, 496, 207, 49);
		panel_1.add(btnNewButton_4);

		if(userRole.equals("Emprunteur")) {
			btnNewButton_3.setVisible(true);
			btnNewButton.setVisible(false);
			btnNewButton_1.setVisible(false);
			btnNewButton_2.setVisible(false);
			btnNewButton_4.setVisible(true);
		}else {
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
			// statistiques
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
			
			ArrayList<Integer> statistiques = new ArrayList<>();
			statistiques = LivreController.AfficherStatistiques();
			
			total_statistique.setText(String.valueOf(statistiques.get(0)));
			total_disponible.setText(String.valueOf(statistiques.get(1)));
			total_perdu.setText(String.valueOf(statistiques.get(2)));
			total_emprunté.setText(String.valueOf(statistiques.get(3)));
			total_rtard.setText(String.valueOf(statistiques.get(4)));

			// fin Statistiques
			btnNewButton_3.setVisible(false);
			btnNewButton_1.setVisible(true);
			btnNewButton_2.setVisible(true);
			btnNewButton.setVisible(true);
			btnNewButton_4.setVisible(false);
		}
		JButton btnNewButton_7 = new JButton("Ajouter un auteur");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AuteurController.addAuteur(select_auteur);
			}
		});
		btnNewButton_7.setBackground(new Color(255, 215, 0));
		btnNewButton_7.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_7.setBounds(27, 496, 207, 49);
		panel_1.add(btnNewButton_7);

    }
	
}
