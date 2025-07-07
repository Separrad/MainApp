/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import model.Transport;
import model.Destination;
import java.util.ArrayList;
import java.util.List;

public class TransportService {
    private final List<Transport> transports;
    private final List<Destination> destinations;
    
    public TransportService(List<Destination> destinations) {
        this.destinations = destinations;
        this.transports = new ArrayList<>();
        initializeSampleData();
    }
    
    public List<Destination> getDestinations() {
        return new ArrayList<>(destinations);
    }
    
    private void initializeSampleData() {
        // Crear transportes de ejemplo
        transports.add(new Transport("Taxi Seguro", findDestinationByName("Catedral de Sal"), 
            "30 min", "Punto de encuentro: Plaza Principal", 25000));
        transports.add(new Transport("Bus Turístico", findDestinationByName("Catedral de Sal"), 
            "45 min", "Salida cada hora desde el centro", 15000));
        transports.add(new Transport("Servicio de Rideshare", findDestinationByName("Museo del Oro"), 
            "25 min", "Aplicación asociada: Uber", 20000));
        transports.add(new Transport("Transmilenio", findDestinationByName("Museo del Oro"), 
            "25 min", "Estación Museo del Oro", 2500));
        // Transporte para Andrés Carne de Res (Chía)
        transports.add(new Transport("Taxi Seguro", findDestinationByName("Andrés Carne de Res"), 
            "40 min", "Puede solicitar taxi por aplicación o en sitio autorizado", 35000));
        transports.add(new Transport("Bus Alimentador", findDestinationByName("Andrés Carne de Res"), 
            "1 hora", "Ruta Chía-Cajicá, bajar en Centro Comercial Chía", 5000));
        transports.add(new Transport("Servicio de Rideshare", findDestinationByName("Andrés Carne de Res"), 
            "35 min", "Aplicaciones como Uber o Didi disponibles", 28000));

        // Transporte para Parque Tayrona (Santa Marta)
        transports.add(new Transport("Bus Turístico", findDestinationByName("Parque Tayrona"), 
            "1.5 horas", "Salida desde el centro de Santa Marta a las 8:00 AM", 20000));
        transports.add(new Transport("Tour Organizado", findDestinationByName("Parque Tayrona"), 
            "1.25 horas", "Incluye guía y seguro, reserva previa requerida", 50000));

        // Transporte para Islas del Rosario (Cartagena)
        transports.add(new Transport("Lancha Turística", findDestinationByName("Islas del Rosario"), 
            "1 hora", "Salida desde el Muelle de la Bodeguita", 60000));
        transports.add(new Transport("Tour en Catamarán", findDestinationByName("Islas del Rosario"), 
            "1.5 horas", "Incluye almuerzo y snorkeling", 120000));

        // Transporte para Comuna 13 (Medellín)
        transports.add(new Transport("Metro + Metroplús", findDestinationByName("Comuna 13"), 
            "30 min", "Estación San Javier + Metroplús hasta la Comuna 13", 3000));
        transports.add(new Transport("Tour Guiado", findDestinationByName("Comuna 13"), 
            "25 min", "Incluye transporte desde su hotel y guía local", 40000));

        // Transporte para Zoológico de Cali (Cali)
        transports.add(new Transport("Taxi", findDestinationByName("Zoológico de Cali"), 
            "20 min", "Tarifa aproximada desde el centro", 15000));
        transports.add(new Transport("Bus Urbano", findDestinationByName("Zoológico de Cali"), 
            "35 min", "Ruta A06A, bajar en Estación Zoológico", 2500));
        transports.add(new Transport("Bicicleta Pública", findDestinationByName("Zoológico de Cali"), 
            "45 min", "Estaciones de MIO Bike disponibles cerca", 5000));
}
    
    private Destination findDestinationByName(String name) {
        return destinations.stream()
            .filter(d -> d.getName().contains(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Destino no encontrado: " + name));
    }
    
    public List<Transport> getTransportsByDestination(Destination destination) {
        List<Transport> filtered = new ArrayList<>();
        
        for (Transport t : transports) {
            if (t.getDestination().equals(destination)) {
                filtered.add(t);
            }
        }
        
        return filtered;
    }
}