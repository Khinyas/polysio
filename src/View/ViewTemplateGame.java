package View;

import View.composants.*;
import controller.ControllerDes;
import controller.ControllerPlateau;
import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.ModelCase;
import model.ModelJoueur;
import model.ModelPlateau;
import model.ModelPropriete;

import java.util.ArrayList;
import java.util.List;

// CORRECTION : le constructeur reçoit maintenant controllerPlateau et joueur1
// car la vue en a besoin pour fonctionner — on ne peut plus les laisser null
public class ViewTemplateGame extends StackPane {
    protected BorderPane layoutPrincipal;
    private ImageView background;
    protected ModelPlateau plateauGraphique;
    private List<ModelCase> listeCases;
    /**
     * Attributs rajoutés
     **/
    private ControllerPlateau controllerPlateau;
    private ControllerDes controllerDes = new ControllerDes(); // Pour tes dés
    ViewPion pionJoueur1 = new ViewPion(Color.CYAN);
    private AudioClip sonDeplacement;
    // Ajout pour liste de joueurs / pions
    private List<ViewPion> listePionsGraphiques = new ArrayList<>();
    private List<ModelJoueur> listeJoueurs;
    private List<ModelPropriete> listeProprietes;
    private List<TilePane> listeInventaireJoueurs = new ArrayList<>();
    private Label lblChrono = new Label();
    // ajout
    private List<Label> labelsPC = new ArrayList<>();
    private boolean partieTerminee = false;

    public ViewTemplateGame(ModelPlateau plateauGraphiqueP, ControllerPlateau controllerPlateauP, List<ModelJoueur> joueursP, List<ModelPropriete> listeProprietesP) {
        // Utilisation de getResource pour charger l'image depuis le dossier resources
        // ToDo : Background sera notre fond, il faut penser à le créer ou le choisir si juste couleur
        background = new ImageView(new Image(getClass().getResource("/images/accueil.png").toExternalForm()));
        background.fitWidthProperty().bind(this.widthProperty());
        background.fitHeightProperty().bind(this.heightProperty());
        background.setPreserveRatio(false);

        this.plateauGraphique = plateauGraphiqueP;
        this.controllerPlateau = controllerPlateauP;
        this.listeJoueurs = joueursP;
        this.listeProprietes = listeProprietesP;
        // chrono
        this.lblChrono = new Label("00:00");
        this.lblChrono.setStyle("-fx-font-size: 32px; -fx-text-fill: #2c3e50; -fx-font-weight: bold;");

        // Structure
        // Conteneur Principal : BorderPane
        layoutPrincipal = new BorderPane();
        // Zone Header
        //layoutPrincipal.setTop(new Header());
        Region header = new Region();
        header.setStyle("-fx-background-color: white;");
        header.prefHeightProperty().bind(layoutPrincipal.heightProperty().multiply(0.01));
        layoutPrincipal.setTop(header);
        // Zone Footer
        layoutPrincipal.setBottom(new Footer());
        // Zone Gauche
        Region espaceGauche = new Region();
        espaceGauche.setStyle("-fx-background-color: white;");
        espaceGauche.prefWidthProperty().bind(layoutPrincipal.widthProperty().multiply(0.05));
        layoutPrincipal.setLeft(espaceGauche);
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
        for (ModelJoueur joueur : listeJoueurs) {
            ViewPion pion = new ViewPion(Color.web(joueur.getCouleur().getCouleurJoueur())); // Assume que ModelJoueur a getCouleur()
            listePionsGraphiques.add(pion);
            placerPionInitial(joueur, pion);
        }

        // CORRECTION : on délègue à placerPionSansAnimation() plutôt que
        // de hardcoder col=10/ligne=10 — il lit la position logique du joueur
        // inutile placerPionsSansAnimation();

        // Pour le centrer parfaitement dans la case du GridPane
        for (ViewPion pion : listePionsGraphiques) {
            GridPane.setHalignment(pion, HPos.CENTER);
            GridPane.setValignment(pion, VPos.CENTER);
        }

        // Lancement de la popup de choix dès le départ
        javafx.application.Platform.runLater(() -> {
            choixInitial();
        });

        // --- AJOUT : Conteneur pour forcer le ratio carré du plateau ---
        StackPane conteneurRatio = new StackPane();
        // aligner le contenu du StackPane à gauche
        conteneurRatio.setAlignment(Pos.CENTER_LEFT);
        conteneurRatio.getChildren().add(plateauGraphique);

        // On lie la largeur et la hauteur du plateau à la plus petite dimension du centre
        // Cela garantit que le plateau reste carré et que les cases se touchent
        plateauGraphique.maxWidthProperty().bind(
                Bindings.min(conteneurRatio.widthProperty(), conteneurRatio.heightProperty())
        );
        plateauGraphique.maxHeightProperty().bind(
                Bindings.min(conteneurRatio.widthProperty(), conteneurRatio.heightProperty())
        );

        // CLÉ : on force le conteneurRatio à ne pas dépasser la taille du carré
        // En limitant sa largeur préférée à sa hauteur, le BorderPane ne lui donne
        // pas plus de place que nécessaire → la droite se colle naturellement
        conteneurRatio.prefWidthProperty().bind(conteneurRatio.heightProperty());
        conteneurRatio.setMaxWidth(Double.MAX_VALUE);

        // On place le conteneur de ratio au centre du layout principal
        layoutPrincipal.setCenter(conteneurRatio);
    }

    // Méthode pour changer le contenu de droite
    protected void setContenuDroite() {
        // Le Contenu de Droite sera une BorderPane avec une TilePane au milieu pour afficher l'inventaire des proprietes
        //Conteneur Principale de la partie DROITE de l'écran de Jeu :
        BorderPane contenuDroite = new BorderPane();
        contenuDroite.setStyle("-fx-background-color: white;");
        contenuDroite.prefWidthProperty().bind(layoutPrincipal.widthProperty().multiply(0.40));


        // H E A D E R de la partie DROITE du Conteneur de Droite de l'écran de jeu :
        VBox header = new VBox(8);
        header.setStyle("-fx-background-color: transparent; -fx-padding: 10;");
        Label labelHeader = new Label("Scores des joueurs :");
        // --- SECTION CHRONO ---
        Label titreChrono = new Label("TEMPS RESTANT");
        titreChrono.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");
        header.getChildren().addAll(labelHeader, titreChrono, this.lblChrono);
        contenuDroite.setTop(header);


        // GAUCHE
        Region sousTribuneGauche = new Region();
        sousTribuneGauche.setStyle("-fx-background-color: yellow;");
        sousTribuneGauche.setPrefWidth(8);
        contenuDroite.setLeft(sousTribuneGauche);


        // CENTRE
        VBox inventaire = new VBox(8);
        inventaire.setStyle("-fx-background-color: grey; -fx-padding: 10;");
        Label labelInventaire = new Label("Inventaire des joueurs :");
        inventaire.getChildren().add(labelInventaire);

        for (ModelJoueur joueur : listeJoueurs) {
        	
        	// Label Score
        	VBox blocJoueur = new VBox(5);
            
            // On récupère les valeurs DIRECTEMENT du modèle
            String nom = (joueur.getPseudonyme() != null) ? joueur.getPseudonyme() : "Joueur " + joueur.getIdJoueur();
            String pc = String.valueOf(joueur.getPointsCompetences());

            // Création du label avec les valeurs fraîches
            Label scoreLabel = new Label(nom + " : " + pc + " PC");
            scoreLabel.setStyle("-fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold;");
            
            // AJOUT à la liste pour le rafraîchissement futur
            labelsPC.add(scoreLabel);
            
        	// Inventaire Propriété
            TilePane inventaireJoueur = new TilePane(Orientation.VERTICAL, 5, 10);

            inventaireJoueur.setVgap(2);
            inventaireJoueur.setHgap(5);
            inventaireJoueur.setPrefColumns(3); // Optionnel : pour organiser les icônes
            inventaireJoueur.setStyle("-fx-background-color: " + joueur.getCouleur().getCouleurJoueur() + "; -fx-background-radius: 10; -fx-padding: 10;");
            listeInventaireJoueurs.add(inventaireJoueur);
            inventaire.getChildren().addAll(scoreLabel,inventaireJoueur);
        }
        contenuDroite.setCenter(inventaire);


        // DROITE
        Region sousTribuneDroite = new Region();
        sousTribuneDroite.setStyle("-fx-background-color: green;");
        sousTribuneDroite.setPrefWidth(8);
        contenuDroite.setRight(sousTribuneDroite);


        // BAS
        Region piedPage = new Region();
        piedPage.setPrefHeight(20);
        piedPage.setStyle("-fx-background-color: red;");
        contenuDroite.setBottom(piedPage);

        // ON FIXE L'ENSEMBLE COMME PARTIE DROITE DE L'ECRAN DE JEU (L'ECRAN EST UNE BORDERPANE ET ICI EST LA PARTIE DROITE)

        this.layoutPrincipal.setRight(contenuDroite);
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
        System.out.println(controllerPlateau.getJoueurActuel());
        ViewCase vcDepart = controllerPlateau.getViewCaseParPosition(posDepartP);
        ViewCase vcArrivee = controllerPlateau.getViewCaseParPosition(posArriveeP);

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
                vcDepart.localToScene(vcDepart.getBoundsInLocal().getCenterX(),
                        vcDepart.getBoundsInLocal().getCenterY()));
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
        for (int i = 0; i < listeJoueurs.size(); i++) {
            ModelJoueur joueur = listeJoueurs.get(i);
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
        ModelJoueur joueurActuel = controllerPlateau.getJoueurActuel();
        // On lance la récursion pour ce joueur précis
        animerPasRecursif(joueurActuel, joueurActuel.getPosition(), 0, scoreDe);
    }

    // Lance le pas numéro pasActuel, puis s'appelle elle-même pour le suivant
    private void animerPasRecursif(ModelJoueur joueur, int positionDeDepart, int pasActuel, int nbCasesTotal) {
        if (pasActuel >= nbCasesTotal) {
            // Tous les pas sont terminés — mise à jour du modèle et popup
            boolean passageDepart = joueur.avancer(nbCasesTotal);
            if (passageDepart) {
                System.out.println(joueur.getPseudonyme() + " passe par Départ ! +200 PC");
                int nouveauSolde = joueur.getPointsCompetences() + 200;
                joueur.setPointsCompetences(nouveauSolde);
                
                // 3. ON MET À JOUR L'INTERFACE IMMÉDIATEMENT
                this.updateScoresUI(controllerPlateau);
            }
            
            
            this.updateScoresUI(controllerPlateau);
            // inutile joueur.setPosition((positionDeDepart + nbCasesTotal) % 40);
            placerPionsSansAnimation();

            PauseTransition pause = new PauseTransition(Duration.millis(300));
            pause.setOnFinished(ev -> afficherPopupEvenement(joueur));
            pause.play();
            return;
        }

        int posDepart = (positionDeDepart + pasActuel) % 40;
        int posArrivee = (positionDeDepart + pasActuel + 1) % 40;

        // Lance ce pas, et quand il est fini lance le suivant
        animerUnPas(joueur, posDepart, posArrivee, () ->
                animerPasRecursif(joueur, positionDeDepart, pasActuel + 1, nbCasesTotal)
        );
    }

    private void afficherPopupEvenement(ModelJoueur joueur) {
        VBox contenu = new VBox(35);
        contenu.setAlignment(Pos.CENTER);

        Label titre = new Label("FIN DU TOUR - JOUEUR " + joueur.getIdJoueur());
        contenu.getChildren().add(titre);

        // CORRECTION : on vérifie null avant tout
        ModelPropriete propriete = controllerPlateau.getProprieteParPosition(joueur.getPosition());
        // DEBUG — à supprimer après
        System.out.println("Position joueur : " + joueur.getPosition());
        System.out.println("Propriete trouvee : " + (propriete != null ? propriete.getNom() + " / casePlateau=" + propriete.getCasePlateau() : "NULL"));

        if (propriete == null) {
            // Case spéciale : départ, prison, chance, etc.
            contenu.getChildren().add(new Label("Case spéciale — pas d'action."));
        } else {
            // On affiche la carte de la propriété
            ViewPropriete locationJoueur = new ViewPropriete(propriete);
            locationJoueur.setPrefSize(120, 170);
            locationJoueur.setMinSize(120, 170);
            locationJoueur.setMaxSize(120, 170);

            Label msg = new Label("Case : " + propriete.getNom());
            contenu.getChildren().addAll(msg, locationJoueur);

            // Todo : Debut Algo Differentiel
            if ("Rue".equals(propriete.getTypeCase())) {

                if (propriete.getProprietaire() == null) {
                    // CORRECTION : cas "personne ne possède" → proposer l'achat
                    // ToDo : Proposer achat de Propriete
                    Label msgAchat = new Label("Cette propriété est libre ! Prix : " + propriete.getPrix());
                    contenu.getChildren().add(msgAchat); // ← CORRECTION : ajouté à contenu

                    // ToDo Possibilite d achat
                    // ToDo : Bouton Achat
                    if (joueur.getPointsCompetences() >= propriete.getPrix()) {
                    BoutonAchat btnAchat = new BoutonAchat();
                    // Todo : Rajouter une modale d'achat de propriete à la place :
                    btnAchat.setOnAction(event -> {
                        nettoyerOverlays();
                        
                        //Ajout 19/04
                        // 1. Soustraire les points dans le modèle
                        controllerPlateau.payer(joueur, propriete.getPrix());
                        
                        // 2. Mettre à jour l'affichage des scores (PC)
                        
                        
                        ajouterProprieteInventaireJoueur();
                        // Joueur suivant dans le moteur
                        controllerPlateau.passerAuJoueurSuivant();
                        // Tour suivant avec un léger décalage technique
                        javafx.application.Platform.runLater(() -> {
                            choixInitial();
                        });
                    });
                    // ToDo : Fin Bouton à changer
                    contenu.getChildren().add(btnAchat);
                    }

                } else if (propriete.getProprietaire().equals(joueur.getPseudonyme())) {
                    // CORRECTION : le joueur est déjà propriétaire
                    Label msgProprietaire = new Label("Bienvenue chez vous ! " + propriete.getNom() + " est déjà à vous.");
                    contenu.getChildren().add(msgProprietaire); // ← CORRECTION : ajouté à contenu

                } else {
                    // CORRECTION : quelqu'un d'autre possède la propriété → payer le loyer
                    int loyer;
                    if (propriete.isBatiment()) {
                        loyer = propriete.getLoyerBatiment();
                    } else {
                        loyer = propriete.getLoyerNu();
                    }

                    Label msgPaiement = new Label("Propriété de : " + propriete.getProprietaire());
                    Label msgPaiementSuite = new Label("Vous devez payer : " + loyer);
                    contenu.getChildren().addAll(msgPaiement, msgPaiementSuite); // ← CORRECTION : ajoutés

                    if (joueur.getPointsCompetences() < loyer) {
                        Label msgBanqueroute = new Label("Vous n'avez pas assez d'argent — vous avez perdu !");
                        contenu.getChildren().add(msgBanqueroute); // ← CORRECTION : ajouté
                        eliminerJoueurGraphiquement(joueur);
                        // ToDo : AFFICHER une modale de défaite !
                    } else {
                        // ToDo : Bouton Paiement
                        BoutonPaiement btnPaiement = new BoutonPaiement();
                        // Todo : Rajouter une modale d'achat de propriete à la place :
                        btnPaiement.setOnAction(event -> {
                            nettoyerOverlays();
                            
                            controllerPlateau.verserLoyer(joueur, loyer, propriete.getProprietaire());
                            
                            if (joueur.getPointsCompetences() < 0) {
                                eliminerJoueurGraphiquement(joueur);
                                // Si tu as besoin d'arrêter la partie :
                                 terminerPartie(); 
                            }
                            
                            
                            
                            //joueur.setPointsCompetences(joueur.getPointsCompetences() - loyer);
                            // Joueur suivant dans le moteur
                            controllerPlateau.passerAuJoueurSuivant();
                            // Tour suivant avec un léger décalage technique
                            javafx.application.Platform.runLater(() -> {
                                choixInitial();
                            });
                        });
                        // ToDo : Fin Bouton à changer
                        contenu.getChildren().add(btnPaiement);
                    }
                }
                // ToDo : If Gare ou Cartes
                // Todo AJOUTER EST LA POUR TEST
                //ajouterProprieteInventaireJoueur();
            }
        }

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

    
    // ajout 19/04



    // LA MÉTHODE DE MISE À JOUR (À appeler après chaque transaction)
    public void updateScoresUI(ControllerPlateau cp) {
        for (int i = 0; i < cp.listeJoueurs.size(); i++) {
            ModelJoueur j = cp.listeJoueurs.get(i);
            // On met à jour le texte du label correspondant au joueur
            labelsPC.get(i).setText(j.getPseudonyme() + " : " + j.getPointsCompetences() + " PC");
        }
    }
    

    // partie chrono 

    // La méthode que le Controller appellera


    public void actualiserAffichageChrono(String temps) {
        if (this.lblChrono != null) {
            this.lblChrono.setText(temps);
            System.out.println("Chrono mis à jour : " + temps);
            this.lblChrono.setVisible(true);
            this.lblChrono.setOpacity(1.0);
         // Si le temps est écoulé
            if (temps.equals("00:00")) {
                terminerPartie();
        
    }}}

    protected void ajouterProprieteInventaireJoueur() {
        ModelJoueur joueur = controllerPlateau.getJoueurActuel();
        int position = joueur.getPosition();

        ModelPropriete propriete = controllerPlateau.getProprieteParPosition(position);
        if (propriete == null) return;

        // CORRECTION : on crée TOUJOURS une nouvelle ViewPropriete pour l'inventaire
        // au lieu de récupérer celle du plateau (qui est déjà dans le scene graph)
        ViewPropriete vueProp = new ViewPropriete(propriete);
        vueProp.setPrefSize(50, 70);
        vueProp.setMinSize(50, 70);
        vueProp.setMaxSize(50, 70);

        // --- ZOOM AU SURVOL ---
        // On crée la carte en grand qu'on affichera en overlay
        ViewPropriete vueZoom = new ViewPropriete(propriete);
        vueZoom.setPrefSize(150, 210); // 3x la taille miniature
        vueZoom.setMinSize(150, 210);
        vueZoom.setMaxSize(150, 210);
        vueZoom.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 4);");
        vueZoom.setMouseTransparent(true); // ne bloque pas les events souris

        vueProp.setOnMouseEntered(e -> {
            // On ajoute le zoom par dessus tout (dans le StackPane racine)
            if (!this.getChildren().contains(vueZoom)) {
                this.getChildren().add(vueZoom);
            }
            // On positionne le zoom à côté du curseur
            vueZoom.setTranslateX(e.getSceneX() - this.getWidth() / 2 - 80);
            vueZoom.setTranslateY(e.getSceneY() - this.getHeight() / 2 - 105);
        });

        vueProp.setOnMouseMoved(e -> {
            // On suit le curseur si la souris bouge
            vueZoom.setTranslateX(e.getSceneX() - this.getWidth() / 2 - 80);
            vueZoom.setTranslateY(e.getSceneY() - this.getHeight() / 2 - 105);
        });

        vueProp.setOnMouseExited(e -> {
            // On retire le zoom quand la souris quitte
            this.getChildren().remove(vueZoom);
        });

        propriete.setProprietaire(joueur.getPseudonyme());

        listeInventaireJoueurs.get(joueur.getIdJoueur() - 1).getChildren().add(vueProp);
    }
        // Ajout 19/04
    private void eliminerJoueurGraphiquement(ModelJoueur joueur) {
        // 1. Trouver l'index du joueur
        int index = listeJoueurs.indexOf(joueur);
        if (index == -1) return;

        // 2. Retirer le pion du plateau (UNE SEULE FOIS, hors de la boucle)
        ViewPion pion = listePionsGraphiques.get(index);
        plateauGraphique.getChildren().remove(pion);

        // 3. Griser l'affichage dans la zone de score
        Label labelScore = labelsPC.get(index);
        labelScore.setText(joueur.getPseudonyme() + " : ÉLIMINÉ");
        labelScore.setStyle("-fx-text-fill: #666666; -fx-font-style: italic; -fx-strikethrough: true;");

        // 4. Masquer son inventaire
        TilePane inv = listeInventaireJoueurs.get(index);
        inv.setOpacity(0.2);
        inv.setDisable(true);

        // 5. Libérer ses propriétés (On nettoie juste les données ici)
        for (ModelPropriete prop : listeProprietes) {
            if (joueur.getPseudonyme().equals(prop.getProprietaire())) {
                prop.setProprietaire(null);
            }
        }
        
        // 6. Logique de retrait moteur
        controllerPlateau.retirerJoueurDeLaPartie(joueur);
        
        // 7. Vérification de victoire
        if (controllerPlateau.estPartieFinie()) {
            terminerPartie();
        }
    }
    // ajout 19/04
    private void terminerPartie() {
        if (partieTerminee) return; // On évite d'appeler la fin plusieurs fois
        this.partieTerminee = true;
        
        // On arrête tout !
        nettoyerOverlays();
        System.out.println("PARTIE STOPPÉE");
        
        // Appelle ViewResultat ou affiche ton message ici
        javafx.application.Platform.runLater(() -> {
            
            controllerPlateau.afficherEcranResultats(this.listeJoueurs);
            

        });}
    
}

