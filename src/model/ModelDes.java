package model;

import java.util.Random;

public class ModelDes {
	private int valeur;
    private static final int nombreDeFaces = 6;
    private  Random generateur;
	
	


	public static int lancer() {
		Random generateur = new Random();
		int valeur = generateur.nextInt(nombreDeFaces) + 1;
		return valeur;
	}
	
	public int getValeur() {
		 return valeur;
	}
	
}
