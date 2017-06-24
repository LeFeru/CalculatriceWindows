package com.example.rachid.calculatricewindows;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rachid on 14/03/17.
 */
public class CalculatriceModel {

    //*****Opérande*****
    private final String plus = "+";
    private final String moins = "—";
    private final String diviseur = "÷";
    private final String fois = "×";

    //*****Actions*****
    private final String ce = "CE";
    private final String c = "C";
    private final String retour = "\u232b";
    private final String egal = "=";
    private final String plusOuMoins = "±";

    //*****Attributs Model*****
    private ArrayList<String> toCompute = new ArrayList<String>();
    private String nombreEnCours = "0";
    private String dernierOperateur = plus;
    private String derniereOperande = "0";

    //*****Attributs Controller*****
    private List<OnModelChangeListener> toNotify = new ArrayList<OnModelChangeListener>();

    //*****Attributs View*****
    private String toDisplay = "0";

    /*  Méthodes Model */
    public void addModelChangeListener(OnModelChangeListener listener){
        toNotify.add(listener);
    }
    //CE C fonctionne pas
    public void ajouterNombre(char elt){
        ajouter(""+elt);
    }
    public void ajouterOperation(String elt){
        switch(elt)
        {
            case diviseur:
                ajouter(diviseur);
                break;
            case fois:
                ajouter(fois);
                break;
            case moins:
                ajouter(moins);
                break;
            case plus:
                ajouter(plus);
                break;
            default:
                break;
        }
    }
    public void effectuerAction(String elt){
        switch(elt)
        {
            case ce:
                supprimerNbEnCours();
                break;
            case c:
                effacerCalcul();
                break;
            case retour:
                supprimerDernierChiffre();
                break;
            case egal:
                egal();
                break;
            case plusOuMoins:
                neg();
                break;
            default:
                break;
        }
    }
    private BigDecimal compute(){
        if(toCompute == null)
            return new BigDecimal(0);
        if(toCompute.isEmpty())
            return new BigDecimal(0);
        BigDecimal result;
        if(toCompute.size() == 1){
            result = new BigDecimal(toCompute.get(0));
            switch(dernierOperateur){
                case plus:
                    result = result.add(new BigDecimal(derniereOperande));
                    break;
                case moins:
                    result = result.subtract(new BigDecimal(derniereOperande));
                    break;
                case fois:
                    result = result.multiply(new BigDecimal(derniereOperande));
                    break;
                case diviseur:
                    BigDecimal temp = new BigDecimal(derniereOperande);
                    if(temp.equals(new BigDecimal(0))){
                        break;
                    }
                    result = result.divide(temp);
                    break;
            }
        }
        else{
            result = new BigDecimal(0);
            String operation = null;
            for(int i = 0; i < toCompute.size();){
                String elt = toCompute.remove(i);
                if(elt.matches("((-\\d)|(\\d)|(\\.))+")){
                    derniereOperande = elt;
                    if(operation == null){
                        result = result.add(new BigDecimal(elt));
                        continue;
                    }
                    switch (operation){
                        case plus:
                            result = result.add(new BigDecimal(elt));
                            operation = null;
                            break;
                        case moins:
                            result = result.subtract(new BigDecimal(elt));
                            operation = null;
                            break;
                        case fois:
                            result = result.multiply(new BigDecimal(elt));
                            operation = null;
                            break;
                        case diviseur:
                            operation = null;
                            BigDecimal temp = new BigDecimal(elt);
                            if(temp.equals(new BigDecimal(0))){
                                continue;
                            }
                            result = result.divide(temp);
                            break;
                        default:
                            operation = null;
                    }
                }
                else if(elt.matches("(\\+)|(\\—)|(\\×)|(\\÷)")){
                    operation = elt;
                    dernierOperateur = operation;
                }
            }
        }
        toCompute = new ArrayList<String>();
        nombreEnCours = "";
        return result;
    }

    private void neg(){
        if(nombreEnCours.contains("-")){
            nombreEnCours = nombreEnCours.replaceAll("-","");
        }
        else {
            nombreEnCours = "-" + nombreEnCours;
        }
        toDisplay=nombreEnCours;
        notifyModelChangeListeners();
    }
    /* Méthodes Controller */
    private void egal(){
        if(nombreEnCours != null && !nombreEnCours.isEmpty()){
            toCompute.add(nombreEnCours);
        }
        BigDecimal result = compute();
        nombreEnCours = result.toString();
        toDisplay = nombreEnCours;
        notifyModelChangeListeners();
    }
    private void ajouter(String obj) {
        if (obj == null || toCompute == null)
            return;
        if (obj.matches("(\\d)|(\\.)")) {
            if(!obj.matches("\\.") || !nombreEnCours.contains(".")){
                if(nombreEnCours.matches("^0+$")){
                    nombreEnCours = obj;
                }
                else if(nombreEnCours.matches("^-0+$")){
                    nombreEnCours = "-"+obj;
                }
                else{
                    nombreEnCours+=obj;
                }
                toDisplay = nombreEnCours;
            }

        } else {
            toCompute.add(nombreEnCours);
            nombreEnCours = "";
            toCompute.add(obj);
        }
        notifyModelChangeListeners();
    }

    private void effacerCalcul(){
        toCompute = new ArrayList<String>();
        nombreEnCours="0";
        dernierOperateur=plus;
        derniereOperande= "0";
        toDisplay=nombreEnCours;
        notifyModelChangeListeners();
    }

    private void supprimerDernierChiffre(){
        if(nombreEnCours != null && !nombreEnCours.isEmpty()){
            nombreEnCours = nombreEnCours.substring(0,nombreEnCours.length() - 1);
            toDisplay = nombreEnCours;
            notifyModelChangeListeners();
        }
    }

    private void supprimerNbEnCours(){
        nombreEnCours = "0";
        toDisplay = nombreEnCours;
        notifyModelChangeListeners();
    }

    private void notifyModelChangeListeners(){
        for (OnModelChangeListener listener: toNotify) {
            listener.notifyModelChange();
        }
    }
    public String getCalculEnCours(){
        if(toCompute == null || toCompute.isEmpty()){
            return "";
        }
        String toString = "";
        for(String elt: toCompute){
            toString+=""+elt;
        }
        return toString+nombreEnCours;
    }

    public String getNombreEnCours() {
        return nombreEnCours;
    }

    public String getToDisplay() {
        return toDisplay;
    }
}
