package com.example.rachid.calculatricewindows;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by rachid on 20/06/17.
 */
public class AffichageDecimalFragment extends Fragment implements OnModelChangeListener{

    private CalculatriceModel calculatriceModel;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_result, container, false);
        this.calculatriceModel = ((CalculatriceApplication)getActivity().getApplication()).getCalculatriceModel();
        this.calculatriceModel.addModelChangeListener(this);
        if(this.calculatriceModel.getNombreEnCours() != null){
            notifyModelChange();
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void notifyModelChange() {
        TextView textView = (TextView) view.findViewById(R.id.result);
        textView.setText(this.calculatriceModel.getNombreEnCours());
    }

}
