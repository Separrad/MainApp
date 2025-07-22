/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

public class Destination {
    private final String name;
    private final String city;
    private final String description;
    private final double safetyRating;
    private final double latitude;  // Nuevo campo
    private final double longitude; // Nuevo campo

    public Destination(String name, String city, String description, double safetyRating, 
                      double latitude, double longitude) {
        this.name = name;
        this.city = city;
        this.description = description;
        this.safetyRating = safetyRating;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Añade estos nuevos getters
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    
    public String getName() {
        return name + " (" + city + ")";
    }
    
    public String getCity() {
        return city;
    }
    
    public String getDescription() {
        return description;
    }
    
    public double getSafetyRating() {
        return safetyRating;
    }
    
    @Override
    public String toString() {
        return getName();
    }
}