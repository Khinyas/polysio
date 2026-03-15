package View;

import View.composants.BoutonAcceuil;
import View.composants.BoutonFermerPoPup;
import View.composants.BoutonLanceDes;
import controller.ControllerDes;
import controller.ControllerPlateau;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.ModelCase;
import model.ModelPlateau;

import java.util.List;

public class ViewTemplateJeu extends StackPane {
    protected ModelPlateau plateauGraphique;
    private ImageView background;
    private List<ModelCase> listeCases ;
    private Pane calquePions; // Nouveau : un panneau dédié aux pions
    /** Attributs rajoutés **/
    private ControllerPlateau controllerPlateau;
    private ControllerDes controllerDes = new ControllerDes(); // Pour tes dés
    


    public ViewTemplateJeu(ModelPlateau plateauGraphiqueP) {

        // Utilisation de getResource pour charger l'image depuis le dossier resources
        // ToDo : Background sera notre fond, il faut penser à le créer ou le choisir si juste couleur
        background = new ImageView(new Image(getClass().getResource("/images/accueil.png").toExternalForm()));
        background.fitWidthProperty().bind(this.widthProperty());
        background.fitHeightProperty().bind(this.heightProperty());
        background.setPreserveRatio(false);
        this.plateauGraphique = plateauGraphiqueP;
        this.getChildren().addAll(background, plateauGraphique);

        int colonneDepart = 10;
        int ligneDepart = 10;
        ViewPion pionJoueur1 = new ViewPion(Color.CYAN);
        this.plateauGraphique.add(pionJoueur1, colonneDepart, ligneDepart);

        // Pour le centrer parfaitement dans la case du GridPane
        GridPane.setHalignment(pionJoueur1, javafx.geometry.HPos.CENTER);
        GridPane.setValignment(pionJoueur1, javafx.geometry.VPos.CENTER);

        // Lancement de la popup de choix dès le départ
        javafx.application.Platform.runLater(() -> {
            choixInitial();
        });
    }

    protected void afficherPopup(Node contenuPopupP, BoutonFermerPoPup choixP) {

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
    private void choixInitial() {
        // Création du contenu de la popup
        VBox contenu = new VBox(15);
        contenu.setAlignment(Pos.CENTER);

        javafx.scene.control.Label titre = new javafx.scene.control.Label("Choisissez votre mode de jeu");
        titre.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        BoutonLanceDes btnLanceDes = new BoutonLanceDes();


        // Ajout d'options (ex: des RadioButtons ou d'autres boutons)
        contenu.getChildren().addAll(titre, btnLanceDes);


        // On crée le bouton de validation (BoutonAcceuil) Pour l'instant
        // Note : le bouton sert aussi à fermer la popup
        BoutonFermerPoPup btnValider = new BoutonFermerPoPup();


        // Appel de ta méthode existante
        this.afficherPopup(contenu, btnValider);
    }

    public ModelPlateau getPlateauGraphique() {
        return plateauGraphique;
    }
}
