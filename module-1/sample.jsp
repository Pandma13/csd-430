<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sample JSP Page</title>
</head>
<body>
    <h1>Welcome to JSP Example</h1>
    
    <!-- Scriptlet: Java code block -->
    <% 
        String message = "Hello from Java!";
        int currentHour = java.time.LocalTime.now().getHour();
        String greeting = (currentHour < 12) ? "Good Morning" : "Good Afternoon";
    %>
    
    <!-- Expression: Outputs Java expression -->
    <p><%= greeting %>, visitor!</p>
    <p>Message from Java: <%= message %></p>
    
    <!-- Declaration: Declares variables or methods -->
    <%!
        private int counter = 0;
        public int incrementCounter() {
            return ++counter;
        }
    %>
    
    <!-- Using declared method -->
    <p>Counter value: <%= incrementCounter() %></p>
    
    <!-- Conditional statement -->
    <% if (currentHour < 12) { %>
        <p>It's morning time!</p>
    <% } else { %>
        <p>It's afternoon or evening!</p>
    <% } %>
    
    <!-- Loop example -->
    <h3>Counting from 1 to 5:</h3>
    <ul>
    <% for(int i = 1; i <= 5; i++) { %>
        <li>Number <%= i %></li>
    <% } %>
    </ul>
    
    <!-- Current date and time -->
    <p>Current date and time: <%= new java.util.Date() %></p>
</body>
</html>
