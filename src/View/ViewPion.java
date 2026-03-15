package View;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.effect.DropShadow;
import javafx.util.Duration;

public class ViewPion extends StackPane {

    public ViewPion(Color couleur) {

        // ToDo : On ira chercher dans l'user.getCouleurPion (set à la génération de la partie).
        //Image img = new Image(getClass().getResourceAsStream("/asset/pions/pion_" + couleurPion + ".png"));


        // 1. Le socle (une ellipse pour donner un effet de base)
        Ellipse socle = new Ellipse(15, 8);
        socle.setFill(couleur.darker());

        // 2. Le corps/tête (un cercle)
        Circle tete = new Circle(12);
        tete.setFill(couleur);
        tete.setStroke(Color.WHITE);
        tete.setStrokeWidth(2);

        // 3. Un petit effet d'ombre pour le réalisme
        DropShadow shadow = new DropShadow();
        shadow.setRadius(5.0);
        shadow.setOffsetY(3.0);
        shadow.setColor(Color.color(0, 0, 0, 0.5));
        this.setEffect(shadow);

        // On empile les formes
        this.getChildren().addAll(socle, tete);

        // On décale un peu la tête vers le haut par rapport au socle
        tete.setTranslateY(-10);

        // Important pour ne pas bloquer les clics sur les cases
        this.setMouseTransparent(true);
    }


    public void deplacerPion(int deltaX, int deltaY) {
        TranslateTransition animation = new TranslateTransition(Duration.millis(500), this);

        // On anime le déplacement relatif
        animation.setByX(deltaX);
        animation.setByY(deltaY);

        // Petit effet de rebond
        animation.setAutoReverse(false);
        animation.setCycleCount(1);

        animation.play();
    }


}