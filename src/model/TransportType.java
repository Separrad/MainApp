/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public enum TransportType {
    TAXI("Taxi"),
    BUS("Bus/Flota"),
    FLIGHT("Vuelo"),
    BIKE("Bicicleta"),
    WALK("Caminar");

    private final String displayName;

    TransportType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}