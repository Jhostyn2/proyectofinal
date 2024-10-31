package com.example.proyectofinal.map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import com.example.proyectofinal.R;
import com.example.proyectofinal.model.Location;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private LocationCRUD locationCRUD; // Declarar la variable
    private FusedLocationProviderClient fusedLocationClient; // Declarar fusedLocationClient

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        locationCRUD = new LocationCRUD(this); // Inicializa locationCRUD
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this); // Inicializa fusedLocationClient

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        enableMyLocation();
        loadMarkers(); // Asegúrate de que solo se llama después de habilitar la ubicación correctamente.
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);

            // Obtener la última ubicación conocida
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<android.location.Location>() {
                        @Override
                        public void onSuccess(android.location.Location location) {
                            if (location != null) {
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();
                                Log.d("MapsActivity", "Ubicación obtenida: " + latitude + ", " + longitude);
                                // Puedes usar estas coordenadas para guardar la ubicación
                            } else {
                                Log.d("MapsActivity", "No se pudo obtener la ubicación");
                            }
                        }
                    });
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // Llamada al método de la clase padre

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void loadMarkers() {
        List<Location> locations = locationCRUD.getAllLocations();
        Log.d("MapsActivity", "Número de ubicaciones cargadas en el mapa: " + locations.size());
        for (Location loc : locations) {
            LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Ubicación guardada")
                    .snippet("Haz clic para ver la imagen"));
            marker.setTag(loc);
        }

        mMap.setOnMarkerClickListener(marker -> {
            Location location = (Location) marker.getTag();
            if (location != null && location.getImageUri() != null) {
                Intent intent = new Intent(MapsActivity.this, ViewImageActivity.class);
                intent.putExtra("imageUri", location.getImageUri());
                startActivity(intent);
            }
            return true;
        });
    }



}
