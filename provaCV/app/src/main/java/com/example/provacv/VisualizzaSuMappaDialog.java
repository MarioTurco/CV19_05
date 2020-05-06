package com.example.provacv;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;

import model.Struttura;

public class VisualizzaSuMappaDialog extends DialogFragment {

    private Struttura strutturaSelezionata;
    /*
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.map_dialog, null));
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
    */

    public VisualizzaSuMappaDialog(Struttura struttura){
        strutturaSelezionata = struttura;
        System.out.println(strutturaSelezionata.getLongitudine() + " " + strutturaSelezionata.getLatitudine());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.map_dialog, null, false);
        return view;
    }

    public void setDimensions(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        getDialog().getWindow().setLayout(width-100, height-500);
    }

    private void setMap(){
        Mapbox.getInstance(this.getActivity(), "pk.eyJ1IjoibWFyaW90dXJjbzQiLCJhIjoiY2s5NXZicG8zMG81aDNsbzFudmJtbXFvZCJ9.SAKPHTJnSi4BpAcRkBRclA");

// Create supportMapFragment
        SupportMapFragment mapFragment;
        if (MainActivity.instanceState == null) {

// Create fragment
            final FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            System.out.println("Ok");
// Build mapboxMap
            MapboxMapOptions options = MapboxMapOptions.createFromAttributes(this.getActivity(), null);
            options.camera(new CameraPosition.Builder()
                    .target(new LatLng(strutturaSelezionata.getLongitudine(), strutturaSelezionata.getLatitudine()))
                    .zoom(9)
                    .build());

// Create map fragment
            mapFragment = SupportMapFragment.newInstance(options);

// Add map fragment to parent container
            transaction.add(R.id.dialog_container, mapFragment, "mappaDialog");
            transaction.commit();
        } else {
            mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentByTag("mappaDialog");
        }

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

    @Override
    public void onResume() {
        super.onResume();
        setDimensions();
        setMap();
    }
}
