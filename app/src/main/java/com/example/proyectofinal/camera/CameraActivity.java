package com.example.proyectofinal.camera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.proyectofinal.R;
import com.example.proyectofinal.map.LocationCRUD;
import com.example.proyectofinal.model.Location;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class CameraActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOCATION_PERMISSION = 2;
    private Bitmap imageBitmap;
    private ImageView imageView;
    private LocationCRUD locationCRUD;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        imageView = findViewById(R.id.imageView);
        Button takePhotoButton = findViewById(R.id.takePhotoButton);
        Button saveButton = findViewById(R.id.saveButton);
        locationCRUD = new LocationCRUD(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Botón para tomar la foto
        takePhotoButton.setOnClickListener(v -> dispatchTakePictureIntent());

        // Botón para guardar la ubicación y la imagen
        saveButton.setOnClickListener(v -> {
            if (imageBitmap != null) {
                if (ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    fusedLocationClient.getLastLocation()
                            .addOnSuccessListener(location -> {
                                if (location != null) {
                                    double latitude = location.getLatitude();
                                    double longitude = location.getLongitude();
                                    Log.d("CameraActivity", "Ubicación obtenida: " + latitude + ", " + longitude);

                                    String imagePath = saveImageToStorage(imageBitmap);
                                    Location newLocation = new Location(latitude, longitude, imagePath);
                                    locationCRUD.addLocation(newLocation);
                                    Toast.makeText(CameraActivity.this, "La foto y la ubicación han sido guardadas", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Log.d("CameraActivity", "No se pudo obtener la ubicación");
                                    Toast.makeText(CameraActivity.this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show();
                                }
                            });

                } else {
                    // Solicitar permiso de ubicación
                    ActivityCompat.requestPermissions(CameraActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_LOCATION_PERMISSION);
                }
            } else {
                Toast.makeText(CameraActivity.this, "No se ha tomado ninguna foto", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso de ubicación concedido. Intente guardar nuevamente.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String saveImageToStorage(Bitmap bitmap) {
        // Implementa la lógica para guardar la imagen en el almacenamiento y devolver la URI
        return "ruta/de/la/imagen";
    }
}
