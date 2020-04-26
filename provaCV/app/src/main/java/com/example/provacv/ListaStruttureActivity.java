package com.example.provacv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ListaStruttureActivity extends Fragment {

    //qui vanno le cose da passare all'adapter
    private ArrayList<String> listaNomi = new ArrayList<>();
    private ArrayList<String> listaCittà = new ArrayList<>();
    private ArrayList<String> listaDescrizioni = new ArrayList<>();
    private ArrayList<String> listaCategorie = new ArrayList<>();

    public static Fragment newInstance() {
        return new ListaStruttureActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lista_strutture, container, false);
        popolaListeManualmente();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
    }

    private void popolaListeManualmente() {
        listaNomi.add("Pub 27");
        listaCittà.add("Pompei");
        listaCategorie.add("Pub");
        listaDescrizioni.add("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        listaNomi.add("Old wild west");
        listaCittà.add("Pompei");
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
        listaCittà.add("Pompei");
        listaCategorie.add("Pub");
        listaDescrizioni.add("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");



    }

    private void initRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.listaStruttureRecyclerView);
        ListaStruttureRecyclerViewAdapter recyclerViewAdapter = new ListaStruttureRecyclerViewAdapter(getContext(), listaNomi, listaCittà, listaDescrizioni, listaCategorie);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}
