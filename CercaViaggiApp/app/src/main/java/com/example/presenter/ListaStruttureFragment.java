package com.example.presenter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import model.Struttura;

public class ListaStruttureFragment extends Fragment {
    static ArrayList<Struttura> listaStrutture;

    public  ListaStruttureFragment(){
    }

    public ListaStruttureFragment(ArrayList<Struttura> strutture){
        listaStrutture = strutture;
    }

    public static Fragment newInstance() {
        return new ListaStruttureFragment();
    }
    public static Fragment newInstance(ArrayList<Struttura> strutture) {
        return new ListaStruttureFragment(strutture);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lista_strutture, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);

        ImageButton backButton = view.findViewById(R.id.backButtonLista);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaStrutture = null;
                ((MainActivity)getActivity()).setMap();
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                listaStrutture = null;
                ((MainActivity)getActivity()).setMap();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void initRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.listaStruttureRecyclerView);
        ListaStruttureRecyclerViewAdapter recyclerViewAdapter = new ListaStruttureRecyclerViewAdapter(listaStrutture, (MainActivity) this.getActivity());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
