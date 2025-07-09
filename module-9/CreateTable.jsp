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
        <title>JSP Create Table</title>
    </head>
    <body>
        <h1>JSP Create Table MySQL</h1>
        
        <jsp:useBean id='setupDB' class='configBean.ConfigProject' />
        
    <br /> <a href="index.jsp">index.jsp</a> <br />

    <%
        try{
            out.print(setupDB.createTable());
        }
        catch(Exception e){
            out.print("Error creating table");
        }
    %>
    </body>
</html>
