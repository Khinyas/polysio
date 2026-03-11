package View;

import controller.ControllerProfil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.MainApp;

//Profil accessible depuis la HBox "creerZoneProfil" dans la classe "Header"
public class ViewProfil extends ViewTemplate { // Ajout de l'héritage
    
    public ViewProfil() {
        super("/images/accueil.png");
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(20));
        setContenuCentral(creerContenuCentral());
    }
    
    private VBox creerContenuCentral() {
    	
    HBox contenuPseudo = new HBox(40);
    contenuPseudo.setAlignment(Pos.CENTER);
    contenuPseudo.setPadding(new Insets(20, 0, 30, 20));
    	
    HBox contenuProfil = new HBox(40);
    contenuProfil.setAlignment(Pos.CENTER);
    contenuProfil.setPadding(new Insets(20, 0, 30, 20));
    
    VBox contenuV1 = new VBox(40);
    contenuV1.setAlignment(Pos.CENTER);
    contenuV1.setPadding(new Insets(20, 0, 30, 20));
    
    // On construit directement le contenu dans le constructeur
    Label welcome = new Label("PROFIL DE : " + MainApp.getUtilisateurConnecte().getUsername());
    welcome.setStyle("-fx-font-size: 35px; -fx-font-weight: bold; -fx-text-fill: #00d4ff;");
    
    TextField inputNom = new TextField(); // On ne récupère pas le texte ici !
    Button changerNom = new Button("Changer le pseudo");
    changerNom.setOnAction(event -> { // C'est ici, lors du clic, qu'on va chercher la valeur actuelle
    	String nouveauNom = inputNom.getText(); // On récupère le texte "en direct"
    	System.out.println("Nouveau nom: " + nouveauNom + ".");
    	new ControllerProfil();
    });
    
    Label label = new Label ("Changer d'image de profil");
    label.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-font-weight: bold; -fx-text-fill: #ffffff");
    
    ImageView avatarCS = new ImageView(new Image("/images/avatars/avatar_logout.jpg"));
    avatarCS.setFitHeight(120);
    avatarCS.setFitWidth(120);
    avatarCS.setCursor(javafx.scene.Cursor.HAND);
    avatarCS.setOnMouseClicked(event -> {
    	System.out.println("Image de profil Counter Strike cliquée");
    	MainApp.getUtilisateurConnecte().setProfilepicture("/images/avatars/avatar_logout.jpg");
    	new ControllerProfil(); // Rafraichit la page
    });
    
    ImageView avatarNavi = new ImageView(new Image("/images/avatars/avatar.jpg"));
    avatarNavi.setFitHeight(120);
    avatarNavi.setFitWidth(120);
    avatarNavi.setCursor(javafx.scene.Cursor.HAND);
    avatarNavi.setOnMouseClicked(event -> {
    	System.out.println("Image de profil Na'vi cliquée");
    	MainApp.getUtilisateurConnecte().setProfilepicture("/images/avatars/avatar.jpg");
    	new ControllerProfil(); // Rafraichit la page
    });
    
    contenuPseudo.getChildren().addAll(inputNom,changerNom);
    contenuProfil.getChildren().addAll(avatarCS,avatarNavi);
    contenuV1.getChildren().addAll(welcome,contenuPseudo,label,contenuProfil);
	return contenuV1;
    }}
