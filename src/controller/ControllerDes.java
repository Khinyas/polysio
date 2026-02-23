package controller;

import model.ModelDes;

public class ControllerDes {
	ModelDes de1 = new ModelDes();
	ModelDes de2 = new ModelDes();
public void auClicLancerDes() {
	de1.lancer();
	de2.lancer();
	
	int totalDes = de1.getValeur() + de2.getValeur();


if (de1.getValeur() == de2.getValeur()) {
	System.out.println("Double ! Relancez !");
	
	
}

}}
