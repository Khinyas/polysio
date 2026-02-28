package View;

import java.util.ArrayList;


import controller.ControllerDes;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.ModelCase;
import model.ModelDes;
import model.ModelJoueur;

public class ViewExo extends ViewTemplate{
	
	private ControllerDes ControllerDes = new ControllerDes();
	 private Label labelResultat = new Label("Prêt ?");
	
	public ViewExo viewExo;
	
	public ViewExo() {
		super("/images/plateaujeu.png");
		
		setContenuCentral(creerContenuCentral());
	}
	
	private HBox creerContenuCentral() {
        HBox contenuH = new HBox(40);
        contenuH.setAlignment(Pos.CENTER); 
        
        HBox visuelPlateau = new HBox(5); 
        visuelPlateau.setStyle("-fx-border-color: black; -fx-padding: 10;");
        
        ArrayList<ModelCase> plateau = new ArrayList<ModelCase>();
        Button plateauBuilder = new Button("Création de plateau");
        plateauBuilder.setOnAction(event -> {
        	
        	visuelPlateau.getChildren().clear();
        	for(int i= 0; i < 11 ; i++) {
        		plateau.add(new ModelCase(i,"Rue n"+i,"Rouge", i));
        		System.out.println("Création de case " + i);
        		
        		VBox caseGraphique = new VBox(5);
                caseGraphique.setStyle("-fx-background-color: white; -fx-border-color: grey; -fx-padding: 5;");
                caseGraphique.setPrefSize(60, 80); // Taille d'une case de Monopoly
                
                Label lblid = new Label();
                
                
                caseGraphique.getChildren().addAll(lblid);
        		}
        });
        
        Button lancerDe = new Button("Lancer votre dés");
        
        // Dans ta classe ViewLancerDes
           Label afficheScore = new Label("0"); 

          
           lancerDe.setOnAction(event -> { 
      
               int resultat = ControllerDes.auClicLancerDes(); 
              
               afficheScore.setText("Score : " + resultat);
               
               if (ControllerDes.estUnDouble()) { 
                   labelResultat.setText("Score : " + resultat + " - DOUBLE !");
              } else {
                  labelResultat.setText("Score : " + resultat);
              }
          
               
               System.out.println("Score calculé : " + resultat); // Pour vérifier dans la console
           });
        // Bouton création de joueur 
        Button createJoueur = new Button("Création de joueur");
        createJoueur.setOnAction(event -> {
        ModelJoueur j1 = new ModelJoueur(0,1,0); 
        		}
        ); 
        
        contenuH.getChildren().addAll(lancerDe,plateauBuilder,labelResultat,createJoueur,visuelPlateau);
        return contenuH;
        }
	

}
