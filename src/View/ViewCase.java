package View;

import javafx.scene.layout.Priority;
import model.ModelCase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import java.net.URL;

public class ViewCase extends VBox {

    public ViewCase(ModelCase modelCaseP) {
        //  Configuration de la VBox
        this.setAlignment(Pos.CENTER);
        this.setMinSize(50, 50);
        VBox.setVgrow(this, Priority.ALWAYS);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // Style temporaire pour le débug (à supprimer plus tard)
       // this.setStyle("-fx-border-color: red; -fx-border-width: 1;");

        String chemin = modelCaseP.getCheminSvg();

        try {
            // Utilisation explicite du type URL au lieu de var
            URL resource = getClass().getResource(chemin);

            if (resource != null) {
                // Chargement de l'image
                Image image = new Image(resource.toExternalForm());
                ImageView imageView = new ImageView(image);

                // Liaison des dimensions à la VBox
                imageView.fitWidthProperty().bind(this.widthProperty());
                imageView.fitHeightProperty().bind(this.heightProperty());

                // Garder le ratio est CRUCIAL pour que l'image ne disparaisse pas
                // si la cellule est plus large que haute
                imageView.setPreserveRatio(true);

                this.getChildren().add(imageView);
            } else {
                // Si l'image est introuvable, on affiche au moins le nom de la case
                System.out.println("ERREUR : Fichier introuvable -> " + chemin);
                this.getChildren().add(new Label(modelCaseP.getNom()));
            }

        } catch (Exception e) {
            System.out.println("ERREUR Exception sur la case : " + modelCaseP.getNom());
            this.getChildren().add(new Label("Erreur Image"));
        }
    }
}