package controller;

import java.util.ArrayList;
import java.util.List;

import View.ViewCase;
import View.ViewTemplateJeu;
import main.MainApp;
import model.ModelCase;
import model.ModelJoueur;
import model.ModelPlateau;

public class ControllerPlateau {
    private ViewTemplateJeu vueJeu;
    private ModelPlateau modelPlateau;
    private ArrayList<ModelCase> listeCases;
//ToDo : Liste de joueurs pour acceder à leur attributs ?
    public List<ModelJoueur> listeJoueurs = new ArrayList<>();

    public ControllerPlateau() {
        this.listeCases = ControllerCase.casePlateau();
        this.modelPlateau = new ModelPlateau(this.listeCases);
        ModelJoueur joueur1 = new ModelJoueur(1, 0, 0, "#00FFFF");
        listeJoueurs.add(joueur1);
        this.vueJeu = new ViewTemplateJeu(modelPlateau, this, joueur1);
        MainApp.basculerEnModeJeu(vueJeu);
    }
    public ModelCase getCaseParPosition(int positionP){
        return listeCases.get(positionP);
    }

    public ViewCase getViewCaseParPisition(int positionP) {
        ViewCase viewCase = modelPlateau.getListeViewCases().get(positionP);
        return viewCase;
    }
}
