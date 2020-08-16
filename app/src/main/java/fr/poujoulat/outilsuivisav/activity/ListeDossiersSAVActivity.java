package fr.poujoulat.outilsuivisav.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.poujoulat.outilsuivisav.R;
import fr.poujoulat.outilsuivisav.adapters.GererDossierSavAdapter;
import fr.poujoulat.outilsuivisav.bo.GererCommercial;
import fr.poujoulat.outilsuivisav.bo.GererDossierSav;
import fr.poujoulat.outilsuivisav.dao.GererDossierSavDao;

public class ListeDossiersSAVActivity extends AppCompatActivity {

    private final String TAG = "ACOS";
    //Objet représentant le recyclerView
    private RecyclerView mRecyclerView;
    //Objet représentant l"adapter remplissant le recyclerView
    private RecyclerView.Adapter mAdapter;
    //Objet permettant de structurer l'ensemble des sous vues contenues dans le RecyclerView.
    private RecyclerView.LayoutManager mLayoutManager;

    private List<GererDossierSav> dossiers = new ArrayList<>();
    //  private List<GererDossierSav> listeDossiersSav = new ArrayList<>();
    private GererCommercial commercialConnecte = null;
    private String bonjourUser;

    /**
     * Définition de l'action du clic sur un item.
     */
    private View.OnClickListener monClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = Integer.parseInt(view.getTag().toString());
            Log.i(TAG, "POSITION : " + view.getTag());
            Intent intent = new Intent(ListeDossiersSAVActivity.this, DossierSavActivity.class);
            intent.putExtra("dossier", dossiers.get(position));
            intent.putExtra("commercial", commercialConnecte);
            startActivity(intent);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_dossiers_sav);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());


        //on récupère le commercial connecté :
        commercialConnecte = getIntent().getParcelableExtra("commercial");
        TextView userConnecte = findViewById(R.id.nomUserConnecte);
        String bonjour = getString(R.string.bonjour);
     
        if (commercialConnecte != null) {
            String bonjourUser = bonjour + " " + commercialConnecte.getPrenom() + " " + commercialConnecte.getNom();
            userConnecte.setText(bonjourUser);

        }


        try {
            chargementDossiers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        chargementRecycler();

    }

    /**
     * Permet de charger le recycler view
     */
    private void chargementRecycler() {

        //Lie le recyclerView aux fonctionnalité offerte par le linear layout
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Liaison permettant de structurer l'ensemble des sous vues contenues dans le RecyclerView.
        mAdapter = new GererDossierSavAdapter(dossiers, monClickListener);
        mRecyclerView.setAdapter(mAdapter);
    }


    /**
     * Permet de charger les dossiers sav
     */
    private void chargementDossiers() throws Exception {
        Log.d("dao", "fonction dao appellée");

        //récupération de la liste des dossiers:
        dossiers = GererDossierSavDao.listeDossiersSavTotal();

    }

    /**
     * Permet de charger les dossiers sav triés
     */
    private void chargementDossiersTries(String nomclientSaisi, String codePostalSaisi) throws Exception {


        try {
            //  listeDossiersSav = GererDossierSavDao.listeDossiersSavTriee(nomclientSaisi, codePostalSaisi);
            //récupération de la liste des dossiers:
            dossiers = GererDossierSavDao.listeDossiersSavTriee(nomclientSaisi, codePostalSaisi);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void listeDossierTriee(View view) throws Exception {
        //récupération des texview et editview :
        EditText nomclientTv = (EditText) findViewById(R.id.nomSocieteSaisi);
        EditText codePostalTv = (EditText) findViewById(R.id.codePostalSaisi);
        TextView msgChampVide = findViewById(R.id.msg_champsVides);
        TextView TV_msg_erreur_nomClient = findViewById(R.id.msg_erreur_nomSociete);
        TextView TV_msg_erreur_codePostal = findViewById(R.id.msg_erreur_codePostal);

        String nomclientSaisi = nomclientTv.getText().toString();
        String codePostalSaisi = codePostalTv.getText().toString();

        //échappement des balises HTML contre la faille XSS:
        String nomClientRecupere = TextUtils.htmlEncode(nomclientSaisi);
        String codePostalRecupere = TextUtils.htmlEncode(codePostalSaisi);

        nomClientRecupere = nomClientRecupere.toUpperCase();


        //si un des champs est renseigné :
        if (!nomClientRecupere.isEmpty() | !codePostalRecupere.isEmpty()) {
            msgChampVide.setText("");
            TV_msg_erreur_nomClient.setText("");
            TV_msg_erreur_codePostal.setText("");

            //Vérification de la saisi du nom client et du code postal :
            GererDossierSav dossier = new GererDossierSav();
            boolean verificationNomClient = dossier.isValidNomClient(nomClientRecupere);
            boolean verificationCodePostal = dossier.isValidCodePostal(codePostalRecupere);
            //vérification si le nomClient et le code postal sont biens structurés :
            if (!nomClientRecupere.isEmpty() && !verificationNomClient) {

                String msg_erreur_nomClient = getString(R.string.msg_erreur_nomClient);
                TV_msg_erreur_nomClient.setText(msg_erreur_nomClient);
            }

            if (!codePostalRecupere.isEmpty() && !verificationCodePostal) {

                String msg_erreur_codePostal = getString(R.string.msg_erreur_codePostal);
                TV_msg_erreur_codePostal.setText(msg_erreur_codePostal);
            }


            if (!nomClientRecupere.isEmpty() && !codePostalRecupere.isEmpty()) {
                //si le nom client et le code postal sont valides :
                if (verificationNomClient && verificationCodePostal) {

                    //récupération liste dossier triés :
                   chargementDossiersTries(nomClientRecupere, codePostalRecupere);

                    if(dossiers.size() == 0){
                        TV_msg_erreur_nomClient.setText("");
                        TV_msg_erreur_codePostal.setText("");
                        //on renseigne le message d'erreur :
                        String erreurAucuneListeTrouvee = getString(R.string.erreurListeTrouveeVide);
                        msgChampVide.setText(erreurAucuneListeTrouvee);
                    } else {
                        Intent intent = new Intent(this, ListeDossiersSavTrieeActivity.class);
                        intent.putExtra("listeDossiers", (Serializable) dossiers);
                        intent.putExtra("commercial", commercialConnecte);
                        intent.putExtra("nomClient", nomClientRecupere);
                        intent.putExtra("codePostal", codePostalRecupere);
                        startActivity(intent);
                    }


                }
            } else {

                if (!nomClientRecupere.isEmpty()) {
                    //si le nom client est  valide :
                    if (verificationNomClient) {

                        //récupération liste dossier triés :
                        chargementDossiersTries(nomClientRecupere, codePostalRecupere);

                        if(dossiers.size() == 0){
                            TV_msg_erreur_nomClient.setText("");
                            TV_msg_erreur_codePostal.setText("");
                            //on renseigne le message d'erreur :
                            String erreurAucuneListeTrouvee = getString(R.string.erreurListeTrouveeVide);
                            msgChampVide.setText(erreurAucuneListeTrouvee);
                        } else {
                            Intent intent = new Intent(this, ListeDossiersSavTrieeActivity.class);
                            intent.putExtra("listeDossiers", (Serializable) dossiers);
                            intent.putExtra("commercial", commercialConnecte);
                            intent.putExtra("nomClient", nomClientRecupere);
                            intent.putExtra("codePostal", codePostalRecupere);
                            startActivity(intent);
                        }

                    }
                }

                if (!codePostalRecupere.isEmpty()) {
                    //si le code postal est  valide :
                    if (verificationCodePostal) {

                        //récupération liste dossier triés :
                        chargementDossiersTries(nomClientRecupere, codePostalRecupere);

                        //si la liste est vide donc aucun résultat trouvé :
                        if(dossiers.size() == 0){
                            TV_msg_erreur_nomClient.setText("");
                            TV_msg_erreur_codePostal.setText("");
                            //on renseigne le message d'erreur :
                            String erreurAucuneListeTrouvee = getString(R.string.erreurListeTrouveeVide);
                            msgChampVide.setText(erreurAucuneListeTrouvee);
                        } else {
                            Intent intent = new Intent(this, ListeDossiersSavTrieeActivity.class);
                            intent.putExtra("listeDossiers", (Serializable) dossiers);
                            intent.putExtra("commercial", commercialConnecte);
                            intent.putExtra("nomClient", nomClientRecupere);
                            intent.putExtra("codePostal", codePostalRecupere);
                            startActivity(intent);
                        }

                    }
                }
            }


        //s'ils sont tous vides :
        } else {
            //génération message d'erreur champs vides :
            TV_msg_erreur_nomClient.setText("");
            TV_msg_erreur_codePostal.setText("");
            String erreurChampsVidesMsg = getString(R.string.erreurChampsVidesCriteres);
            msgChampVide.setText(erreurChampsVidesMsg);
        }
    }
}