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

}
