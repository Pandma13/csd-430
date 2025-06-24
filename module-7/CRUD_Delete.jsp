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
        <title>CRUD Delete - Library Management</title>
    </head>
    <body>
        <h1>Delete Book Record</h1>
        
        <jsp:useBean id='dbBean' class='database.DbBean' />
        
        <%
            String result = "";
            if ("POST".equalsIgnoreCase(request.getMethod())) {
                String bookIDStr = request.getParameter("bookID");
                
                if (bookIDStr != null) {
                    try {
                        int bookID = Integer.parseInt(bookIDStr);
                        result = dbBean.delete(bookID);
                    } catch (NumberFormatException e) {
                        result = "Error: Invalid Book ID format";
                    }
                }
            }
        %>
        
        <div style="color: red;"><%= result %></div>
        
        <form method="post">
            <label for="bookID">Book ID to Delete:</label><br>
            <input type="number" id="bookID" name="bookID" required><br><br>
            
            <input type="submit" value="Delete Record" 
                   onclick="return confirm('Are you sure you want to delete this record?')">
        </form>
        
        <br><a href="index.jsp">Back to Index</a>
    </body>
</html>
