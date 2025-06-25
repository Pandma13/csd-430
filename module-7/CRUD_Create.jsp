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
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f0f0f0;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #2c3e50;
            text-align: center;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border: 1px solid #ddd;
        }
        th {
            background-color: #3625f2;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #ddd;
        }
    </style>
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
        out.print("<h3>Search Library</h3>");
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
        }

        out.print("<div class='container'>");
        out.print("<h3>Search Library</h3>");
        out.print(dbBean.formGetPK("CRUD_Read.jsp"));

        out.print("<h3>New Book Record</h3>");
        out.print("<table>");
        out.print("<thead><tr><th>Book ID</th><th>Title</th><th>Author</th><th>Genre</th><th>Publication Year</th><th>Page Count</th><th>ISBN</th></tr></thead>");
        out.print("<tbody>");
        out.print(dbBean.read(result));
        out.print("</tbody>");
        out.print("</table>");

        out.print("<h3>All Book Records</h3>");
        out.print("<table>");
        out.print("<thead><tr><th>Book ID</th><th>Title</th><th>Author</th><th>Genre</th><th>Publication Year</th><th>Page Count</th><th>ISBN</th></tr></thead>");
        out.print("<tbody>");
        out.print(dbBean.readAll());
        out.print("</tbody></table>");
        out.print("</div>");
    }
    %>
    
    </div>
</body>
</html>
