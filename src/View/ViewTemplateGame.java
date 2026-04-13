package View;

import View.composants.BoutonFermerPoPup;
import View.composants.BoutonLanceDes;
import View.composants.Footer;
import View.composants.Header;
import controller.ControllerDes;
import controller.ControllerPlateau;
import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.ModelCase;
import model.ModelJoueur;
import model.ModelJoueurCouleur;
import model.ModelPlateau;

import java.util.ArrayList;
import java.util.List;

// CORRECTION : le constructeur reçoit maintenant controllerPlateau et joueur1
// car la vue en a besoin pour fonctionner — on ne peut plus les laisser null
public class ViewTemplateGame extends StackPane {
    protected BorderPane layoutPrincipal;
    private ImageView background;
    protected ModelPlateau plateauGraphique;
    private List<ModelCase> listeCases;
    /** Attributs rajoutés **/
    private ControllerPlateau controllerPlateau;
    private ControllerDes controllerDes = new ControllerDes(); // Pour tes dés
    ViewPion pionJoueur1 = new ViewPion(Color.CYAN);
    private AudioClip sonDeplacement;
    // Ajout pour liste de joueurs / pions
    private List<ViewPion> listePionsGraphiques = new ArrayList<>();
    private List<ModelJoueur> modelJoueurs;
    public ViewTemplateGame(ModelPlateau plateauGraphiqueP, ControllerPlateau controllerPlateauP, List<ModelJoueur> joueursP) {
        // Utilisation de getResource pour charger l'image depuis le dossier resources
        // ToDo : Background sera notre fond, il faut penser à le créer ou le choisir si juste couleur
        background = new ImageView(new Image(getClass().getResource("/images/accueil.png").toExternalForm()));
        background.fitWidthProperty().bind(this.widthProperty());
        background.fitHeightProperty().bind(this.heightProperty());
        background.setPreserveRatio(false);

        this.plateauGraphique = plateauGraphiqueP;
        this.controllerPlateau = controllerPlateauP;
        this.modelJoueurs = joueursP;

        // Structure
        // Conteneur Principal : BorderPane
        layoutPrincipal = new BorderPane();
        // Zone Header
        layoutPrincipal.setTop(new Header());
        // Zone Footer
        layoutPrincipal.setBottom(new Footer());
        // Zone Gauche
        Region manchetteGauche = new Region();
        manchetteGauche.setStyle("-fx-background-color: white;");
        manchetteGauche.prefWidthProperty().bind(layoutPrincipal.widthProperty().multiply(0.05));
        layoutPrincipal.setLeft(manchetteGauche);
        // Zone Droite
        setContenuDroite();
        // Zone Centrale
        setContenuCentral();
        // Bruitage Déplacement
        String path = getClass().getResource("/audio/deplacement.mp3").toExternalForm();
        sonDeplacement = new AudioClip(path);
        sonDeplacement.setVolume(0.6); // volume de 0.0 à 1.0
        this.getChildren().addAll(background, layoutPrincipal);
    }


    // Méthode pour changer le contenu central
    protected void setContenuCentral() {
        // --- Création des pions pour chaque joueur ---
        for (ModelJoueur mj : modelJoueurs) {
            ViewPion pion = new ViewPion(Color.web(mj.getCouleur().getCouleurJoueur())); // Assume que ModelJoueur a getCouleur()
            listePionsGraphiques.add(pion);
            placerPionInitial(mj, pion);
        }

        // CORRECTION : on délègue à placerPionSansAnimation() plutôt que
        // de hardcoder col=10/ligne=10 — il lit la position logique du joueur
        // inutile placerPionsSansAnimation();

        // Pour le centrer parfaitement dans la case du GridPane
        GridPane.setHalignment(pionJoueur1, javafx.geometry.HPos.CENTER);
        GridPane.setValignment(pionJoueur1, javafx.geometry.VPos.CENTER);

        // Lancement de la popup de choix dès le départ
        javafx.application.Platform.runLater(() -> {
            choixInitial();
        });

        // --- AJOUT : Conteneur pour forcer le ratio carré du plateau ---
        StackPane conteneurRatio = new StackPane();
        conteneurRatio.getChildren().add(plateauGraphique);

        // On lie la largeur et la hauteur du plateau à la plus petite dimension du centre
        // Cela garantit que le plateau reste carré et que les cases se touchent
        plateauGraphique.maxWidthProperty().bind(
                Bindings.min(conteneurRatio.widthProperty(), conteneurRatio.heightProperty())
        );
        plateauGraphique.maxHeightProperty().bind(
                Bindings.min(conteneurRatio.widthProperty(), conteneurRatio.heightProperty())
        );

        // On place le conteneur de ratio au centre du layout principal
        layoutPrincipal.setCenter(conteneurRatio);
    }

    // Méthode pour changer le contenu de droite
    protected void setContenuDroite() {
        VBox contenuDroite = new VBox();
        contenuDroite.setStyle("-fx-background-color: white;");
        contenuDroite.getChildren().add(new Label("Scores des joueurs :"));
        contenuDroite.prefWidthProperty().bind(layoutPrincipal.widthProperty().multiply(0.25));
        layoutPrincipal.setRight(contenuDroite);
    }

    private void placerPionInitial(ModelJoueur mj, ViewPion vp) {
        ModelCase caseDepart = controllerPlateau.getCaseParPosition(mj.getPosition());
        plateauGraphique.add(vp, caseDepart.getPositionX(), caseDepart.getPositionY());

        // Décalage léger si plusieurs pions sur la même case (optionnel)
        vp.setTranslateX((mj.getIdJoueur() - 1) * 5);
    }

    // On récupère le pion du joueur qui doit bouger
    private ViewPion getPionActuel() {
        int id = controllerPlateau.getJoueurActuel().getIdJoueur();
        return listePionsGraphiques.get(id - 1);
    }


    // Anime UN seul pas posDepart → posArrivee,
    // puis appelle onTermine quand le pion est arrivé.
    // La chaîne récursive dans deplacerPionGraphique garantit
    // que chaque pas attend la fin du précédent → animation fluide.
    private void animerUnPas(ModelJoueur joueur, int posDepartP, int posArriveeP, Runnable onTermine) {
        ViewCase vcDepart  = controllerPlateau.getViewCaseParPisition(posDepartP);
        ViewCase vcArrivee = controllerPlateau.getViewCaseParPisition(posArriveeP);

        ViewPion pionQuiBouge = listePionsGraphiques.get(joueur.getIdJoueur() - 1);

        // Sécurité : si une case est introuvable on saute directement
        if (vcDepart == null || vcArrivee == null) {
            placerPionsSansAnimation();
            onTermine.run();
            return;
        }
        if (sonDeplacement != null) sonDeplacement.play();
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
        TranslateTransition move = new TranslateTransition(Duration.millis(160), pionQuiBouge);
        move.setByX(dx);
        move.setByY(dy);
        move.setInterpolator(Interpolator.EASE_BOTH);
        // Le replace final n'a lieu qu'une seule fois, à la toute fin dans animerPasRecursif.
        move.setOnFinished(e -> onTermine.run());
        move.play();
    }


    private void placerPionsSansAnimation() {
        // 1. On parcourt la liste de tous les joueurs du contrôleur
        for (int i = 0; i < modelJoueurs.size(); i++) {
            ModelJoueur joueur = modelJoueurs.get(i);
            ViewPion pionGraphique = listePionsGraphiques.get(i);

            // 2. On récupère la case où doit être le joueur
            ModelCase caseActuelle = controllerPlateau.getCaseParPosition(joueur.getPosition());

            // 3. On retire le pion de son ancienne position pour éviter les doublons
            plateauGraphique.getChildren().remove(pionGraphique);

            // 4. On l'ajoute à la nouvelle case (X, Y)
            plateauGraphique.add(pionGraphique, caseActuelle.getPositionX(), caseActuelle.getPositionY());

            // 5. Centrage et Reset des translations (très important après une animation)
            GridPane.setHalignment(pionGraphique, javafx.geometry.HPos.CENTER);
            GridPane.setValignment(pionGraphique, javafx.geometry.VPos.CENTER);

            pionGraphique.setTranslateX(0);
            pionGraphique.setTranslateY(0);

            // OPTIONNEL : Petit décalage pour voir les pions s'ils sont sur la même case
            pionGraphique.setTranslateX(i * 4); // Décale chaque pion de 4px
            pionGraphique.setTranslateY(i * 4);
        }
    }


    protected void afficherPopup(Node contenuPopupP, Button boutonFermeture) {
        // 1. On nettoie les restes des popups précédentes
        nettoyerOverlays();

        Region voile = new Region();
        voile.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6);"); // Noir transparent
        voile.prefWidthProperty().bind(this.widthProperty());
        voile.prefHeightProperty().bind(this.heightProperty());

        VBox popupContainer = new VBox(20);
        popupContainer.setAlignment(Pos.CENTER);
        popupContainer.setMaxSize(500, 400);
        popupContainer.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-padding: 30;");
        popupContainer.getChildren().addAll(contenuPopupP, boutonFermeture);

        // Capture de l'action pour ne pas la perdre
        var actionOriginale = boutonFermeture.getOnAction();

        boutonFermeture.setOnAction(event -> {
            // Au clic, on nettoie TOUT (le voile et la box)
            nettoyerOverlays();

            // Puis on exécute la logique de jeu (passer au joueur suivant, etc.)
            if (actionOriginale != null) {
                actionOriginale.handle(event);
            }
        });

        this.getChildren().addAll(voile, popupContainer);
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
            // inutile this.lancerAnimationJoueurActuel(scoretotal);
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

        ModelJoueur joueurActuel = controllerPlateau.getJoueurActuel();
        animerPasRecursif(joueurActuel, joueurActuel.getPosition(), 0, nbCases);
    }


    public void lancerAnimationJoueurActuel(int scoreDe) {
        ModelJoueur j = controllerPlateau.getJoueurActuel();
        // On lance la récursion pour ce joueur précis
        animerPasRecursif(j, j.getPosition(), 0, scoreDe);
    }

    // Lance le pas numéro pasActuel, puis s'appelle elle-même pour le suivant
    private void animerPasRecursif(ModelJoueur joueur,int positionDeDepart, int pasActuel, int nbCasesTotal) {
        if (pasActuel >= nbCasesTotal) {
            // Tous les pas sont terminés — mise à jour du modèle et popup
            boolean passageDepart = joueur.avancer(nbCasesTotal);
            if (passageDepart) System.out.println(joueur.getIdJoueur() + " passe par Depart !");
            // inutile joueur.setPosition((positionDeDepart + nbCasesTotal) % 40);
            placerPionsSansAnimation();

            PauseTransition pause = new PauseTransition(Duration.millis(300));
            pause.setOnFinished(ev -> afficherPopupEvenement(joueur));
            pause.play();
            return;
        }

        int posDepart  = (positionDeDepart + pasActuel)     % 40;
        int posArrivee = (positionDeDepart + pasActuel + 1) % 40;

        // Lance ce pas, et quand il est fini lance le suivant
        animerUnPas(joueur ,posDepart, posArrivee, () ->
                animerPasRecursif(joueur, positionDeDepart, pasActuel + 1, nbCasesTotal)
        );
    }

    private void afficherPopupEvenement(ModelJoueur joueur) {
        VBox contenu = new VBox(15);
        contenu.setAlignment(Pos.CENTER);

        Label titre = new Label("FIN DU TOUR - JOUEUR " + joueur.getIdJoueur());
        ViewCase carte = new ViewCase(controllerPlateau.getCaseParPosition(joueur.getPosition()));
        Label msg = new Label("Case : " + carte.getViewCaseName());
        contenu.getChildren().addAll(titre, msg, carte);

        BoutonFermerPoPup btnFin = new BoutonFermerPoPup();
        btnFin.setText("AU SUIVANT !");

        btnFin.setOnAction(event -> {
            // 1. On passe au joueur suivant dans le moteur
            controllerPlateau.passerAuJoueurSuivant();

            // 2. On relance le tour suivant avec un léger décalage technique
            javafx.application.Platform.runLater(() -> {
                choixInitial();
            });
        });

        this.afficherPopup(contenu, btnFin);
    }

    private void nettoyerOverlays() {
        // On retire tous les éléments qui ne sont ni le background, ni le plateau
        // On suppose que background est à l'index 0 et plateauGraphique à l'index 1
        this.getChildren().removeIf(node -> node != background && node != layoutPrincipal);
    }

    public ModelPlateau getPlateauGraphique() {
        return plateauGraphique;
    }
}
