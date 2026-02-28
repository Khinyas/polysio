package model;

public class ModelCase {
	
	private int id;
	private String nom;
	private int position;
	private String group;
	
	public ModelCase(int idP, String nomP, String groupP, int positionP) {
		this.id = idP;
		this.nom = nomP;
		this.group = groupP;
		this.position = positionP;
	}
	
	public int getPosition() {
		return position;
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

}
