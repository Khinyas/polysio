package View;

import java.util.Timer;

import controller.ControllerChoixPartie;
import controller.ControllerPlateau;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.MainApp;
import model.ModelUser;

public class ViewChoixPartie extends ViewTemplate {
	// public Timer PartieTimer;
	// private Button choixnbjoueurs;
	
	public ViewChoixPartie viewChoixPartie;
	private ControllerChoixPartie controllerChoixPartie = new ControllerChoixPartie();
	
	public ViewChoixPartie() {
		super("/images/G1.png");
		setContenuCentral(creerContenuCentral()); 
		}
		
	private HBox creerContenuCentral() {
        HBox contenuH = new HBox(40);
        contenuH.setAlignment(Pos.CENTER);
        
        // --- Section Joueurs ---
        VBox sectionJoueurs = new VBox(10);
        sectionJoueurs.getChildren().add(new javafx.scene.control.Label("NOMBRE DE JOUEURS"));
        
        Button btn2joueurs = new Button("2 Joueurs");
        Button btn3joueurs = new Button("3 Joueurs");
        Button btn4joueurs = new Button("4 Joueurs");

        // Actions via le contrôleur
        btn2joueurs.setOnAction(e -> controllerChoixPartie.choisirNombreJoueurs(2));
        btn3joueurs.setOnAction(e -> controllerChoixPartie.choisirNombreJoueurs(3));
        btn4joueurs.setOnAction(e -> controllerChoixPartie.choisirNombreJoueurs(4));

        sectionJoueurs.getChildren().addAll(btn2joueurs, btn3joueurs, btn4joueurs);

        // --- Section Chrono (Temps de partie) ---
        VBox sectionTemps = new VBox(10);
        sectionTemps.getChildren().add(new javafx.scene.control.Label("DURÉE DE LA PARTIE"));
        
        Button temps15 = new Button("15 min");
        Button temps30 = new Button("30 min");
        Button temps60 = new Button("1 heure");
        
        temps15.setOnAction(e -> controllerChoixPartie.choisirDuree(15));
        temps30.setOnAction(e -> controllerChoixPartie.choisirDuree(30));
        temps60.setOnAction(e -> controllerChoixPartie.choisirDuree(60));
        
        
        Button lancerPartie = new Button("Lancer la partie !");
        	lancerPartie.setOnAction(event -> { 
        		controllerChoixPartie.lancerLeJeu();
            });

            sectionTemps.getChildren().addAll(temps15, temps30, temps60);
        contenuH.getChildren().addAll(sectionJoueurs, sectionTemps,lancerPartie);
        return contenuH;
    }
}


