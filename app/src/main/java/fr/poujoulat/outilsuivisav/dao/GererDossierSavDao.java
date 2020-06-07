package fr.poujoulat.outilsuivisav.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.poujoulat.outilsuivisav.ListeDossiersSAVActivity;
import fr.poujoulat.outilsuivisav.bo.GererDossierSav;

public class GererDossierSavDao {
   
    //Objet Connection
    private static Connection connect;

    private static List<GererDossierSav> listeDossiersSav = new ArrayList<>();

    private static final String SELECT_ALL = "SELECT saerf1, saeda1_fmt, saerf2, clrais, saerf3, saecau_des, saests_des, saety1_des FROM FLV00TST.SAVENTV1" +
            " where saeeta='NI' and saelit='SAV' and   saests <3 order by saeda1 desc";

    private static final String SELECT_LISTE_NAMEC =  "SELECT saerf1, saeda1_fmt, saerf2, clrais, clcodp, saerf3, saecau_des, saests_des, saety1_des FROM FLV00TST.SAVENTV1 " +
            " where saeeta='NI' and saelit='SAV' and   saests <3 and clrais = '?' order by saeda1 desc";

    private static final String SELECT_LISTE_CODEP =  "SELECT saerf1, saeda1_fmt, saerf2, clrais, clcodp, saerf3, saecau_des, saests_des, saety1_des FROM FLV00TST.SAVENTV1 " +
            " where saeeta='NI' and saelit='SAV' and saests <3 and clcodp = '?' order by saeda1 desc";

    private static final String SELECT_LISTE_NAME_CODEP =  "SELECT saerf1, saeda1_fmt, saerf2, clrais, clcodp, saerf3, saecau_des, saests_des, saety1_des FROM FLV00TST.SAVENTV1 " +
            " where saeeta='NI' and saelit='SAV' and   saests <3 and clrais = '?' and clcodp = '?' order by saeda1 desc";


    public GererDossierSavDao(ListeDossiersSAVActivity listeDossiersSAVActivity) {
    }


    public static List<GererDossierSav> listeDossiersSavTotal() throws SQLException, ClassNotFoundException {

           try {
                Connection cnx = null;
                Statement state = null;

                ResultSet rs = null;
                try {
                     cnx = BddConnexion.getConnection();
                     state = cnx.createStatement();
                     rs = state.executeQuery(SELECT_ALL);

                    while(rs.next()) {
                        GererDossierSav dossier = new GererDossierSav();

                            dossier.setId(rs.getInt("saerf1"));
                            dossier.setDateCreation(rs.getString("saeda1_fmt"));
                            dossier.setNomClient(rs.getString("clrais"));
                            dossier.setNumCommande(rs.getInt("saerf3"));
                            dossier.setCause(rs.getString("saecau_des"));
                            dossier.setStatut(rs.getString("saests_des"));
                            dossier.setType(rs.getString("saety1_des"));

                        listeDossiersSav.add(dossier);
                        System.out.println(dossier.getId() + "/" + dossier.getDateCreation() + "/" + dossier.getNomClient() + "/" + dossier.getNumCommande() + "/" + dossier.getCause() + "/" + dossier.getStatut() + "/" + dossier.getType());
                    }

                    return listeDossiersSav;

                } finally {

                   state.close();
                   rs.close();
                   cnx.close();

                }


        } catch (Exception e) {
               e.printStackTrace();
           }

               return listeDossiersSav;
    }



    public static List<GererDossierSav> listeDossiersSavTriee(String nomClient, String codePostal) throws SQLException, ClassNotFoundException {

        try {
            Connection cnx = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {

                //si les deux paramètres sont renseignés :
                if((nomClient != null && !nomClient.isEmpty()) && (codePostal != null && !codePostal.isEmpty())){
                    cnx = BddConnexion.getConnection();

                    pstmt = cnx.prepareStatement(SELECT_LISTE_NAME_CODEP);

                        pstmt.setString(1, nomClient);
                        pstmt.setString(2, codePostal);

                    rs = pstmt.executeQuery();


                    while(rs.next()) {
                        GererDossierSav dossier = new GererDossierSav();

                        dossier.setId(rs.getInt("saerf1"));
                        dossier.setDateCreation(rs.getString("saeda1_fmt"));
                        dossier.setNomClient(rs.getString("clrais"));
                        dossier.setCodePostal(rs.getString("clcodp"));
                        dossier.setNumCommande(rs.getInt("saerf3"));
                        dossier.setCause(rs.getString("saecau_des"));
                        dossier.setStatut(rs.getString("saests_des"));
                        dossier.setType(rs.getString("saety1_des"));

                        listeDossiersSav.add(dossier);
                        System.out.println(dossier.getId() + "/" + dossier.getDateCreation() + "/" + dossier.getNomClient() + "/" + dossier.getCodePostal() + "/" + dossier.getNumCommande() + "/" + dossier.getCause() + "/" + dossier.getStatut() + "/" + dossier.getType());
                    }

                }//fin du if
                else {
                    //si le nom client est renseigné :
                    if (nomClient != null && !nomClient.isEmpty()) {

                        cnx = BddConnexion.getConnection();

                        pstmt = cnx.prepareStatement(SELECT_LISTE_NAMEC);

                        pstmt.setString(1, nomClient);

                        rs = pstmt.executeQuery();


                        while (rs.next()) {
                            GererDossierSav dossier = new GererDossierSav();

                            dossier.setId(rs.getInt("saerf1"));
                            dossier.setDateCreation(rs.getString("saeda1_fmt"));
                            dossier.setNomClient(rs.getString("clrais"));
                            dossier.setCodePostal(rs.getString("clcodp"));
                            dossier.setNumCommande(rs.getInt("saerf3"));
                            dossier.setCause(rs.getString("saecau_des"));
                            dossier.setStatut(rs.getString("saests_des"));
                            dossier.setType(rs.getString("saety1_des"));

                            listeDossiersSav.add(dossier);
                            System.out.println(dossier.getId() + "/" + dossier.getDateCreation() + "/" + dossier.getNomClient() + "/" + dossier.getCodePostal() + "/" + dossier.getNumCommande() + "/" + dossier.getCause() + "/" + dossier.getStatut() + "/" + dossier.getType());
                        }
                    }//fin du if
                    //si le code postal est renseigné :
                    if (codePostal != null && !codePostal.isEmpty()) {


                        cnx = BddConnexion.getConnection();

                        pstmt = cnx.prepareStatement(SELECT_LISTE_CODEP);

                        pstmt.setString(1, codePostal);

                        rs = pstmt.executeQuery();




                        while (rs.next()) {
                            GererDossierSav dossier = new GererDossierSav();

                            dossier.setId(rs.getInt("saerf1"));
                            dossier.setDateCreation(rs.getString("saeda1_fmt"));
                            dossier.setNomClient(rs.getString("clrais"));
                            dossier.setCodePostal(rs.getString("clcodp"));
                            dossier.setNumCommande(rs.getInt("saerf3"));
                            dossier.setCause(rs.getString("saecau_des"));
                            dossier.setStatut(rs.getString("saests_des"));
                            dossier.setType(rs.getString("saety1_des"));

                            listeDossiersSav.add(dossier);
                            System.out.println(dossier.getId() + "/" + dossier.getDateCreation() + "/" + dossier.getNomClient() + "/" + dossier.getCodePostal() + "/" + dossier.getNumCommande() + "/" + dossier.getCause() + "/" + dossier.getStatut() + "/" + dossier.getType());
                        }
                    }//fin du if

                }

                return listeDossiersSav;

            } finally {

                pstmt.close();
                rs.close();
                cnx.close();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return listeDossiersSav;
    }

}
