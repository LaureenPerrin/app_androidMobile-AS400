package fr.poujoulat.outilsuivisav.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.poujoulat.outilsuivisav.R;
import fr.poujoulat.outilsuivisav.adapters.GererDossierSavAdapter;
import fr.poujoulat.outilsuivisav.bo.GererCommercial;
import fr.poujoulat.outilsuivisav.bo.GererDossierSav;

public class ListeDossiersSavTrieeActivity extends AppCompatActivity {

    private final String TAG = "ACOS";
    //Objet représentant le recyclerView
    private RecyclerView mRecyclerView;
    //Objet représentant l"adapter remplissant le recyclerView
    private RecyclerView.Adapter mAdapter;
    //Objet permettant de structurer l'ensemble des sous vues contenues dans le RecyclerView.
    private RecyclerView.LayoutManager mLayoutManager;
    private List<GererDossierSav> dossiers = new ArrayList<>();
    private List<GererDossierSav> listeDossiersSav = new ArrayList<>();
    private GererCommercial commercialConnecte = null;
    private String nomClient;
    private String codePostal;

    /**
     * Définition de l'action du clic sur un item.
     */
    private View.OnClickListener monClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            int position = Integer.parseInt(view.getTag().toString());
            Log.i(TAG,"POSITION : " + view.getTag());
            Intent intent = new Intent(ListeDossiersSavTrieeActivity.this, DossierSavActivity.class);
            intent.putExtra("dossier", dossiers.get(position));
            intent.putExtra("listeDossiers", (Serializable) dossiers);
            intent.putExtra("retourListeTriee", "true");
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_dossiers_sav_triee);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        //on récupère le commercial connecté, le nom du client et le code postal :
        Intent intent = getIntent();
        if (intent != null){
            if (intent.hasExtra("nomClient")){
                nomClient = getIntent().getStringExtra("nomClient");
            }
            if(intent.hasExtra("codePostal")){
                codePostal = getIntent().getStringExtra("codePostal");
            }

            commercialConnecte = getIntent().getParcelableExtra("commercial");
        }




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


        try {
            chargementDossiers();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if(nomClient != null){
            TextView tv_nomClient = findViewById(R.id.nomSocieteSaisi);
            tv_nomClient.setText(nomClient);
        }
        if(codePostal != null){
            TextView tv_codePostal = findViewById(R.id.codePostalSaisi);
            tv_codePostal.setText(codePostal);
        }
        if(dossiers.size() != 0){
            TextView tv_nombreDossiesTotal = findViewById(R.id.nombreDossierSav);
            int tailleListe = dossiers.size();
            String nombreDossiersSav = String.valueOf(tailleListe);
            tv_nombreDossiesTotal.setText(nombreDossiersSav);
        }

        chargementRecycler();

    }


    /**
     * Permet de charger le recycler view
     */
    private void chargementRecycler()
    {

        //Lie le recyclerView aux fonctionnalité offerte par le linear layout
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Liaison permettant de structurer l'ensemble des sous vues contenues dans le RecyclerView.
        mAdapter = new GererDossierSavAdapter(dossiers,monClickListener);
        mRecyclerView.setAdapter(mAdapter);
    }



    /**
     * Permet de charger les dossiers sav tries
     */
    private void chargementDossiers() throws Exception {


        //récupération de la liste des dossiers triés :
        Intent intent = getIntent();
        if (intent != null){

            if (intent.hasExtra("listeDossiers")){
                dossiers = intent.getParcelableArrayListExtra("listeDossiers");
            }

        }


    }


    public void retourListeDossiers(View view) {
        Intent intent = new Intent(ListeDossiersSavTrieeActivity.this, ListeDossiersSAVActivity.class);
        intent.putExtra("commercial", commercialConnecte);
        startActivity(intent);
    }
}
