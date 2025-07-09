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
<title>CRUD Read - Library Management</title>
<link rel="stylesheet" type="text/css" href="MyStyleSheet.css">
</head>
<body>
    <div class="container">
        <h1>Read All Book Records</h1>
        
        <jsp:useBean id='dbBean' class='database.DbBean' />

        <a href="index_02.jsp">Back to Index 02</a>
        
        <%
        if(request.getMethod().equals("GET")){
            String value = dbBean.formGetPK("CRUD_Read.jsp");
            out.print("<div class='container'>");
            out.print("<h3>Search Library</h3>");
            out.print(value);
            out.print("</div>");
        }
        %>

        <%
        if(request.getMethod().equals("POST")){
            out.print("<div class='container'>");
            out.print("<h3>Search Library</h3>");
            out.print(dbBean.formGetPK("CRUD_Read.jsp"));

            out.print("<h3>Selected Book Record</h3>");
            String bookID = request.getParameter("bookID");
            
            // Debug information
            // out.print("<p><strong>Debug:</strong> Received bookID parameter: '" + (bookID != null ? bookID : "null") + "'</p>");
            
            if (bookID != null && !bookID.trim().isEmpty()) {
                out.print(dbBean.read(bookID));
            } else {
                out.print("<p>Please select a book from the dropdown above.</p>");
            }

            out.print("<h3>All Book Records</h3>");
            out.print(dbBean.readAll());
            out.print("</div>");
        }
        %>
    </div>
</body>
</html>
