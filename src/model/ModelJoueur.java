package model;

public class ModelJoueur {
	private int idJoueur;
	private int pos;
	private int pointsCompetences;

	public ModelJoueur(int idJoueurP, int posP, int pcP){
		this.idJoueur = idJoueurP;
		this.pos = posP;
		this.pointsCompetences= pcP;		
	}
	
	public int getIdJoueur() {
		return idJoueur;
	}

	public void setIdJoueur(int idJoueur) {
		this.idJoueur = idJoueur;
	}

	public int getPointsCompetences() {
		return pointsCompetences;
	}

	public void setPointsCompetences(int pointsCompetences) {
		this.pointsCompetences = pointsCompetences;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public int getPos() {
		return pos;
	}
}
