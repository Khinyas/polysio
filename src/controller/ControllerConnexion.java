package controller;

import View.ViewAccueil;
import com.sun.tools.javac.Main;
import com.sun.webkit.Timer;
import connexion.ConfigLoader;
import connexion.DAOAcces;
import main.MainApp;
import model.ModelUser;
import model.ModelUserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static service.GestionAffichage.afficherAlerte;

public class ControllerConnexion {
    public ControllerConnexion(String usernameP, String passwordP) {
        System.out.println("Tentative de connexion pour : " + usernameP);

        // CONTROL DE SAISIE :
        if (usernameP == null || usernameP.isBlank()) {
            System.out.println("Le champ username est incorrect ! ");
            afficherAlerte("Erreur de saisie", "Le champ username est incorrect !");
            return;
        }
        if (passwordP == null || passwordP.isBlank()) {
            System.out.println("Le champ password est incorrect ! ");
            afficherAlerte("Password Absent", "Le champ password est Absent !");
            return;
        }
        // On verifie que l'user EXISTE
        if (!ModelUser.reqVerifierUserExiste(usernameP)) {
            afficherAlerte("Compte inconnu", "Cet utilisateur n'existe pas.");
            return;
        }
        // ICI , on suppose que Si l'user existe mais profil est null, c'est que le mot de passe est faux
            // Logique métier (on remplit profilUser)
            // validerIdentification va set un vrai profil ou en nul l'attribut du controller : userProfil.
            // Donc on enregistre la session avec cette methode
            validerIdentitication(usernameP, passwordP);

        if (MainApp.getUtilisateurConnecte() != null) {
            // Succès : Le MainApp a déjà stocké l'user grâce à validerIdentitication

            // On crée la nouvelle vue (Accueil) avec le profil
            ViewAccueil vueAccueil = new ViewAccueil();
            MainApp.changerDePage(vueAccueil);

            System.out.println("Redirection vers l'accueil réussie.");
        } else {
            // ERREUR : On reste sur la vue et on prévient l'utilisateur
            System.err.println("Impossible de se connecter : vérifiez vos identifiants.");
            System.err.println("Mot de passe incorrect pour : " + usernameP);
            afficherAlerte("Connexion échouée", "Mot de passe incorrect. Veuillez réessayer.");
        }
    }

    // LOGIQUE METIER :
    // C est ici qu'on determine si la personne est authentifié ou non
    // Ensuite on Sauvegarde l'utilisateur dans l'attribut du MainApp.setUtilisateurConnecte(userProfil); :
    // userProfil qui est Static donc Accesssible partout dans le code ensuite
    private void validerIdentitication(String usernameP, String passowrdP) {
        ModelUser userProfil = ModelUser.connexionUtilisateur(usernameP, passowrdP);
        if (userProfil != null) {
            System.out.println("Succès : Utilisateur récupéré, profil Construit ! ");
            MainApp.setUtilisateurConnecte(userProfil);
            MainApp.cfgApp.set("db.utilisateur", usernameP);
        } else {
            MainApp.setUtilisateurConnecte(null);
            System.err.println("Échec : Aucun utilisateur trouvé avec ces identifiants."); }
    }
}
