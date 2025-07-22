package model;

import java.util.List;

public class Restaurant implements Comparable<Restaurant> {
    private final String name;
    private final String type;
    private final String priceRange;
    private final Destination destination; 
    private final double rating;
    private final String imagePath;
    private final String description;
    private final List<String> featuredDishes;
    private final String phone;
    private final String address;
    private final String website;
    
    public Restaurant(String name, String type, String priceRange, Destination destination, 
                     double rating, String imagePath, String description, 
                     List<String> featuredDishes, String phone, String address, String website) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del restaurante no puede ser nulo");
        }
        if (destination == null) {
            throw new IllegalArgumentException("El destino no puede ser nulo");
        }
        this.name = name.trim();
        this.type = type;
        this.priceRange = priceRange;
        this.destination = destination;
        this.rating = rating;
        this.imagePath = imagePath;
        this.description = description;
        this.featuredDishes = featuredDishes;
        this.phone = phone;
        this.address = address;
        this.website = website;
    }
    
    public String getName() { return name; }
    public String getType() { return type; }
    public String getPriceRange() { return priceRange; }
    public Destination getDestination() { return destination; }
    public double getRating() { return rating; }
    public String getImagePath() { return imagePath; }
    public String getDescription() { return description; }
    public List<String> getFeaturedDishes() { return featuredDishes; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getWebsite() { return website; }

    // Implementación para el Árbol AVL
    @Override
    public int compareTo(Restaurant other) {
        return this.name.compareToIgnoreCase(other.name);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return name.equalsIgnoreCase(that.name);
    }
    
    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s (%s) - %s - Calificación: %.1f", 
            name, type, priceRange, destination.getCity(), rating);
    }
}