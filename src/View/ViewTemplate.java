package View;

import View.composants.Footer;
import View.composants.Header;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import model.ModelUser;

public class ViewTemplate extends StackPane {
    protected BorderPane layoutPrincipal;
    private ImageView background;
    public ViewTemplate(String cheminImageBackground, ModelUser profilUserP) {
        // 1. Fond d'écran (commun)
        // Le fond est créé ici, mais l'image dépend du chemin reçu
        background = new ImageView(new Image(cheminImageBackground));
        background.fitWidthProperty().bind(this.widthProperty());
        background.fitHeightProperty().bind(this.heightProperty());
        background.setPreserveRatio(false);

        // 2. Structure
        layoutPrincipal = new BorderPane();
        layoutPrincipal.setTop(new Header(profilUserP));
        layoutPrincipal.setBottom(new Footer());

        this.getChildren().addAll(background, layoutPrincipal);
    }

    // Méthode simple pour changer le contenu central
    protected void setContenuCentral(javafx.scene.Node node) {
        layoutPrincipal.setCenter(node);
    }
}
/**
 * On peut Extends de Borderpane directement si on le souhaite et metre this.setTop / Bottom si on le souhaite
 * Ici pour l'instant c est une structure ou à chaque view central on défini un background et le contenu.
 * NOTE : model profil user est une methode parmis d'autre, cela peut etre supprimer et passer le profil autrement
 * Ne pas en TENIR COMPTE
 */