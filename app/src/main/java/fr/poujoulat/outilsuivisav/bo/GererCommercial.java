package fr.poujoulat.outilsuivisav.bo;

public class GererCommercial {

    private int id;
    private String identifiant;
    private String motDePasse;

    public GererCommercial() {
    }

    public GererCommercial(int id, String identifiant, String motDePasse) {
        this.id = id;
        this.identifiant = identifiant;
        this.motDePasse = motDePasse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
