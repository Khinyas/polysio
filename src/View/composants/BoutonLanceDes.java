package View.composants;

import controller.ControllerDes;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

import java.util.ArrayList;

public class BoutonLanceDes extends Button {
    private Label labelResultat = new Label("Prêt ?");
    private ControllerDes controllerDes = new ControllerDes();
    Label afficheScore = new Label("0");
    private ArrayList<Integer> scoreLanceDes;
    private Button boutonSuivant;

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


    public BoutonLanceDes(Button boutonSuivantP, ControllerDes controllerDesP) {
        super("LanceDeDés");
        this.setStyle(STYLE_BASE);
        this.setCursor(javafx.scene.Cursor.HAND);
        this.controllerDes = controllerDesP;
        this.boutonSuivant = boutonSuivantP;
        this.boutonSuivant.setVisible(false);
        try {
            String path = getClass().getResource("/audio/lanceDes.mp3").toExternalForm();
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
            System.out.println("Lancer de dés en cours...");
            // On met à jour l'attribut de classe pour que getScoreLanceDes() renvoie les bonnes valeurs
            this.scoreLanceDes = this.controllerDes.auClicLancerDes();
            this.setDisable(true);
            this.setOpacity(0.5); // On le grise visuellement
            this.setText("Dés lancés !");
            this.boutonSuivant.setVisible(true);
            System.out.println("Dés lancés : " + scoreLanceDes.get(0) + " et " + scoreLanceDes.get(1));
        });
    }

    public ArrayList<Integer> getScoreLanceDes() {
        return scoreLanceDes;
    }
}
