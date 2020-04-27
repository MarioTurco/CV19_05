package com.example.provacv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;

import model.Struttura;

public class ListaStruttureFragment extends Fragment {
    private ImageButton backButton;
    //qui vanno le cose da passare all'adapter
    private ArrayList<Struttura> listaStrutture = new ArrayList<>();

    public static Fragment newInstance() {
        return new ListaStruttureFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lista_strutture, container, false);
        //popolaListeManualmente(); TODO Eliminare
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
        backButton = view.findViewById(R.id.backButtonLista);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).toolbar.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).setMap(savedInstanceState);
            }
        });
    }

/*    private void popolaListeManualmente() { TODO Eliminami
        listaNomi.add("Pub 27");
        listaCittà.add("Pompei");
        listaCategorie.add("Pub");
        listaDescrizioni.add("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        listaNomi.add("Old wild west");
        listaCittà.add("Ma tipo vicino la cartiera, boh, da quelle parti la");
        listaCategorie.add("Ristorante");
        listaDescrizioni.add("YI-YA!");

        listaNomi.add("Pepp o' Ftus");
        listaCittà.add("Torre del Greco");
        listaCategorie.add("Riciclaggio di denaro");
        listaDescrizioni.add("Sconti pazzi e ftus");

        listaNomi.add("Pub 28");
        listaCittà.add("Pompei");
        listaCategorie.add("Pub");
        listaDescrizioni.add("Come il pub 27 però un poco meglio");

        listaNomi.add("Pub 27");
        listaCittà.add("Parigi");
        listaCategorie.add("Pub");
        listaDescrizioni.add("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    }*/

    private void initRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.listaStruttureRecyclerView);
        ListaStruttureRecyclerViewAdapter recyclerViewAdapter = new ListaStruttureRecyclerViewAdapter(getContext(), listaStrutture);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}
