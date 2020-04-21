package com.example.provacv;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.mapbox.mapboxsdk.maps.SupportMapFragment;


public class FiltriFragment extends Fragment {
    private ImageButton backButton;
    private ViewGroup container;
    private SupportMapFragment mapFragment;

    public FiltriFragment(SupportMapFragment mapFragment) {

        this.mapFragment = mapFragment;
    }


    public static FiltriFragment newInstance(SupportMapFragment mapFragment) {
        FiltriFragment fragment = new FiltriFragment(mapFragment);
        return fragment;
    }

    private void setHomepageActionInDrawer(){
        Menu drawerMenu = ((MainActivity)getActivity()).navigationView.getMenu();
        drawerMenu.findItem(R.id.login).setVisible(false);
        drawerMenu.findItem(R.id.homepage).setVisible(true);
    }

    private void setLoginActionInDrawer(){
        Menu drawerMenu = ((MainActivity)getActivity()).navigationView.getMenu();
        drawerMenu.findItem(R.id.login).setVisible(true);
        drawerMenu.findItem(R.id.homepage).setVisible(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHomepageActionInDrawer();
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.container = container;
        return inflater.inflate(R.layout.fragment_filtri, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backButton = view.findViewById(R.id.backButtonSignup);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).toolbar.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).setMap(savedInstanceState);
                setLoginActionInDrawer();
            }
        });
    }
}
