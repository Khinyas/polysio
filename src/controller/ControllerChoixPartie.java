package controller;

import model.ModelPartie;

public class ControllerChoixPartie {

	private ModelPartie modelPartie = new ModelPartie();

    public void choisirNombreJoueurs(int nb) {
        modelPartie.setNbJoueurs(nb);
        System.out.println("Partie configurée pour " + nb + " joueurs.");
    }

    public void choisirDuree(int minutes) {
        modelPartie.setDureeMinutes(minutes);
        System.out.println("Durée de la partie : " + minutes + " min.");
    }
}

