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
        <title>CRUD Update - Library Management</title>
    </head>
    <body>
        <h1>Update Book Record</h1>
        
        <jsp:useBean id='dbBean' class='database.DbBean' />
        
        <%
            String result = "";
            if ("POST".equalsIgnoreCase(request.getMethod())) {
                String title = request.getParameter("title");
                String author = request.getParameter("author");
                String genre = request.getParameter("genre");
                String pubYearStr = request.getParameter("pubYear");
                String bookIDStr = request.getParameter("bookID");
                
                if (title != null && author != null && genre != null && 
                    pubYearStr != null && bookIDStr != null) {
                    try {
                        int pubYear = Integer.parseInt(pubYearStr);
                        int bookID = Integer.parseInt(bookIDStr);
                        result = dbBean.updateRecord(title, author, genre, pubYear, bookID);
                    } catch (NumberFormatException e) {
                        result = "Error: Invalid number format";
                    }
                }
            }
        %>
        
        <div style="color: green;"><%= result %></div>
        
        <form method="post">
            <label for="bookID">Book ID:</label><br>
            <input type="number" id="bookID" name="bookID" required><br><br>
            
            <label for="title">Title:</label><br>
            <input type="text" id="title" name="title" required><br><br>
            
            <label for="author">Author:</label><br>
            <input type="text" id="author" name="author" required><br><br>
            
            <label for="genre">Genre:</label><br>
            <input type="text" id="genre" name="genre" required><br><br>
            
            <label for="pubYear">Publication Year:</label><br>
            <input type="number" id="pubYear" name="pubYear" min="1800" max="2025" required><br><br>
            
            <input type="submit" value="Update Record">
        </form>
        
        <br><a href="index.jsp">Back to Index</a>
    </body>
</html>
