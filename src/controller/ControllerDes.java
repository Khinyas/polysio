package controller;

import model.ModelDes;

public class ControllerDes {
	ModelDes de1 = new ModelDes();
	ModelDes de2 = new ModelDes();
public int auClicLancerDes() {
	de1.lancer();
	de2.lancer();
	
	return de1.getValeur() + de2.getValeur();	
}

//public int getValeurDe1() { return de1.getValeur(); } // trouver un autre moyen pour l'affichage des dés en gif
// public int getValeurDe2() { return de2.getValeur(); }

public boolean estUnDouble() {
    return de1.getValeur() == de2.getValeur();
}
}	
