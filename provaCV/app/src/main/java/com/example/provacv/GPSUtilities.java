package com.example.provacv;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

public class GPSUtilities {
    private Context context;
    public GPSUtilities(Context context){
        this.context = context;
    }

    public boolean hasFineLocationAccess() {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    public boolean hasCoarseLocationAccess() {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    public boolean hasGPSPermissions() {
        return hasFineLocationAccess() && hasCoarseLocationAccess();
    }
}
