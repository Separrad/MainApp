/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class AlphabeticalComparator implements DestinationComparator {
    @Override
    public int compare(Destination d1, Destination d2) {
        return d1.getName().compareToIgnoreCase(d2.getName());
    }
}