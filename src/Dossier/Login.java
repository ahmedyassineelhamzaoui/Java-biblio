package Dossier;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.swing.DropMode;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.JPasswordField;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField userCin;
	private JTextField btnregister;
	private JPasswordField passWord;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login login = new Login();
					login.setVisible(true);
					login.setResizable(false);
					login.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	private boolean checkFields() {    
	    String user = userCin.getText();
	    String pass = String.valueOf(passWord.getPassword());
	    return user.equals("") || pass.equals(""); 
    }
	public Login() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 128));
		panel.setBounds(0, 0, 434, 62);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel loginlabel = new JLabel("Login");
		loginlabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginlabel.setBackground(new Color(220, 20, 60));
		loginlabel.setForeground(new Color(255, 255, 255));
		loginlabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		loginlabel.setBounds(198, 20, 56, 26);
		loginlabel.setOpaque(true); 
		panel.add(loginlabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(0, 61, 444, 200);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("CIN");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(34, 42, 68, 14);
		panel_1.add(lblNewLabel);
		
		JLabel lblNewLabel_3 = new JLabel("PASSWORD");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3.setBounds(34, 80, 80, 14);
		panel_1.add(lblNewLabel_3);
		
		userCin = new JTextField();
		userCin.setBounds(165, 35, 194, 30);
		panel_1.add(userCin);
		userCin.setColumns(10);
		
		passWord = new JPasswordField();
		passWord.setBounds(165, 76, 194, 30);
		panel_1.add(passWord);
		
		JButton btnlogin = new JButton("Connexion");
		btnlogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkFields()) {
					JOptionPane.showMessageDialog(null,"merci d'entrer un cin et un mot de pass valid","information manquante\r\n"
							+ "",JOptionPane.ERROR_MESSAGE);
				}else {
					PreparedStatement ps;
					ResultSet rs;
					
					String query ="SELECT * FROM users.users WHERE cin=? and password=?";
					try {
						ps = ConnexionDB.getConnection().prepareStatement(query);
						ps.setString(1, userCin.getText());
						ps.setString(2,String.valueOf(passWord.getPassword()));
						rs = ps.executeQuery();
						if(rs.next()) {
							dispose();
							
							String role ="";
							if(Integer.parseInt(rs.getString("role")) == 2) {
								role ="Emprunteur";
							}else {
								role ="Bibliothécaire";
							}
								Menu menu = new Menu(role);
								menu.setVisible(true);
								menu.setLocationRelativeTo(null);
								menu.getAuthname().setText("Bienvenu "+rs.getString("name"));
								menu.getRole().setText(role);
								InputStream is = rs.getBinaryStream("image");
								if(is != null) {
								  BufferedImage image = ImageIO.read(is);  // ici l'objet image conteint l'image corespendant a l'image existe on db
								  ImageIcon icon = new ImageIcon(image);
								  menu.getImage().setIcon(icon);
								}	
						}else {
							JOptionPane.showMessageDialog(null,"cin ou mot de passe est incorrect","données incorrectes",JOptionPane.ERROR_MESSAGE);
						}
					}catch(Exception ex) {
						JOptionPane.showMessageDialog(null,"la connexion a échoué\r\n","info",JOptionPane.ERROR_MESSAGE);
					}
			   }
			}
		});
		
		btnlogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnlogin.setBackground(Color.BLACK);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnlogin.setBackground(new Color(32,178,170));
			}
		});
		btnlogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnlogin.setBackground(new Color(32, 178, 170));
		btnlogin.setForeground(new Color(230, 230, 250));
		btnlogin.setBounds(165, 114, 194, 44);
		panel_1.add(btnlogin);
		
		btnregister = new JTextField();
		btnregister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		        dispose();
				Register register = new Register();
				register.setVisible(true);
				register.setLocationRelativeTo(null);
			}
		});
		btnregister.setEditable(false);
		btnregister.setForeground(new Color(0, 0, 128));
		btnregister.setText(">>je n'est pas de compte créer un");
		btnregister.setBounds(45, 169, 178, 20);
		panel_1.add(btnregister);
		btnregister.setColumns(10);
		
		
	}
}
