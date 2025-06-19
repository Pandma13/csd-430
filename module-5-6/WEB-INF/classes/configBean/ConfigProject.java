package configBean;

/*
* Megan Wheeler
* CSD 430 - Module 5 & 6
* 18 June 2025
*/

public class ConfigProject implements java.io.Serializable {

    private static final long serialVersionUID = 1111222233334444L;
    
    // Database configuration constants
    private static final String DB_URL = System.getProperty("db.url", "jdbc:mysql://localhost:3306/csd430");
    private static final String DB_USER = System.getProperty("db.user", "student1");
    private static final String DB_PASSWORD = System.getProperty("db.password", "pass");
    
    // Table name constant
    private static final String TABLE_NAME = "wheeler_library_data";
    
    // Constructor
    public ConfigProject() {
        // No need to create connection in constructor
        // Connections will be created as needed in each method
    }
    
    // Create Table
    public String createTable() {
        StringBuilder dataStringBuilder = new StringBuilder();
        
        // Drop table if exists (this is safe and won't throw if table doesn't exist)
        try (java.sql.Connection conn = getConnection();
            java.sql.Statement stmt = conn.createStatement()) {
            
            System.out.println("Attempting to drop table if exists: " + TABLE_NAME);
            stmt.executeUpdate("DROP TABLE IF EXISTS " + TABLE_NAME);
            dataStringBuilder.append("<b>Table ").append(TABLE_NAME).append(" dropped (if it existed).</b><br />");
            System.out.println("Drop operation completed");
            
        } catch(java.sql.SQLException e) {
            dataStringBuilder.append("<b>Error during drop operation: ").append(e.getMessage()).append("</b><br />");
            System.err.println("Error dropping table: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
        }

        // Create table
        try (java.sql.Connection conn = getConnection();
            java.sql.Statement stmt = conn.createStatement()) {
            
            String createTableSQL = "CREATE TABLE " + TABLE_NAME + " (" +
                "BookID int NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "Title varchar(255), " +
                "Author varchar(255), " +
                "Genre varchar(255), " +
                "PublicationYear int NOT NULL" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
            
            System.out.println("Attempting to create table with SQL: " + createTableSQL);
            stmt.executeUpdate(createTableSQL);
            dataStringBuilder.append("<b>Table ").append(TABLE_NAME).append(" created successfully.</b><br />");
            System.out.println("Table created successfully");
            
        } catch(java.sql.SQLException e) {
            dataStringBuilder.append("<b>Table creation failed: ").append(e.getMessage()).append("</b><br />");
            System.err.println("SQL Exception during table creation: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
        } catch(Exception e) {
            dataStringBuilder.append("<b>Unexpected error during table creation: ").append(e.getMessage()).append("</b><br />");
            System.err.println("Unexpected Exception during table creation: " + e.getMessage());
        }

        return dataStringBuilder.toString();
    }
    
    // Populate Table
    public String populateTable() {
        StringBuilder dataStringBuilder = new StringBuilder();
        
        // Sample book data
        String[][] books = {
            {"The Time Machine", "H.G. Wells", "Science fiction", "1895"},
            {"Pride and Prejudice", "Jane Austen", "Classic Romance", "1813"},
            {"The Color of Magic", "Terry Pratchett", "Fantasy", "1983"},
            {"Kushiels Dart", "Jacqueline Carey", "Fantasy", "2001"},
            {"Guilty Pleasures", "Laurell K. Hamilton", "Horror", "1993"},
            {"Frankenstein", "Mary Shelley", "Gothic", "1818"},
            {"Twenty Thousand Leagues Under the Sea", "Jules Verne", "Adventure", "1870"},
            {"Fool Moon", "Jim Butcher", "Detective", "2001"},
            {"The Girl with the Dragon Tattoo", "Stieg Larsson", "Crime", "2005"},
            {"Wizards First Rule", "Terry Goodkind", "Fantasy", "1994"}
        };

        try (java.sql.Connection conn = getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO " + TABLE_NAME + "(Title, Author, Genre, PublicationYear) VALUES(?, ?, ?, ?)")) {
            
            // Disable auto-commit for batch operations
            conn.setAutoCommit(false);
            
            for (String[] book : books) {
                stmt.setString(1, book[0]); // Title
                stmt.setString(2, book[1]); // Author
                stmt.setString(3, book[2]); // Genre
                stmt.setInt(4, Integer.parseInt(book[3])); // PublicationYear
                stmt.addBatch();
            }
            
            stmt.executeBatch();
            conn.commit();
            
            dataStringBuilder.append("Table populated successfully with ").append(books.length).append(" books.");
            
        } catch(java.sql.SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            dataStringBuilder.append("<b>Error inserting data: ").append(e.getMessage()).append("</b><br />");
        }

        return dataStringBuilder.toString();
    }

    // Read Table
    public String read() {
        StringBuilder dataStringBuilder = new StringBuilder();
        
        String sql = "SELECT * FROM " + TABLE_NAME;
        
        try (java.sql.Connection conn = getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
            java.sql.ResultSet resultSet = stmt.executeQuery()) {
            
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
