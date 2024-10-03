package com.example.lab02_q2;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.lab02_q2.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng kelowna = new LatLng(49.8801, -119.4436);
        LatLng ubco = new LatLng(49.9394, -119.3948);
        LatLng lakeCountry = new LatLng(50.0537, -119.4106);

        mMap.addMarker(new MarkerOptions().position(kelowna).title("Kelowna"));
        mMap.addMarker(new MarkerOptions().position(ubco).title("UBCO"));
        mMap.addMarker(new MarkerOptions().position(lakeCountry).title("Lake Country"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kelowna, 10));
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}