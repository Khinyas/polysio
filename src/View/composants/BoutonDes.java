package View.composants;

import javafx.scene.control.Button;
import main.MainApp;

import java.util.Random;

import View.ViewLancerDes;

public class BoutonDes extends Button {
	
	private static final String STYLE_BASE =
            "-fx-background-color: #34495e; " +
                    "-fx-text-fill: white; " +
                    "-fx-background-radius: 15; " +
                    "-fx-padding: 10 20;";

    private static final String STYLE_HOVER =
            "-fx-background-color: #e67e22; " +
                    "-fx-text-fill: white; " +
                    "-fx-background-radius: 15; " +
                    "-fx-padding: 10 20;";
    
    
    public BoutonDes() {
    	super("LANCER LES DES");
        this.setStyle(STYLE_BASE);
        this.setCursor(javafx.scene.Cursor.HAND);
    	
        this.setOnAction(event -> {
            System.out.println("Lancer de dés !");
            ViewLancerDes LancerDes = new ViewLancerDes(); 
            MainApp.changerDePage(LancerDes);
    	
    });

    }}
