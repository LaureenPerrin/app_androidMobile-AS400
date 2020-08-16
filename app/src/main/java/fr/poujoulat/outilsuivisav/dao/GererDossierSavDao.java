package fr.poujoulat.outilsuivisav.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.poujoulat.outilsuivisav.bo.GererDossierSav;

//classe d'accès aux données de la base DB2 :
public class GererDossierSavDao {


    private static Connection cnx = null;

    private static Statement state = null;

    private static ResultSet rs = null;

    private static PreparedStatement pstmt = null;

  //  private static List<GererDossierSav> listeDossiersSav = new ArrayList<>();

    private static GererDossierSav dossier;

    //Requêtes sql utilisées pour la liste des dossiers sav :
    private static final String SELECT_ALL = "SELECT saerf1, saeda1_fmt, saerf2, clrais, saerf3, saecau_des, saests_des, saety1_des FROM FLV00TST.SAVENTV1" +
            " where saeeta='NI' and saelit='SAV' and   saests <3 order by saeda1 desc";

    private static final String SELECT_LISTE_NAMEC =  "SELECT saerf1, saeda1_fmt, saerf2, clrais, clcodp, saerf3, saecau_des, saests_des, saety1_des FROM FLV00TST.SAVENTV1 " +
            " where saeeta='NI' and saelit='SAV' and   saests <3 and clrais LIKE CONCAT(?, '%') order by saeda1 desc";

    private static final String SELECT_LISTE_CODEP =  "SELECT saerf1, saeda1_fmt, saerf2, clrais, clcodp, saerf3, saecau_des, saests_des, saety1_des FROM FLV00TST.SAVENTV1 " +
            " where saeeta='NI' and saelit='SAV' and saests <3 and clcodp LIKE CONCAT(?, '%') order by saeda1 desc";

    private static final String SELECT_LISTE_NAME_CODEP =  "SELECT saerf1, saeda1_fmt, saerf2, clrais, clcodp, saerf3, saecau_des, saests_des, saety1_des FROM FLV00TST.SAVENTV1 " +
            " where saeeta='NI' and saelit='SAV' and   saests <3 and clrais LIKE CONCAT(?, '%') and clcodp LIKE CONCAT(?, '%') order by saeda1 desc";

    // constructeur :
    public GererDossierSavDao() {
    }

    //Fonction qui donne la liste total des dossiers sav (statut en cours et ouvert) trié par date de création dans l'ordre décroissant :
    public static List<GererDossierSav> listeDossiersSavTotal() throws SQLException {
        List<GererDossierSav> listeDossiersSav = new ArrayList<>();
                try {

                    BddCnxAsync classBddCnx = new BddCnxAsync();
                    cnx = classBddCnx.execute().get();
                     state = cnx.createStatement();
                     rs = state.executeQuery(SELECT_ALL);

                    while(rs.next()) {
                         dossier = new GererDossierSav();

                            dossier.setId(rs.getInt("saerf1"));
                            dossier.setDateCreation(rs.getString("saeda1_fmt"));
                            dossier.setNomClient(rs.getString("clrais"));
                            dossier.setNumCommande(rs.getInt("saerf3"));
                            dossier.setCause(rs.getString("saecau_des"));
                            dossier.setStatut(rs.getString("saests_des"));
                            dossier.setType(rs.getString("saety1_des"));

                        listeDossiersSav.add(dossier);

                    }
                    return listeDossiersSav;

                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } finally {
                    if(state != null){
                        state.close();
                    }
                    if(rs != null){
                        rs.close();
                    }
                   if(cnx != null){
                       cnx.close();
                   }

                }
               return listeDossiersSav;
    }


    //Fonction qui donne la liste total des dossiers sav (statut en cours et ouvert) trié par des critères saisis et par date de création dans l'ordre décroissant :
    public static List<GererDossierSav> listeDossiersSavTriee(String nomClient, String codePostal) throws SQLException, ExecutionException, InterruptedException {

        List<GererDossierSav> listeDossiersSav = new ArrayList<>();
            try {

                //si les deux paramètres sont renseignés :
                if((nomClient != null && !nomClient.isEmpty()) && (codePostal != null && !codePostal.isEmpty())){
                    BddCnxAsync classBddCnx = new BddCnxAsync();
                    cnx = classBddCnx.execute().get();
                    pstmt = cnx.prepareStatement(SELECT_LISTE_NAME_CODEP);

                    pstmt.setString(1, nomClient);
                    pstmt.setString(2, codePostal);

                    rs = pstmt.executeQuery();


                    while(rs.next()) {
                        dossier = new GererDossierSav();

                        dossier.setId(rs.getInt("saerf1"));
                        dossier.setDateCreation(rs.getString("saeda1_fmt"));
                        dossier.setNomClient(rs.getString("clrais"));
                        dossier.setCodePostal(rs.getString("clcodp"));
                        dossier.setNumCommande(rs.getInt("saerf3"));
                        dossier.setCause(rs.getString("saecau_des"));
                        dossier.setStatut(rs.getString("saests_des"));
                        dossier.setType(rs.getString("saety1_des"));

                        listeDossiersSav.add(dossier);
                    }

                }//fin du if
                else {
                    //si le nom client est renseigné :
                    if (nomClient != null && !nomClient.isEmpty()) {

                        BddCnxAsync classBddCnx = new BddCnxAsync();
                        cnx = classBddCnx.execute().get();
                        pstmt = cnx.prepareStatement(SELECT_LISTE_NAMEC);

                        pstmt.setString(1, nomClient);
                        rs = pstmt.executeQuery();

                        while (rs.next()) {
                             dossier = new GererDossierSav();

                            dossier.setId(rs.getInt("saerf1"));
                            dossier.setDateCreation(rs.getString("saeda1_fmt"));
                            dossier.setNomClient(rs.getString("clrais"));
                            dossier.setCodePostal(rs.getString("clcodp"));
                            dossier.setNumCommande(rs.getInt("saerf3"));
                            dossier.setCause(rs.getString("saecau_des"));
                            dossier.setStatut(rs.getString("saests_des"));
                            dossier.setType(rs.getString("saety1_des"));

                            listeDossiersSav.add(dossier);
                        }
                        return listeDossiersSav;
                    }//fin du if
                    //si le code postal est renseigné :
                    if (codePostal != null && !codePostal.isEmpty()) {


                        BddCnxAsync classBddCnx = new BddCnxAsync();
                        cnx = classBddCnx.execute().get();
                        pstmt = cnx.prepareStatement(SELECT_LISTE_CODEP);

                        pstmt.setString(1, codePostal);
                        rs = pstmt.executeQuery();

                        while (rs.next()) {
                             dossier = new GererDossierSav();

                            dossier.setId(rs.getInt("saerf1"));
                            dossier.setDateCreation(rs.getString("saeda1_fmt"));
                            dossier.setNomClient(rs.getString("clrais"));
                            dossier.setCodePostal(rs.getString("clcodp"));
                            dossier.setNumCommande(rs.getInt("saerf3"));
                            dossier.setCause(rs.getString("saecau_des"));
                            dossier.setStatut(rs.getString("saests_des"));
                            dossier.setType(rs.getString("saety1_des"));

                            listeDossiersSav.add(dossier);
                        }
                    }//fin du if

                }

                return listeDossiersSav;

            } finally {

                pstmt.close();
                rs.close();
                cnx.close();

            }

    }


}
