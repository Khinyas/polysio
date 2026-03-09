package View;

import controller.ControllerPlateau;

import java.util.ArrayList;

import controller.ControllerDes;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.ModelCase;

public class ViewPlateau extends ViewTemplate {

    // Attributs : Le contrôleur et les éléments graphiques principaux
    private ControllerPlateau controllerPlateau;
    private ControllerDes controllerDes = new ControllerDes(); // Pour tes dés
    boolean affiche = false;
    private GridPane plateauGrid = new GridPane();
    private Label labelResultat = new Label("Prêt ?");

    public ViewPlateau() {
        super("/images/plateaujeu.png");
        
        
        this.controllerPlateau = new ControllerPlateau(this);
        
        
        setContenuCentral(creerContenuCentral());
    }

    private HBox creerContenuCentral() {
        HBox contenuH = new HBox(40);
        contenuH.setAlignment(Pos.CENTER);

        // 1. Panneau de contrôle (Boutons à gauche)
        VBox panneauControle = new VBox(15);
        panneauControle.setAlignment(Pos.CENTER);

        Button btnGenerer = new Button("Initialiser le Plateau");
        Button btnLancerDe = new Button("Lancer les dés");

        // Action : On délègue au contrôleur
        btnGenerer.setOnAction(e -> {
            plateauGrid.getChildren().clear();
            controllerPlateau.commanderGenerationPlateau();
        });
        
        
        btnLancerDe.setOnAction(e -> {
            ArrayList<Integer> resultat = ControllerDes.auClicLancerDes();
            int score = resultat.get(0) + resultat.get(1);
            labelResultat.setText("Résultat : " + score + (ControllerDes.estUnDouble() ? " (DOUBLE !)" : ""));
  
            Image image1 = new Image(getClass().getResourceAsStream("/images/dice/d"+resultat.get(0).toString()+".png"));
    		Image image2 = new Image(getClass().getResourceAsStream("/images/dice/d"+resultat.get(1).toString()+".png"));
    		ImageView imageDe1 = new ImageView(image1);
    		ImageView imageDe2 = new ImageView(image2);
    		
    		imageDe1.setFitHeight(200); 
    		imageDe1.setFitWidth(200);
            //Setting the position of the image 
    		imageDe1.setX(50); 
    		imageDe1.setY(25); 
    		
    		imageDe2.setFitHeight(200); 
    		imageDe2.setFitWidth(200);
            //Setting the position of the image 
    		imageDe2.setX(50); 
    		imageDe2.setY(25); 
    		
    		HBox zoneDes = new HBox(10, imageDe1, imageDe2); // Zone pour les deux dés côte à côte
            zoneDes.setAlignment(Pos.CENTER);
            
            
            
            if (ControllerDes.estUnDouble()) { 
                labelResultat.setText("Score : " + score + " - DOUBLE !");
           } else {
               labelResultat.setText("Score : " + score);
           }
            if(affiche == false) {
	            affiche = true;
	            contenuH.getChildren().addAll(labelResultat, zoneDes);
            }
            if(affiche==true) {
            	contenuH.getChildren().setAll(btnGenerer, btnLancerDe, labelResultat, zoneDes);
            }
            System.out.println("Score calculé : " + resultat); // Pour vérifier dans la console
        
        
        });

        panneauControle.getChildren().addAll(btnGenerer, btnLancerDe, labelResultat);

        // 2. Le visuel du plateau (GridPane à droite)
        plateauGrid.setStyle("-fx-background-color: #BFDBAE; -fx-padding: 10; -fx-border-color: black;");
        plateauGrid.setHgap(2);
        plateauGrid.setVgap(2);

        contenuH.getChildren().addAll(panneauControle, plateauGrid);
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