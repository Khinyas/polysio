package controller;

import View.ViewProfil;
import main.MainApp;

public class ControllerProfil {
    public ControllerProfil() { // Constructeur vide simple
        ViewProfil viewProfil = new ViewProfil();
        MainApp.changerDePage(viewProfil); // Utilise la méthode générique
        System.out.println("Affichage de la page profil");
    }
}
