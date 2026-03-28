package controller;

import java.util.ArrayList;
import java.util.List;

import View.ViewCase;
import View.ViewTemplateJeu;
import main.MainApp;
import model.ModelCase;
import model.ModelJoueur;
import model.ModelPartie;
import model.ModelPlateau;

public class ControllerPlateau {
    private ViewTemplateJeu vueJeu;
    private ModelPartie configuration;
    private ModelPlateau modelPlateau;
    private ArrayList<ModelCase> listeCases;
//ToDo : Liste de joueurs pour acceder à leur attributs ?
    public List<ModelJoueur> listeJoueurs = new ArrayList<>();
    
    private int indexJoueurActuel = 0;

    public ControllerPlateau(ModelPartie configP) {
    	this.configuration = configP;
        this.listeCases = ControllerCase.casePlateau();
        this.modelPlateau = new ModelPlateau(this.listeCases);
        //ModelJoueur joueur1 = new ModelJoueur(1, 0, 0, "#00FFFF");
        
        
        String[] couleurs = {"#FF0000", "#00FF00", "#0000FF", "#FFFF00"}; // Rouge, Vert, Bleu, Jaune
        
        for (int i = 0; i < configuration.getNbJoueurs(); i++) {
            // On crée chaque joueur avec une ID et une couleur unique
            ModelJoueur j = new ModelJoueur(i + 1, 0, 0, couleurs[i % couleurs.length]);
            listeJoueurs.add(j);
        }
        
        this.vueJeu = new ViewTemplateJeu(modelPlateau, this, listeJoueurs);
        MainApp.basculerEnModeJeu(vueJeu);
    }
    public ModelCase getCaseParPosition(int positionP){
        return listeCases.get(positionP);
    }

    public ViewCase getViewCaseParPisition(int positionP) {
        ViewCase viewCase = modelPlateau.getListeViewCases().get(positionP);
        return viewCase;
    }
    public void passerAuJoueurSuivant() {
        indexJoueurActuel = (indexJoueurActuel + 1) % listeJoueurs.size();
    }
    public ModelJoueur getJoueurActuel() {
        return listeJoueurs.get(indexJoueurActuel);
    }

    
}
