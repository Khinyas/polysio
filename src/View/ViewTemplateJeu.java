package View;

import View.composants.BoutonAcceuil;
import View.composants.BoutonFermerPoPup;
import View.composants.BoutonLanceDes;
import controller.ControllerDes;
import controller.ControllerPlateau;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.ModelCase;
import model.ModelJoueur;
import model.ModelPlateau;

import java.util.ArrayList;
import java.util.List;

public class ViewTemplateJeu extends StackPane {
    protected ModelPlateau plateauGraphique;
    private ImageView background;
    private List<ModelCase> listeCases;
    /** Attributs rajoutés **/
    private ControllerPlateau controllerPlateau;
    private ControllerDes controllerDes = new ControllerDes(); // Pour tes dés
    ViewPion pionJoueur1 = new ViewPion(Color.CYAN);
    private ModelJoueur joueur1;


    // CORRECTION : le constructeur reçoit maintenant controllerPlateau et joueur1
    // car la vue en a besoin pour fonctionner — on ne peut plus les laisser null
    public ViewTemplateJeu(ModelPlateau plateauGraphiqueP, ControllerPlateau controllerPlateauP, ModelJoueur joueur1P) {

        // Utilisation de getResource pour charger l'image depuis le dossier resources
        // ToDo : Background sera notre fond, il faut penser à le créer ou le choisir si juste couleur
        background = new ImageView(new Image(getClass().getResource("/images/accueil.png").toExternalForm()));
        background.fitWidthProperty().bind(this.widthProperty());
        background.fitHeightProperty().bind(this.heightProperty());
        background.setPreserveRatio(false);

        this.plateauGraphique = plateauGraphiqueP;
        this.controllerPlateau = controllerPlateauP;
        this.joueur1 = joueur1P;

        this.getChildren().addAll(background, plateauGraphique);

        // CORRECTION : on délègue à placerPionSansAnimation() plutôt que
        // de hardcoder col=10/ligne=10 — il lit la position logique du joueur
        placerPionSansAnimation();

        // Pour le centrer parfaitement dans la case du GridPane
        GridPane.setHalignment(pionJoueur1, javafx.geometry.HPos.CENTER);
        GridPane.setValignment(pionJoueur1, javafx.geometry.VPos.CENTER);

        // Lancement de la popup de choix dès le départ
        javafx.application.Platform.runLater(() -> {
            choixInitial();
        });
    }


    // Anime UN seul pas posDepart → posArrivee,
    // puis appelle onTermine quand le pion est arrivé.
    // La chaîne récursive dans deplacerPionGraphique garantit
    // que chaque pas attend la fin du précédent → animation fluide.
    private void animerUnPas(int posDepartP, int posArriveeP, Runnable onTermine) {
        ViewCase vcDepart  = controllerPlateau.getViewCaseParPisition(posDepartP);
        ViewCase vcArrivee = controllerPlateau.getViewCaseParPisition(posArriveeP);

        // Sécurité : si une case est introuvable on saute directement
        if (vcDepart == null || vcArrivee == null) {
            placerPionSansAnimation();
            onTermine.run();
            return;
        }

        // Conversion en coordonnées scène puis dans l'espace local du GridPane
        // → dx/dy dans le même repère que translateX/Y du pion
        javafx.geometry.Point2D cD = plateauGraphique.sceneToLocal(
                vcDepart .localToScene(vcDepart .getBoundsInLocal().getCenterX(),
                        vcDepart .getBoundsInLocal().getCenterY()));
        javafx.geometry.Point2D cA = plateauGraphique.sceneToLocal(
                vcArrivee.localToScene(vcArrivee.getBoundsInLocal().getCenterX(),
                        vcArrivee.getBoundsInLocal().getCenterY()));
        double dx = cA.getX() - cD.getX();
        double dy = cA.getY() - cD.getY();

        // Saut supprimé : son setByY s'additionnait au setByY du move → décalage vertical
        TranslateTransition move = new TranslateTransition(Duration.millis(160), pionJoueur1);
        move.setByX(dx);
        move.setByY(dy);
        move.setInterpolator(Interpolator.EASE_BOTH);
        // Le replace final n'a lieu qu'une seule fois, à la toute fin dans animerPasRecursif.
        move.setOnFinished(e -> onTermine.run());
        move.play();
    }


    private void placerPionSansAnimation() {
        // CORRECTION : getCaseParPosition prend un int (la position logique du joueur)
        // et non un ModelPlateau comme c'était appelé avant
        ModelCase caseActuelle = controllerPlateau.getCaseParPosition(joueur1.getPosition());
        plateauGraphique.getChildren().remove(pionJoueur1);
        plateauGraphique.add(pionJoueur1, caseActuelle.getPositionX(), caseActuelle.getPositionY());
        GridPane.setHalignment(pionJoueur1, javafx.geometry.HPos.CENTER);
        GridPane.setValignment(pionJoueur1, javafx.geometry.VPos.CENTER);
        pionJoueur1.setTranslateX(0);
        pionJoueur1.setTranslateY(0);
    }


    protected void afficherPopup(Node contenuPopupP, BoutonFermerPoPup choixP) {

        Region voile = new Region();
        voile.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        VBox popup = new VBox();
        popup.setAlignment(Pos.CENTER);
        popup.setMaxSize(500, 400);
        popup.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
        popup.getChildren().addAll(contenuPopupP, choixP);
        choixP.setAlignment(Pos.BOTTOM_RIGHT);
        choixP.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            this.getChildren().removeAll(voile, popup);
        });

        this.getChildren().addAll(voile, popup);
    }

    private void choixInitial() {
        VBox contenu = new VBox(20);
        contenu.setAlignment(Pos.CENTER);

        Label titre = new Label("LANCEZ LES DÉS");
        titre.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        BoutonFermerPoPup btnValider = new BoutonFermerPoPup();
        btnValider.setText("VOIR LE RÉSULTAT");

        BoutonLanceDes btnLanceDes = new BoutonLanceDes(btnValider, this.controllerDes);

        contenu.getChildren().addAll(titre, btnLanceDes);

        btnValider.setOnAction(event -> {
            ArrayList<Integer> scoreObtenu = btnLanceDes.getScoreLanceDes();
            this.afficherPopupResultat(scoreObtenu);
        });

        this.afficherPopup(contenu, btnValider);
    }


    private void afficherPopupResultat(ArrayList<Integer> scoreObtenuP) {
        VBox contenu = new VBox(20);
        contenu.setAlignment(Pos.CENTER);

        Label titre = new Label("RÉSULTAT FINAL");
        titre.setStyle("-fx-font-size: 20px; -fx-text-fill: grey;");

        // Zone des dés avec fond noir
        HBox zoneDes = new HBox(15);
        zoneDes.setAlignment(Pos.CENTER);
        zoneDes.setStyle("-fx-background-color: #2c3e50; -fx-padding: 15; -fx-background-radius: 10;");

        // Chargement des images des dés basés sur le score reçu
        ImageView imgDe1 = creerVueDe(scoreObtenuP.get(0));
        ImageView imgDe2 = creerVueDe(scoreObtenuP.get(1));
        zoneDes.getChildren().addAll(imgDe1, imgDe2);

        int scoretotal = scoreObtenuP.get(2);
        Label scoreLabel = new Label(String.valueOf(scoretotal));
        scoreLabel.setStyle("-fx-font-size: 60px; -fx-font-weight: bold; -fx-text-fill: #e67e22;");

        if (controllerDes.estUnDouble()) {
            Label doubleLabel = new Label("🔥 DOUBLE ! 🔥");
            doubleLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-font-size: 22px;");
            contenu.getChildren().add(doubleLabel);
        }

        contenu.getChildren().addAll(titre, zoneDes, scoreLabel, new Label("Le pion va avancer !"));

        BoutonFermerPoPup btnGo = new BoutonFermerPoPup();
        btnGo.setText("LANCER L'ANIMATION");

        btnGo.setOnAction(event -> {
            this.deplacerPionGraphique(scoretotal);
        });

        this.afficherPopup(contenu, btnGo);
    }

    // Méthode utilitaire pour créer une image de dé proprement
    private ImageView creerVueDe(int valeur) {
        Image img = new Image(getClass().getResourceAsStream("/images/dice/d" + valeur + ".png"));
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }


    public void deplacerPionGraphique(int nbCases) {
        if (nbCases <= 0) return;
        // Chaîne récursive : chaque pas attend la fin du précédent via onTermine
        // C'est le seul moyen de garantir la fluidité — une SequentialTransition
        // ne peut pas contenir des animations lancées dynamiquement à l'intérieur.
        animerPasRecursif(joueur1.getPosition(), 0, nbCases);
    }

    // Lance le pas numéro pasActuel, puis s'appelle elle-même pour le suivant
    private void animerPasRecursif(int positionDeDepart, int pasActuel, int nbCasesTotal) {
        if (pasActuel >= nbCasesTotal) {
            // Tous les pas sont terminés — mise à jour du modèle et popup
            boolean passageDepart = joueur1.avancer(nbCasesTotal);
            if (passageDepart) System.out.println(joueur1.getIdJoueur() + " passe par Depart !");
            placerPionSansAnimation();
            PauseTransition pause = new PauseTransition(Duration.millis(300));
            pause.setOnFinished(ev -> afficherPopupEvenement());
            pause.play();
            return;
        }

        int posDepart  = (positionDeDepart + pasActuel)     % 40;
        int posArrivee = (positionDeDepart + pasActuel + 1) % 40;

        // Lance ce pas, et quand il est fini lance le suivant
        animerUnPas(posDepart, posArrivee, () ->
                animerPasRecursif(positionDeDepart, pasActuel + 1, nbCasesTotal)
        );
    }

    private void afficherPopupEvenement() {
        VBox contenu = new VBox(20);
        contenu.setAlignment(Pos.CENTER);

        Label titre = new Label("CASE X !");
        titre.setStyle("-fx-font-size: 22px; -fx-text-fill: #16a085; -fx-font-weight: bold;");

        Label message = new Label("Vous avez atterri sur une case chance !");
        contenu.getChildren().addAll(titre, message);

        BoutonFermerPoPup btnFin = new BoutonFermerPoPup();
        btnFin.setText("CONTINUER");
        // CORRECTION : relance le tour suivant
        btnFin.setOnAction(event -> choixInitial());

        this.afficherPopup(contenu, btnFin);
    }

    public ModelPlateau getPlateauGraphique() {
        return plateauGraphique;
    }
}