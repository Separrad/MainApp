/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Flight {
    private final String originCity;
    private final String destinationCity;
    private final String flightNumber;
    private final String departureTime;
    private final String arrivalTime;
    private final double price;

    public Flight(String originCity, String destinationCity, String flightNumber,
                 String departureTime, String arrivalTime, double price) {
        this.originCity = originCity;
        this.destinationCity = destinationCity;
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
    }

    public String getDuration() {
        return departureTime + " - " + arrivalTime;
    }

    // Getters
    public String getOriginCity() { return originCity; }
    public String getDestinationCity() { return destinationCity; }
    public String getFlightNumber() { return flightNumber; }
    public double getPrice() { return price; }
}