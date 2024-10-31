package com.example.proyectofinal.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinal.R;
import com.example.proyectofinal.camera.CameraActivity;
import com.example.proyectofinal.map.LocationListActivity;
import com.example.proyectofinal.map.MapsActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnCamera;
    private Button btnViewMap;
    private Button btnViewLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCamera = findViewById(R.id.btn_camera);
        btnViewMap = findViewById(R.id.btn_view_map);
        btnViewLocations = findViewById(R.id.btn_view_locations);

        // Listener para abrir CameraActivity
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        // Listener para abrir MapsActivity
        btnViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        // Listener para abrir LocationListActivity
        btnViewLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LocationListActivity.class);
                startActivity(intent);
            }
        });
    }
}
