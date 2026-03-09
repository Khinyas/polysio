package View;

import View.composants.BoutonAcceuil;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import model.ModelCase;
import model.ModelPlateau;

import java.util.List;

public class ViewTemplateJeu extends StackPane {
    protected GridPane plateau;
    private ImageView background;
    private List<ModelCase> listeCases ;
    private ModelCase caseCentrale;
    
    


    public ViewTemplateJeu() {
    	this.listeCases = ModelPlateau.getListeCases();
    	this.caseCentrale = ModelPlateau.getListeCases().get(30);
        background = new ImageView(new Image("/images/accueil.png"));
        background.fitWidthProperty().bind(this.widthProperty());
        background.fitHeightProperty().bind(this.heightProperty());
        background.setPreserveRatio(false);
        this.plateau = new GridPane();
        plateau.prefHeightProperty().bind(this.heightProperty().multiply(0.8));
        plateau.prefWidthProperty().bind(this.heightProperty().multiply(0.8));
        plateau.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        // 0, 0, 0 = Noir | 0.5 = Opacité (de 0.0 à 1.0)
        plateau.setStyle("-fx-background-color: rgba(0, 0, 0, 1.0);");
        //Espacement entre cellule grille :
        /*
        this.plateau.setHgap(2); // 2 pixels d'espace horizontal entre les cases ( s ajoute aux pourcentages ! )
        this.plateau.setVgap(2); // 2 pixels d'espace vertical entre les cases
         */

        this.initialiserPlateau(listeCases,caseCentrale);
        this.getChildren().addAll(background, plateau);
    }

    public void initialiserPlateau(List<ModelCase> listeCasesP, ModelCase caseCentraleP) {
        // On clear toujours la gridPane pour s'assurer qu'elle est vide
        this.plateau.getChildren().clear();
        this.plateau.getColumnConstraints().clear();
        this.plateau.getRowConstraints().clear();
        // Je crée une boucle pour assigner des positions à chaque case de la liste
        // Important, les cases doivent être Ordonnées dans la liste
        for (int i = 0; i < 11; i++) {
            ColumnConstraints col = new ColumnConstraints();
            RowConstraints row = new RowConstraints();
            if (i == 0 || i == 10) {
                col.setPercentWidth(12.5);
                row.setPercentHeight(12.5);
            } else {
                col.setPercentWidth(8.3);
                row.setPercentHeight(8.3);
            }
            this.plateau.getColumnConstraints().add(col);
            this.plateau.getRowConstraints().add(row);
        }

        for (int i = 0; i < listeCasesP.size(); i++) {
            ModelCase casePlateau = listeCasesP.get(i);
            int colonne = 0;
            int ligne = 0;

            if (i <= 10) {
                colonne = 10 - i;
                ligne = 10;
            } else if (i <= 20) {
                colonne = 0;
                ligne = 20 - i;
            } else if (i <= 30) {
                colonne = i - 20;
                ligne = 0;
            } else {
                colonne = 10;
                ligne = i - 30;
            }

            GridPane.setConstraints(casePlateau, colonne, ligne);
            this.plateau.getChildren().add(casePlateau);
        }
        this.plateau.add(caseCentraleP, 1, 1, 9, 9);
    }


    protected void afficherPopup(Node contenuPopupP, BoutonAcceuil choixP) {

        Region voile = new Region();
        voile.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        VBox popup = new VBox();
        popup.setAlignment(Pos.CENTER);
        popup.setMaxSize(500, 400);
        popup.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
        popup.getChildren().addAll(contenuPopupP, choixP);

        choixP.setOnAction( event -> this.getChildren().removeAll(voile, popup));

        this.getChildren().addAll(voile, popup);
    }

}
