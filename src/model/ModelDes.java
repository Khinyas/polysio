package model;

import java.util.Random;

public class ModelDes {
	private int valeur;
    private static final int nombreDeFaces = 6;

	public int lancer() {
		Random generateur = new Random();
		int resultat = generateur.nextInt(nombreDeFaces) + 1;
		this.valeur = resultat;
		return resultat;
	}
	
	public int getValeur() {
		 return valeur;
	}
	
	
	
}

