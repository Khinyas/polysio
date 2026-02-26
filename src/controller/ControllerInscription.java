package controller;

import View.ViewAccueil;
import View.ViewConnexion;
import com.sun.tools.javac.Main;
import javafx.scene.control.Alert;
import main.MainApp;
import model.ModelUser;
import service.Securite;

import static service.GestionAffichage.afficherAlerte;

public class ControllerInscription {
    public ControllerInscription(String usernameP, String passwordP, String confirmPasswordP, String emailP) {
        System.out.println("Tentative d'inscription pour : " + usernameP);

        // CONTROL DE SAISIE :
        if (usernameP == null || usernameP.isBlank() ) {
            System.out.println("Le champ username est incorrect ! ");
            // Dans le contrôleur
            afficherAlerte("Erreur de saisie", "Le champ username est incorrect !");
            return;
        }

        if (usernameExiste(usernameP)) {
            System.out.println("Cet username existe déjà, veuillez en choisir un autre ! ");
            afficherAlerte("Username déjà existant", "Cet username existe déjà, veuillez en choisir un autre !");
            return;
        }

        // Vérification longueur et caractères interdits
        if (!isLenghtValid(usernameP)) {
            afficherAlerte("Longueur incorrecte", "L'username doit contenir au moins 8 caractères.");
            return;
        }
        if (contientCaractereInterdit(usernameP)) {
            afficherAlerte("Caractère interdit", "L'username contient des caractères non autorisés.");
            return;
        }

        if (passwordP == null || passwordP.isBlank()){
            System.out.println("Le champ password est incorrect ! ");
            afficherAlerte("Password Incorrect", "Le champ password est incorrect !");
            return;
        }

        if (!isLenghtValid(passwordP)){
            System.out.println("Le champ password est incorrect ! ");
            afficherAlerte("Longueur incorrecte", "Le password doit contenir au moins 8 caractères.");
            return;
        }

        if (confirmPasswordP == null || confirmPasswordP.isBlank()) {
            System.out.println("Le champ confirmPassword est incorrect ! ");
            afficherAlerte("Confirm Password Absent", "Le champ confirmPassword est Incorrect");
            return;
        }

        // Utilisation du ! car on veut agir si les mots de passe ne sont PAS identiques
        if (!passwordCompare(passwordP, confirmPasswordP)) {
            System.out.println("Les Passwords ne sont pas identiques! ");
            afficherAlerte("Password Compare", "Les Passwords ne sont pas identiques!");
            return;
        }

        if (emailP == null || emailP.isBlank() || !isEmailValide(emailP)) {
            System.out.println("Le champ email est incorrect ! ");
            afficherAlerte("Email Incorrect", "L'adresse email est vide ou invalide.");
            return;
        }

        // Si on arrive ici, tout est OK
        System.out.println("✅ Inscription valide, envoi en base de données...");
        String passwordHache = Securite.hacherPassword(passwordP);
        ModelUser.insererUser(usernameP, passwordHache, emailP);
        MainApp.cfgPolysio.set("db.utilisateur", "");
        ViewConnexion connexion = new ViewConnexion();
        MainApp.changerDePage(connexion);
    }


    private boolean contientCaractereInterdit(String texte) {
        String regexInterdit = ".*[^a-zA-Z0-9_].*";
        return texte.matches(regexInterdit);
    }

    private boolean isEmailValide(String email) {
        // Regex simple : texte + @ + texte + . + texte
        String regexEmail = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(regexEmail);
    }

    private boolean isLenghtValid(String texte) {
        return texte.length() > 7;
    }

    private boolean passwordCompare(String texte, String confirmTexte) {
        return texte.equals(confirmTexte);
    }

    private boolean usernameExiste(String usernameP) {
        return ModelUser.reqVerifierUserExiste(usernameP);
    }
}