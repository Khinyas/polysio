package View.composants;

import controller.ControllerDes;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import main.MainApp;

import java.util.ArrayList;

public class BoutonAchat extends Button {
    private Label labelResultat = new Label("Acheter la Propriete ?");

    private static final String STYLE_BASE =
            "-fx-background-color: #34495e; " +
                    "-fx-text-fill: white; " +
                    "-fx-background-radius: 15; " +
                    "-fx-padding: 10 20;";

    private static final String STYLE_HOVER =
            "-fx-background-color: #e67e22; " +
                    "-fx-text-fill: white; " +
                    "-fx-background-radius: 15; " +
                    "-fx-padding: 10 20;";
    private AudioClip soundClick;


    public BoutonAchat() {
        super("Acheter la Propriete ?");
        this.setStyle(STYLE_BASE);
        this.setCursor(javafx.scene.Cursor.HAND);
        try {
            String path = getClass().getResource("/audio/coin-2.mp3").toExternalForm();
            soundClick = new AudioClip(path);
            soundClick.setBalance(0.9);
        } catch (Exception erreur) {
            System.out.println("⚠️ Son introuvable");
        }
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), this);
        this.setOnMouseEntered(event -> {
            this.setStyle(STYLE_HOVER);
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.playFromStart();
        });
        // Sortie de souris
        this.setOnMouseExited(event -> {
            this.setStyle(STYLE_BASE);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);
            scaleTransition.playFromStart();
        });

        // Clic de souris (Stop avant de Play)
        this.setOnMousePressed(event -> {
            if (soundClick != null) {
                if (soundClick.isPlaying()) {
                    soundClick.stop(); // Arrête le son s'il est déjà en cours
                }
                soundClick.play();
            }
            this.setTranslateY(2);
        });
        this.setOnMouseReleased(event -> {
            this.setTranslateY(0); // Relâchement
        });

        this.setOnAction(event -> {
            System.out.println(MainApp.getUtilisateurConnecte().getUsername() + " Ferme Popup");
            this.getChildren().addAll(labelResultat);
        });
    }
}
