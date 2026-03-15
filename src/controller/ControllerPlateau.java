package controller;

import java.util.ArrayList;

import View.ViewTemplateJeu;
import com.sun.tools.javac.Main;
import com.sun.webkit.Timer;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.MainApp;
import model.ModelCase;
import model.ModelPlateau;

public class ControllerPlateau {
    private ViewTemplateJeu vueJeu;
    private ModelPlateau modelPlateau;
    private ArrayList<ModelCase> listeCases;

    public ControllerPlateau() {
        this.listeCases = ControllerCase.casePlateau();
        this.modelPlateau = new ModelPlateau(this.listeCases);
        this.vueJeu = new ViewTemplateJeu(modelPlateau);
        MainApp.basculerEnModeJeu(vueJeu);
    }
}
