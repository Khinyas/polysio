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
        VBox De = LancerDes();
        System.out.println("Je lance les dés");

        // On resserre un peu vers le centre pour que ça tombe dans les formes de l'image
        contenuH.setPadding(new Insets(00, 00, 00, 00));

        // On ajoute uniquement le formulaire ici car le message est maintenant dedans
        contenuH.getChildren().addAll(form);
        return contenuH;
    } 
	
	
	}
