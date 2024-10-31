package com.example.proyectofinal.model;

public class Location {
    private int id; // Agregado ID único
    private double latitude;
    private double longitude;
    private String imageUri;

    // Constructor para ubicación sin imagen
    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUri = null;
    }

    // Constructor para ubicación con imagen
    public Location(double latitude, double longitude, String imageUri) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUri = imageUri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}