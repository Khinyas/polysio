package controller;

import View.ViewProfil;
import connexion.DAOUser;
import main.MainApp;

public class ControllerProfil {
    public ControllerProfil() { // Constructeur vide simple
        ViewProfil viewProfil = new ViewProfil();
        MainApp.changerDePage(viewProfil); // Utilise la méthode générique
        System.out.println("Affichage de la page profil");
    }

//Dans ControllerProfil.java
public static void modifierPseudo(int id_utilisateur, String pseudo) {
 DAOUser dao = new DAOUser();
 dao.updateUsername(id_utilisateur, pseudo);
}
}
