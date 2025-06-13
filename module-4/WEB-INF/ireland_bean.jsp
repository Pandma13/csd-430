<!--
    Megan Wheeler
    CSD 430 Module 4 Assignment
    June 13, 2025
    Using JavaBean to display Ireland data
-->


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.ireland.IrelandData" %>
<jsp:useBean id="irelandData" class="com.ireland.IrelandData" scope="page"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ireland Data Using JavaBean</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f0f0f0;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #2c3e50;
            text-align: center;
        }
        .description {
            margin: 20px 0;
            line-height: 1.6;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border: 1px solid #ddd;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #ddd;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Exploring Ireland's Cities</h1>
        
        <div class="description">
            <p>This page displays information about major cities in Ireland, including their population and famous landmarks. 
            The data is managed through a JavaBean, demonstrating proper separation of concerns and data management.</p>
        </div>

        <table>
            <thead>
                <tr>
                    <th>City/Region</th>
                    <th>Population</th>
                    <th>Famous Landmark</th>
                </tr>
            </thead>
            <tbody>
                <%
                    String[] locations = irelandData.getLocations();
                    String[] populations = irelandData.getPopulations();
                    String[] landmarks = irelandData.getLandmarks();
                    
                    for(int i = 0; i < irelandData.getCityCount(); i++) {
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
        
        <div class="description">
            <p>Total number of cities displayed: <%= irelandData.getCityCount() %></p>
        </div>
    </div>
</body>
</html> 