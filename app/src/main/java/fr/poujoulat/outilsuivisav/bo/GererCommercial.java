package fr.poujoulat.outilsuivisav.bo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GererCommercial implements Parcelable {

    private int id;
    private String identifiant;
    private String motDePasse;
    private String nom;
    private String prenom;
    public static final Creator<GererCommercial> CREATOR = new Creator<GererCommercial>() {
        @Override
        public GererCommercial createFromParcel(Parcel in) {
            return new GererCommercial(in);
        }

        @Override
        public GererCommercial[] newArray(int size) {
            return new GererCommercial[size];
        }
    };


    public GererCommercial() {
    }

    public boolean isValidIdentifiant(String identifiant){
        //création de la regex :
        //commence 1 ou + minuscule ou majuscule ou chifre, 1 ou + minuscule ou majuscule ou chifre ou caractères (._-) , @ ,
        // 1 ou + minuscule ou majuscule ou chifre ou caractères (._-), . , de 2 à 3 minuscules ou majuscules  pour terminer le mail :
        String regex = "^[A-Za-z0-9]+[A-Za-z0-9_.-]+@[A-Za-z0-9+_.-]+[.]+[A-Za-z]{2,3}$";
        //création du pattern:
        Pattern pattern = Pattern.compile(regex);
        //oncompare l'identifiant et le pattern :
        Matcher matcher = pattern.matcher(identifiant);

        boolean verifcationId = matcher.matches();
        if (verifcationId) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isValidPassword(String password){
        //création de la regex :
        //entre 8 et 15 caractères max avec au moins 1 majuscule, 1 minuscule et 1 chiffre :
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,15}$";
        //création du pattern:
        Pattern pattern = Pattern.compile(regex);
        //on compare le password et le pattern :
        Matcher matcher = pattern.matcher(password);
        boolean verificationPassword = matcher.matches();

        if (verificationPassword) {
            return true;
        } else {
            return false;
        }
    }

    protected GererCommercial(Parcel in) {
        id = in.readInt();
        identifiant = in.readString();
        motDePasse = in.readString();
        nom = in.readString();
        prenom = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(identifiant);
        dest.writeString(motDePasse);
        dest.writeString(nom);
        dest.writeString(prenom);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getNom() {

        return nom;
    }

    public void setNom(String nom) {

        this.nom = nom;
    }

    public String getPrenom() {

        return prenom;
    }

    public void setPrenom(String prenom) {

        this.prenom = prenom;
    }
}
