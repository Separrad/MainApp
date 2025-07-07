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
    
    public Destination(String name, String city, String description, double safetyRating) {
        this.name = name;
        this.city = city;
        this.description = description;
        this.safetyRating = safetyRating;
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