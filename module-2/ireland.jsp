<!--
    Megan Wheeler
    CSD 430 Module 2 Assignment
    June 1, 2025
-->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <!-- Meta tags for proper character encoding -->
    <meta charset="UTF-8">
    <title>Exploring Ireland</title>
    <!-- External CSS stylesheet link -->
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <!-- Main container for content layout -->
    <div class="container">
        <!-- Page header -->
        <h1>Exploring the Emerald Isle</h1>
        
        <!-- Introduction section -->
        <div class="description">
            <p>This page showcases some of Ireland's most notable cities and regions, highlighting their unique characteristics, 
            population, and famous landmarks. From the vibrant capital to historic towns, each location tells a story of Ireland's 
            rich cultural heritage.</p>
        </div>

        <!-- Data table structure -->
        <table>
            <!-- Table header row -->
            <thead>
                <tr>
                    <th>City/Region</th>
                    <th>Population</th>
                    <th>Famous Landmark</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Arrays to store city information
                    // Each array index corresponds to the same city across all arrays
                    String[] locations = {
                        "Dublin",    // Capital city
                        "Galway",    // Cultural hub
                        "Cork",      // Second largest city
                        "Limerick",  // Third largest city
                        "Waterford"  // Oldest city
                    };
                    
                    // Population data for each city
                    String[] populations = {
                        "1.4 million",
                        "79,934",
                        "210,000",
                        "94,192",
                        "54,000"
                    };
                    
                    // Notable landmarks for each city
                    String[] landmarks = {
                        "Trinity College & Guinness Storehouse",
                        "Galway Cathedral & Spanish Arch",
                        "St. Fin Barre's Cathedral",
                        "King John's Castle",
                        "Waterford Crystal Factory"
                    };
                    
                    // Loop through arrays to generate table rows
                    // Each iteration creates a new row with corresponding data
                    for(int i = 0; i < locations.length; i++) {
                %>
                    <tr>
                        <td><%= locations[i] %></td>
                        <td><%= populations[i] %></td>
                        <td><%= landmarks[i] %></td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html> 