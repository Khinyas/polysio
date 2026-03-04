package controller;

import View.ViewAccueil;
import com.sun.webkit.Timer;
import connexion.ConfigLoader;
import connexion.DAOAcces;
import connexion.DAOUser;
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
        if (!DAOUser.reqVerifierUserExiste(usernameP)) {
            afficherAlerte("Compte inconnu", "Cet utilisateur n'existe pas.");
            return;
        }
        // ICI , on suppose que Si l'user existe mais profil est null, c'est que le mot de passe est faux
        ModelUser profilUtilisateur = validerIdentitication(usernameP, passwordP);
        if (profilUtilisateur != null) {
            // 1 On enregistre la session !
            MainApp.setUtilisateurConnecte(profilUtilisateur);

            // 2 On crée la nouvelle vue (Accueil) avec le profil
            ViewAccueil vueAccueil = new ViewAccueil(profilUtilisateur);
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
    private ModelUser validerIdentitication(String usernameP, String passowrdP) {
        String username = usernameP;
        String password = passowrdP;
        ModelUser userProfil = DAOUser.connexionUtilisateur(usernameP, passowrdP);
        if (userProfil != null) {
            System.out.println("Succès : Utilisateur récupéré, profil Construit ! ");
            MainApp.setUtilisateurConnecte(userProfil);
            MainApp.cfgApp.set("db.utilisateur", usernameP);
        } else {
            MainApp.setUtilisateurConnecte(null);
            System.err.println("Échec : Aucun utilisateur trouvé avec ces identifiants."); }
    }
}