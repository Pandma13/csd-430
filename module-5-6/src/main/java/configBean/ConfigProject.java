package configBean;

/*
* Megan Wheeler
* CSD 430 - Module 5 & 6
* 18 June 2025
*/
public class ConfigProject implements java.io.Serializable {

    java.sql.Connection connection;
    java.sql.Statement statement;
    
    private static final long serialVersionUID = 1111222233334444L;
    
    // Constructor
    
    public ConfigProject() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:8080/csd430";
            connection = java.sql.DriverManager.getConnection(url + "user=student1&password=pass");
            statement = connection.createStatement();
        }
        catch(ClassNotFoundException | java.sql.SQLException e) {
            System.out.print("Exception: " + e);
        }
    }
    
    // Create Table

    public String createTable() {

        StringBuilder dataStringBuilder = new StringBuilder();

        try{
            statement.executeUpdate("DROP TABLE wheeler_library_data");
            dataStringBuilder.append("<b>Table wheeler_library_data dropped.</b><br />");
        }
        catch(java.sql.SQLException e){
            dataStringBuilder.append("<b>Table wheeler_library_data does not exist.</b><br />");
        }

        try{
            statement.executeUpdate("CREATE TABLE wheeler_library_data(BookID int NOT NULL AUTO_INCREMENT PRIMARY KEY, Title varchar(255), Author varchar(255), Genre varchar(255), PublicationYear int NOT NULL)");
            dataStringBuilder.append("<b>Table wheeler_library_data created.</b><br />");
        }
        catch(java.sql.SQLException e){
            dataStringBuilder.append("<b>Table wheeler_library_data creation failed.</b><br />");
        }

        return dataStringBuilder.toString();
    }
    
    // Populate Table

    public String populateTable() {

        StringBuilder dataStringBuilder = new StringBuilder();

        try{
            statement.executeUpdate("INSERT INTO wheeler_library_data(Title, Author, Genre, PublicationYear) VALUES('The Time Machine', 'H.G. Wells', 'Science fiction', 1895)");
            statement.executeUpdate("INSERT INTO wheeler_library_data(Title, Author, Genre, PublicationYear) VALUES('Pride and Prejudice', 'Jane Austen', 'Classic Romance', 1813)");
            statement.executeUpdate("INSERT INTO wheeler_library_data(Title, Author, Genre, PublicationYear) VALUES('The Color of Magic', 'Terry Pratchett', 'Fantasy', 1983)");
            statement.executeUpdate("INSERT INTO wheeler_library_data(Title, Author, Genre, PublicationYear) VALUES('Kushiels Dart', 'Jacqueline Carey', 'Fantasy', 2001)");
            statement.executeUpdate("INSERT INTO wheeler_library_data(Title, Author, Genre, PublicationYear) VALUES('Guilty Pleasures', 'Laurell K. Hamilton', 'Horror', 1993)");
            statement.executeUpdate("INSERT INTO wheeler_library_data(Title, Author, Genre, PublicationYear) VALUES('Frankenstein', 'Mary Shelley', 'Gothic', 1818)");
            statement.executeUpdate("INSERT INTO wheeler_library_data(Title, Author, Genre, PublicationYear) VALUES('Twenty Thousand Leagues Under the Sea', 'Jules Verne', 'Adventure', 1870)");
            statement.executeUpdate("INSERT INTO wheeler_library_data(Title, Author, Genre, PublicationYear) VALUES('Fool Moon', 'Jim Butcher', 'Detective', 2001)");
            statement.executeUpdate("INSERT INTO wheeler_library_data(Title, Author, Genre, PublicationYear) VALUES('The Girl with the Dragon Tattoo', 'Stieg Larsson', 'Crime', 2005)");
            statement.executeUpdate("INSERT INTO wheeler_library_data(Title, Author, Genre, PublicationYear) VALUES('Wizards First Rule', 'Terry Goodkind', 'Fantasy', 1994)");

            statement.executeUpdate("COMMIT");
            
            dataStringBuilder.append("Table fill completed.");
            
        }
        catch(java.sql.SQLException e){
            dataStringBuilder.append("<b>Error inserting data</b><br />");
        }

        return dataStringBuilder.toString();
    }

    // Read Table
    
    public String read() {

        StringBuilder dataStringBuilder = new StringBuilder();
        java.sql.ResultSet resultSet = null;


        try{
        	resultSet = statement.executeQuery("SELECT * FROM wheeler_library_data");
        }
        catch(java.sql.SQLException e){
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
        catch(java.sql.SQLException e){
            System.out.print("SQL Exception" + e);
        }
        
    return dataStringBuilder.toString();
    }

    // Close Connection

    public void closeConnection(){

        try {
            statement.close();
            connection.close();
        } catch (java.sql.SQLException sqle) {
            System.out.print("SQL Exception" + sqle);
        }
    }
}
