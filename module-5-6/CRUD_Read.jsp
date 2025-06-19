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
    </head>
    <body>
        <h1>Read All Book Records</h1>
        
        <jsp:useBean id='dbBean' class='database.DbBean' />
        
        <%
            String result = "";
            try {
                result = dbBean.readAll();
            } catch (Exception e) {
                result = "Error reading records: " + e.getMessage();
            }
        %>
        
        <div><%= result %></div>
        
        <br><a href="index.jsp">Back to Index</a>
    </body>
</html>
