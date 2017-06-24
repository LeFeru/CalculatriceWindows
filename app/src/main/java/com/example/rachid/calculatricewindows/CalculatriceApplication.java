package com.example.rachid.calculatricewindows;

import android.app.Application;

/**
 * Created by rachid on 14/03/17.
 */
public class CalculatriceApplication extends Application{

    private CalculatriceModel calculatriceModel;
    @Override
    public void onCreate() {
        calculatriceModel = new CalculatriceModel();
        super.onCreate();
    }

    public CalculatriceModel getCalculatriceModel(){
        return calculatriceModel;
    }
}
