package database;

import java.sql.SQLException;

/*
* Megan Wheeler
* CSD 430 - Module 5 & 6
* 18 June 2025
*/

public class DbBean implements java.io.Serializable {

    java.sql.Connection connection;
    java.sql.Statement statement;
    
    private static final long serialVersionUID = 1111222233334444L;
    
    // Constructor
    
    public DbBean() {

        try {
    
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:8080/csd430";
            connection = java.sql.DriverManager.getConnection(url + "user=student1&password=pass");
            statement = connection.createStatement();
        }
        catch(ClassNotFoundException | java.sql.SQLException e) {

            System.out.print("SQL Exception" + e);
        }
    }

    // Update Record

    public String updateRecord(String title, String author, String genre, int pubYear) {
    
        try {
    
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:8080/csd430";
            connection = java.sql.DriverManager.getConnection(url + "user=student1&password=pass");
        }
        catch(ClassNotFoundException | java.sql.SQLException e) {

            System.out.print("SQL Exception" + e);
        }
    
		String sql = "UPDATE wheeler_library_data SET title = ?, author = ?, genre = ?, pubYear = ? WHERE bookID = ?";
	
		try {
            java.sql.PreparedStatement sqlStatement = connection.prepareStatement(sql);
            
            sqlStatement.setString(1, title );
            sqlStatement.setString(2, author );
            sqlStatement.setString(3, genre );
            sqlStatement.setInt(4, pubYear );

            sqlStatement.executeUpdate();
            statement.close();
		}
        catch(java.sql.SQLException sqle){
    
            System.out.print("SQL Exception" + sqle);
        }
		
        return "Complete";
    }
    
    // Create Record
    
    public void createRecord(String title, String author, String genre, int pubYear) {
    
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/baseball?";
            connection = java.sql.DriverManager.getConnection(url + "user=student5&password=pass");
        }
        catch(ClassNotFoundException | java.sql.SQLException e) {
            System.out.print("SQL Exception" + e);
        }
    
        try {
            String sql = "INSERT INTO wheeler_library_data(title, author, genre, pubYear)" + "VALUES(?, ?, ?, ?)";
    
            try (java.sql.PreparedStatement sqlStatement = connection.prepareStatement(sql)) {
                sqlStatement.setString(1, title );
                sqlStatement.setString(2, author );
                sqlStatement.setString(3, genre );
                sqlStatement.setInt(4, pubYear );
                
                sqlStatement.executeUpdate();
            }
        }
        catch(java.sql.SQLException sqle) {
            System.out.print("SQL Exception" + sqle);
        }
    }
    
    // Form Get, Create, or Update
    
    public String formGetCreateOrUpdate(String requestURL) {
    
        StringBuilder dataStringBuilder = new StringBuilder();

        java.sql.ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:8080/csd430";
            connection = java.sql.DriverManager.getConnection(url + "user=student1&password=pass");
            statement = connection.createStatement();
        }
        catch(ClassNotFoundException | java.sql.SQLException e) {
            System.out.print("SQL Exception" + e);
        }
        
        try{
            resultSet = statement.executeQuery("SELECT DISTINCT title FROM wheeler_library_data ORDER BY title ASC");
        }
        catch(java.sql.SQLException e){
            System.out.print("SQL Exception" + e);
        }

        dataStringBuilder.append("<form method='post' action='" + requestURL + "'>");
        dataStringBuilder.append("<br /><br />");

        // Open Title

        dataStringBuilder.append("<label for='title'>Title</label>");
        dataStringBuilder.append("&nbsp&nbsp");  
        dataStringBuilder.append("<input name='title' list='title'>");     
        dataStringBuilder.append("<datalist id='title'>\n");  
    
        try{
            while(resultSet.next()){
                dataStringBuilder.append("<option value='").append(resultSet.getString(1)).append("'>\n");
            }
        }
        catch(SQLException sqle){

            System.out.print("<b>SQL Exception.</b><br />");
            System.out.print(sqle);
        }
    
        dataStringBuilder.append("</datalist>");
        dataStringBuilder.append("</input>");
        dataStringBuilder.append("<br /><br />");

        // Close Title

        // Open Author

        try{
            resultSet = statement.executeQuery("SELECT DISTINCT author FROM wheeler_library_data ORDER BY author ASC");
        }
        catch(SQLException sqle){
            System.out.print("<b>SQL Exception.</b><br />");
            System.out.print(sqle);
        }

        dataStringBuilder.append("<label for='author'>Author</label>");
        dataStringBuilder.append("&nbsp&nbsp");
        dataStringBuilder.append("<input name='author' list='author'>");
        dataStringBuilder.append("<datalist id='author'>\n");

        try{
            while(resultSet.next()){
                dataStringBuilder.append("<option value='").append(resultSet.getString(1)).append("'>\n");
            }
        }
        catch(SQLException sqle){
            System.out.print("<b>SQL Exception.</b><br />");
            System.out.print(sqle);
        }
        
        dataStringBuilder.append("</datalist>");
        dataStringBuilder.append("</input>");  
        dataStringBuilder.append("<br /><br />");  

        // Close Author

        // ---------------------------------------------------------------
        // ---------------------------------------------------------------
        // ------------------------ Open Year ----------------------------
        // ---------------------------------------------------------------
        // ---------------------------------------------------------------
    	
    	if( requestURL.equals("CRUD_Create.jsp") ) {
    	
            dataStringBuilder.append("<label for='year'>Year</label>");
            dataStringBuilder.append("&nbsp&nbsp");   
            dataStringBuilder.append("<input type='text' name='year' maxlength='20'>");      	
            dataStringBuilder.append("<br /><br />");   
    	}
    	else if ( requestURL.equals("CRUD_Update_2.jsp") ) {
    	
        	try{
        		
        		resultSet = statement.executeQuery("SELECT DISTINCT Year_T FROM World_Series ORDER BY Year_T ASC");
            }
            catch(java.sql.SQLException e){
            	
            }
    		
            dataStringBuilder.append("<label for='year'>Year</label>");
            dataStringBuilder.append("&nbsp&nbsp");   
            dataStringBuilder.append("<select name='year' id='year' maxlength='20'>");   
            
            try {
                while(resultSet.next()) {
            	    dataStringBuilder.append("<option value=" + resultSet.getString(1) + ">" + resultSet.getString(1) + "</option>");
                }
            }
            catch(java.sql.SQLException e){
            	dataStringBuilder.append("<br /><br />");   
            }
   
            dataStringBuilder.append("</select>");
            dataStringBuilder.append("<br /><br />");
    	}
    	
        // ---------------------------------------------------------------
        // ------------------------ Close Year ---------------------------
        // ---------------------------------------------------------------

        // Open Genre
        try{
            resultSet = statement.executeQuery("SELECT DISTINCT genre FROM wheeler_library_data ORDER BY genre ASC");
        }
        catch(java.sql.SQLException e){
            System.out.print("<b>SQL Exception.</b><br />");
            System.out.print(e);
        }

        dataStringBuilder.append("<label for='genre'>Genre</label>");
        dataStringBuilder.append("&nbsp&nbsp");
        dataStringBuilder.append("<input name='genre' list='genre'>");
        dataStringBuilder.append("<datalist id='genre'>\n");

        try{
            while(resultSet.next()){
                dataStringBuilder.append("<option value='").append(resultSet.getString(1)).append("'>\n");
            }
        }
        catch(SQLException sqle){
            System.out.print("<b>SQL Exception.</b><br />");
            System.out.print(sqle);
        }

        dataStringBuilder.append("</datalist>");
        dataStringBuilder.append("</input>");
        dataStringBuilder.append("<br /><br />");

        // Close Genre

        // Close Form

        dataStringBuilder.append("<input type='submit' value='Submit'>");
        dataStringBuilder.append("</form>");

        return dataStringBuilder.toString();
    }
    
    // Form Get PK

    public String formGetPK(String requestURL) {

        java.sql.ResultSet resultSet = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:8080/csd430";
            connection = java.sql.DriverManager.getConnection(url + "user=student1&password=pass");
            statement = connection.createStatement();
        }
        catch(ClassNotFoundException | java.sql.SQLException e) {
            System.out.print("SQL Exception" + e);
        }
        
        try{
            resultSet = statement.executeQuery("SELECT bookID FROM wheeler_library_data");
        }
        catch(java.sql.SQLException e){
            System.out.print("SQL Exception" + e);
        }

        StringBuilder dataStringBuilder = new StringBuilder();
        
        // Add Data to StringBuilder
        dataStringBuilder.append("<form method='post' action='").append(requestURL).append("'>\n");
        dataStringBuilder.append("<label>Enter your favorite Book Title</label>&nbsp;&nbsp;&nbsp;\n");
        dataStringBuilder.append("<br /> \n");
        dataStringBuilder.append("<label for=\\\"title\\\">Select a Book Title:</label>\n");
        dataStringBuilder.append("<select name=\"title\" id=\"title\">\n");

        try{
            while(resultSet.next()){

                for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++){

                dataStringBuilder.append("<option value=\"");

                dataStringBuilder.append((resultSet.getString(i)));

                dataStringBuilder.append("\">\n");

                dataStringBuilder.append((resultSet.getString(i)));

                dataStringBuilder.append("</option>");
                }
            }

        }
        catch(SQLException e){
            System.out.print("<b>SQL Exception.</b><br />");
            System.out.print(e);
        }

        dataStringBuilder.append("</select>");

        dataStringBuilder.append("<input type='submit' />");

        dataStringBuilder.append("</form>");

        resultSet = null;
        
        return dataStringBuilder.toString();
    }
    
    // Read

    public String read(int bookID) {
        
        StringBuilder dataStringBuilder = new StringBuilder();
        
        java.sql.ResultSet resultSet = null;
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:8080/csd430";
            connection = java.sql.DriverManager.getConnection(url + "user=student1&password=pass");
            statement = connection.createStatement();
        }
        catch(ClassNotFoundException cnfe) {
            System.out.print("Class Not Found Exception" + cnfe);
        }
        catch(java.sql.SQLException e){
            System.out.print("SQL Exception" + e);
        }

        try{
            resultSet = statement.executeQuery("SELECT * FROM wheeler_library_data WHERE bookID = " + bookID);
        }
        catch(java.sql.SQLException e){
            System.out.print("SQL Exception" + e);
        }
        
        try{
            dataStringBuilder.append("<table border='1' bgcolor='FA8072'>");
            
            while(resultSet.next()){
                dataStringBuilder.append("<tr>");
                    
                for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++){
                    dataStringBuilder.append("<td>");
                    dataStringBuilder.append((resultSet.getString(i)).trim());
                    dataStringBuilder.append("</td>");
                }
                    
                dataStringBuilder.append("</tr>");
            }
                
        dataStringBuilder.append("</table>");
        }
        catch(SQLException sqle){

        System.out.print("<b>SQL Exception.</b><br />");
        System.out.print(sqle);
        }
        
        return dataStringBuilder.toString();
    }
    
    // Delete
    
    public String delete(int bookID) {

        StringBuilder dataStringBuilder = new StringBuilder();

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:8080/csd430";
            connection = java.sql.DriverManager.getConnection(url + "user=student1&password=pass");
            statement = connection.createStatement();
        }
        catch(ClassNotFoundException cnfe) {
            System.out.print("Class Not Found Exception" + cnfe);
        }
        catch(java.sql.SQLException e){
            System.out.print("SQL Exception" + e);
        }

        try{
            statement.executeUpdate("DELETE FROM wheeler_library_data WHERE bookID = " + bookID);
            dataStringBuilder.append("The record has been deleted.");
        }
        catch(java.sql.SQLException e){
            System.out.print("SQL Exception" + e);
        }

        return dataStringBuilder.toString();
    }

    // Read All
    
    public String readAll() {

        StringBuilder dataStringBuilder = new StringBuilder();

        java.sql.ResultSet resultSet = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:8080/csd430";
            connection = java.sql.DriverManager.getConnection(url + "user=student1&password=pass");
            statement = connection.createStatement();
        }
        catch(ClassNotFoundException cnfe) {
            System.out.print("Class Not Found Exception" + cnfe);
        }
        catch(java.sql.SQLException e){
            System.out.print("SQL Exception" + e);
        }

        try{
            resultSet = statement.executeQuery("SELECT * FROM wheeler_library_data");
        }
        catch(java.sql.SQLException e){
            System.out.print("SQL Exception" + e);
        }

        try{
            dataStringBuilder.append("<table border='1' bgcolor='FFFF00'>");
            
            while(resultSet.next()){
                dataStringBuilder.append("<tr>");
                    
                for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++){
                    dataStringBuilder.append("<td>");
                    dataStringBuilder.append((resultSet.getString(i)).trim());
                    dataStringBuilder.append("</td>");
                }
                    
                dataStringBuilder.append("</tr>");
            }
                
            dataStringBuilder.append("</table>");
        }
        catch(SQLException sqle){
            System.out.print("<b>SQL Exception.</b><br />");
            System.out.print(sqle);
        }

        return dataStringBuilder.toString();
    }

    // Close Connection

    public void closeConnection(){
        
        try {
            statement.close();
            connection.close();
        }
        catch(java.sql.SQLException sqle){
            System.out.print("SQL Exception" + sqle);
        }
    }
}