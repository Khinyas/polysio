package connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.ModelUser;
import model.ModelUserRole;
import service.Securite;

public class DAOUser {
    // REQUETES SQL
    // VERIFIER EXISTENCE UTILISATEUR
    public static boolean reqVerifierUserExiste(String usernameP) {
        String reqSQL = "SELECT id_utilisateur, pseudo FROM polysio.utilisateur WHERE pseudo = ?";
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
        String reqSQL = "SELECT * FROM polysio.utilisateur WHERE pseudo = ?";
        try (PreparedStatement pst = DAOAcces.getConnexion().prepareStatement(reqSQL)) {
            pst.setString(1, usernameP);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    // Utilisateur Existe en base de données
                    //On verifie ensuite le password en BDD et celui donné par l utilisateur
                    String hashedPasswordBDD = rs.getString("mot_de_passe");
                    if (Securite.verifyPassword(passwordP, hashedPasswordBDD)) {
                        //On utilise le constructeur de PROFIL pour ne pas renvoyer mot de pass pour l'instant
                        return new ModelUser(
                                rs.getInt("id_utilisateur"),
                                rs.getString("pseudo"),
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
    // RECUPERER UTILISATEUR EN BDD : RETURN UN OBJET DE TYPE MODELUSER :
    public static ModelUser reqRecupererUser(int idP) {
        String reqSQL = "SELECT * FROM polysio.utilisateur WHERE id_utilisateur = ?";

        try (PreparedStatement pst = DAOAcces.getConnexion().prepareStatement(reqSQL)) {
            pst.setInt(1, idP);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_utilisateur");
                    String username = rs.getString("pseudo");
                    String email = rs.getString("email");
                    ModelUserRole roleBDD; // On la déclare et on affecte la valeur suivant le resultat du try catch
                    try {
                        roleBDD = ModelUserRole.valueOf(rs.getString("role").toUpperCase());
                    } catch (Exception e) {
                        roleBDD = ModelUserRole.UTILISATEUR; // Valeur par défaut si erreur
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
        String reqSQL = "INSERT INTO polysio.utilisateur (pseudo, mot_de_passe, email) VALUES (?,?,?)";
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
        String reqSQL = "DELETE FROM polysio.utilisateur WHERE id_utilisateur = ?";
        try (PreparedStatement pst = DAOAcces.getConnexion().prepareStatement(reqSQL)) {
            pst.setInt(1, idP);
            int lignesAffectees = pst.executeUpdate();
            return lignesAffectees > 0;
        } catch (SQLException erreur) {
            System.err.println("Erreur SQL Impossible de Supprimer l'Utilisateur en BDD : " + erreur.getMessage());
            erreur.printStackTrace();
            return false;
        }}
        
       // ADMIN : LISTE UTILISATEUR 
        
        public static ArrayList<ModelUser> reqListeTousLesUtilisateurs() {
            ArrayList<ModelUser> liste = new ArrayList<>();
            String reqSQL = "SELECT * FROM polysio.utilisateur";
            try (PreparedStatement pst = DAOAcces.getConnexion().prepareStatement(reqSQL);
                 ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    liste.add(new ModelUser(
                        rs.getInt("id_utilisateur"),
                        rs.getString("pseudo"),
                        rs.getString("email"),
                        ModelUserRole.valueOf(rs.getString("role").toUpperCase())
                    ));
                }
            } catch (java.sql.SQLException erreur) {
                erreur.printStackTrace();
            }
            return liste;
        }
     // Dans DAOUser.java
        public void updateUsername(int id, String nouveauNom) {
            String query = "UPDATE utilisateur SET pseudo = ? WHERE id_utilisateur = ?"; // Vérifie les noms de tes colonnes !
            
            try (Connection conn = DAOAcces.getConnexion(); // Utilise ta méthode habituelle pour la connexion
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                
                pstmt.setString(1, nouveauNom);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                System.out.println("Base de données mise à jour avec succès !");
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

