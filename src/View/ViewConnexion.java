package View;

import View.composants.Footer;
import View.composants.Header;
import controller.ControllerConnexion;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ViewConnexion extends ViewTemplate {
    private TextField txtUsername;
    private PasswordField txtPassword;
    private Button btnLogin;

    public ViewConnexion() {
        super("/images/connexion.png");

        setContenuCentral(creerContenuCentral());
    }

    private HBox creerContenuCentral() {
        HBox contenuH = new HBox(140); // Espace entre les deux zones
        contenuH.setAlignment(Pos.CENTER);


        // --- GAUCHE : TEXTE AJUSTÉ À L'HEXAGONE ---
        VBox zoneTexte = new VBox(5);
        zoneTexte.setAlignment(Pos.CENTER);
        zoneTexte.setPrefWidth(280); // Largeur pour rentrer dans la forme
        zoneTexte.setPadding(new Insets(0, 0, 0, 00)); // Petit décalage vers la droite

        Label welcome = new Label("POLYSIO");
        welcome.setStyle("-fx-font-size: 35px; -fx-font-weight: bold; -fx-text-fill: #00d4ff;");

        Label message = new Label("Rejoignez la partie.\nEntrez vos identifiants.");
        message.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-text-alignment: center;");
        message.setWrapText(true);

        zoneTexte.getChildren().addAll(welcome, message);

        // --- DROITE : FORMULAIRE DANS L'HEXAGONE DROIT ---
        VBox form = formulaireConnexion();

        // On resserre un peu vers le centre pour que ça tombe dans les formes de l'image
        contenuH.setPadding(new Insets(90, 00, 30, 20));

        contenuH.getChildren().addAll(zoneTexte, form);
        return contenuH;
    }

    private VBox formulaireConnexion() {
        VBox boxFormConnexion = new VBox(12); // Espace entre les champs réduit
        boxFormConnexion.setAlignment(Pos.CENTER);
        boxFormConnexion.setPrefWidth(320); // Plus étroit pour l'hexagone

        // --- L'ASTUCE : On descend tout le bloc de 40 pixels ---
        boxFormConnexion.setTranslateY(40);

        // Style des champs : Encore plus fin et transparent
        String styleChamp = "-fx-background-color: rgba(0, 212, 255, 0.05); " +
                "-fx-text-fill: white; " +
                "-fx-prompt-text-fill: rgba(255,255,255,0.4); " +
                "-fx-border-color: rgba(0, 212, 255, 0.3); " +
                "-fx-border-radius: 15; " +
                "-fx-background-radius: 15;";

        txtUsername = new TextField();
        txtUsername.setPromptText("Nom d'utilisateur");
        txtUsername.setPrefSize(220, 30); // Taille réduite (Largeur 180, Hauteur 30)
        txtUsername.setMaxWidth(220);    // On force la largeur max
        txtUsername.setStyle(styleChamp);

        txtPassword = new PasswordField();
        txtPassword.setPromptText("Mot de passe");
        txtPassword.setPrefSize(220, 30); // Taille réduite
        txtPassword.setMaxWidth(220);
        txtPassword.setStyle(styleChamp);

        // Bouton : Plus compact
        btnLogin = new Button("S'IDENTIFIER");
        btnLogin.setPrefSize(140, 30);
        btnLogin.setStyle("-fx-background-color: rgba(0, 212, 255, 0.3); " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 11px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 15; " +
                "-fx-border-color: rgba(0, 212, 255, 0.6); " +
                "-fx-border-radius: 15; " +
                "-fx-cursor: hand;");

        // ACTION btnLogin
        btnLogin.setOnAction(event -> {
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            new ControllerConnexion(username, password);
        } );
        boxFormConnexion.getChildren().addAll(txtUsername, txtPassword, btnLogin);
        return boxFormConnexion;
    }

    private ImageView creerBackground(StackPane root) {
        ImageView background = new ImageView(new Image("/images/connexion.png"));
        background.fitWidthProperty().bind(root.widthProperty());
        background.fitHeightProperty().bind(root.heightProperty());
        background.setPreserveRatio(false);
        return background;
    }
}