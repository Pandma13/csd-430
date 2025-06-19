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
        <title>JSP Select Title Module 5 & 6</title>
    </head>
    <body>
        <h1>JSP Select Title MySQL</h1>
        
        <jsp:useBean id='setupDB' class='configBean.ConfigProject' />
        
    <br /> <a href="index.jsp">index.jsp</a> <br />

    <%
        try{
            out.print(setupDB.read());
        }
        catch(Exception e){
            out.print("Error selecting title");
        }
    %>
    </body>
</html>
