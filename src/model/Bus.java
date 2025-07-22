/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Bus {
    private final String origin;
    private final String destination;
    private final String company;
    private final String departure;
    private final String arrival;
    private final double price;
    private final String services;

    public Bus(String origin, String destination, String company, 
               String departure, String arrival, double price, String services) {
        this.origin = origin;
        this.destination = destination;
        this.company = company;
        this.departure = departure;
        this.arrival = arrival;
        this.price = price;
        this.services = services;
    }

    public String getDuration() {
        return departure + " - " + arrival;
    }

    // Getters
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public String getCompany() { return company; }
    public double getPrice() { return price; }
    public String getServices() { return services; }
}