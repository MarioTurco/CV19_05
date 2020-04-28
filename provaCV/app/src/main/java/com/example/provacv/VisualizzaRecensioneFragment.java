package com.example.provacv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import model.Recensione;

public class VisualizzaRecensioneFragment extends Fragment {
    private TextView autore,data,titolo,testo;
    private RatingBar ratingBar;
    private Recensione recensione;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public VisualizzaRecensioneFragment(Recensione recensione){
        this.recensione=recensione;
    }

    public static VisualizzaRecensioneFragment newInstance(Recensione recensione) {
        VisualizzaRecensioneFragment fragment = new VisualizzaRecensioneFragment(recensione);
        return fragment;
    }

    private void initGUIElements(View itemView){
        autore = itemView.findViewById(R.id.autoreRecensione);
        ratingBar = itemView.findViewById(R.id.ratingBarRecensione);
        data = itemView.findViewById(R.id.dataRecensione);
        titolo = itemView.findViewById(R.id.titoloRecensione);
        testo = itemView.findViewById(R.id.testoRecensione);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visualizza_recensione, container, false);
        initGUIElements(view);
        return view;
    }

    private void riempiCampiRecensione(){
        autore.setText(recensione.getAutore());
        data.setText(recensione.getDataRecensione());
        ratingBar.setRating(recensione.getValutazione());
        testo.setText(recensione.getTesto());
        titolo.setText(recensione.getTitolo());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        riempiCampiRecensione();
    }
}
