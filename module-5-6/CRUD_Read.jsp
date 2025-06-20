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

        <br /><a href="index_02.jsp">Back to Index 02</a> <br />
        
        <%
            if(request.getMethod().equals("GET")){
                String value = dbBean.formGetPK("CRUD_Read.jsp");
                out.print(value);
            }
        %>

        <%
            if(request.getMethod().equals("POST")){
                out.print(dbBean.formGetPK("CRUD_Read.jsp"));

                String title = request.getParameter("title");
                out.print(dbBean.read(title));

                out.println("<br />");
                out.print(dbBean.readAll());
            }
        %>
        
    </body>
</html>
