package View.composants;

import View.ViewAccueil;

import View.ViewAccueil;
import View.ViewInscription;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import main.MainApp;

public class BoutonInscription extends Button {
    private ImageView iconBuouton;
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

    public BoutonInscription () {
        super("INSCRIPTION");
        this.setStyle(STYLE_BASE);
        this.setCursor(javafx.scene.Cursor.HAND);
        // --- 1. CHARGEMENT DU SON ---
        try {
            String path = getClass().getResource("/audio/clickBtnAccueil.wav").toExternalForm();
            soundClick = new AudioClip(path);
            soundClick.setBalance(0.9); // Optionnel selon la version
        } catch (Exception e) {
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
        // LIEN DU BOUTON VERS ACCUEIL : ON LE MET DANS LE CONSTRUCTOR CAR CE BOUTON AURA TOUJOURS LE MEME LIEN
        this.setOnAction(event -> {
            System.out.println("Direction l'Inscription !");
            // 1. On prépare la vue suivante
            ViewInscription accueil = new ViewInscription();
            // 2. On demande au Main de l'afficher
            MainApp.changerDePage(accueil);
        });
    }
}
