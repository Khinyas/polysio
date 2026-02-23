package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class ViewLancerDes extends ViewTemplate {
	public Button BoutonDes;
	

	public ViewLancerDes() {
        super("/images/dice.jpg", null);

        setContenuCentral(creerContenuCentral());
    }

	private HBox creerContenuCentral() {
        HBox contenuH = new HBox(40); // Espace entre les deux zones
        contenuH.setAlignment(Pos.CENTER);

        // --- CENTRE : LANCER DE DES DANS LE CENTRE ---
        
        System.out.println("Je lance les dés");
        return contenuH;

    } 
	
	
	}
