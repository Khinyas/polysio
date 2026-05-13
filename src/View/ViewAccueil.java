package View;

import java.lang.classfile.Label;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.MainApp;
import model.ModelDes;
import model.ModelUser;

public class ViewAccueil extends ViewTemplate {
    private TextField txtUsername;
    private PasswordField txtPassword;

    public ViewAccueil() {
        // Appelle le constructeur de ViewAbstraite (Fond + Header + Footer)
        super("/images/accueil.png"); //fond d ecran dans le constructeur
        // On place uniquement le formulaire au centre
        setContenuCentral(creerContenuCentral());
    }

    private VBox creerContenuCentral() {
        HBox contenuH1 = new HBox(40);
        contenuH1.setAlignment(Pos.CENTER);
        contenuH1.setPadding(new Insets(20, 0, 30, 20));
        
        HBox contenuH2 = new HBox(40);
        contenuH2.setAlignment(Pos.CENTER);
        contenuH2.setPadding(new Insets(20, 0, 30, 20));
        
        VBox contenuV = new VBox(40);
        contenuV.setAlignment(Pos.CENTER);
        contenuV.setPadding(new Insets(20, 0, 30, 20));
        
        Button btnChoixPartie = new Button();
        btnChoixPartie.setText("Lancer une partie");
        	btnChoixPartie.setOnAction(event -> {
        	ViewChoixPartie viewChoixPartie = new ViewChoixPartie(); 
        	
            MainApp.changerDePage(viewChoixPartie);});
        	
      /*  Button btnChargePartie = new Button();
        btnChargePartie.setText("Charger une partie");
        btnChargePartie.setOnAction(event -> {
        	System.out.println("Bouton charger partie");
        }); */
        
      /*  Button btnRegles = new Button();
        btnRegles.setText("Règles");
        btnRegles.setOnAction(event -> {
        	System.out.println("Regles du jeu");
        }); */
        
      /*  Button btnScores = new Button();
        btnScores.setText("Scores");
        btnScores.setOnAction(event -> {
        	System.out.println("Affiche le score");
        }); */
        
     /*   Button btnSalleC119 = new Button();
        btnSalleC119.setText("Salle C119");
        btnSalleC119.setOnAction(event -> {
        	System.out.println("Crédits du jeu");
        }); */
        
     /*   Button btnOptions = new Button();
        btnOptions.setText("Options");
        btnOptions.setOnAction(event -> {
        	System.out.println("Options du jeu");
        }); */
        
        Button btnQuitterJeu = new Button("Quitter le jeu");
        btnQuitterJeu.setOnAction(event -> {
		    System.out.println("Fermeture du programme...");
		    System.exit(0); // Ferme la fenêtre
		});
        
    /*    Button btnProfil = new Button();
        btnProfil.setText("Profil");
        btnProfil.setOnAction(event -> {
        	System.out.println("Profil du joueur");
        }); */
        
     
            
        ModelUser user = MainApp.getUtilisateurConnecte();
        
        if(user != null) {
        	// contenuH1.getChildren().addAll(btnRegles,btnScores,btnSalleC119);
            contenuH2.getChildren().addAll(btnQuitterJeu);
            contenuV.getChildren().addAll(btnChoixPartie,contenuH1,contenuH2);
        return contenuV;
        } else {
        
        	// contenuH1.getChildren().addAll(btnRegles,btnScores,btnSalleC119);
            contenuH2.getChildren().addAll(btnQuitterJeu);
            contenuV.getChildren().addAll(btnChoixPartie,contenuH1,contenuH2);
        return contenuV; 
        }
        
        
     }
}

/**
 * Si on ne veut pas définir le fond d ecran dans le constructeur pour le dynamisme
 *
 * On définit le fond spécifiquement pour cette vue
 *     this.setStyle(
 *         "-fx-background-image: url('/images/connexion.png');" +
 *         "-fx-background-size: cover;" +
 *         "-fx-background-position: center;"
 *     );
 */