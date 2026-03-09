package View;

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

public class ViewTest extends ViewTemplate {
    private TextField txtUsername;
    private PasswordField txtPassword;

    public ViewTest() {
        // Appelle le constructeur de ViewAbstraite (Fond + Header + Footer)
        super("/images/accueil.png"); //fond d ecran dans le constructeur
        // On place uniquement le formulaire au centre
        setContenuCentral(creerContenuCentral());
    }

    private HBox creerContenuCentral() {
        HBox contenuH1 = new HBox(40);
        contenuH1.setAlignment(Pos.CENTER);
        contenuH1.setPadding(new Insets(20, 0, 30, 20));

        	contenuH1.getChildren().addAll();
        return contenuH1; 
        }
        
        
     }

