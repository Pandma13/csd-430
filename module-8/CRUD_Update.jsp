<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
    Megan Wheeler
    CSD 430 - Module 8
    6 July 2025
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
        String value = dbBean.formGetCreateOrUpdate("CRUD_Update.jsp");
        out.print("<div class='container'>");
        out.print("<h3>Update Book Record</h3>");
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
        
        if (title != null && author != null && genre != null && pubYearStr != null && ISBN != null) {
            try {
                int pubYear = Integer.parseInt(pubYearStr);
                result = dbBean.updateRecord(title, author, genre, pubYear, ISBN);
            } catch (NumberFormatException e) {
                result = "Error: Invalid number format";
            }
        } else {
            result = "Error: All fields are required";
        }

        out.print("<div class='container'>");
        out.print("<h3>Update Book Record</h3>");
        out.print(dbBean.formGetCreateOrUpdate("CRUD_Update.jsp"));

        out.print("<h3>Result</h3>");
        out.print("<p>" + result + "</p>");
        
        // Try to display the updated record if we got a bookID back
        if (result != null && !result.startsWith("Error") && !result.startsWith("Failed")) {
            try {
                int bookID = Integer.parseInt(result);
                out.print("<h3>Updated Book Record</h3>");
                out.print(dbBean.read(result));
            } catch (NumberFormatException e) {
                // If result is not a number, it's probably a success message
                out.print("<h3>Updated Book Record</h3>");
                out.print("<p>Record was updated successfully.</p>");
            }
        }

        out.print("<h3>All Book Records</h3>");
        out.print(dbBean.readAll());
        out.print("</div>");
    }
    %>

    </div>
</body>
</html>
