package com.example.proyectofinal.map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinal.R;
import com.example.proyectofinal.model.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationListActivity extends AppCompatActivity {
    private ListView locationListView;
    private LocationCRUD locationCRUD;
    private List<Location> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        locationListView = findViewById(R.id.locationListView);
        locationCRUD = new LocationCRUD(this);
        locations = locationCRUD.getAllLocations();

        List<String> locationNames = new ArrayList<>();
        for (Location loc : locations) {
            locationNames.add("Ubicación: " + loc.getLatitude() + ", " + loc.getLongitude());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, locationNames);
        locationListView.setAdapter(adapter);

        locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Location selectedLocation = locations.get(position);
                Intent intent = new Intent(LocationListActivity.this, ViewImageActivity.class);
                intent.putExtra("imageUri", selectedLocation.getImageUri());
                startActivity(intent);
            }
        });
    }
}