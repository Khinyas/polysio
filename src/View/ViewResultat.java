package View;

import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.ModelJoueur;

public class ViewResultat extends StackPane {
    
    public ViewResultat(List<ModelJoueur> joueurs) {
        // Trier les joueurs par score décroissant
        joueurs.sort((j1, j2) -> Integer.compare(j2.getPointsCompetences(), j1.getPointsCompetences()));
        
        VBox layout = new VBox(30);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #2c3e50;");

        Label titre = new Label("CLASSEMENT FINAL");
        titre.setStyle("-fx-font-size: 40px; -fx-text-fill: gold; -fx-font-weight: bold;");

        VBox podium = new VBox(15);
        podium.setAlignment(Pos.CENTER);

        for (int i = 0; i < joueurs.size(); i++) {
            ModelJoueur j = joueurs.get(i);
            String prefixe = (i == 0) ? "🏆 " : (i + 1) + ". ";
            
            Label lblJoueur = new Label(prefixe + j.getPseudonyme() + " - " + j.getPointsCompetences() + " PC");
            lblJoueur.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
            
            // On peut même mettre le gagnant en plus gros
            if (i == 0) lblJoueur.setStyle(lblJoueur.getStyle() + "-fx-font-size: 32px; -fx-text-fill: #f1c40f;");
            
            podium.getChildren().add(lblJoueur);
        }

        Button btnMenu = new Button("RETOUR AU MENU");
        layout.getChildren().addAll(titre, podium, btnMenu);
        
        btnMenu.setOnAction(e -> {
            // 1. On crée la vue d'accueil
            // Attention : Vérifie les paramètres nécessaires à ton constructeur de ViewAccueil
            ViewAccueil accueil = new ViewAccueil(); 

            // 2. On récupère la scène de la fenêtre et on change la racine
            if (this.getScene() != null) {
                this.getScene().setRoot(accueil);
            } else {
                // Sécurité si la scène n'est pas encore chargée
                System.out.println("Erreur : Scène introuvable.");
            }
        });
        
        this.getChildren().add(layout);
    }
}