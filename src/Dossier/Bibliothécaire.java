package Dossier;

import com.mysql.cj.jdbc.Blob;

public class Bibliothécaire extends Utilisateur {

	public Bibliothécaire(String nom,String cin,String password,int role,String phone,String mail,String gender,Blob image) {
		super(nom,cin,password,role,phone,mail,gender,image);
	}
}
