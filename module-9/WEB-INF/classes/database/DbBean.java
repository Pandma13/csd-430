package database;

/*
* Megan Wheeler
* CSD 430 - Module 9
* 9 July 2025
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

    public String updateRecord(String title, String author, String genre, int pubYear, String ISBN) {
        String sql = "UPDATE wheeler_library_data SET title = ?, author = ?, genre = ?, publicationYear = ? WHERE ISBN = ?";
        
        try (java.sql.Connection conn = getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, genre);
            stmt.setInt(4, pubYear);
            stmt.setString(5, ISBN);

            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Return the ISBN so we can display the updated record
                return ISBN;
            } else {
                return "No record found to update";
            }
            
        } catch(java.sql.SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            return "Error updating record: " + e.getMessage();
        }
    }

    // Create Record

    public String createRecord(String title, String author, String genre, int pubYear, String ISBN) {
        String sql = "INSERT INTO wheeler_library_data(title, author, genre, publicationYear, ISBN) VALUES(?, ?, ?, ?, ?)";

        try (java.sql.Connection conn = getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, genre);
            stmt.setInt(4, pubYear);
            stmt.setString(5, ISBN);
            
            // Print the SQL statement for debugging
            System.out.println("Executing SQL: " + sql);
            System.out.println("Parameters: " + title + ", " + author + ", " + genre + ", " + pubYear + ", " + ISBN);

            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Get the generated bookID
                try (java.sql.ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int bookID = generatedKeys.getInt(1);
                        System.out.println("Record created successfully with bookID: " + bookID);
                        return String.valueOf(bookID);
                    } else {
                        System.out.println("Record created successfully but could not retrieve bookID");
                        return "Record created successfully";
                    }
                }
            } else {
                System.out.println("Failed to create record - no rows affected");
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
        
        dataStringBuilder.append("<form method='post' action='").append(requestURL).append("'>\n");
        dataStringBuilder.append("<br /><br />");

        // Title Input
        dataStringBuilder.append("<label for='title'>Title</label>\n");
        dataStringBuilder.append("&nbsp;&nbsp;\n");
        dataStringBuilder.append("<input type='text' name='title' id='title' required maxlength='255'>\n");
        dataStringBuilder.append("<br /><br />\n");

        // Author Input
        dataStringBuilder.append("<label for='author'>Author</label>\n");
        dataStringBuilder.append("&nbsp;&nbsp;\n");
        dataStringBuilder.append("<input type='text' name='author' id='author' required maxlength='255'>\n");
        dataStringBuilder.append("<br /><br />\n");

        // Genre Input
        dataStringBuilder.append("<label for='genre'>Genre</label>\n");
        dataStringBuilder.append("&nbsp;&nbsp;\n");
        dataStringBuilder.append("<input type='text' name='genre' id='genre' required maxlength='100'>\n");
        dataStringBuilder.append("<br /><br />\n");

        // Publication Year Input
        dataStringBuilder.append("<label for='pubYear'>Publication Year</label>\n");
        dataStringBuilder.append("&nbsp;&nbsp;\n");
        dataStringBuilder.append("<input type='number' name='pubYear' id='pubYear' required min='1800' max='2025'>\n");
        dataStringBuilder.append("<br /><br />\n");

        // ISBN Input
        dataStringBuilder.append("<label for='ISBN'>ISBN</label>\n");
        dataStringBuilder.append("&nbsp;&nbsp;\n");
        dataStringBuilder.append("<input type='text' name='ISBN' id='ISBN' required maxlength='13' pattern='[0-9-]{10,13}'>\n");
        dataStringBuilder.append("<br /><br />\n");

        // Submit Button
        dataStringBuilder.append("<input type='submit' value='Create Record'>\n");
        dataStringBuilder.append("</form>\n");

        return dataStringBuilder.toString();
    }

    // Form for Update - Select book to update
    public String formGetUpdate(String requestURL) {
        StringBuilder dataStringBuilder = new StringBuilder();
        
        dataStringBuilder.append("<form method='post' action='").append(requestURL).append("'>\n");
        dataStringBuilder.append("<label>Select a Book to Update</label>&nbsp;&nbsp;&nbsp;\n");
        dataStringBuilder.append("<br /> \n");
        dataStringBuilder.append("<label for=\"ISBN\">Select a Book by ISBN:</label>\n");
        dataStringBuilder.append("<select name=\"ISBN\" id=\"ISBN\">\n");

        try (java.sql.Connection conn = getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement("SELECT ISBN, title FROM wheeler_library_data ORDER BY title ASC");
            java.sql.ResultSet resultSet = stmt.executeQuery()) {
            
            while(resultSet.next()) {
                dataStringBuilder.append("<option value=\"");
                dataStringBuilder.append(resultSet.getString("ISBN"));
                dataStringBuilder.append("\">");
                dataStringBuilder.append(resultSet.getString("title"));
                dataStringBuilder.append(" (ISBN: ").append(resultSet.getString("ISBN")).append(")");
                dataStringBuilder.append("</option>");
            }
        } catch(java.sql.SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }

        dataStringBuilder.append("</select>");
        dataStringBuilder.append("<input type='submit' value='Select Book to Update' />");
        dataStringBuilder.append("</form>");
        
        return dataStringBuilder.toString();
    }

    // Form for Update with pre-populated data
    public String formUpdateWithData(String requestURL, String ISBN) {
        StringBuilder dataStringBuilder = new StringBuilder();
        
        // First, get the current data for the selected ISBN
        String currentTitle = "";
        String currentAuthor = "";
        String currentGenre = "";
        String currentPubYear = "";
        
        try (java.sql.Connection conn = getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement("SELECT title, author, genre, publicationYear FROM wheeler_library_data WHERE ISBN = ?")) {
            
            stmt.setString(1, ISBN);
            try (java.sql.ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    currentTitle = resultSet.getString("title");
                    currentAuthor = resultSet.getString("author");
                    currentGenre = resultSet.getString("genre");
                    currentPubYear = resultSet.getString("publicationYear");
                }
            }
        } catch(java.sql.SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }
        
        dataStringBuilder.append("<form method='post' action='").append(requestURL).append("'>\n");
        dataStringBuilder.append("<br /><br />");
        dataStringBuilder.append("<h3>Update Book Record (ISBN: ").append(ISBN).append(")</h3>");

        // Title Input
        dataStringBuilder.append("<label for='title'>Title</label>\n");
        dataStringBuilder.append("&nbsp;&nbsp;\n");
        dataStringBuilder.append("<input type='text' name='title' id='title' value='").append(currentTitle).append("' required maxlength='255'>\n");
        dataStringBuilder.append("<br /><br />\n");

        // Author Input
        dataStringBuilder.append("<label for='author'>Author</label>\n");
        dataStringBuilder.append("&nbsp;&nbsp;\n");
        dataStringBuilder.append("<input type='text' name='author' id='author' value='").append(currentAuthor).append("' required maxlength='255'>\n");
        dataStringBuilder.append("<br /><br />\n");

        // Genre Input
        dataStringBuilder.append("<label for='genre'>Genre</label>\n");
        dataStringBuilder.append("&nbsp;&nbsp;\n");
        dataStringBuilder.append("<input type='text' name='genre' id='genre' value='").append(currentGenre).append("' required maxlength='100'>\n");
        dataStringBuilder.append("<br /><br />\n");

        // Publication Year Input
        dataStringBuilder.append("<label for='pubYear'>Publication Year</label>\n");
        dataStringBuilder.append("&nbsp;&nbsp;\n");
        dataStringBuilder.append("<input type='number' name='pubYear' id='pubYear' value='").append(currentPubYear).append("' required min='1800' max='2025'>\n");
        dataStringBuilder.append("<br /><br />\n");

        // Hidden ISBN field
        dataStringBuilder.append("<input type='hidden' name='ISBN' value='").append(ISBN).append("'>\n");

        // Submit Button
        dataStringBuilder.append("<input type='submit' value='Update Record'>\n");
        dataStringBuilder.append("</form>\n");

        return dataStringBuilder.toString();
    }

    // Form Get PK

    public String formGetPK(String requestURL) {
        StringBuilder dataStringBuilder = new StringBuilder();
        
        dataStringBuilder.append("<form method='post' action='").append(requestURL).append("'>\n");
        dataStringBuilder.append("<label>Select a Book by Title</label>&nbsp;&nbsp;&nbsp;\n");
        dataStringBuilder.append("<br /> \n");
        dataStringBuilder.append("<label for=\"bookID\">Select a Book Title:</label>\n");
        dataStringBuilder.append("<select name=\"bookID\" id=\"bookID\">\n");

        try (java.sql.Connection conn = getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement("SELECT bookID, title FROM wheeler_library_data ORDER BY title ASC");
            java.sql.ResultSet resultSet = stmt.executeQuery()) {
            
            while(resultSet.next()) {
                dataStringBuilder.append("<option value=\"");
                dataStringBuilder.append(resultSet.getString("bookID"));
                dataStringBuilder.append("\">");
                dataStringBuilder.append(resultSet.getString("title"));
                dataStringBuilder.append("</option>");
            }
        } catch(java.sql.SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }

        dataStringBuilder.append("</select>");
        dataStringBuilder.append("<input type='submit' />");
        dataStringBuilder.append("</form>");
        
        return dataStringBuilder.toString();
    }

    // Read by Book ID

    public String read(String bookID) {
        StringBuilder dataStringBuilder = new StringBuilder();
        
        // Check if bookID is null or empty
        if (bookID == null || bookID.trim().isEmpty()) {
            return "<p>No book ID provided for search.</p>";
        }
        
        String sql = "SELECT * FROM wheeler_library_data WHERE bookID = ?";
        
        try (java.sql.Connection conn = getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, bookID);
            
            try (java.sql.ResultSet resultSet = stmt.executeQuery()) {
                dataStringBuilder.append("<table border='1' class='table'>");
                dataStringBuilder.append("<thead><tr><th>Book ID</th><th>Title</th><th>Author</th><th>Genre</th><th>Publication Year</th><th>ISBN</th></tr></thead>");
                dataStringBuilder.append("<tbody>");

                boolean found = false;
                while(resultSet.next()) {
                    found = true;
                    dataStringBuilder.append("<tr>");
                    
                    for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        dataStringBuilder.append("<td>");
                        String value = resultSet.getString(i);
                        dataStringBuilder.append(value != null ? value.trim() : "");
                        dataStringBuilder.append("</td>");
                    }
                    dataStringBuilder.append("</tr>");
                }
                
                if (!found) {
                    dataStringBuilder.append("<tr><td colspan='7'>No book found with ID: ").append(bookID).append("</td></tr>");
                }
                
                dataStringBuilder.append("</tbody>");
                dataStringBuilder.append("</table>");
            }
            
        } catch(java.sql.SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            dataStringBuilder.append("<p>Error retrieving data: ").append(e.getMessage()).append("</p>");
        }
        
        return dataStringBuilder.toString();
    }

    // Read by ISBN

    public String readByISBN(String ISBN) {
        StringBuilder dataStringBuilder = new StringBuilder();
        
        // Check if ISBN is null or empty
        if (ISBN == null || ISBN.trim().isEmpty()) {
            return "<p>No ISBN provided for search.</p>";
        }
        
        String sql = "SELECT * FROM wheeler_library_data WHERE ISBN = ?";
        
        try (java.sql.Connection conn = getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, ISBN);
            
            try (java.sql.ResultSet resultSet = stmt.executeQuery()) {
                dataStringBuilder.append("<table border='1' class='table'>");
                dataStringBuilder.append("<thead><tr><th>Book ID</th><th>Title</th><th>Author</th><th>Genre</th><th>Publication Year</th><th>ISBN</th></tr></thead>");
                dataStringBuilder.append("<tbody>");

                boolean found = false;
                while(resultSet.next()) {
                    found = true;
                    dataStringBuilder.append("<tr>");
                    
                    for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        dataStringBuilder.append("<td>");
                        String value = resultSet.getString(i);
                        dataStringBuilder.append(value != null ? value.trim() : "");
                        dataStringBuilder.append("</td>");
                    }
                    dataStringBuilder.append("</tr>");
                }
                
                if (!found) {
                    dataStringBuilder.append("<tr><td colspan='7'>No book found with ISBN: ").append(ISBN).append("</td></tr>");
                }
                
                dataStringBuilder.append("</tbody>");
                dataStringBuilder.append("</table>");
            }
            
        } catch(java.sql.SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            dataStringBuilder.append("<p>Error retrieving data: ").append(e.getMessage()).append("</p>");
        }
        
        return dataStringBuilder.toString();
    }

    // Delete

    public String delete(String bookID) {
        String sql = "DELETE FROM wheeler_library_data WHERE bookID = ?";
        
        try (java.sql.Connection conn = getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, bookID);
            
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
            
            dataStringBuilder.append("<table border='1'>");
            dataStringBuilder.append("<thead><tr><th>Book ID</th><th>Title</th><th>Author</th><th>Genre</th><th>Publication Year</th><th>ISBN</th></tr></thead>");
            dataStringBuilder.append("<tbody>");

            while(resultSet.next()) {
                dataStringBuilder.append("<tr>");
                for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    dataStringBuilder.append("<td>");
                    dataStringBuilder.append(resultSet.getString(i).trim());
                    dataStringBuilder.append("</td>");
                }
                dataStringBuilder.append("</tr>");
            }
            dataStringBuilder.append("</tbody>");
            dataStringBuilder.append("</table>");
            
        } catch(java.sql.SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            dataStringBuilder.append("<p>Error retrieving data: ").append(e.getMessage()).append("</p>");
        }

        return dataStringBuilder.toString();
    }

    // Database Connection

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