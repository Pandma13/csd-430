<!--
    Megan Wheeler
    CSD 430 Module 3 Assignment
    June 7, 2025
    
    This JSP file processes and displays the submitted restaurant feedback data.
    It receives form data from feedback_form.jsp and presents it in a formatted table.
    
    Features:
    - Processes form data using request parameters
    - Displays data in a formatted HTML table
    - Includes data formatting and validation
    - Provides a thank you message with the customer's name
    - Responsive design with CSS styling
-->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Feedback Response</title>
    <!-- CSS styles for response page layout and appearance -->
    <style>
        /* Main container styling */
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        /* Table styling for data display */
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        /* Table cell styling */
        th, td {
            padding: 12px;
            text-align: left;
            border: 1px solid #ddd;
        }
        /* Table header styling */
        th {
            background-color: #4CAF50;
            color: white;
        }
        /* Alternating row colors */
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        /* Thank you message styling */
        .thank-you {
            color: #4CAF50;
            font-size: 1.2em;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <h1>Feedback Response</h1>
    
    <%
    // Scriptlet: Retrieve and process form data
    // Get all form parameters using request.getParameter()
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String visitDate = request.getParameter("visitDate");
    String rating = request.getParameter("rating");
    String mealType = request.getParameter("mealType");
    String comments = request.getParameter("comments");
    %>

    <!-- Personalized thank you message -->
    <div class="thank-you">
        Thank you for your feedback, <%= name %>!
    </div>

    <!-- Feedback summary table -->
    <h2>Feedback Summary</h2>
    <table>
        <tr>
            <th>Field</th>
            <th>Response</th>
        </tr>
        <!-- Display customer name -->
        <tr>
            <td>Name</td>
            <td><%= name %></td>
        </tr>
        <!-- Display email address -->
        <tr>
            <td>Email</td>
            <td><%= email %></td>
        </tr>
        <!-- Display visit date -->
        <tr>
            <td>Date of Visit</td>
            <td><%= visitDate %></td>
        </tr>
        <!-- Display rating with context -->
        <tr>
            <td>Rating</td>
            <td><%= rating %> out of 5</td>
        </tr>
        <!-- Display meal type with proper capitalization -->
        <tr>
            <td>Meal Type</td>
            <td><%= mealType.substring(0, 1).toUpperCase() + mealType.substring(1) %></td>
        </tr>
        <!-- Display customer comments -->
        <tr>
            <td>Comments</td>
            <td><%= comments %></td>
        </tr>
    </table>

    <!-- Closing message -->
    <p>We appreciate your time in providing feedback about your dining experience. Your input helps us improve our service and maintain high standards.</p>
</body>
</html>