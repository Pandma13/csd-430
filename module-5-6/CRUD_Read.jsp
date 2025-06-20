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
<title>CRUD Read - Library Management</title>
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
    .nav-link {
        display: inline-block;
        margin: 10px 0;
        padding: 10px 15px;
        background-color: #3625f2;
        color: white;
        text-decoration: none;
        border-radius: 5px;
        transition: background-color 0.3s;
    }
    .nav-link:hover {
        background-color: #2a1dd9;
    }
    .content-section {
        margin: 20px 0;
        padding: 15px;
        background-color: #f9f9f9;
        border-radius: 5px;
        border-left: 4px solid #3625f2;
    }
    .form-output {
        margin: 15px 0;
        padding: 10px;
        background-color: #e8f4f8;
        border-radius: 5px;
        border: 1px solid #b3d9e6;
    }
</style>
</head>
<body>
    <div class="container">
        <h1>Read All Book Records</h1>
        
        <jsp:useBean id='dbBean' class='database.DbBean' />

        <a href="index_02.jsp" class="nav-link">Back to Index 02</a>
        
        <%
            if(request.getMethod().equals("GET")){
                String value = dbBean.formGetPK("CRUD_Read.jsp");
                out.print("<div class='content-section'>");
                out.print("<h3>Search Form</h3>");
                out.print("<div class='form-output'>");
                out.print(value);
                out.print("</div>");
                out.print("</div>");
            }
        %>

        <%
            if(request.getMethod().equals("POST")){
                out.print("<div class='content-section'>");
                out.print("<h3>Book Records</h3>");
                
                out.print("<div class='form-output'>");
                out.print("<h4>Search Form</h4>");
                out.print(dbBean.formGetPK("CRUD_Read.jsp"));
                out.print("</div>");

                out.print("<div class='form-output'>");
                out.print("<h4>All Book Records</h4>");
                out.print("<table>");
                out.print("<thead><tr><th>Book ID</th><th>Title</th><th>Author</th><th>Genre</th><th>Publication Year</th></tr></thead>");
                out.print("<tbody>");
                out.print(dbBean.readAll());
                out.print("</tbody></table>");
                out.print("</div>");
                
                out.print("</div>");
            }
        %>
    </div>
</body>
</html>
