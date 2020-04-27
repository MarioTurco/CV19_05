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
    private ArrayList<Struttura> listaStrutture;

    public ListaStruttureFragment(ArrayList<Struttura> strutture){
        this.listaStrutture = strutture;
        System.out.println(listaStrutture);
    }

    public static Fragment newInstance(ArrayList<Struttura> strutture) {
        return new ListaStruttureFragment(strutture);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lista_strutture, container, false);
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

    private void initRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.listaStruttureRecyclerView);
        ListaStruttureRecyclerViewAdapter recyclerViewAdapter = new ListaStruttureRecyclerViewAdapter(getContext(), listaStrutture);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}
