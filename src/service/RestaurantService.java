/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import model.Restaurant;
import model.Destination;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
    private final List<Restaurant> restaurants;
    private final List<Destination> destinations;
    
    public RestaurantService(List<Destination> destinations) {
        this.destinations = destinations;
        this.restaurants = new ArrayList<>();
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        // Restaurantes para Chía
        restaurants.add(new Restaurant("Andrés Carne de Res", "Tradicional", "Medio", 
            findDestinationByCity("Chía"), 4.6));
        restaurants.add(new Restaurant("Costa Azul", "Mariscos", "Medio", 
            findDestinationByCity("Chía"), 4.2));
        restaurants.add(new Restaurant("Semilla Verde", "Vegetariana", "Bajo", 
            findDestinationByCity("Chía"), 4.1));
        restaurants.add(new Restaurant("La Piazza", "Internacional", "Alto", 
            findDestinationByCity("Chía"), 4.4));
        restaurants.add(new Restaurant("El Corral Gourmet", "Gourmet", "Alto", 
            findDestinationByCity("Chía"), 4.5));
  
        // Restaurantes para Bogotá
        restaurants.add(new Restaurant("El Pesquero", "Mariscos", "Alto", 
            findDestinationByCity("Bogotá"), 4.6));
        restaurants.add(new Restaurant("Leo Cocina y Cava", "Gourmet", "Alto", 
            findDestinationByCity("Bogotá"), 4.8));
        restaurants.add(new Restaurant("Criterion", "Internacional", "Alto", 
            findDestinationByCity("Bogotá"), 4.7));
        restaurants.add(new Restaurant("Mesa Franca", "Vegetariana", "Medio", 
            findDestinationByCity("Bogotá"), 4.3));
        restaurants.add(new Restaurant("La Puerta Falsa", "Tradicional", "Bajo", 
            findDestinationByCity("Bogotá"), 4.2));
        // Restaurantes para Medellín
        restaurants.add(new Restaurant("Hacienda", "Tradicional", "Medio", 
            findDestinationByCity("Medellín"), 4.3));
        restaurants.add(new Restaurant("Mar y Leña", "Mariscos", "Medio", 
            findDestinationByCity("Medellín"), 4.4));
        restaurants.add(new Restaurant("Verdeo", "Vegetariana", "Medio", 
            findDestinationByCity("Medellín"), 4.5));
        restaurants.add(new Restaurant("Bonhomía", "Internacional", "Alto", 
            findDestinationByCity("Medellín"), 4.7));
        restaurants.add(new Restaurant("El Cielo", "Gourmet", "Alto", 
            findDestinationByCity("Medellín"), 4.8));
        // Restaurantes para Cartagena
        restaurants.add(new Restaurant("La Cevichería", "Mariscos", "Medio", 
            findDestinationByCity("Cartagena"), 4.7));
        restaurants.add(new Restaurant("La Vitrola", "Tradicional", "Medio", 
            findDestinationByCity("Cartagena"), 4.5));
        restaurants.add(new Restaurant("Girasoles", "Vegetariana", "Bajo", 
            findDestinationByCity("Cartagena"), 4.2));
        restaurants.add(new Restaurant("Carmen", "Gourmet", "Alto", 
            findDestinationByCity("Cartagena"), 4.8));
        restaurants.add(new Restaurant("Restaurante 1621", "Internacional", "Alto", 
            findDestinationByCity("Cartagena"), 4.6));
        // Restaurantes para Cali
        restaurants.add(new Restaurant("El Zaguan", "Tradicional", "Medio", 
            findDestinationByCity("Cali"), 4.4));
        restaurants.add(new Restaurant("Costa Mar", "Mariscos", "Medio", 
            findDestinationByCity("Cali"), 4.3));
        restaurants.add(new Restaurant("Verdemente", "Vegetariana", "Bajo", 
            findDestinationByCity("Cali"), 4.1));
        restaurants.add(new Restaurant("Platillos Voladores", "Internacional", "Alto", 
            findDestinationByCity("Cali"), 4.5));
        restaurants.add(new Restaurant("Ocio", "Gourmet", "Alto", 
            findDestinationByCity("Cali"), 4.7));

        // Restaurantes para Santa Marta
        restaurants.add(new Restaurant("Donde Chucho", "Tradicional", "Medio", 
            findDestinationByCity("Santa Marta"), 4.3));
        restaurants.add(new Restaurant("Burukuka", "Mariscos", "Medio", 
            findDestinationByCity("Santa Marta"), 4.6));
        restaurants.add(new Restaurant("Fresco", "Vegetariana", "Bajo", 
            findDestinationByCity("Santa Marta"), 4.0));
        restaurants.add(new Restaurant("La Terraza", "Internacional", "Alto", 
            findDestinationByCity("Santa Marta"), 4.4));
        restaurants.add(new Restaurant("Restaurante San Valentín", "Gourmet", "Alto", 
            findDestinationByCity("Santa Marta"), 4.7));
    }
    
    private Destination findDestinationByCity(String city) {
        return destinations.stream()
            .filter(d -> d.getCity().equalsIgnoreCase(city))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Destino no encontrado: " + city));
    }
    
    public List<Restaurant> getRestaurantsByFilters(String city, String type, String priceRange) {
        List<Restaurant> filtered = new ArrayList<>();
        
        for (Restaurant r : restaurants) {
            boolean cityMatch = city.equals("Todas") || r.getDestination().getCity().equalsIgnoreCase(city);
            boolean typeMatch = type.equals("Todos") || r.getType().equalsIgnoreCase(type);
            boolean priceMatch = priceRange.equals("Todos") || r.getPriceRange().equalsIgnoreCase(priceRange);
            
            if (cityMatch && typeMatch && priceMatch) {
                filtered.add(r);
            }
        }
        
        return filtered;
    }
}