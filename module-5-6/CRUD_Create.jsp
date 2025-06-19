<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
    Megan Wheeler
    CSD 430 - Module 5 & 6
    18 June 2025
-->
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CRUD Create - Library Management</title>
    </head>
    <body>
        <h1>Create New Book Record</h1>
        
        <jsp:useBean id='dbBean' class='database.DbBean' />
        
        <%
            String result = "";
            if ("POST".equalsIgnoreCase(request.getMethod())) {
                String title = request.getParameter("title");
                String author = request.getParameter("author");
                String genre = request.getParameter("genre");
                String pubYearStr = request.getParameter("pubYear");
                
                if (title != null && author != null && genre != null && pubYearStr != null) {
                    try {
                        int pubYear = Integer.parseInt(pubYearStr);
                        result = dbBean.createRecord(title, author, genre, pubYear);
                    } catch (NumberFormatException e) {
                        result = "Error: Invalid publication year";
                    }
                }
            }
        %>
        
        <div style="color: green;"><%= result %></div>
        
        <form method="post">
            <label for="title">Title:</label><br>
            <input type="text" id="title" name="title" required><br><br>
            
            <label for="author">Author:</label><br>
            <input type="text" id="author" name="author" required><br><br>
            
            <label for="genre">Genre:</label><br>
            <input type="text" id="genre" name="genre" required><br><br>
            
            <label for="pubYear">Publication Year:</label><br>
            <input type="number" id="pubYear" name="pubYear" min="1800" max="2025" required><br><br>
            
            <input type="submit" value="Create Record">
        </form>
        
        <br><a href="index.jsp">Back to Index</a>
    </body>
</html>
