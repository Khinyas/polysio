package model;

import java.util.ArrayList;

public class ModelPlateau {
    private ArrayList<ModelCase> listeCases = new ArrayList<>();

    public ModelPlateau() {
        for (int i = 0; i < 40; i++) {
            listeCases.add(new ModelCase(i, "Rue " + i, "Bleu", i * 10, 200));
        }
    }

    public ArrayList<ModelCase> getListeCases() {
        return listeCases;
    }
}