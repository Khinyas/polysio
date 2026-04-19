 package model;

public class ModelJoueur {
	private int idJoueur;
	private int position;
	private String pseudonyme;
	private ModelJoueurCouleur couleur;
	private int pointsCompetences = 5000;
	private boolean enPrison;
	private int toursEnPrison;
	private ModelJoueurRole roleJoueur = ModelJoueurRole.JOUEUR; // Je définis la valeur par défaut du role ICI
	private int nombreToursJoues = 0;
// ToDo : Remplir CFG avec les paramètre de début de game : argent, position etc ??
	public ModelJoueur(int idJoueurP, int posP, int pcP, ModelJoueurCouleur couleurP, String pseudoP){
		this.idJoueur = idJoueurP;
		this.position = posP;
		this.pseudonyme = pseudoP;
		this.pointsCompetences= pcP;
		this.couleur = couleurP;
		this.enPrison     = false;
		this.toursEnPrison = 0;
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
		System.out.println("Nouveau solde en mémoire : " + this.pointsCompetences);
	}

	public boolean avancer(int nbCases) {
		int anciennePosition = this.position;
		this.position = (this.position + nbCases) % 40;
		return this.position < anciennePosition;
	}

	public void allerA(int positionCible) {
		this.position = positionCible;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPosition() {
		return position;
	}
	public ModelJoueurCouleur getCouleur() {
		return couleur;
	}

	public void incrementerToursEnPrison() {
		this.toursEnPrison++;
	}

	public void sortirDePrison() {
		this.enPrison      = false;
		this.toursEnPrison = 0;
	}

	public int getNombreToursJoues() {
		return nombreToursJoues;
	}

	public void setNombreToursJoues(int nombreToursJoues) {
		this.nombreToursJoues = nombreToursJoues;
	}

	public String getPseudonyme() {
		return pseudonyme;
	}

	public void setPseudonyme(String pseudonyme) {
		this.pseudonyme = pseudonyme;
	}

	@Override
	public String toString() {
		return "Joueur :{" + pseudonyme
				+ ", pos :" + position
				+ ", argent :" + pointsCompetences
				+ ", couleur :" + couleur
				+ ", enPrison :" + enPrison
				+ ", toursEnPrison :" + toursEnPrison
				+ ", toursEnPrison :" + toursEnPrison
				+ ", nombre de tour(s) joue(s) :" + nombreToursJoues
		+ "}";
	}

}
