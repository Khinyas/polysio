package controller;

import View.ViewAccueil;
import View.ViewConnexion;
import com.sun.tools.javac.Main;
import connexion.DAOUser;
import javafx.scene.control.Alert;
import main.MainApp;
import model.ModelUser;
import service.Securite;

import static service.GestionAffichage.afficherAlerte;

public class ControllerInscription {
    public ControllerInscription(String usernameP, String passwordP, String confirmPasswordP, String emailP) {
        System.out.println("Tentative d'inscription pour : " + usernameP);
        String passwordPropre = passwordP.trim();
        String confirmPasswordPropre = confirmPasswordP.trim();

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
            afficherAlerte("Longueur incorrecte", "L'username doit contenir au moins 12 caractères.");
            return;
        }
        if (contientCaractereInterdit(usernameP)) {
            afficherAlerte("Caractère interdit", "L'username contient des caractères non autorisés.");
            return;
        }

        if (passwordPropre == null || passwordPropre.isBlank()){
            System.out.println("Le champ password est incorrect ! ");
            afficherAlerte("Password Incorrect", "Le champ password est incorrect !");
            return;
        }

        if (!isComplex(passwordPropre)){
            System.out.println("Le champ password est incorrect ! ");
            afficherAlerte("Password Invalide", "2 Majuscules, 4 Miniscules, 2 chiffres, 2 symboles (12 caractères min).");
            return;
        }

        if (confirmPasswordPropre == null || confirmPasswordPropre.isBlank()) {
            System.out.println("Le champ confirmPassword est incorrect ! ");
            afficherAlerte("Confirm Password Absent", "Le champ confirmPassword est Incorrect");
            return;
        }

        // Utilisation du ! car on veut agir si les mots de passe ne sont PAS identiques
        if (!passwordCompare(passwordPropre, confirmPasswordPropre)) {
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
        String passwordHache = Securite.hacherPassword(passwordPropre);
        DAOUser.insererUser(usernameP, passwordHache, emailP);
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
    private boolean isComplex(String password) {
        // Changement de la contrainte à 12 caractères pour correspondre à la DB partagée
        if (password.length() < 12) {
            afficherAlerte("Password trop court", "Le Password doit contenir au moins 12 caractères.");
            return false; // On s'arrête là pour ne pas spammer d'autres alertes
        }
        // Regex mise à jour pour être robuste avec les symboles et la longueur de 12
        String regexPassword = "^(?=(.*[A-Z]){2,})(?=(.*[a-z]){4,})(?=(.*[0-9]){2,})(?=(.*[-+!*$@%?&]){2,}).{12,}$";

        if (!password.matches("^(?=(.*[A-Z]){2,}).*$")) {
            afficherAlerte("Password Incorrect", "Le Password doit comporter au moins 2 majuscules.");
            return false;
        }
        if (!password.matches("^(?=(.*[a-z]){4,}).*$")) {
            afficherAlerte("Password Incorrect", "Le Password doit comporter au moins 4 minuscules.");
            return false;
        }
        if (!password.matches("^(?=(.*[0-9]){2,}).*$")) {
            afficherAlerte("Password Incorrect", "Le Password doit comporter au moins 2 chiffres.");
            return false;
        }
        if (!password.matches("^(?=(.*[-+!*$@%?&]){2,}).*$")) {
            afficherAlerte("Password Incorrect", "Le Password doit comporter au moins 2 symboles.");
            return false;
        }
        return password.matches(regexPassword);
    }
    private boolean isLenghtValid(String texte) {
        // Changement de la contrainte à 12 caractères (longueur > 11)
        return texte.length() > 11;
    }

    private boolean passwordCompare(String texte, String confirmTexte) {
        return texte.equals(confirmTexte);
    }

    private boolean usernameExiste(String usernameP) {
        return DAOUser.reqVerifierUserExiste(usernameP);
    }
}