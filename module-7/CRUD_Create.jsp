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

        <br><a href="index_02.jsp">Back to Index 02</a>

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
        
    </body>
</html>
