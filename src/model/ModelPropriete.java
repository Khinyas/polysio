package model;

public class ModelPropriete {

    private int id;
    private String nom;
    private String typeCase;
    private int prix;
    private int loyerNu;
    private int loyerBatiment;
    private int idCouleur;
    private String proprietaire;
    private boolean batiment;
    private String cheminSvg;

    public ModelPropriete(int idP, String nomP, String typeCaseP, int prixP, int loyerNuP, int loyerBatimentP, int idCouleurP, String proprietaireP, boolean batimentP, String cheminSvgP) {
        this.id = idP;
        this.nom = nomP;
        this.typeCase = typeCaseP;
        this.prix = prixP;
        this.loyerNu = loyerNuP;
        this.loyerBatiment = loyerBatimentP;
        this.idCouleur = idCouleurP;
        this.proprietaire = proprietaireP;
        this.batiment = batimentP;
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

    public String getCheminSvg() {
        return cheminSvg;
    }


    public void setCheminSvg(String cheminSvg) {
        this.cheminSvg = cheminSvg;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getLoyerNu() {
        return loyerNu;
    }

    public void setLoyerNu(int loyerNu) {
        this.loyerNu = loyerNu;
    }

    public int getLoyerBatiment() {
        return loyerBatiment;
    }

    public void setLoyerBatiment(int loyerBatiment) {
        this.loyerBatiment = loyerBatiment;
    }

    public String getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(String proprietaire) {
        this.proprietaire = proprietaire;
    }

    public boolean isBatiment() {
        return batiment;
    }

    public void setBatiment(boolean batiment) {
        this.batiment = batiment;
    }
}
