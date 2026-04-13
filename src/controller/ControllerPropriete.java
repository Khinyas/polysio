package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import connexion.DAOAcces;

import model.ModelCase;

public class ControllerPropriete {
    public static ArrayList<ModelCase> casePlateau() {
        String reqSQL = "SELECT * FROM polysio.case_plateau ORDER BY id_case_plateau ASC";
        ArrayList<ModelCase> listeCaseBdd = new ArrayList<>();
        try (PreparedStatement pst = DAOAcces.getConnexion().prepareStatement(reqSQL);

             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                {
                    ModelCase casePlateau =  new ModelCase(
                            rs.getInt("id_case_plateau"),
                            rs.getString("nom_case"),
                            rs.getString("type_case"),
                            rs.getInt("positionX"),
                            rs.getInt("positionY"),
                            rs.getInt("prix"),
                            rs.getInt("loyer_nue"),
                            rs.getInt("loyer_batiment"),
                            rs.getInt("id_couleur"),
                            //MainApp.cfgPolysio.get("pf.case"+rs.getInt("id_case_plateau"))
                            rs.getString("chemin_svg")
                    );
                    listeCaseBdd.add(casePlateau);
                }
            }
            System.out.println("DEBUG SQL : Nombre de cases récupérées = " + listeCaseBdd.size());
            return listeCaseBdd;
        }
        catch (SQLException erreur) {
            // Un seul CATCH : erreurs SQL
            System.err.println("Erreur SQL : Liste Cases non récupérée " + erreur.getMessage());
            erreur.printStackTrace();
        }
        return listeCaseBdd;}}




