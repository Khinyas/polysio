package View;

import java.util.ArrayList;

import controller.ControllerDes;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.ModelDes;


public class ViewLancerDes extends ViewTemplate {
	public Button BoutonDes;
	private ControllerDes ControllerDes = new ControllerDes();
    private Label labelResultat = new Label("Prêt ?");
   // private ImageView vueGif = new ImageView(new Image(getClass().getResourceAsStream("/images/dice-roll.gif")));
	HBox zoneDes = new HBox(10);

	public ViewLancerDes() {
        super("/images/dice.jpg");

        super.setContenuCentral(creerContenuCentral());
    }

	private HBox creerContenuCentral() {
        HBox contenuH = new HBox(40); // Espace entre les deux zones
        contenuH.setAlignment(Pos.CENTER);
        
        Button boutonDe = new Button();
        boutonDe.setText("Lancer de dés");
        
     // Dans ta classe ViewLancerDes
        Label afficheScore = new Label("0"); 

       
        //ImageView imageDe1 = new ImageView();
        //ImageView imageDe2 = new ImageView();
        
        
        
        boutonDe.setOnAction(event -> { 
            // On récupère le int renvoyé par le contrôleur
            ArrayList<Integer> resultat = ControllerDes.auClicLancerDes();
            int score = resultat.get(0) + resultat.get(1);
            Image image1 = new Image(getClass().getResourceAsStream("/ressources/images/dice/"+resultat.get(0)+".png"));
    		Image image2 = new Image(getClass().getResourceAsStream("/ressources/images/dice/"+resultat.get(1)+".png"));
    		ImageView imageDe1 = new ImageView(image1);
    		ImageView imageDe2 = new ImageView(image2);
    		
    		zoneDes.getChildren().add(imageDe1);
    		zoneDes.getChildren().add(imageDe2);
    		
            zoneDes.setAlignment(Pos.CENTER);
            
            // On transforme le int en texte pour le Label
            afficheScore.setText("Score : " + score);
            
            if (ControllerDes.estUnDouble()) { 
                labelResultat.setText("Score : " + resultat + " - DOUBLE !");
           } else {
               labelResultat.setText("Score : " + resultat);
           }
            contenuH.getChildren().addAll(boutonDe, labelResultat, zoneDes);
            
            System.out.println("Score calculé : " + resultat); // Pour vérifier dans la console
        });

        
        return contenuH;
        
    }
        
	
	
	}
