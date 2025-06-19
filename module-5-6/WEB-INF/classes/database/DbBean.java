package database;

/*
* Megan Wheeler
* CSD 430 - Module 5 & 6
* 18 June 2025
*/

public class DbBean implements java.io.Serializable {

    private static final long serialVersionUID = 1111222233334444L;
    
    // Move database configuration to properties file or environment variables
    private static final String DB_URL = System.getProperty("db.url", "jdbc:mysql://localhost:3306/csd430");
    private static final String DB_USER = System.getProperty("db.user", "student1");
    private static final String DB_PASSWORD = System.getProperty("db.password", "pass");
    
    // Constructor
    
    public DbBean() {
        // No need to create connection in constructor
        // Connections will be created as needed in each method
    }

    // Update Record

    public String updateRecord(String title, String author, String genre, int pubYear, int bookID) {
        String sql = "UPDATE wheeler_library_data SET title = ?, author = ?, genre = ?, pubYear = ? WHERE bookID = ?";
        
        try (java.sql.Connection conn = getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, genre);
            stmt.setInt(4, pubYear);
            stmt.setInt(5, bookID);

            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                return "Record updated successfully";
            } else {
                return "No record found to update";
            }
            
        } catch(java.sql.SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            return "Error updating record: " + e.getMessage();
        }
    }
    
    // Create Record
    
    public String createRecord(String title, String author, String genre, int pubYear) {
        String sql = "INSERT INTO wheeler_library_data(title, author, genre, pubYear) VALUES(?, ?, ?, ?)";
        
        try (java.sql.Connection conn = getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, genre);
            stmt.setInt(4, pubYear);
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                return "Record created successfully";
            } else {
                return "Failed to create record";
            }
            
        } catch(java.sql.SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            return "Error creating record: " + e.getMessage();
        }
    }
    
    // Form Get, Create, or Update
    
    public String formGetCreateOrUpdate(String requestURL) {
        StringBuilder dataStringBuilder = new StringBuilder();
        
        dataStringBuilder.append("<form method='post' action='").append(requestURL).append("'>");
        dataStringBuilder.append("<br /><br />");

        // Title dropdown
        dataStringBuilder.append("<label for='title'>Title</label>");
        dataStringBuilder.append("&nbsp&nbsp");
        dataStringBuilder.append("<input name='title' list='title'>");
        dataStringBuilder.append("<datalist id='title'>\n");
        
        try (java.sql.Connection conn = getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT title FROM wheeler_library_data ORDER BY title ASC");
            java.sql.ResultSet resultSet = stmt.executeQuery()) {
            
            while(resultSet.next()) {
                dataStringBuilder.append("<option value='").append(resultSet.getString(1)).append("'>\n");
            }
        } catch(java.sql.SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }
        
        dataStringBuilder.append("</datalist>");
        dataStringBuilder.append("</input>");
        dataStringBuilder.append("<br /><br />");

        // Author dropdown
        dataStringBuilder.append("<label for='author'>Author</label>");
        dataStringBuilder.append("&nbsp&nbsp");
        dataStringBuilder.append("<input name='author' list='author'>");
        dataStringBuilder.append("<datalist id='author'>\n");
        
        try (java.sql.Connection conn = getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT author FROM wheeler_library_data ORDER BY author ASC");
            java.sql.ResultSet resultSet = stmt.executeQuery()) {
            
            while(resultSet.next()) {
                dataStringBuilder.append("<option value='").append(resultSet.getString(1)).append("'>\n");
            }
        } catch(java.sql.SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }
        
        dataStringBuilder.append("</datalist>");
        dataStringBuilder.append("</input>");
        dataStringBuilder.append("<br /><br />");

        // Genre dropdown
        dataStringBuilder.append("<label for='genre'>Genre</label>");
        dataStringBuilder.append("&nbsp&nbsp");
        dataStringBuilder.append("<input name='genre' list='genre'>");
        dataStringBuilder.append("<datalist id='genre'>\n");
        
        try (java.sql.Connection conn = getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT genre FROM wheeler_library_data ORDER BY genre ASC");
            java.sql.ResultSet resultSet = stmt.executeQuery()) {
            
            while(resultSet.next()) {
                dataStringBuilder.append("<option value='").append(resultSet.getString(1)).append("'>\n");
            }
        } catch(java.sql.SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }

        dataStringBuilder.append("</datalist>");
        dataStringBuilder.append("</input>");
        dataStringBuilder.append("<br /><br />");

        // Publication Year
        if (requestURL.equals("CRUD_Create.jsp")) {
            dataStringBuilder.append("<label for='pubYear'>Publication Year</label>");
            dataStringBuilder.append("&nbsp&nbsp");
            dataStringBuilder.append("<input type='text' name='pubYear' maxlength='20'>");
            dataStringBuilder.append("<br /><br />");
        } else {
            dataStringBuilder.append("<label for='pubYear'>Publication Year</label>");
            dataStringBuilder.append("&nbsp&nbsp");   
            dataStringBuilder.append("<select name='pubYear' id='pubYear' maxlength='20'>");   
            
            try (java.sql.Connection conn = getConnection();
                java.sql.PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT pubYear FROM wheeler_library_data ORDER BY pubYear ASC");
                java.sql.ResultSet resultSet = stmt.executeQuery()) {
                
                while(resultSet.next()) {
                    dataStringBuilder.append("<option value='").append(resultSet.getString(1)).append("'>").append(resultSet.getString(1)).append("</option>");
                }
            } catch(java.sql.SQLException e) {
                System.err.println("SQL Exception: " + e.getMessage());
            }

            dataStringBuilder.append("</select>");
            dataStringBuilder.append("<br /><br />");
        }

        // Close Form
        dataStringBuilder.append("<input type='submit' value='Submit'>");
        dataStringBuilder.append("</form>");

        return dataStringBuilder.toString();
    }
    
    // Form Get PK

    public String formGetPK(String requestURL) {
        StringBuilder dataStringBuilder = new StringBuilder();
        
        dataStringBuilder.append("<form method='post' action='").append(requestURL).append("'>\n");
        dataStringBuilder.append("<label>Enter your favorite Book Title</label>&nbsp;&nbsp;&nbsp;\n");
        dataStringBuilder.append("<br /> \n");
        dataStringBuilder.append("<label for=\"title\">Select a Book Title:</label>\n");
        dataStringBuilder.append("<select name=\"title\" id=\"title\">\n");

        try (java.sql.Connection conn = getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement("SELECT bookID FROM wheeler_library_data");
            java.sql.ResultSet resultSet = stmt.executeQuery()) {
            
            while(resultSet.next()) {
                for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    dataStringBuilder.append("<option value=\"");
                    dataStringBuilder.append(resultSet.getString(i));
                    dataStringBuilder.append("\">\n");
                    dataStringBuilder.append(resultSet.getString(i));
                    dataStringBuilder.append("</option>");
                }
            }
        } catch(java.sql.SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }

        dataStringBuilder.append("</select>");
        dataStringBuilder.append("<input type='submit' />");
        dataStringBuilder.append("</form>");
        
        return dataStringBuilder.toString();
    }
    
    // Read

    public String read(int bookID) {
        StringBuilder dataStringBuilder = new StringBuilder();
        
        String sql = "SELECT * FROM wheeler_library_data WHERE bookID = ?";
        
        try (java.sql.Connection conn = getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, bookID);
            
            try (java.sql.ResultSet resultSet = stmt.executeQuery()) {
                dataStringBuilder.append("<table border='1' bgcolor='FA8072'>");
                while(resultSet.next()) {
                    dataStringBuilder.append("<tr>");
                    for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        dataStringBuilder.append("<td>");
                        dataStringBuilder.append(resultSet.getString(i).trim());
                        dataStringBuilder.append("</td>");
                    }
                    dataStringBuilder.append("</tr>");
                }
                dataStringBuilder.append("</table>");
            }
            
        } catch(java.sql.SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            dataStringBuilder.append("<p>Error retrieving data: ").append(e.getMessage()).append("</p>");
        }
        
        return dataStringBuilder.toString();
    }
    
    // Delete
    
    public String delete(int bookID) {
        String sql = "DELETE FROM wheeler_library_data WHERE bookID = ?";
        
        try (java.sql.Connection conn = getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, bookID);
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                return "Record deleted successfully";
            } else {
                return "No record found to delete";
            }
            
        } catch(java.sql.SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            return "Error deleting record: " + e.getMessage();
        }
    }

    // Read All
    
    public String readAll() {
        StringBuilder dataStringBuilder = new StringBuilder();
        
        String sql = "SELECT * FROM wheeler_library_data";
        
        try (java.sql.Connection conn = getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
            java.sql.ResultSet resultSet = stmt.executeQuery()) {
            
            dataStringBuilder.append("<table border='1' bgcolor='FFFF00'>");
            
            while(resultSet.next()) {
                dataStringBuilder.append("<tr>");
                for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    dataStringBuilder.append("<td>");
                    dataStringBuilder.append(resultSet.getString(i).trim());
                    dataStringBuilder.append("</td>");
                }
                dataStringBuilder.append("</tr>");
            }
            
            dataStringBuilder.append("</table>");
            
        } catch(java.sql.SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            dataStringBuilder.append("<p>Error retrieving data: ").append(e.getMessage()).append("</p>");
        }

        return dataStringBuilder.toString();
    }

    // Centralized connection method
    private java.sql.Connection getConnection() throws java.sql.SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connectionUrl = DB_URL + "?user=" + DB_USER + "&password=" + DB_PASSWORD + 
                "&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            return java.sql.DriverManager.getConnection(connectionUrl);
        } catch(ClassNotFoundException e) {
            throw new java.sql.SQLException("Database driver not found: " + e.getMessage(), e);
        }
    }
}