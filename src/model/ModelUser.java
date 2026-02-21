package model;

import connexion.DAOAcces;
import connexion.DAOAccesOld;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelUser {
    private int id;
    private String password;
    private String username;
    private String email;
    private ModelUserRole role = ModelUserRole.SPECTATEUR; // Je définis la valeur par défaut du role ICI

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
        this.email = email;
        this.role = roleP;
    }

    // REQUETES SQL
    // VERIFIER EXISTENCE UTILISATEUR
    public boolean reqVerifierUserExiste(int idP) {
        String reqSQL = "SELECT id, username FROM polysio WHERE id = ?";
        try (PreparedStatement pst = DAOAcces.getConnexion().prepareStatement(reqSQL)) {
                pst.setInt(1, idP);
                try (ResultSet rs = pst.executeQuery()) {
                        return rs.next(); // Si resultat alors il existe
                }
        } catch (SQLException erreur) {
            System.err.println("Erreur SQL lors du checkUser : " + erreur.getMessage());
            erreur.printStackTrace();
        }
        return false; // L'UTILISATEUR N EXISTE PAS = FALSE (VALEUR PAR DEFAULT PRESUME)
    }

    // RECUPERER UTILISATEUR EN BDD : RETURN UN OBJET DE TYPE MODELUSER :
    public ModelUser reqRecupererUser(int idP) {
        String reqSQL = "SELECT * FROM polysio WHERE id = ?";

        try (PreparedStatement pst = DAOAcces.getConnexion().prepareStatement(reqSQL)) {
                pst.setInt(1, idP);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        int id = rs.getInt("id");
                        String username = rs.getString("username");
                        String email = rs.getString("email");
                        ModelUserRole roleBDD; // On la déclare et on affecte la valeur suivant le resultat du try catch
                        try {
                            roleBDD = ModelUserRole.valueOf(rs.getString("role").toUpperCase());
                        } catch (Exception e) {
                            roleBDD = ModelUserRole.SPECTATEUR; // Valeur par défaut si erreur
                        }
                        return new ModelUser(id, username, email, roleBDD);
                    }
                }
        } catch (SQLException erreur) {
            // Un seul CATCH : erreurs SQL
            System.err.println("Erreur SQL Impossible de Récupérer le Profil Utilisateur en BDD : " + erreur.getMessage());
            erreur.printStackTrace();
        }
        return null;
    }


    // INSERTION UTILISATEUR EN BASE DE DONNEES
    public Boolean insererUser(String usernameP, String passwordP, String emailP)  {
        String reqSQL = "INSERT INTO polysio (username, password, email) VALUES (?,?,?)";
        try (PreparedStatement pst = DAOAcces.getConnexion().prepareStatement(reqSQL)) {
            pst.setString(2, usernameP);
            pst.setString(2, passwordP);
            pst.setString(2, emailP);
            int lignesAffectees = pst.executeUpdate();
            return lignesAffectees > 0;
        } catch (SQLException erreur) {
            System.err.println("Erreur SQL Impossible de Créer l'Utilisateur en BDD : " + erreur.getMessage());
            erreur.printStackTrace();
            return false;
        }
    }

    // DELETE UTILISATEUR
    public Boolean deleteUSer(int idP) {
        String reqSQL = "DELETE FROM polysio WHERE id = ?";
        try (PreparedStatement pst = DAOAcces.getConnexion().prepareStatement(reqSQL)) {
            pst.setInt(1, idP);
            int lignesAffectees = pst.executeUpdate();
            return lignesAffectees > 0;
        } catch (SQLException erreur) {
            System.err.println("Erreur SQL Impossible de Créer l'Utilisateur en BDD : " + erreur.getMessage());
            erreur.printStackTrace();
            return false;
        }
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
