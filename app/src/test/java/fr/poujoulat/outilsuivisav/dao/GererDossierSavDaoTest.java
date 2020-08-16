package fr.poujoulat.outilsuivisav.dao;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import fr.poujoulat.outilsuivisav.bo.GererDossierSav;

import static org.junit.Assert.assertArrayEquals;

public class GererDossierSavDaoTest {
private List<GererDossierSav> liste1 ;
    private List<GererDossierSav> liste2 = null;
    private  GererDossierSav dossier1;
private GererDossierSav dossier2;
    @Before
    public void setUp() throws Exception {
        GererDossierSavDao gererDossierSavDao = new GererDossierSavDao();
         dossier1 = new GererDossierSav();
        dossier1.setId(34567);
        dossier1.setNomClient("toto");

         dossier2 = new GererDossierSav();
        dossier2.setId(34967);
        dossier2.setNomClient("titi");
        liste1.add(dossier1);
        liste1.add(dossier2);
        //  liste2 = GererDossierSavDao.listeDossiersSavTotal();
        liste2.add(dossier1);
        liste2.add(dossier2);


    }

    @Test
    public void listeDossiersSavTotal() throws SQLException {


if(liste1 != null && liste2 != null){
    assertArrayEquals(liste2.toArray(), liste1.toArray());
}

    }

   /* @Test
    public void listeDossiersSavTriee() {
    }*/
}