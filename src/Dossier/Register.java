package Dossier;

import java.sql.*;
import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSlider;
import javax.swing.JSeparator;
import java.awt.Canvas;
import javax.swing.JPasswordField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Register extends JFrame {

	private JPanel contentPane;
	private JTextField naMe;
	private JTextField userCin;
	private JTextField phone;
	private JTextField email;
	private JTextField goLogin;
	private JPasswordField passWord;
	private JPasswordField confirmPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
    private boolean checkFeilds()
    {
    	  return   naMe.getText().equals("") || userCin.getText().equals("") || String.valueOf(passWord.getPassword()).equals("") || String.valueOf(confirmPassword.getPassword()).equals("") || phone.getText().equals("") || email.getText().equals("");
    }
    private boolean passwordMatch(String p1,String p2)
    {
         return  p1.equals(p2) ;
    }
	/**
	 * Create the frame.
	 */
	public Register() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		ButtonGroup buttonGroup = new ButtonGroup();
        
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 128));
		panel.setBounds(0, 0, 434, 53);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Register");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBackground(new Color(220, 20, 60));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(190, 11, 74, 31);
		lblNewLabel.setOpaque(true); 
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 53, 434, 508);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setForeground(new Color(0, 139, 139));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(22, 30, 46, 14);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Cin");
		lblNewLabel_2.setForeground(new Color(0, 139, 139));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_2.setBounds(22, 69, 74, 14);
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Password");
		lblNewLabel_3.setForeground(new Color(0, 139, 139));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3.setBounds(22, 113, 74, 14);
		panel_1.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Confirm password");
		lblNewLabel_4.setForeground(new Color(0, 139, 139));
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_4.setBounds(22, 148, 125, 14);
		panel_1.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Phone");
		lblNewLabel_5.setForeground(new Color(0, 139, 139));
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_5.setBounds(22, 189, 46, 14);
		panel_1.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("E-Mail");
		lblNewLabel_6.setForeground(new Color(0, 139, 139));
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_6.setBounds(22, 225, 46, 14);
		panel_1.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Gender");
		lblNewLabel_7.setForeground(new Color(0, 139, 139));
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_7.setBounds(22, 269, 46, 14);
		panel_1.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Image");
		lblNewLabel_8.setForeground(new Color(0, 139, 139));
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_8.setBounds(22, 307, 46, 23);
		panel_1.add(lblNewLabel_8);
		
		naMe = new JTextField();
		naMe.setBounds(160, 25, 242, 28);
		panel_1.add(naMe);
		naMe.setColumns(10);
		
		userCin = new JTextField();
		userCin.setBounds(160, 64, 242, 31);
		panel_1.add(userCin);
		userCin.setColumns(10);
		
		passWord = new JPasswordField();
		passWord.setBounds(160, 106, 242, 28);
		panel_1.add(passWord);
		
		confirmPassword = new JPasswordField();
		confirmPassword.setBounds(160, 141, 242, 31);
		panel_1.add(confirmPassword);
		
		phone = new JTextField();
		phone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char k = e.getKeyChar(); // dans k en stock la clé entré par l'utilisateur
				if(!Character.isDigit(k) || phone.getText().length()>9) {
					e.consume(); // la methode consume est skipé les cartére déferenet de digit
				}
			}
		});
		phone.setBounds(160, 182, 242, 31);
		panel_1.add(phone);
		phone.setColumns(10);
		
		email = new JTextField();
		email.setBounds(160, 219, 242, 31);
		panel_1.add(email);
		email.setColumns(10);
		
		JRadioButton radio1 = new JRadioButton("Homme");
		radio1.setFont(new Font("Tahoma", Font.BOLD, 13));
		radio1.setBounds(160, 266, 109, 23);
		panel_1.add(radio1);
		buttonGroup.add(radio1);

		JRadioButton radio2 = new JRadioButton("Femme");
		radio2.setFont(new Font("Tahoma", Font.BOLD, 13));
		radio2.setBounds(271, 266, 109, 23);
		panel_1.add(radio2);
		buttonGroup.add(radio2);
		JLabel imageResult = new JLabel();
		imageResult.setBounds(22, 337, 402, 36);
		panel_1.add(imageResult);
		JButton chosePicture = new JButton("Séléctioner un fichier");
		chosePicture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jf = new JFileChooser("./src/images");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("images","jpeg","png","jpg");
				jf.addChoosableFileFilter(filter);
				jf.setDialogTitle("choisir une image");
				int result  = jf.showDialog(null,"ok");
				
				// on utilise pour séléctioner un seul fichier et n'est pas un dossier
				// jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
				// on utilise ça pour séléctioner plusieur fichier ou dossier
				// jf.setMultiSelectionEnabled(true);
				
				if(result == JFileChooser.APPROVE_OPTION) {
					File selectedImage = jf.getSelectedFile();  // creéer un objet pour stocker l'image séléctioner
					String c = selectedImage.getAbsolutePath(); // stocké le chemin de l'image dans une variable c
					// ImageIcon icon = new ImageIcon(c);
					imageResult.setText(c);
					// imageResult.setSize(width, height);
					// imageResult.setIcon(icon);
					
				}
			}
		});
		chosePicture.setBounds(165, 308, 153, 23);
		panel_1.add(chosePicture);
		
		JButton registerButton = new JButton("Créer mon compte");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(checkFeilds()) {
					JOptionPane.showMessageDialog(null, "merci de remplire tous les champs ","message d'erreur",JOptionPane.ERROR_MESSAGE);
				}else {
					String name = naMe.getText();
					String cin  = userCin.getText();
					String mail = email.getText();
					String password = String.valueOf(passWord.getPassword());
					String confirmpassword = String.valueOf(confirmPassword.getPassword());
					String tele     = phone.getText();
					String gender   = "Homme";
					if(!radio1.isSelected()) gender   = "Femme";
					if(!passwordMatch(password,confirmpassword)) {
						JOptionPane.showMessageDialog(null,"les deux mot de pass ne sont pas identiques","mot de passe erreur",JOptionPane.ERROR_MESSAGE);
					}
					PreparedStatement ps;
					ResultSet rs;
					String Query ="Select * From users.users where cin=? or phone = ? or mail =?";
					try {
						ps = ConnexionDB.getConnection().prepareStatement(Query);
						ps.setString(1, cin);
						ps.setString(2, tele);
						ps.setString(3, mail);
                        rs = ps.executeQuery();
                        if(rs.next()) {
                        	JOptionPane.showMessageDialog(null,"le cin ou le téléphone ou l'email que tu as entrée est déja existe","infos exists",JOptionPane.ERROR_MESSAGE);
                        }else {
                        	PreparedStatement p;
        					ResultSet r;
        					String QueryInsert ="INSERT INTO users.users(name,cin,password,role,phone,mail,gender,image) VALUES(?,?,?,?,?,?,?,?)";
        					try {
        						p = ConnexionDB.getConnection().prepareStatement(QueryInsert);
            					p.setString(1, name);
            					p.setString(2, cin);
            					p.setString(3, password);
            					p.setString(4, "2");
            					p.setString(5, tele);
            					p.setString(6, mail);
            					p.setString(7, gender);
            					try {
                               	 FileInputStream image = new FileInputStream(new File(imageResult.getText())); // ici image contient l'image corespendant a la chemin de l'image séléctioné
                               	 p.setBlob(8, image);
                                }catch(Exception exc) {
                                    JOptionPane.showMessageDialog(null, "imposible de stocké cette image","erreur d'insertion d'image",JOptionPane.ERROR_MESSAGE);
                                }
            					if(p.executeUpdate()!=0) {
            						JOptionPane.showMessageDialog(null, "votre compte a été créer avec succés","succés",JOptionPane.PLAIN_MESSAGE);
            						dispose();
            						Login login = new Login();
            						login.setVisible(true);
            						login.setLocationRelativeTo(null);
            					}
        					}catch(SQLException ex) {
                                 JOptionPane.showMessageDialog(null, "imposible d'inserer ces donnes","erreur d'insertion",JOptionPane.ERROR_MESSAGE);
        					}
                        }
					}catch(SQLException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),"erreur de connexion",JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
		});
		registerButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				registerButton.setBackground(new Color(0,128,128));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				registerButton.setBackground(new Color(139,0,139));
			}
		});
		registerButton.setForeground(new Color(211, 211, 211));
		registerButton.setBackground(new Color(139, 0, 139));
		registerButton.setBounds(148, 395, 158, 28);
		panel_1.add(registerButton);
		
		goLogin = new JTextField();
		goLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				Login login = new Login();
				login.setVisible(true);
				login.setLocationRelativeTo(null);
			}
		});
		goLogin.setForeground(new Color(0, 139, 139));
		goLogin.setEditable(false);
		goLogin.setHorizontalAlignment(SwingConstants.CENTER);
		goLogin.setText(">> Déja j'ai un compte se connecter");
		goLogin.setBounds(128, 444, 203, 20);
		panel_1.add(goLogin);
		goLogin.setColumns(10);
		
		
		
		
		
		
		
		
		
		
	}
}
