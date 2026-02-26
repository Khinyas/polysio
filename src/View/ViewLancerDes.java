package View;

import controller.ControllerDes;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.ModelDes;


public class ViewLancerDes extends ViewTemplate {
	public Button BoutonDes;
	private ControllerDes ControllerDes = new ControllerDes();
    private Label labelResultat = new Label("Prêt ?");
   // private ImageView vueGif = new ImageView(new Image(getClass().getResourceAsStream("/images/dice-roll.gif")));
	

	public ViewLancerDes() {
        super("/images/dice.jpg");

        setContenuCentral(creerContenuCentral());
    }

	private HBox creerContenuCentral() {
        HBox contenuH = new HBox(40); // Espace entre les deux zones
        contenuH.setAlignment(Pos.CENTER);
        
        Button boutonDe = new Button();
        boutonDe.setText("Lancer de dés");
        
     // Dans ta classe ViewLancerDes
        Label afficheScore = new Label("0"); 

       
        boutonDe.setOnAction(event -> { 
            // On récupère le int renvoyé par le contrôleur
            int resultat = ControllerDes.auClicLancerDes(); 
            
            // On transforme le int en texte pour le Label
            afficheScore.setText("Score : " + resultat);
            
            if (ControllerDes.estUnDouble()) { 
                labelResultat.setText("Score : " + resultat + " - DOUBLE !");
           } else {
               labelResultat.setText("Score : " + resultat);
           }
       
            
            System.out.println("Score calculé : " + resultat); // Pour vérifier dans la console
        });
        
        ImageView imgDe1 = new ImageView();
        ImageView imgDe2 = new ImageView();
        
        HBox zoneDes = new HBox(10, imgDe1, imgDe2); // Zone pour les deux dés côte à côte
        zoneDes.setAlignment(Pos.CENTER);
               

        contenuH.getChildren().addAll(boutonDe,labelResultat, zoneDes);
        return contenuH;
    }
        
	
	
	}
