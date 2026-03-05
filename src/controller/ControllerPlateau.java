package controller;

import java.util.ArrayList;

import View.ViewPlateau;
import model.ModelCase;
import model.ModelPlateau;

public class ControllerPlateau {
    private ModelPlateau modelPlateau;
    private ViewPlateau viewPlateau;

    public ControllerPlateau(ViewPlateau viewPlateau) {
        this.viewPlateau = viewPlateau;
        this.modelPlateau = new ModelPlateau();
    }

    
    public void commanderGenerationPlateau() {
        ArrayList<ModelCase> cases = modelPlateau.getListeCases();
        
        // On demande à la vue d'afficher chaque case
        for (ModelCase c : cases) {
            viewPlateau.dessinerUneCase(c);
        }
    }
}
