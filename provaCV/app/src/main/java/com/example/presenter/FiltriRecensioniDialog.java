package com.example.presenter;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

public class FiltriRecensioniDialog extends DialogFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.filtri_recensioni_dialog, null, false);
        return view;
    }

    private void setDimensioni(){
        int height = 1100;
        int width = 1000;
        getDialog().getWindow().setLayout(width, height);
    }

    @Override
    public void onResume() {
        super.onResume();
        setDimensioni();
    }
}
