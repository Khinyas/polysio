package View.composants;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import model.ModelUser;


public class Header extends HBox {
    private BoutonAcceuil boutonAcceuil;
    private BoutonInscription boutonInscription;
    private BoutonConnexion boutonConnexion;

    public Header(ModelUser profilUserP) {
        this.setPadding(new Insets(10));
        this.setSpacing(20);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setStyle("-fx-background-color: transparent;");

        // Bouton Accueil : Toujours présent
        this.boutonAcceuil = new BoutonAcceuil();
        this.getChildren().add(boutonAcceuil);

        if (profilUserP == null) {
            // --- CAS : UTILISATEUR NON CONNECTÉ ---

            // Bouton Inscription
            this.boutonInscription = new BoutonInscription();
            //Bouton Connexion
            this.boutonConnexion = new BoutonConnexion();

            // Remplissage Conteneur (On ajoute uniquement les boutons de navigation standard)
            this.getChildren().addAll(boutonInscription, boutonConnexion);

        } else {
            // --- CAS : UTILISATEUR CONNECTÉ ---

            // --- CORRECTION ICI ---
            // Au lieu de setHgrow sur le bouton (qui ne marcherait pas bien),
            // on utilise un Spacer (Region) qui va pousser tout le reste à droite.
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            this.getChildren().add(spacer);

            HBox zoneProfil = creerZoneProfil(profilUserP);
            this.getChildren().add(zoneProfil);

            // Note : L'alignement reste CENTER_LEFT, le spacer s'occupe de l'écartement
        }
    }

    // Le Getter pour pouvoir rajouter un EVENT depuis un controller (afficher page Accueil)
    public BoutonAcceuil getBoutonAcceuil() {
        return boutonAcceuil;
    }

    // Getters pour les autres boutons (Retourneront null si l'utilisateur est connecté)
    public BoutonInscription getBoutonInscription() { return boutonInscription; }
    public BoutonConnexion getBoutonConnexion() { return boutonConnexion; }

    public HBox creerZoneProfil(ModelUser profilUserP) {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER_RIGHT);
        // box.setPadding(new Insets(0, 0, 0, 500)); // Suppression du padding fixe, le Spacer fait le travail !

        // AVATAR UTILISATEUR
        // Avatar (Petit rond ou image)
        //ImageView avatar = new ImageView(new Image("/images/avatar_default.png"));
        //avatar.setFitHeight(30);
        //avatar.setFitWidth(30);

        // Nom de l'utilisateur
        javafx.scene.control.Label lblName = new javafx.scene.control.Label(profilUserP.getUsername());
        lblName.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        box.getChildren().addAll(lblName /*,avatar*/);
        box.setCursor(javafx.scene.Cursor.HAND);
        box.setOnMouseClicked(event -> System.out.println("Ouverture Profil User " + profilUserP.getUsername()));

        return box; // Ajout du point-virgule manquant ici
    }
}