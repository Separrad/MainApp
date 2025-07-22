/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Shuttle {
    private String origin;
    private String destination;
    private String departure;
    private String arrival;
    private double price;
    private String company;
    private String amenities;

    public Shuttle(String origin, String destination, String departure, 
                  String arrival, double price, String company, String amenities) {
        this.origin = origin;
        this.destination = destination;
        this.departure = departure;
        this.arrival = arrival;
        this.price = price;
        this.company = company;
        this.amenities = amenities;
    }

    // Getters
    public String getOriginCity() { return origin; }
    public String getDestination() { return destination; }
    public String getDepartureTime() { return departure; }
    public String getArrivalTime() { return arrival; }
    public double getPrice() { return price; }
    public String getCompany() { return company; }
    public String getAmenities() { return amenities; }
    
    public String getDuration() {
        return departure + " - " + arrival;
    }
}