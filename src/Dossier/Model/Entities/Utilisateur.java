package Dossier.Model.Entities;

import com.mysql.cj.jdbc.Blob;

public class Utilisateur {

	protected String nom,cin,password,phone,mail,gender;
	protected static int id;
	protected int role;
	protected Blob image;

	public Utilisateur(String nom,String cin,String password,int role,String phone,String mail,String gender,Blob image) {
		this.cin=cin;
		this.nom = nom;
		this.password = password;
		this.role = role;
		this.phone = phone;
		this.mail =  mail;
		this.gender = gender;
		if(image != null) {
			this.image = image;
		}
		id++;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin= cin;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone= phone;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail =mail;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Blob getImage() {
		return image;
	}
	public void setImage(Blob image)
	{
		this.image = image;
	}
	public int getId() {
		return id;
	}
}
