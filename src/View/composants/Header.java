package View.composants;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class Header extends HBox {
    private BoutonAcceuil boutonAcceuil;

    public Header() {
        this.setPadding(new Insets(10));
        this.setSpacing(20);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setStyle("-fx-background-color: transparent;");
        this.boutonAcceuil = new BoutonAcceuil();
        this.getChildren().add(boutonAcceuil);
    }
    // Le Getter pour pouvoir rajouter un EVENT depuis un controller (afficher page Accueil)
    public BoutonAcceuil getBoutonAcceuil() {
        return boutonAcceuil;
    }
}
