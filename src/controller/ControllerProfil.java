package controller;

import View.ViewProfil;
import com.sun.tools.javac.Main;
import connexion.DAOUser;
import main.MainApp;
import model.ModelUser;

public class ControllerProfil {
    public ControllerProfil() { // Constructeur vide simple
        ViewProfil viewProfil = new ViewProfil();
        MainApp.changerDePage(viewProfil); // Utilise la méthode générique
        System.out.println("Affichage de la page profil");
        ModelUser user = MainApp.getUtilisateurConnecte();
        System.out.println(user);
    }

//Dans ControllerProfil.java
public static void modifierPseudo(int id_utilisateur, String pseudo) {
 DAOUser dao = new DAOUser();
 dao.updateUsername(id_utilisateur, pseudo);
}
}
