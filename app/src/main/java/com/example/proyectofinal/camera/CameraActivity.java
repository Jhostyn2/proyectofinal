package com.example.proyectofinal.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinal.R;
import com.example.proyectofinal.model.Location;
import com.example.proyectofinal.map.LocationCRUD;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;
    private Button saveButton;
    private LocationCRUD locationCRUD;
    private Bitmap imageBitmap;
    private double latitude = 0.0;  // Puedes obtener la ubicación actual del usuario si se necesita.
    private double longitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        imageView = findViewById(R.id.imageView);
        saveButton = findViewById(R.id.saveButton);
        locationCRUD = new LocationCRUD(this);

        dispatchTakePictureIntent();

        saveButton.setOnClickListener(v -> {
            if (imageBitmap != null) {
                String imagePath = saveImageToStorage(imageBitmap);
                if (imagePath != null) {
                    Location location = new Location(latitude, longitude, imagePath);
                    locationCRUD.addLocation(location);
                    Toast.makeText(CameraActivity.this, "La foto y la ubicación han sido guardadas", Toast.LENGTH_SHORT).show();
                    finish();  // Vuelve a la actividad anterior.
                } else {
                    Toast.makeText(CameraActivity.this, "Error al guardar la imagen", Toast.LENGTH_SHORT).show();
                }
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
            if (imageBitmap != null) {
                imageView.setImageBitmap(imageBitmap);
            }
        }
    }

    private String saveImageToStorage(Bitmap bitmap) {
        File directory = getExternalFilesDir(null);
        String fileName = "IMG_" + System.currentTimeMillis() + ".png";
        File imageFile = new File(directory, fileName);

        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            return Uri.fromFile(imageFile).toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
