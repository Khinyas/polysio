package model;




public class ModelCase {
	
	private int id;
	private String nom;
	private int positionY;
	private String typeCase;
	private int prix;
	private int positionId;
	private int positionX;
	private int loyerNu;
	private int idCouleur;
	private String cheminSvg;
	
	
	public String getCheminSvg() {
		return cheminSvg;
	}



	public void setCheminSvg(String cheminSvg) {
		this.cheminSvg = cheminSvg;
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


	
	
	public ModelCase(int idP, String nomP, String typeCaseP, int positionXP,int positionYP, int prixP, int idCouleurP, String cheminSvgP) {
		this.id = idP;
		this.nom = nomP;
		this.typeCase = typeCaseP;
		this.positionX = positionXP;
		this.positionY = positionYP;
		
		this.prix = prixP;
		this.idCouleur = idCouleurP;
		this.cheminSvg = cheminSvgP;
		
	}
	
	

	public String getTypeCase() {
		return typeCase;
	}



	public void setTypeCase(String typeCase) {
		this.typeCase = typeCase;
	}



	public int getIdCouleur() {
		return idCouleur;
	}



	public void setIdCouleur(int idCouleur) {
		this.idCouleur = idCouleur;
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

	public int getPrix() {
		// TODO Auto-generated method stub
		return prix;
	}

}
