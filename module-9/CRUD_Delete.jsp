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
    <title>CRUD Delete - Library Management</title>
    <link rel="stylesheet" type="text/css" href="MyStyleSheet.css">
</head>
<body>
    <h1>Delete Book Record</h1>
    
    <jsp:useBean id='dbBean' class='database.DbBean' />

    <a href="index_02.jsp">Back to Index 02</a>

    <%
    if ("GET".equalsIgnoreCase(request.getMethod())) {
        String value = dbBean.formGetPK("CRUD_Delete.jsp");
        out.print("<div class='container'>");
        out.print("<h3>Delete Book Record</h3>");
        out.print(value);
        out.print("</div>");
    }
    %>

    <%
    String result = "";
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String bookIDStr = request.getParameter("bookID");
        
        if (bookIDStr != null) {
            try {
                result = dbBean.delete(bookIDStr);
            } catch (NumberFormatException e) {
                result = "Error: Invalid Book ID format";
            }
        } else {
            result = "Error: Book ID is required";
        }

        out.print("<div class='container'>");
        out.print("<h3>Delete Result</h3>");
        out.print("<p>" + result + "</p>");

        if (result != null && !result.startsWith("Error")) {
            out.print("<h3>Deleted Book Record</h3>");
            out.print(dbBean.read(result));
        }

        out.print("<h3>All Book Records</h3>");
        out.print(dbBean.readAll());
        out.print("</div>");
    }
    %>
    
</body>
</html>
