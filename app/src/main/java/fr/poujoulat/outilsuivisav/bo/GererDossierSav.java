package fr.poujoulat.outilsuivisav.bo;

import android.os.Parcel;
import android.os.Parcelable;

public class GererDossierSav implements Parcelable {

    private int id;
    private String dateCreation;
    private String nomClient;
    private String codePostal;
    private int numCommande;
    private String cause;
    private String statut;
    private String type;


    public GererDossierSav() {
    }

    public GererDossierSav(int id, String dateCreation, String nomClient, String codePostal, int numCommande, String cause, String statut, String type) {
        this.id = id;
        this.dateCreation = dateCreation;
        this.nomClient = nomClient;
        this.codePostal = codePostal;
        this.numCommande = numCommande;
        this.cause = cause;
        this.statut = statut;
        this.type = type;

    }

    protected GererDossierSav(Parcel in) {
        id = in.readInt();
        dateCreation = in.readString();
        nomClient = in.readString();
        codePostal = in.readString();
        numCommande = in.readInt();
        cause = in.readString();
        statut = in.readString();
        type = in.readString();

    }

    public static final Creator<GererDossierSav> CREATOR = new Creator<GererDossierSav>() {
        @Override
        public GererDossierSav createFromParcel(Parcel in) {
            return new GererDossierSav(in);
        }

        @Override
        public GererDossierSav[] newArray(int size) {
            return new GererDossierSav[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public int getNumCommande() {
        return numCommande;
    }

    public void setNumCommande(int numCommande) {
        this.numCommande = numCommande;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(dateCreation);
        dest.writeString(nomClient);
        dest.writeString(codePostal);
        dest.writeInt(numCommande);
        dest.writeString(cause);
        dest.writeString(statut);
        dest.writeString(type);

    }
}
