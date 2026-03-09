package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.MainApp;

//Profil accessible depuis la HBox "creerZoneProfil" dans la classe "Header"
public class ViewProfil extends VBox { // Ajout de l'héritage
    
    public ViewProfil() {
        super();
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(20));
        Label label = new Label ("COUCOU");
        label.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        
        // On construit directement le contenu dans le constructeur
        Label welcome = new Label("PROFIL DE : " + MainApp.getUtilisateurConnecte().getUsername());
        welcome.setStyle("-fx-font-size: 35px; -fx-font-weight: bold; -fx-text-fill: #00d4ff;");
        
        Button retour = new Button("Retour menu");
        retour.setOnAction(event -> System.out.println("Hey"));
        
        this.getChildren().addAll(welcome,label,retour);
    }
}
