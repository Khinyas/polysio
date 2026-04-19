package controller;

import java.util.ArrayList;

import java.util.List;

import View.ViewPropriete;
import View.ViewResultat;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import View.ViewCase;
import View.ViewTemplateGame;
import main.MainApp;
import model.*;

public class ControllerPlateau {
    private ViewTemplateGame vueJeu;
    private ModelPartie configuration;
    private ModelPlateau modelPlateau;
    private ArrayList<ModelCase> listeCases;
    private ArrayList<ModelPropriete> listeProprietes;
    private int secondesRestantes;
    private Timeline chrono;
//ToDo : Liste de joueurs pour acceder à leur attributs ?
    public List<ModelJoueur> listeJoueurs = new ArrayList<>();
    
    private int indexJoueurActuel = 0;

    public ControllerPlateau(ModelPartie configP) {
    	this.configuration = configP;
        this.listeCases = ControllerCase.casePlateau();
        this.listeProprietes = ControllerPropriete.proprietePlateau();
        this.modelPlateau = new ModelPlateau(this.listeCases, this.listeProprietes);
    

        
        
        // On crée la palette de couleur à partir de l'enum ModelJoueurCouleur
        ModelJoueurCouleur[] couleursDisponible = ModelJoueurCouleur.values();
        
        for (int i = 0; i < configuration.getNbJoueurs(); i++) {
            // On pioche une couleur dans l'enum et on l'attribue ensuite au joueur
            ModelJoueurCouleur couleurAttribue = couleursDisponible[i];
            // On crée chaque joueur avec une ID et une couleur unique
            ModelJoueur j = new ModelJoueur(i + 1, 0, 500, couleurAttribue, "Joueur"+(i+1));
            listeJoueurs.add(j);
        }
        
        
        
        
     
        
        this.vueJeu = new ViewTemplateGame(modelPlateau, this, listeJoueurs, listeProprietes);
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

    public void terminerPartie() {
        this.chrono.stop();
        System.out.println("FIN DE LA PARTIE : Le temps est écoulé !");
        // Logique de fin de partie (ex: MainApp.changerDePage(new ViewResultat()))
    }


    public ModelCase getCaseParPosition(int positionP) {
        for (ModelCase modelCase : listeCases) {
            if (modelCase.getId() == positionP) return modelCase;
        }
        return null;
    }

    public ViewCase getViewCaseParPosition(int positionP) {
        for (ViewCase viewCase : modelPlateau.getListeViewCases()) {
            ModelCase modelCase = getCaseParPosition(positionP);
            if (modelCase == null) return null;
            // On compare par nom ou par position X/Y
            if (viewCase.getViewCasePositionX() == modelCase.getPositionX()
                    && viewCase.getViewCasePositionY() == modelCase.getPositionY()) return viewCase;
        }
        return null;
    }

    public ModelPropriete getProprieteParPosition(int positionP){
        for (ModelPropriete propriete : this.listeProprietes) {
            if (propriete.getCasePlateau() == positionP) {
                return propriete;
            }
        }
        return null;
    }


    public ViewPropriete getViewProprieteParPosition(int positionP) {
        ModelPropriete modelPropriete = getProprieteParPosition(positionP);
        if (modelPropriete == null) { return null; }
        for (ViewPropriete viewPropriete : modelPlateau.getListeViewPropriete()) {
            if (viewPropriete.getViewProprieteId() == modelPropriete.getId()) {
                return viewPropriete;
            }
        }
        return null;
    }

    // indexJoueurActuel commence à ZERO (attribut de la classe)
    // La méthode passerAuJoueur Suivant l'incrémente dans une boucle infinie
    // LE +1 = joueur suivant, MODULO pour ne pas dépasser l'index Joueur 3 = index 0
    public void passerAuJoueurSuivant() {
        indexJoueurActuel = (indexJoueurActuel + 1) % listeJoueurs.size();
    }
    public ModelJoueur getJoueurActuel() {
        return listeJoueurs.get(indexJoueurActuel);
    }
    
 // Dans ta méthode d'achat ou de paiement de loyer
    public void payer(ModelJoueur acheteur, int montant) {
        // 1. On change la valeur en mémoire
        int nouveauSolde = acheteur.getPointsCompetences() - montant;
        acheteur.setPointsCompetences(nouveauSolde);
        
        // 2. On demande à la vue de se rafraîchir
        // 'maVue' doit être ton instance de ViewTemplateGame
        this.vueJeu.updateScoresUI(this); 
    }
    public void eliminerJoueurDuMoteur(ModelJoueur joueur) {
        // On peut par exemple changer son rôle ou un flag
        // joueur.setElimine(true); // Si tu as ce flag dans ModelJoueur
        
        // Ou plus radical : le retirer de la liste des joueurs actifs
        this.listeJoueurs.remove(joueur);
        
        // On ajuste l'index pour ne pas sauter le joueur suivant
        indexJoueurActuel--; 
        if (indexJoueurActuel < 0) indexJoueurActuel = 0;
        
        System.out.println("Le moteur a retiré : " + joueur.getPseudonyme());
        
        
    }
    
    public void afficherEcranResultats(List<ModelJoueur> finalJoueurs) {
        // 1. Créer la vue
        ViewResultat vueResultat = new ViewResultat(finalJoueurs);

        // 2. Récupérer la fenêtre actuelle (Stage) via n'importe quel noeud de la vue actuelle
        // (Ici on suppose que ta vue de jeu s'appelle 'vueJeu')
        javafx.scene.Scene sceneActuelle = vueJeu.getScene(); 
        
        if (sceneActuelle != null) {
            // On remplace la racine de la scène par la nouvelle vue
            sceneActuelle.setRoot(vueResultat);
            System.out.println("Changement de racine vers ViewResultat réussi.");
        } else {
            System.out.println("Erreur : Impossible de trouver la scène pour changer de vue.");
        }
    }

    
}
