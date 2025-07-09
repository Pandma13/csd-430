<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
    Megan Wheeler
    CSD 430 - Module 9
    9 July 2025
-->
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>CRUD Update - Library Management</title>
    <link rel="stylesheet" type="text/css" href="MyStyleSheet.css">
</head>
<body>
    <div class="container">
    <h1>Update Book Record</h1>
    
    <jsp:useBean id='dbBean' class='database.DbBean' />
    
    <a href="index_02.jsp">Back to Index 02</a>

    <%
    if ("GET".equalsIgnoreCase(request.getMethod())) {
        String value = dbBean.formGetUpdate("CRUD_Update.jsp");
        out.print("<div class='container'>");
        out.print("<h3>Select Book to Update</h3>");
        out.print(value);
        out.print("</div>");
    }
    %>

    <%
    String result = "";
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        String pubYearStr = request.getParameter("pubYear");
        String ISBN = request.getParameter("ISBN");
        
        // Check if this is the first POST (selecting a book) or the second POST (updating the book)
        if (title == null && author == null && genre == null && pubYearStr == null && ISBN != null) {
            // First POST - user selected a book to update
            out.print("<div class='container'>");
            out.print("<h3>Update Book Record</h3>");
            out.print(dbBean.formUpdateWithData("CRUD_Update.jsp", ISBN));
            out.print("</div>");
        } else if (title != null && author != null && genre != null && pubYearStr != null && ISBN != null) {
            // Second POST - user submitted the update form
            try {
                int pubYear = Integer.parseInt(pubYearStr);
                result = dbBean.updateRecord(title, author, genre, pubYear, ISBN);
            } catch (NumberFormatException e) {
                result = "Error: Invalid publication year";
            }

            out.print("<div class='container'>");
            out.print("<h3>Update Result</h3>");
            out.print("<p>" + result + "</p>");
            
            // Display the updated record if update was successful
            if (result != null && !result.startsWith("Error") && !result.startsWith("No record found")) {
                out.print("<h3>Updated Book Record</h3>");
                out.print(dbBean.readByISBN(result));
            }

            out.print("<h3>All Book Records</h3>");
            out.print(dbBean.readAll());
            out.print("</div>");
        } else {
            out.print("<div class='container'>");
            out.print("<h3>Error</h3>");
            out.print("<p>Invalid form data. Please try again.</p>");
            out.print(dbBean.formGetUpdate("CRUD_Update.jsp"));
            out.print("</div>");
        }
    }
    %>

    </div>
</body>
</html>
