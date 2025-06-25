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
    <link rel="stylesheet" type="text/css" href="MyStyleSheet.css">
</head>
<body>
    <div class="container">
    <h1>Create New Book Record</h1>
    
    <jsp:useBean id='dbBean' class='database.DbBean' />

    <a href="index_02.jsp">Back to Index 02</a>

    <%
    if ("GET".equalsIgnoreCase(request.getMethod())) {
        String value = dbBean.formGetCreateOrUpdate("CRUD_Create.jsp");
        out.print("<div class='container'>");
        out.print("<h3>Create New Book Record</h3>");
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
        } else {
            result = "Error: All fields are required";
        }

        out.print("<div class='container'>");
        out.print("<h3>Create New Book Record</h3>");
        out.print(dbBean.formGetCreateOrUpdate("CRUD_Create.jsp"));

        out.print("<h3>Result</h3>");
        out.print("<p>" + result + "</p>");

        // Try to display the newly created record if we got a bookID back
        if (result != null && !result.startsWith("Error") && !result.startsWith("Failed")) {
            try {
                int bookID = Integer.parseInt(result);
                out.print("<h3>Newly Created Book Record</h3>");
                out.print(dbBean.read(result));
            } catch (NumberFormatException e) {
                // If result is not a number, it's probably a success message
                out.print("<h3>Newly Created Book Record</h3>");
                out.print("<p>Record was created successfully. Please check the 'All Book Records' section below to see the new record.</p>");
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
