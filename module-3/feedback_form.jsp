<!--
    Megan Wheeler
    CSD 430 Module 3 Assignment
    June 7, 2025
    
    Restaurant Feedback Form
    ------------------------
    Purpose: Interactive form for collecting restaurant dining feedback.
    
    Features:
    - JSP 2.0+ with HTML5 form elements
    - Client-side validation
    - Responsive design
    - POST method submission to feedback_response.jsp
    
    Form Fields:
    1. Customer Info: Name, Email
    2. Visit Details: Date, Rating (1-5), Meal Type
    3. Feedback: Comments
    
    Security: Required fields, email validation, POST method
    Dependencies: Servlet container (e.g., Tomcat), modern browser
-->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Restaurant Feedback Form</title>
    <!-- CSS: Responsive layout with consistent styling -->
    <style>
        /* Container and layout */
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
        }
        /* Form elements */
        input[type="text"], input[type="email"], select, textarea {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        /* Rating layout */
        .rating {
            display: flex;
            gap: 10px;
        }
        .rating label {
            display: inline;
        }
        /* Submit button */
        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <h1>Restaurant Feedback Form</h1>
    <p>Please share your dining experience with us. Your feedback helps us improve our service.</p>

    <!-- Form: POST method, required fields -->
    <form action="feedback_response.jsp" method="post">
        <!-- Name input -->
        <div class="form-group">
            <label for="name">Full Name:</label>
            <input type="text" id="name" name="name" required 
                   placeholder="Enter your full name">
        </div>

        <!-- Email input -->
        <div class="form-group">
            <label for="email">Email Address:</label>
            <input type="email" id="email" name="email" required 
                   placeholder="Enter your email address">
        </div>

        <!-- Date input -->
        <div class="form-group">
            <label for="visitDate">Date of Visit:</label>
            <input type="date" id="visitDate" name="visitDate" required>
        </div>

        <!-- Rating: 1-5 scale -->
        <div class="form-group">
            <label>Overall Rating:</label>
            <div class="rating">
                <label><input type="radio" name="rating" value="1" required> 1</label>
                <label><input type="radio" name="rating" value="2"> 2</label>
                <label><input type="radio" name="rating" value="3"> 3</label>
                <label><input type="radio" name="rating" value="4"> 4</label>
                <label><input type="radio" name="rating" value="5"> 5</label>
            </div>
        </div>

        <!-- Meal type selection -->
        <div class="form-group">
            <label for="mealType">Type of Meal:</label>
            <select id="mealType" name="mealType" required>
                <option value="">Select a meal type</option>
                <option value="breakfast">Breakfast</option>
                <option value="lunch">Lunch</option>
                <option value="dinner">Dinner</option>
                <option value="brunch">Brunch</option>
            </select>
        </div>

        <!-- Comments -->
        <div class="form-group">
            <label for="comments">Additional Comments:</label>
            <textarea id="comments" name="comments" rows="4" required 
                      placeholder="Please share your dining experience with us..."></textarea>
        </div>

        <button type="submit">Submit Feedback</button>
    </form>
</body>
</html>