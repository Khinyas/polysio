package View;

import java.lang.classfile.Label;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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

    private HBox creerContenuCentral() {
        HBox contenuH = new HBox(40);
        contenuH.setAlignment(Pos.CENTER);
        contenuH.setPadding(new Insets(90, 0, 30, 20));
        
        Button btnChoixPartie = new Button();
        btnChoixPartie.setText("Lancer une partie");
        	btnChoixPartie.setOnAction(event -> {
        	ViewChoixPartie viewChoixPartie = new ViewChoixPartie(); 
        	
            MainApp.changerDePage(viewChoixPartie);});
        	
    
        Button btnViewExo = new Button();
            btnViewExo.setText("Test");
            btnViewExo.setOnAction(event -> {
            System.out.println("Bouton cliqué, allons vers Exo");
            ViewExo viewExo = new ViewExo(); 
            
            MainApp.changerDePage(viewExo);
            
            });
            
        ModelUser user = MainApp.getUtilisateurConnecte();
        
        if(user != null) {
        contenuH.getChildren().addAll(btnChoixPartie,btnViewExo);
        return contenuH;
        } else {
        
        contenuH.getChildren().addAll();
        return contenuH; 
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