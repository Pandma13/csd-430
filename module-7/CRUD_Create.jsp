<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
    Megan Wheeler
    CSD 430 - Module 7
    24 June 2025
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
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            String value = dbBean.formGetCreateOrUpdate("CRUD_Create.jsp");
            out.print(value);
        }
        %>
        
        <%
        String result = "";
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String genre = request.getParameter("genre");
            String pubYearStr = request.getParameter("pubYear");
            String pageCountStr = request.getParameter("pageCount");
            String ISBN = request.getParameter("ISBN");
            
            if (title != null && author != null && genre != null && pubYearStr != null && pageCountStr != null && ISBN != null) {
                try {
                    int pubYear = Integer.parseInt(pubYearStr);
                    int pageCount = Integer.parseInt(pageCountStr);
                    result = dbBean.createRecord(title, author, genre, pubYear, pageCount, ISBN);
                } catch (NumberFormatException e) {
                    result = "Error: Invalid publication year or page count";
                }
            }

            out.print(dbBean.read(title));

            out.println("<br />");

            out.print(dbBean.readAll());
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

            <label for="pageCount">Page Count:</label><br>
            <input type="number" id="pageCount" name="pageCount" min="1" required><br><br>

            <label for="ISBN">ISBN:</label><br>
            <input type="text" id="ISBN" name="ISBN" required><br><br>
            
            <input type="submit" value="Create Record">
        </form>
        
        <br><a href="index_02.jsp">Back to Index 02</a>
    </body>
</html>
