package com.example.rachid.calculatricewindows;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class CalculatriceActivity extends AppCompatActivity implements OnModelChangeListener{

    private List<Integer> clavierNombres = Arrays.asList(R.id.b0, R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7, R.id.b8, R.id.b9, R.id.b_virgule);
    private List<Integer> clavierOperateurs = Arrays.asList(R.id.b_plus, R.id.b_moins, R.id.b_division, R.id.b_fois);
    private List<Integer> clavierActions = Arrays.asList(R.id.b_c, R.id.b_ce, R.id.b_retour, R.id.b_egal, R.id.b_plusmoins);
    private List<Integer> ecrans = Arrays.asList(R.id.result, R.id.historique);
    private CalculatriceModel calculatriceModel = null;
    private Bundle savedInstanceState;
    private AffichageDecimalFragment affichageDecimalFragment;
    private AffichageBinaireFragment affichageBinaireFragment;
    private AffichageHexaFragment affichageHexaFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalculatriceApplication calculatriceApplication = (CalculatriceApplication) getApplication();
        calculatriceModel = calculatriceApplication.getCalculatriceModel();
        calculatriceModel.addModelChangeListener(this);
        this.savedInstanceState = savedInstanceState;
        addOrReplaceAffichageDecimalFragment();
    }

    private void addOrReplaceAffichageDecimalFragment(){
        affichageDecimalFragment = new AffichageDecimalFragment();
        affichageDecimalFragment.setArguments(getIntent().getExtras());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(this.savedInstanceState != null){
            affichageDecimalFragment.setArguments(savedInstanceState);
        }
        if(getSupportFragmentManager().findFragmentById(R.id.fragment_result) != null){
            fragmentTransaction.replace(R.id.fragment_result, affichageDecimalFragment);
        }
        else{
            fragmentTransaction.add(R.id.fragment_result, affichageDecimalFragment);
        }
        fragmentTransaction.commit();
        getSupportFragmentManager().executePendingTransactions();
    }
    private void addOrReplaceAffichageHexaFragment(){
        affichageHexaFragment = new AffichageHexaFragment();
        affichageHexaFragment.setArguments(getIntent().getExtras());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(this.savedInstanceState != null){
            affichageHexaFragment.setArguments(savedInstanceState);
        }
        if(getSupportFragmentManager().findFragmentById(R.id.fragment_result) != null){
            fragmentTransaction.replace(R.id.fragment_result, affichageHexaFragment);
        }
        else{
            fragmentTransaction.add(R.id.fragment_result, affichageHexaFragment);
        }
        fragmentTransaction.commit();
        getSupportFragmentManager().executePendingTransactions();
    }
    private void addOrReplaceAffichageBinaireFragment(){
        affichageBinaireFragment = new AffichageBinaireFragment();
        affichageBinaireFragment.setArguments(getIntent().getExtras());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(this.savedInstanceState != null){
            affichageBinaireFragment.setArguments(savedInstanceState);
        }
        if(getSupportFragmentManager().findFragmentById(R.id.fragment_result) != null){
            fragmentTransaction.replace(R.id.fragment_result, affichageBinaireFragment);
        }
        else{
            fragmentTransaction.add(R.id.fragment_result, affichageBinaireFragment);
        }
        fragmentTransaction.commit();
        getSupportFragmentManager().executePendingTransactions();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }

    public void buttonOnClick(View view) {
        if(clavierNombres.contains(view.getId())){
            calculatriceModel.ajouterNombre(((Button)view).getText().charAt(0));
        }
        else if(clavierOperateurs.contains(view.getId())){
            calculatriceModel.ajouterOperation(((Button)view).getText().toString());
        }
        else if(clavierActions.contains(view.getId())){
            calculatriceModel.effectuerAction(((Button)view).getText().toString());
        }
    }

    /*private void afficherNombreEnCours() {
        TextView textView = (TextView) findViewById(R.id.result);
        textView.setText(calculatriceModel.getToDisplay());
    }*/
    private void afficherCalculEnCours(){
        TextView textView = (TextView) findViewById(R.id.historique);
        textView.setText(calculatriceModel.getCalculEnCours());
    }

    public void notifyModelChange(){
        //afficherNombreEnCours();
        afficherCalculEnCours();
    }

    public void buttonBinaire(View view){
        addOrReplaceAffichageBinaireFragment();
    }
    public void buttonDecimal(View view){
        addOrReplaceAffichageDecimalFragment();
    }

    public void buttonHexa(View view){
        addOrReplaceAffichageHexaFragment();
    }
}
