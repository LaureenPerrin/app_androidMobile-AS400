package fr.poujoulat.outilsuivisav.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.poujoulat.outilsuivisav.bo.GererDossierSav;

public class Test {

    private static List<GererDossierSav> listeDossiersSav = new ArrayList<>();

    public static void main(String[] args) throws SQLException {
Connection cnx = BddConnexion.getConnection();
        Statement state = cnx.createStatement();
        ResultSet rs = state.executeQuery("SELECT saerf1, saeda1_fmt, saerf2, clrais, saerf3, saecau_des, saests_des, saety1_des FROM FLV00TST.SAVENTV1 " +
                " where saeeta='NI' and saelit='SAV' and   saests <3 order by saeda1 desc");

        while(rs.next()) {
            GererDossierSav dossier = new GererDossierSav();
            // if (rs.getInt("N° dossier") != dossier.getId()) {
            dossier.setId(rs.getInt("saerf1"));
            dossier.setDateCreation(rs.getString("saeda1_fmt"));
            dossier.setNomClient(rs.getString("clrais"));
            dossier.setNumCommande(rs.getInt("saerf3"));
            dossier.setCause(rs.getString("saecau_des"));
            dossier.setStatut(rs.getString("saests_des"));
            dossier.setType(rs.getString("saety1_des"));
            //   }

            listeDossiersSav.add(dossier);
            System.out.println(dossier.getId() + "/" + dossier.getDateCreation() + "/" + dossier.getNomClient() + "/" + dossier.getNumCommande() + "/" + dossier.getCause() + "/" + dossier.getStatut() + "/" + dossier.getType());
        }
state.close();
        rs.close();
        cnx.close();
    }
}
