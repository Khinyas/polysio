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
		resultat.add(r1 + r2);
		return resultat;
	}


	public boolean estUnDouble() {
		// Un double n'est vrai que si les valeurs sont égales ET supérieures à 0
		return de1.getValeur() == de2.getValeur() && de1.getValeur() > 0;
	}
}	
