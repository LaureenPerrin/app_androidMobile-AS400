package fr.poujoulat.outilsuivisav.bo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GererCommercialTest {
    private GererCommercial commercial;
    private String[] identifiantsOK;
    private String[] passwordsOK;
    private String[] identifiantsKO;
    private String[] paswwordsKO;
    @Before
    public void setUp() throws Exception {
        //jeu de test :
        commercial = new GererCommercial();
        identifiantsOK = new String[]{ "t.titi@domaine.com", "tit_.-i@domaine.com", "tit_.-i@domaine.co", "tit_.-i@domai_ne.co" };
        passwordsOK = new String[]{ "Thjuio78", "jikoPL9089", "087hjikuoJIKO" };
        identifiantsKO = new String[]{ "GThyu_.$@domaine.com", "@domaine.com", "tit_.-idomaine.co", "tit_.-i@.co", "tit_.-i@.", "tit_.-i890@.comm"};
        paswwordsKO = new String[]{ "Thjuio7", "ThtyhuKOLPjuio7980", "Thgujikol", "5678hjuik", "HUJIKO678" };
    }

    @Test
    public void isValidIdentifiant() {
        for (String id : identifiantsOK) {
            assertEquals(true, commercial.isValidIdentifiant(id));
            System.out.println(id.toString() + " est valide : " + commercial.isValidIdentifiant(id));
        }

        for (String id : identifiantsKO) {
            assertEquals(false, commercial.isValidIdentifiant(id));
            System.out.println(id.toString() + " est valide : " + commercial.isValidIdentifiant(id));
        }

    }

    @Test
    public void isValidPassword() {
        for (String pass : passwordsOK) {
            assertEquals(true, commercial.isValidPassword(pass));
            System.out.println(pass.toString() + " est valide : " + commercial.isValidIdentifiant(pass));
        }

        for (String pass : paswwordsKO) {
            assertEquals(false, commercial.isValidPassword(pass));
            System.out.println(pass.toString() + " est valide : " + commercial.isValidIdentifiant(pass));
        }

    }
}