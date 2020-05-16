package com.example.presenter;

import android.app.Activity;
import android.content.Intent;
import android.icu.util.EthiopicCalendar;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class FiltriRecensioniDialog extends DialogFragment {

    private Button applicaFiltriButton;
    private EditText autoreText;
    private Spinner spinnerValutazioneRecensioni;
    private Switch recentiSwitch;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.filtri_recensioni_dialog, null, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState){
        referenziaElementiGUI();
        applicaFiltriButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Autore", autoreText.getText().toString());
                //bundle.putInt("Rating", Integer.parseInt(spinnerValutazioneRecensioni.getSelectedItem().toString()));
                bundle.putBoolean("Recenti", recentiSwitch.isChecked());

                //System.out.println(bundle);
                Intent intent = new Intent().putExtras(bundle);

                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);

                dismiss();
            }
        });
    }

    private void setDimensioni(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        getDialog().getWindow().setLayout(width-150, height-700);
    }

    private void referenziaElementiGUI(){
        applicaFiltriButton = view.findViewById(R.id.applicaFiltriButton);
        autoreText = view.findViewById(R.id.autoreText);
        spinnerValutazioneRecensioni = view.findViewById(R.id.spinnerValutazioneRecensioni);
        recentiSwitch = view.findViewById(R.id.recentiSwitch);
    }

    @Override
    public void onResume() {
        super.onResume();
        setDimensioni();
    }
}
