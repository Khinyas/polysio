package controller;

import connexion.DAOUser;
import javafx.scene.control.TableView;
import main.MainApp;
import model.ModelUser;
import model.ModelUserRole;

public class ControllerAdmin {
    
    // Suppression
    public static void supprimerUtilisateur(ModelUser user, TableView<ModelUser> table) {
        if (user == null) return;
        
        if (user.getId() == MainApp.getUtilisateurConnecte().getId()) {
            System.out.println("Action impossible : Vous ne pouvez pas supprimer votre propre compte.");
            return;
        }

        if (DAOUser.deleteUSer(user.getId())) {
            table.getItems().remove(user);
            System.out.println("Utilisateur supprimé avec succès.");
        }
    }

    // Modification
    public static void modifierUtilisateur(ModelUser user, String nouveauPseudo, String nouvelEmail, ModelUserRole nouveauRole, TableView<ModelUser> table) {
        if (user == null) return;

        // Mise à jour de l'objet
        user.setUsername(nouveauPseudo);
        user.setEmail(nouvelEmail);
       // user.setRole(nouveauRole);
        
 
        }
    }