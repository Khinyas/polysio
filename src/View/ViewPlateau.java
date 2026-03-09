package View;

import controller.ControllerPlateau;
import controller.ControllerDes;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.MainApp;
import model.ModelCase;

public class ViewPlateau extends ViewTemplate {

    // Attributs : Le contrôleur et les éléments graphiques principaux
    private ControllerPlateau controllerPlateau;
    private ControllerDes controllerDes = new ControllerDes(); // Pour tes dés
    
    private GridPane plateauGrid = new GridPane();
    private Label labelResultat = new Label("Prêt ?");

    public ViewPlateau() {
        super("/images/plateaujeu.png");
        
        
        this.controllerPlateau = new ControllerPlateau(this);
        
        
        setContenuCentral(creerContenuCentral());
    }

    private HBox creerContenuCentral() {
        HBox contenuH = new HBox();
        contenuH.setAlignment(Pos.CENTER);

        // Style du plateau
        plateauGrid.setStyle("-fx-background-color: #BFDBAE; -fx-padding: 10; -fx-border-color: #2e4d23; -fx-border-width: 3;");
        plateauGrid.setHgap(2);
        plateauGrid.setVgap(2);

        // 1. D'abord on génère les cases (le tour du plateau)
        controllerPlateau.commanderGenerationPlateau();
        Button boutonListe = new Button("BOUTON LISTE");
        boutonListe.setOnAction(event -> {
        	ViewTemplateJeu viewTemplateJeu = new ViewTemplateJeu();
        	MainApp.changerDePage(viewTemplateJeu);
        });

        // 2. Ensuite on ajoute le panneau de commande AU CENTRE

        contenuH.getChildren().addAll(plateauGrid,boutonListe);
        return contenuH;
    }

    /**
     * Cette méthode est appelée par le Controller pour chaque case du Model
     */
    public void dessinerUneCase(ModelCase mCase) {
        VBox cellule = new VBox();
        cellule.setPrefSize(55, 55);
        cellule.setAlignment(Pos.CENTER);
        cellule.setStyle("-fx-background-color: white; -fx-border-color: grey;");

        // On affiche les infos venant du ModelCase
        Label nom = new Label(mCase.getNom());
        nom.setStyle("-fx-font-size: 9px; -fx-font-weight: bold;");
        
        Label prix = new Label(mCase.getPrix() + "€");
        prix.setStyle("-fx-font-size: 8px;");

        cellule.getChildren().addAll(nom, prix);

        // Placement automatique sur le carré du Monopoly
        placerDansGrille(cellule, mCase.getId());
    }
    
    
    

    private void placerDansGrille(VBox cellule, int id) {
        int col, row;
        if (id < 10) { col = 10 - id; row = 10; }      // Bas
        else if (id < 20) { col = 0; row = 10 - (id - 10); } // Gauche
        else if (id < 30) { col = id - 20; row = 0; }      // Haut
        else { col = 10; row = id - 30; }             // Droite

        plateauGrid.add(cellule, col, row);
    }
    
    
    }
    