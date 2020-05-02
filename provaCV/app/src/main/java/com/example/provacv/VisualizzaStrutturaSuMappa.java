package com.example.provacv;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;

import model.Struttura;


public class VisualizzaStrutturaSuMappa extends Fragment {
    private  SupportMapFragment mapFragment;
    ImageButton backbutton;
    private Struttura struttura;


    public VisualizzaStrutturaSuMappa(Struttura struttura) {
        this.struttura = struttura;
    }


    public static VisualizzaStrutturaSuMappa newInstance(Struttura struttura) {
        VisualizzaStrutturaSuMappa fragment = new VisualizzaStrutturaSuMappa(struttura);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visualizza_su_mappa, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setMap();
        backbutton = view.findViewById(R.id.backButtonVisualizzaSuMappa);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                transaction.replace(R.id.container, DettagliStrutturaFragment.newInstance(struttura), "Visualizza su mappa to Dettagli struttura");
                transaction.commit();
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                transaction.replace(R.id.container, DettagliStrutturaFragment.newInstance(struttura), "Visualizza su mappa to Dettagli struttura");
                transaction.commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    protected void setMap() {
        Mapbox.getInstance(getContext(), "pk.eyJ1IjoibWFyaW90dXJjbzQiLCJhIjoiY2s5NXZicG8zMG81aDNsbzFudmJtbXFvZCJ9.SAKPHTJnSi4BpAcRkBRclA");
            // Create fragment
            final FragmentTransaction transaction = getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
            // Build mapboxMap
            MapboxMapOptions options = MapboxMapOptions.createFromAttributes(getContext(), null);
            options.camera(new CameraPosition.Builder()
                    .target(new LatLng(struttura.getLatitudine(), struttura.getLongitudine()))
                    .zoom(17)
                    .build());
            // Create map fragment
            mapFragment = SupportMapFragment.newInstance(options);
            // Add map fragment to parent container
            transaction.add(R.id.mapContainer, mapFragment, "com.mapbox.map");
            transaction.commit();
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull MapboxMap mapboxMap) {
                    mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/marioturco4/ck95w1ltx0sdn1iqt1enmib6y"), new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {

                            // Map is set up and the style has loaded. Now you can add data or make other map adjustments
                        }
                    });
                }
            });
        }

    }
}
