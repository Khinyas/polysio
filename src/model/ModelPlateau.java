package model;

import java.util.ArrayList;

public class ModelPlateau {
    private static ArrayList<ModelCase> listeCases = new ArrayList<>();
    // private ModelCase couleur_case;
    public ModelPlateau(/*ModelCase couleur_caseP*/) {
    	// this.couleur_case = couleur_caseP;
    	
    	
        for (int i = 0; i < 40; i++) {
            listeCases.add(new ModelCase(i, "Rue " + i, "Bleu", i * 10, 200));
        }
    }

    public static ArrayList<ModelCase> getListeCases() {
        return listeCases;
    }
}