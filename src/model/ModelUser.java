package model;

import connexion.DAOAcces;
import service.Securite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelUser {
    private int id;
    private String password;
    private String username;
    private String email;
    private String profilepicture;
    private ModelUserRole role = ModelUserRole.SPECTATEUR; // Je définis la valeur par défaut du role ICI
	public Object setProfilepicture;
    
    //CONSTRUCTEUR INSCRIPTION
    public ModelUser(String usernameP,String passwordP, String emailP, ModelUserRole roleP) {
        this.username = usernameP;
        this.password = passwordP;
        this.email = emailP;
        this.role = roleP;
    }
    // CONSTRUCTEUR PROFIL
    // Java différencie grâce au "int id" au début
    public ModelUser(int id, String username, String email, ModelUserRole roleP) {
        this.id = id;
        this.username = username;
        this.profilepicture = "/images/avatars/avatar.jpg";
        this.email = email;
        this.role = roleP;
    }



    //GETTERS SETTERS
    public ModelUser setId(int id) {
        this.id = id;
        return this;
    }
    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public ModelUser setPassword(String password) {
        this.password = password;
        return this;
    }
    public String getUsername() {
        return username;
    }

    public ModelUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getProfilepicture() {
		return profilepicture;
	}
	public void setProfilepicture(String profilepicture) {
		this.profilepicture = profilepicture;
	}
	public String getEmail() {
        return email;
    }

    public ModelUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public ModelUserRole getRole() {
        return role;
    }
    public ModelUser setModelUserRole(ModelUserRole roleP) {
        this.role = roleP;
        return this;
    }
}
