/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

public class Transport {
    private final String type;
    private final Destination destination;
    private final String estimatedTime;
    private final String instructions;
    private final double estimatedCost;
    
    public Transport(String type, Destination destination, String estimatedTime, String instructions, double estimatedCost) {
        this.type = type;
        this.destination = destination;
        this.estimatedTime = estimatedTime;
        this.instructions = instructions;
        this.estimatedCost = estimatedCost;
    }
    
    public String getType() {
        return type;
    }
    
    public Destination getDestination() {
        return destination;
    }
    
    public String getEstimatedTime() {
        return estimatedTime;
    }
    
    public String getInstructions() {
        return instructions;
    }
    
    public double getEstimatedCost() {
        return estimatedCost;
    }
}