package View;

import controller.ControllerInscription;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ViewInscription extends ViewTemplate {
    private TextField txtUsername, txtEmail;
    private PasswordField txtPassword, txtconfirmPassword;
    private Button btnInscription;

    public ViewInscription() {
        super("/images/inscription.jpg", null); // Inscription = userProfil NULL

        setContenuCentral(creerContenuCentral());
    }

    private HBox creerContenuCentral() {
        HBox contenuH = new HBox(40); // Espace entre les deux zones
        contenuH.setAlignment(Pos.CENTER);

        // --- CENTRE : FORMULAIRE DANS LE CENTRE ---
        VBox form = formulaireInscription();

        // On resserre un peu vers le centre pour que ça tombe dans les formes de l'image
        contenuH.setPadding(new Insets(00, 00, 00, 00));

        // On ajoute uniquement le formulaire ici car le message est maintenant dedans
        contenuH.getChildren().addAll(form);
        return contenuH;
    }

    private VBox formulaireInscription() {
        VBox boxFormInscription = new VBox(12); // Espace entre les champs réduit
        boxFormInscription.setAlignment(Pos.CENTER);
        boxFormInscription.setPrefWidth(320); // Plus étroit pour l'hexagone

        // --- L'ASTUCE : On descend tout le bloc de 40 pixels ---
        boxFormInscription.setTranslateY(20);

        // MESSAGE AU DESSUS FORMULAIRE MAIS MEME CONTENEUR
        // Déplacé ici pour être au-dessus des champs dans la VBox
        Label message = new Label("INSCRIPTION");
        message.setStyle("-fx-font-size: 36px; -fx-text-fill: white; -fx-text-alignment: center; -fx-font-weight: bold;");
        message.setWrapText(true);
        // On ajoute une petite marge sous le titre pour ne pas coller au premier champ
        VBox.setMargin(message, new Insets(0, 0, 15, 0));

        // Style des champs : Encore plus fin et transparent
        String styleChamp = "-fx-background-color: rgba(0, 212, 255, 0.05); " +
                "-fx-text-fill: white; " +
                "-fx-prompt-text-fill: rgba(255,255,255,0.4); " +
                "-fx-border-color: rgba(0, 212, 255, 0.3); " +
                "-fx-border-radius: 15; " +
                "-fx-background-radius: 15;";

        // --- MODIFICATION DE LA LARGEUR (Passage de 220 à 190) ---
        double largeurChamp = 190;
        txtUsername = new TextField();
        txtUsername.setPromptText("Nom d'utilisateur");
        txtUsername.setPrefSize(largeurChamp, 30); // Taille réduite (Largeur 180, Hauteur 30)
        txtUsername.setMaxWidth(largeurChamp);    // On force la largeur max
        txtUsername.setStyle(styleChamp);

        txtPassword = new PasswordField();
        txtPassword.setPromptText("Mot de passe");
        txtPassword.setPrefSize(largeurChamp, 30); // Taille réduite
        txtPassword.setMaxWidth(largeurChamp);
        txtPassword.setStyle(styleChamp);

        txtconfirmPassword = new PasswordField();
        txtconfirmPassword.setPromptText("Confirmation Mot de passe");
        txtconfirmPassword.setPrefSize(largeurChamp, 30); // Taille réduite
        txtconfirmPassword.setMaxWidth(largeurChamp);
        txtconfirmPassword.setStyle(styleChamp);

        txtEmail = new TextField();
        txtEmail.setPromptText("Email utilisateur");
        txtEmail.setPrefSize(largeurChamp, 30); // Taille réduite (Largeur 180, Hauteur 30)
        txtEmail.setMaxWidth(largeurChamp);    // On force la largeur max
        txtEmail.setStyle(styleChamp);

        // Bouton : Plus compact
        btnInscription = new Button("REJOINDRE POLYSIO");
        btnInscription.setPrefSize(140, 30);
        btnInscription.setStyle("-fx-background-color: rgba(0, 212, 255, 0.3); " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 11px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 15; " +
                "-fx-border-color: rgba(0, 212, 255, 0.6); " +
                "-fx-border-radius: 15; " +
                "-fx-cursor: hand;");

        // ACTION btnLogin
        btnInscription.setOnAction(event -> {
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            String confirmPassword = txtconfirmPassword.getText();
            String email = txtEmail.getText();
            new ControllerInscription(username, password, confirmPassword, email);
        } );

        // On ajoute le message en premier, puis les champs
        boxFormInscription.getChildren().addAll(message, txtUsername, txtPassword, txtconfirmPassword, txtEmail, btnInscription);
        return boxFormInscription;
    }
}