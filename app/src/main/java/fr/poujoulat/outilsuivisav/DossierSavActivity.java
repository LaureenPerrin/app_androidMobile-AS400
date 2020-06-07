package fr.poujoulat.outilsuivisav;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import fr.poujoulat.outilsuivisav.bo.GererDossierSav;

public class DossierSavActivity extends AppCompatActivity {

    GererDossierSav dossier = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dossiersav);

        dossier = getIntent().getParcelableExtra("dossier");

        TextView titreDossier = findViewById(R.id.tv_cv_titreIdDossier);
        TextView id = findViewById(R.id.tv_cv_idDossier);
        TextView titreDate = findViewById(R.id.tv_cv_titreDatecrea);
        TextView dateCrea = findViewById(R.id.tv_cv_dateCrea);
        TextView titreNomClient = findViewById(R.id.tv_cv_titreNomClient);
        TextView  nomClient = findViewById(R.id.tv_cv_nomClient);
        TextView titreStatut = findViewById(R.id.tv_cv_titreStatut);
        TextView statut = findViewById(R.id.tv_cv_statut);
        TextView titreType = findViewById(R.id.tv_cv_titreType);
        TextView  type = findViewById(R.id.tv_cv_type);
        TextView titreCause = findViewById(R.id.tv_cv_titreCause);
        TextView cause = findViewById(R.id.tv_cv_cause);
        TextView titreCommande = findViewById(R.id.tv_cv_titreNumeroCommande);
        TextView numeroCommande = findViewById(R.id.tv_cv_numeroCommande);

        titreDossier.setText(R.string.id_dossier);
        id.setText(String.valueOf(dossier.getId()));
        titreDate.setText(R.string.date_creation);
        dateCrea.setText(dossier.getDateCreation());
        titreNomClient.setText(R.string.nom_client);
        nomClient.setText(dossier.getNomClient());
        titreStatut.setText(R.string.statut);
        statut.setText(dossier.getStatut());
        titreType.setText(R.string.type);
        type.setText(dossier.getType());
        titreCause.setText(R.string.cause);
        cause.setText(dossier.getCause());
        titreCommande.setText(R.string.numCommande);
        numeroCommande.setText(String.valueOf(dossier.getNumCommande()));
    }


    public void retourListeDossier(View view) {
        Intent intent = new Intent(this, ListeDossiersSAVActivity.class);
        startActivity(intent);
    }

    public void retour(View view) {
        Intent intent = new Intent(this, ListeDossiersSAVActivity.class);
        startActivity(intent);
    }
}
