package com.example.presenter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class NessunaStrrutturaTrovataFragment extends Fragment {
    ImageButton tastoIndietro;


    public static NessunaStrrutturaTrovataFragment newInstance() {
        NessunaStrrutturaTrovataFragment fragment = new NessunaStrrutturaTrovataFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nessuna_struttura, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tastoIndietro = view.findViewById(R.id.backButtonNessunaStruttura);

        tastoIndietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                transaction.replace(R.id.container, FiltriFragment.newInstance(), "filtriFragment");
                transaction.commit();
            }
        });
    }
}
