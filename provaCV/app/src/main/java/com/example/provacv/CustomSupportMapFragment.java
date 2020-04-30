package com.example.provacv;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;
import com.mapbox.mapboxsdk.utils.MapFragmentUtils;

public class CustomSupportMapFragment extends SupportMapFragment {

    private Toolbar toolbar;

    public CustomSupportMapFragment(Toolbar toolbar){
        super();
        this.toolbar = toolbar;
    }

    @NonNull
    public static CustomSupportMapFragment newInstance(@Nullable MapboxMapOptions mapboxMapOptions, Toolbar toolbar) {
        CustomSupportMapFragment mapFragment = new CustomSupportMapFragment(toolbar);
        mapFragment.setArguments(MapFragmentUtils.createFragmentArgs(mapboxMapOptions));
        return mapFragment;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation anim = null;
        if (enter) {
            anim = AnimationUtils.loadAnimation(getActivity(), nextAnim);

            anim.setAnimationListener(new Animation.AnimationListener() {


                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });


        }
        return anim;
    }
}
