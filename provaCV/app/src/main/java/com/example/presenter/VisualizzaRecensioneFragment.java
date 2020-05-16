package com.example.presenter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import model.Recensione;

public class VisualizzaRecensioneFragment extends Fragment {
    private TextView autore, data, titolo, testo;
    private RatingBar ratingBar;
    private Recensione recensione;
    private ImageButton tastoIndietro;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public VisualizzaRecensioneFragment(Recensione recensione) {
        this.recensione = recensione;
    }

    public static VisualizzaRecensioneFragment newInstance(Recensione recensione) {
        VisualizzaRecensioneFragment fragment = new VisualizzaRecensioneFragment(recensione);
        return fragment;
    }

    private void referenziaElementiGUI(View itemView) {
        autore = itemView.findViewById(R.id.autoreRecensione);
        ratingBar = itemView.findViewById(R.id.ratingBarRecensione);
        data = itemView.findViewById(R.id.dataRecensione);
        titolo = itemView.findViewById(R.id.titoloRecensione);
        testo = itemView.findViewById(R.id.testoRecensione);
        tastoIndietro = itemView.findViewById(R.id.backButtonRecensione);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visualizza_recensione, container, false);
        referenziaElementiGUI(view);
        aggiungiListenerTastoIndietro();

        return view;
    }

    private void aggiungiListenerTastoIndietro() {
        tastoIndietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                transaction.replace(R.id.container, new DettagliStrutturaFragment(), "filtriFragment");
                transaction.commit();
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                transaction.replace(R.id.container, new DettagliStrutturaFragment(), "dettagliStrutturaFragment");
                transaction.commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    private void riempiCampiRecensione() {
        autore.setText(recensione.getAutore().getNickname());
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
