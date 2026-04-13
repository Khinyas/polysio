package controller;

import java.util.ArrayList;

import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import View.ViewCase;
import View.ViewTemplateGame;
import View.ViewTemplateJeu;
import com.sun.webkit.Timer;
import main.MainApp;
import model.*;

public class ControllerPlateau {
    private ViewTemplateGame vueJeu;
    private ModelPartie configuration;
    private ModelPlateau modelPlateau;
    private ArrayList<ModelCase> listeCases;
    private int secondesRestantes;
    private Timeline chrono;
//ToDo : Liste de joueurs pour acceder à leur attributs ?
    public List<ModelJoueur> listeJoueurs = new ArrayList<>();
    
    private int indexJoueurActuel = 0;

    public ControllerPlateau(ModelPartie configP) {
    	this.configuration = configP;
        this.listeCases = ControllerCase.casePlateau();
        this.modelPlateau = new ModelPlateau(this.listeCases);
        //ModelJoueur joueur1 = new ModelJoueur(1, 0, 0, "#00FFFF");
    
        
        
        
        
        
        
        // On crée la palette de couleur à partir de l'enum ModelJoueurCouleur
        ModelJoueurCouleur[] couleursDisponible = ModelJoueurCouleur.values();
        
        for (int i = 0; i < configuration.getNbJoueurs(); i++) {
            // On pioche une couleur dans l'enum et on l'attribue ensuite au joueur
            ModelJoueurCouleur couleurAttribue = couleursDisponible[i];
            // On crée chaque joueur avec une ID et une couleur unique
            ModelJoueur j = new ModelJoueur(i + 1, 0, 0, couleurAttribue);
            listeJoueurs.add(j);
        }
        
        
        
        
     
        
        this.vueJeu = new ViewTemplateGame(modelPlateau, this, listeJoueurs);
        this.secondesRestantes = configP.getDureeMinutes() * 60;
        this.vueJeu.actualiserAffichageChrono(getTempsFormate());
        MainApp.basculerEnModeJeu(vueJeu);
        lancerLeChrono();
        
    }
    
    private void lancerLeChrono() {
        this.chrono = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            secondesRestantes--;
            
            int min = secondesRestantes / 60;
            int sec = secondesRestantes % 60;
            String tempsFormate = String.format("%02d:%02d", min, sec);

            // SECURITÉ : On vérifie que la vue existe bien avant de lui parler
            if (this.vueJeu != null) {
                this.vueJeu.actualiserAffichageChrono(tempsFormate);
            }

            if (secondesRestantes <= 0) {
                this.chrono.stop();
                terminerPartie();
            }
        }));
        this.chrono.setCycleCount(Animation.INDEFINITE);
        this.chrono.play();
    }

    public String getTempsFormate() {
        int minutes = secondesRestantes / 60;
        int secondes = secondesRestantes % 60;
        return String.format("%02d:%02d", minutes, secondes);
    }

    private void terminerPartie() {
        this.chrono.stop();
        System.out.println("FIN DE LA PARTIE : Le temps est écoulé !");
        // Logique de fin de partie (ex: MainApp.changerDePage(new ViewResultat()))
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
