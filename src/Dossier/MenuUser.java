package Dossier;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MenuUser extends JFrame {

	private JPanel contentPane;
	private JLabel auth_name;
    private JLabel user_image;
    
    public JLabel getAuthname() {
    	return auth_name;
    }
    public JLabel getImage() {
    	return user_image;
    }
	
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuUser frame = new MenuUser();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
    
	/**
	 * Create the frame.
	 */
	public MenuUser() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 128));
		panel.setBounds(0, 0, 283, 561);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel user_image = new JLabel("");
		user_image.setHorizontalAlignment(SwingConstants.CENTER);
		user_image.setBounds(75, 38, 137, 149);
		panel.add(user_image);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(280, 0, 704, 561);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel auth_name = new JLabel("");
		auth_name.setHorizontalAlignment(SwingConstants.CENTER);
		auth_name.setBounds(25, 29, 286, 34);
		panel_1.add(auth_name);
	}
}
