/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class RegionComparator implements DestinationComparator {
    @Override
    public int compare(Destination d1, Destination d2) {
        int regionCompare = d1.getCity().compareToIgnoreCase(d2.getCity());
        if (regionCompare == 0) {
            return d1.getName().compareToIgnoreCase(d2.getName());
        }
        return regionCompare;
    }
}