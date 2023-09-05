package Dossier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;


public class ConnexionDB {

	// on va utiliser une methode static pour appeler seulment la class et n'est pas créer un objet(instancié la class toujour)
	public static Connection  getConnection()
	{
		
		Connection c = null;
		try {
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "eRROR404@");
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null,"connection failed");
		}
		return c;
	}
}
