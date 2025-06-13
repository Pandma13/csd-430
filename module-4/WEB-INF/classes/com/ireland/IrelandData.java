package com.ireland;

import java.io.Serializable;

/**
 * Megan Wheeler
 * CSD 430 Module 4 Assignment
 * June 13, 2025
 * JavaBean class to hold Ireland city data
 * Implements Serializable for proper bean functionality
 */
public class IrelandData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String[] locations;
    private String[] populations;
    private String[] landmarks;
    
    // Default constructor
    public IrelandData() {
        // Initialize arrays with Ireland data
        locations = new String[] {
            "Dublin",    // Capital city
            "Galway",    // Cultural hub
            "Cork",      // Second largest city
            "Limerick",  // Third largest city
            "Waterford"  // Oldest city
        };
        
        populations = new String[] {
            "1.4 million",
            "79,934",
            "210,000",
            "94,192",
            "54,000"
        };
        
        landmarks = new String[] {
            "Trinity College & Guinness Storehouse",
            "Galway Cathedral & Spanish Arch",
            "St. Fin Barre's Cathedral",
            "King John's Castle",
            "Waterford Crystal Factory"
        };
    }
    
    // Getters and setters
    public String[] getLocations() {
        return locations;
    }
    
    public void setLocations(String[] locations) {
        this.locations = locations;
    }
    
    public String[] getPopulations() {
        return populations;
    }
    
    public void setPopulations(String[] populations) {
        this.populations = populations;
    }
    
    public String[] getLandmarks() {
        return landmarks;
    }
    
    public void setLandmarks(String[] landmarks) {
        this.landmarks = landmarks;
    }
    
    // Helper method to get number of cities
    public int getCityCount() {
        return locations.length;
    }
} 