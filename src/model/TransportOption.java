/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class TransportOption {
    private final TransportType type;
    private final double cost;
    private final String estimatedTime;
    private final String instructions;

    public TransportOption(TransportType type, double cost, 
                          String estimatedTime, String instructions) {
        this.type = type;
        this.cost = cost;
        this.estimatedTime = estimatedTime;
        this.instructions = instructions;
    }

    // Getters
    public TransportType getType() { return type; }
    public double getCost() { return cost; }
    public String getEstimatedTime() { return estimatedTime; }
    public String getInstructions() { return instructions; }
}