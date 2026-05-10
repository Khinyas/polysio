package model;

public class ModelCase {
	
	private int id;
	private String nom;
	private int positionY;
	private String typeCase;
	private int positionId;
	private int positionX;
	private String cheminPng;

	public ModelCase(int idP, String nomP, String typeCaseP, int positionXP,int positionYP, String cheminSvgP, String cheminPngP) {
		this.id = idP;
		this.nom = nomP;
		this.typeCase = typeCaseP;
		this.positionX = positionXP;
		this.positionY = positionYP;
		this.cheminPng = cheminPng;
		this.cheminPng = cheminPngP;
		
	}

	public int getPositionId() {
		return positionId;
	}


	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}


	public int getPositionX() {
		return positionX;
	}


	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}


	public int getPositionY() {
		return positionY;
	}


	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}


	public String getTypeCase() {
		return typeCase;
	}


	public void setTypeCase(String typeCase) {
		this.typeCase = typeCase;
	}


	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getId() {
		return id;
	}
	
	public String getCheminSvg() {
		return cheminPng;
	}

	public String getCheminPng() {
		return cheminPng;
	}

	public void setCheminPng(String cheminPng) {
		this.cheminPng = cheminPng;
	}

	public void setCheminSvg(String cheminSvg) {
		this.cheminPng = cheminSvg;
	}

}
