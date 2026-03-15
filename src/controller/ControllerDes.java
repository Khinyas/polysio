package controller;

import model.ModelDes;

import java.util.ArrayList;

public class ControllerDes {
	ModelDes de1 = new ModelDes();
	ModelDes de2 = new ModelDes();
	public ArrayList<Integer> auClicLancerDes() {
		int r1 = de1.lancer();
		int r2 = de2.lancer();
		ArrayList<Integer> resultat = new ArrayList<Integer>();
		resultat.add(r1);
		resultat.add(r2);
		return resultat;
	}

//public int getValeurDe1() { return de1.getValeur(); } // trouver un autre moyen pour l'affichage des dés en gif
//public int getValeurDe2() { return de2.getValeur(); }

public boolean estUnDouble() {
    return de1.getValeur() == de2.getValeur();
}
}	
