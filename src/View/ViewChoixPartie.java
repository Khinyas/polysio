package View;

import java.util.Timer;

import controller.ControllerChoixPartie;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.ModelUser;

public class ViewChoixPartie extends ViewTemplate {
	// public Timer PartieTimer;
	// private Button choixnbjoueurs;
	
	public ViewChoixPartie viewChoixPartie;
	private ControllerChoixPartie controllerChoixPartie = new ControllerChoixPartie();
	
	public ViewChoixPartie(String cheminImageBackground, ModelUser profilUserP) {
		super(cheminImageBackground, profilUserP);
		setContenuCentral(creerContenuCentral()); 
		}
		
	private HBox creerContenuCentral() {
        HBox contenuH = new HBox(40);
        contenuH.setAlignment(Pos.CENTER);
        
        // --- Section Joueurs ---
        VBox sectionJoueurs = new VBox(10);
        sectionJoueurs.getChildren().add(new javafx.scene.control.Label("NOMBRE DE JOUEURS"));
        
        Button btn2 = new Button("2 Joueurs");
        Button btn3 = new Button("3 Joueurs");
        Button btn4 = new Button("4 Joueurs");

        // Actions via le contrôleur
        btn2.setOnAction(e -> controllerChoixPartie.choisirNombreJoueurs(2));
        btn3.setOnAction(e -> controllerChoixPartie.choisirNombreJoueurs(3));
        btn4.setOnAction(e -> controllerChoixPartie.choisirNombreJoueurs(4));

        sectionJoueurs.getChildren().addAll(btn2, btn3, btn4);

        // --- Section Chrono (Temps de partie) ---
        VBox sectionTemps = new VBox(10);
        sectionTemps.getChildren().add(new javafx.scene.control.Label("DURÉE DE LA PARTIE"));
        
        Button temps30 = new Button("30 min");
        Button temps60 = new Button("1 heure");
        
        temps30.setOnAction(e -> controllerChoixPartie.choisirDuree(30));
        temps60.setOnAction(e -> controllerChoixPartie.choisirDuree(60));
        
        sectionTemps.getChildren().addAll(temps30, temps60);

        contenuH.getChildren().addAll(sectionJoueurs, sectionTemps);
        return contenuH;
    }
}


