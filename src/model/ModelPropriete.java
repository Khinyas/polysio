package model;

import controller.ControllerPlateau;

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
    private int casePlateau;

    public ModelPropriete(int idP, boolean batimentP, int casePlateauP, String cheminSvgP, int loyerNuP, int loyerBatimentP, int idCouleurP,  int prixP, String nomProprieteP, String typeCaseP) {
        this.id = idP;
        this.batiment = batimentP;
        this.casePlateau = casePlateauP;
        this.cheminSvg = cheminSvgP;
        this.loyerNu = loyerNuP;
        this.loyerBatiment = loyerBatimentP;
        this.idCouleur = idCouleurP;
        this.prix = prixP;
        this.nom = nomProprieteP;
        this.typeCase = typeCaseP;
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

    public int getCasePlateau() {
        return casePlateau;
    }

    public void setBatiment(boolean batiment) {
        this.batiment = batiment;
    }
}
