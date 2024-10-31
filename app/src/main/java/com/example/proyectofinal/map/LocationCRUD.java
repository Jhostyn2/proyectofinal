package com.example.proyectofinal.map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.proyectofinal.database.DatabaseHelper;
import com.example.proyectofinal.model.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationCRUD {
    private DatabaseHelper dbHelper;

    public LocationCRUD(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void addLocation(Location location) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("latitude", location.getLatitude());
        values.put("longitude", location.getLongitude());
        values.put("imageUri", location.getImageUri());
        long result = db.insert("locations", null, values);
        if (result != -1) {
            Log.d("LocationCRUD", "Ubicación agregada con éxito: " + location.getLatitude() + ", " + location.getLongitude());
        } else {
            Log.d("LocationCRUD", "Error al agregar la ubicación");
        }
        db.close();
    }



    public List<Location> getAllLocations() {
        List<Location> locations = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("locations", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude"));
                double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude"));
                String imageUri = cursor.getString(cursor.getColumnIndexOrThrow("imageUri"));
                locations.add(new Location(latitude, longitude, imageUri));
            } while (cursor.moveToNext());
        }
        Log.d("LocationCRUD", "Número de ubicaciones recuperadas: " + locations.size());
        cursor.close();
        db.close();
        return locations;
    }



    public void deleteAllLocations() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("locations", null, null);
        db.close();
    }

}