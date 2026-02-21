package View.composants;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Footer extends HBox {
    public Footer() {
        this.setAlignment(Pos.CENTER);
        this.setPrefHeight(40);
        this.setStyle("-fx-background-color: #34495e;");

        Label credits = new Label("© 2026 POLYSIO - Tous droits réservés");
        credits.setStyle("-fx-text-fill: white; -fx-font-size: 10px;");

        this.getChildren().add(credits);
    }
}

