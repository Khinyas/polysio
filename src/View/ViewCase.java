package View;

import model.ModelCase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;

public class ViewCase extends VBox {

    public ViewCase(ModelCase modelCaseP) {

        File fichierSvg  = new File(modelCaseP.getCheminSvg());
        Image imageSvg   = new Image(fichierSvg.toURI().toString());

        ImageView imageView = new ImageView(imageSvg);
        imageView.fitWidthProperty().bind(this.widthProperty());
        imageView.fitHeightProperty().bind(this.heightProperty());
        imageView.setPreserveRatio(false);

        this.getChildren().add(imageView);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }
}