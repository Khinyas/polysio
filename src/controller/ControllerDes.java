package controller;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.ModelDes;

public class ControllerDes {
	static ModelDes de1 = new ModelDes();
	static ModelDes de2 = new ModelDes();
	
	public static ArrayList<Integer> auClicLancerDes() {
		int r1 = de1.lancer();
		int r2 = de2.lancer();
		ArrayList<Integer> resultat = new ArrayList<Integer>();
		resultat.add(r1);
		resultat.add(r2);
		return resultat;	
	}
	
	//public int getValeurDe1() { return de1.getValeur(); } // trouver un autre moyen pour l'affichage des dés en gif
	//public int getValeurDe2() { return de2.getValeur(); }
	
	public static boolean estUnDouble() {
	    return de1.getValeur() == de2.getValeur();
	}
}	
