package model;

import connexion.DAOAcces;
import connexion.DAOAccesOld;
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
    public static boolean reqVerifierUserExiste(String usernameP) {
        String reqSQL = "SELECT id, username FROM polysio WHERE username = ?";
        try (PreparedStatement pst = DAOAcces.getConnexion().prepareStatement(reqSQL)) {
                pst.setString(1, usernameP);
                try (ResultSet rs = pst.executeQuery()) {
                        return rs.next(); // Si resultat alors il existe
                }
        } catch (SQLException erreur) {
            System.err.println("Erreur SQL lors du checkUser : " + erreur.getMessage());
            erreur.printStackTrace();
        }
        return false; // L'UTILISATEUR N EXISTE PAS = FALSE (VALEUR PAR DEFAULT PRESUME)
    }
    // CONNECTER UN UTILISATEUR
    public static ModelUser connexionUtilisateur(String usernameP, String passwordP) {
        String reqSQL = "SELECT * FROM polysio WHERE username = ?";
        try (PreparedStatement pst = DAOAcces.getConnexion().prepareStatement(reqSQL)) {
            pst.setString(1, usernameP);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    // Utilisateur Existe en base de données
                    //On verifie ensuite le password en BDD et celui donné par l utilisateur
                    String hashedPasswordBDD = rs.getString("password");
                    if (Securite.verifyPassword(passwordP, hashedPasswordBDD)) {
                        //On utilise le constructeur de PROFIL pour ne pas renvoyer mot de pass pour l'instant
                        return new ModelUser(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("email"),
                                ModelUserRole.valueOf(rs.getString("role").toUpperCase())
                        );
                    }
                }
            }
        } catch (SQLException erreur) {
            // Un seul CATCH : erreurs SQL
            System.err.println("Erreur SQL Impossible d'authentifier l'utilisateur : " + erreur.getMessage());
            erreur.printStackTrace();
        }
        return null; // Aucun utilisateur en Base de données ou erreur
    }




    // CONNECTER UN UTILISATEUR OLD VERSION SANS HASHING
    // todo: A supprimer plus tard, obsolete avec hashing
    /*
    public static ModelUser connexionUtilisateur(String usernameP, String passwordP) {
        String reqSQL = "SELECT * FROM polysio WHERE username = ? AND password = ?";
        try (PreparedStatement pst = DAOAcces.getConnexion().prepareStatement(reqSQL)) {
            pst.setString(1, usernameP);
            pst.setString(2,passwordP);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    // Utilisateur Existe en base de données
                    //On utilise le constructeur de PROFIL pour ne pas renvoyer mot de pass pour l'instant
                    return new ModelUser(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            ModelUserRole.valueOf(rs.getString("role").toUpperCase())
                    );
                }
            }
        } catch (SQLException erreur) {
            // Un seul CATCH : erreurs SQL
            System.err.println("Erreur SQL Impossible d'authentifier l'utilisateur : " + erreur.getMessage());
            erreur.printStackTrace();
        }
        return null; // Aucun utilisateur en Base de données ou erreur
    }
*/

    // RECUPERER UTILISATEUR EN BDD : RETURN UN OBJET DE TYPE MODELUSER :
    public static ModelUser reqRecupererUser(int idP) {
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
    public static Boolean insererUser(String usernameP, String passwordP, String emailP)  {
        String reqSQL = "INSERT INTO polysio (username, password, email) VALUES (?,?,?)";
        try (PreparedStatement pst = DAOAcces.getConnexion().prepareStatement(reqSQL)) {
            pst.setString(1, usernameP); // Ici 1 = (1er "?") et pas l index de colonne BDD 1 ( id )
            pst.setString(2, passwordP);
            pst.setString(3, emailP);
            int lignesAffectees = pst.executeUpdate();
            return lignesAffectees > 0;
        } catch (SQLException erreur) {
            System.err.println("Erreur SQL Impossible de Créer l'Utilisateur en BDD : " + erreur.getMessage());
            erreur.printStackTrace();
            return false;
        }
    }

    // DELETE UTILISATEUR
    public static Boolean deleteUSer(int idP) {
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
