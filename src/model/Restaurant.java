/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

public class Restaurant {
    private final String name;
    private final String type;
    private final String priceRange;
    private final Destination destination;
    private final double rating;
    
    public Restaurant(String name, String type, String priceRange, Destination destination, double rating) {
        this.name = name;
        this.type = type;
        this.priceRange = priceRange;
        this.destination = destination;
        this.rating = rating;
    }
    
    public String getName() {
        return name;
    }
    
    public String getType() {
        return type;
    }
    
    public String getPriceRange() {
        return priceRange;
    }
    
    public Destination getDestination() {
        return destination;
    }
    
    public double getRating() {
        return rating;
    }
}