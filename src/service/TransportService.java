/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import model.*;
import java.util.*;

public class TransportService {
    private BinarySearchTree destinationsTree;
    private DestinationComparator currentComparator;
    private final Map<String, List<Flight>> flightRoutes;
    private final Map<String, List<Bus>> busRoutes;

    public TransportService(List<Destination> destinations) {
        // Orden por defecto alfabético
        this.currentComparator = new AlphabeticalComparator();
        this.destinationsTree = new BinarySearchTree(currentComparator);
        
        // Insertar todos los destinos
        for (Destination dest : destinations) {
            destinationsTree.insert(dest);
        }
        
        this.flightRoutes = new HashMap<>();
        this.busRoutes = new HashMap<>();
        initializeFlightRoutes();
        initializeBusRoutes();
    }

    private List<Destination> createSampleDestinations() {
        return Arrays.asList(
            new Destination("Catedral de Sal", "Zipaquirá", "Iglesia subterránea en mina de sal", 4.5, 5.028, -74.006),
            new Destination("Andrés Carne de Res", "Chía", "Famoso restaurante colombiano", 4.6, 4.861, -74.069),
            new Destination("Museo del Oro", "Bogotá", "Colección de orfebrería prehispánica", 4.7, 4.711, -74.072),
            new Destination("Parque Tayrona", "Santa Marta", "Parque natural con playas", 4.3, 11.311, -74.077),
            new Destination("Islas del Rosario", "Cartagena", "Archipiélago con arrecifes de coral", 4.2, 10.176, -75.751),
            new Destination("Comuna 13", "Medellín", "Barrio con grafitis y escaleras eléctricas", 4.0, 6.248, -75.574),
            new Destination("Zoológico de Cali", "Cali", "Zoológico con diversidad de especies", 4.1, 3.420, -76.520)
        );
    }
    
    public void sortByRegion() {
        List<Destination> current = destinationsTree.inOrderTraversal();
        currentComparator = new RegionComparator();
        destinationsTree = new BinarySearchTree(currentComparator);
        for (Destination dest : current) {
            destinationsTree.insert(dest);
        }
    }
    
    public void sortAlphabetically() {
        List<Destination> current = destinationsTree.inOrderTraversal();
        currentComparator = new AlphabeticalComparator();
        destinationsTree = new BinarySearchTree(currentComparator);
        for (Destination dest : current) {
            destinationsTree.insert(dest);
        }
    }
    
    public List<Destination> getDestinations() {
        return destinationsTree.inOrderTraversal();
    }

    private void initializeFlightRoutes() {
    // Vuelos desde Bogotá
    flightRoutes.put("Bogotá", Arrays.asList(
        new Flight("Bogotá", "Medellín", "AV123", "08:00", "09:00", 150000),
        new Flight("Bogotá", "Cartagena", "AV456", "10:00", "11:30", 200000),
        new Flight("Bogotá", "Cali", "AV789", "12:00", "13:15", 180000),
        new Flight("Bogotá", "Santa Marta", "AV101", "14:00", "15:30", 220000)
        
    ));

    // Vuelos desde Medellín
    flightRoutes.put("Medellín", Arrays.asList(
        new Flight("Medellín", "Bogotá", "AV321", "07:00", "08:00", 140000),
        new Flight("Medellín", "Cartagena", "AV654", "09:30", "10:45", 160000),
        new Flight("Medellín", "Cali", "AV987", "11:00", "12:15", 120000),
        new Flight("Medellín", "Santa Marta", "AV111", "13:00", "14:45", 180000)
    ));

    // Vuelos desde Cartagena
    flightRoutes.put("Cartagena", Arrays.asList(
        new Flight("Cartagena", "Bogotá", "AV753", "13:00", "14:30", 190000),
        new Flight("Cartagena", "Medellín", "AV159", "15:00", "16:15", 170000),
        new Flight("Cartagena", "Santa Marta", "AV222", "09:00", "09:45", 80000),
        new Flight("Cartagena", "Cali", "AV334", "10:00", "11:45", 160000),
        // Vuelo a Islas del Rosario (en lancha rápida)
        new Flight("Cartagena", "Islas del Rosario", "LANCHA01", "08:00", "09:30", 120000)
    ));

    // Vuelos desde Cali
    flightRoutes.put("Cali", Arrays.asList(
        new Flight("Cali", "Bogotá", "AV444", "06:00", "07:15", 160000),
        new Flight("Cali", "Medellín", "AV555", "10:00", "11:15", 130000),
        new Flight("Cali", "Cartagena", "AV667", "12:00", "13:45", 170000), 
        new Flight("Cali", "Santa Marta", "AV778", "14:00", "15:30", 150000), 
        // Vuelo al Zoológico de Cali (en helicóptero)
        new Flight("Cali", "Zoológico de Cali", "HELI01", "09:00", "09:15", 80000)
    ));

    // Vuelos desde Santa Marta
    flightRoutes.put("Santa Marta", Arrays.asList(
        new Flight("Santa Marta", "Bogotá", "AV666", "12:00", "13:30", 210000),
        new Flight("Santa Marta", "Medellín", "AV777", "14:00", "15:45", 190000),
        new Flight("Santa Marta", "Cartagena", "AV888", "16:00", "16:45", 90000),
        new Flight("Santa Marta", "Cali", "AV999", "08:00", "09:45", 140000), 
        // Vuelo al Parque Tayrona (en avioneta)
        new Flight("Santa Marta", "Parque Tayrona", "AV999", "08:00", "08:30", 60000)
    ));
}

private void initializeBusRoutes() {
    // Flotas desde Bogotá
    busRoutes.put("Bogotá", Arrays.asList(
        new Bus("Bogotá", "Medellín", "Bolivariano", "08:00", "17:00", 80000, "WiFi, A/C, Baño"),
        new Bus("Bogotá", "Cali", "Expreso Palmira", "10:00", "20:00", 70000, "A/C, Snacks"),
        new Bus("Bogotá", "Cartagena", "Brasilia", "22:00", "13:00+1", 120000, "Asientos cama, WiFi"),
        new Bus("Bogotá", "Santa Marta", "Copetran", "19:00", "09:00+1", 110000, "Asientos cama"),
        new Bus("Bogotá", "Zipaquirá", "Libertadores", "06:00", "07:30", 15000, "Directo"),
        new Bus("Bogotá", "Chía", "Alianza", "05:30", "06:15", 10000, "Frecuencia cada 15 min"),
        new Bus("Bogotá", "Museo del Oro", "TransMilenio", "06:00", "06:30", 2500, "Ruta H15")
    ));

    // Flotas desde Medellín
    busRoutes.put("Medellín", Arrays.asList(
        new Bus("Medellín", "Bogotá", "Flota Occidental", "08:00", "17:00", 75000, "WiFi, A/C"),
            new Bus("Medellín", "Chía", "Expreso Brasilia", "22:00", "12:00+1", 85000, "Incluye traslado a Chía"),
        new Bus("Medellín", "Zipaquirá", "Libertadores", "21:00", "11:00+1", 90000, "Combina con ruta local en Bogotá"),
        new Bus("Medellín", "Cartagena", "Unitransco", "20:00", "08:00+1", 100000, "Snacks, A/C"),
        new Bus("Medellín", "Cali", "Expreso Palmira", "09:00", "19:00", 90000, "WiFi, A/C"),
        new Bus("Medellín", "Santa Marta", "Brasilia", "18:00", "10:00+1", 130000, "Asientos cama"),
        new Bus("Medellín", "Comuna 13", "Metroplus", "06:00", "06:30", 3000, "Línea 2")
    ));

    // Flotas desde Cartagena
    busRoutes.put("Cartagena", Arrays.asList(
        new Bus("Cartagena", "Bogotá", "Brasilia", "20:00", "11:00+1", 120000, "Asientos cama"),
            new Bus("Cartagena", "Chía", "Unitransco", "18:00", "10:00+1", 125000, "Servicio ejecutivo"),
        new Bus("Cartagena", "Zipaquirá", "Expreso Norte", "17:00", "09:00+1", 130000, "Asientos reclinables"),
        new Bus("Cartagena", "Medellín", "Unitransco", "19:00", "07:00+1", 110000, "A/C"),
        new Bus("Cartagena", "Santa Marta", "Marsol", "07:00", "10:00", 40000, "Directo"),
        new Bus("Cartagena", "Cali", "Expreso Bolivariano", "21:00", "12:00+1", 140000, "Asientos cama"),
        new Bus("Cartagena", "Islas del Rosario", "Lancha Turística", "08:00", "09:30", 80000, "Tour incluido")
    ));

    // Flotas desde Cali
    busRoutes.put("Cali", Arrays.asList(
        new Bus("Cali", "Bogotá", "Expreso Palmira", "08:00", "18:00", 90000, "WiFi"),
            new Bus("Cali", "Chía", "Taxi Verde", "20:00", "10:00+1", 95000, "Servicio puerta a puerta"),
        new Bus("Cali", "Zipaquirá", "Cootransandino", "19:00", "09:00+1", 100000, "Incluye alimentación"),
        new Bus("Cali", "Medellín", "Flota Magdalena", "10:00", "20:00", 85000, "A/C"),
        new Bus("Cali", "Cartagena", "Unitransco", "20:00", "11:00+1", 130000, "Asientos cama"),
        new Bus("Cali", "Santa Marta", "Copetran", "18:00", "08:00+1", 120000, "Asientos cama"),
        new Bus("Cali", "Zoológico de Cali", "MetroCali", "06:00", "06:40", 2000, "Línea 1")
    ));

    // Flotas desde Santa Marta
    busRoutes.put("Santa Marta", Arrays.asList(
        new Bus("Santa Marta", "Bogotá", "Copetran", "18:00", "08:00+1", 110000, "Asientos cama"),
            new Bus("Santa Marta", "Chía", "Brasilia", "16:00", "08:00+1", 115000, "WiFi, A/C"),
        new Bus("Santa Marta", "Zipaquirá", "Libertadores Norte", "15:00", "07:00+1", 120000, "Servicio premium"),
        new Bus("Santa Marta", "Medellín", "Brasilia", "17:00", "09:00+1", 120000, "WiFi"),
        new Bus("Santa Marta", "Cartagena", "Marsol", "08:00", "11:00", 45000, "Directo"),
        new Bus("Santa Marta", "Cali", "Expreso Palmira", "19:00", "09:00+1", 125000, "Asientos cama"),
        new Bus("Santa Marta", "Parque Tayrona", "Colectivo", "05:00", "06:30", 15000, "Salidas frecuentes")
    ));
}

    public List<TransportOption> getTransportOptions(Location origin, Destination destination) {
    List<TransportOption> options = new ArrayList<>();
    double distance = calculateDistance(origin, destination);
    
    // Para destinos en la misma ciudad (ej: Museo del Oro en Bogotá)
    
    // Para distancias cortas (menos de 50km)
    if (distance < 50) {
        addTaxiOptions(origin.getCity(), distance, options);
        addBusOptions(origin.getCity(), destination.getCity(), options);
    }
    // Para distancias medias (50-200km)
    else if (distance < 200) {
        addBusOptions(origin.getCity(), destination.getCity(), options);
    }
    // Para distancias largas (más de 200km)
    else {
        addBusOptions(origin.getCity(), destination.getCity(), options);
        addFlightOptions(origin.getCity(), destination.getCity(), options);
    }
    
    return options;
}

    
   

    
    
    private void addTaxiOptions(String city, double distance, List<TransportOption> options) {
        double basePrice = switch(city) {
            case "Bogotá" -> 5000;
            case "Medellín" -> 4500;
            case "Cartagena" -> 6000;
            default -> 4000;
        };
        
        double cost = basePrice + (distance * 2800);
        options.add(new TransportOption(
            TransportType.TAXI,
            cost,
            Math.round(distance * 2) + " min",
            "Tarifa calculada por km. Pida en app seguras"
        ));
    }

    private void addBusOptions(String origin, String destination, List<TransportOption> options) {
        if (busRoutes.containsKey(origin)) {
            busRoutes.get(origin).stream()
                .filter(bus -> bus.getDestination().equals(destination))
                .forEach(bus -> options.add(new TransportOption(
                    TransportType.BUS,
                    bus.getPrice(),
                    bus.getDuration(),
                    bus.getCompany() + " • Servicios: " + bus.getServices()
                )));
        }
    }

    private void addFlightOptions(String origin, String destination, List<TransportOption> options) {
        if (flightRoutes.containsKey(origin)) {
            flightRoutes.get(origin).stream()
                .filter(flight -> flight.getDestinationCity().equals(destination))
                .forEach(flight -> options.add(new TransportOption(
                    TransportType.FLIGHT,
                    flight.getPrice(),
                    flight.getDuration(),
                    "Aerolínea: " + flight.getFlightNumber().substring(0, 2) + 
                    " • Aeropuerto: " + getAirportName(destination)
                )));
        }
    }

    private double calculateDistance(Location origin, Destination destination) {
        double lat1 = origin.getLatitude();
        double lon1 = origin.getLongitude();
        double lat2 = destination.getLatitude();
        double lon2 = destination.getLongitude();

        // Fórmula Haversine para calcular distancia entre coordenadas
        final int R = 6371; // Radio de la Tierra en km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distancia en km
    }
    private String getAirportName(String city) {
        switch(city) {
            case "Bogotá": return "El Dorado (BOG)";
            case "Medellín": return "José María Córdova (MDE)";
            case "Cartagena": return "Rafael Núñez (CTG)";
            case "Cali": return "Alfonso Bonilla Aragón (CLO)";
            case "Santa Marta": return "Simón Bolívar (SMR)";
            default: return city + " Airport";
        }
    }

    public List<Transport> getTransportsByDestination(Destination selectedDestination) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   
}