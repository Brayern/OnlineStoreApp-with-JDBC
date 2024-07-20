import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {

    // Database connection parameters
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/LiveDb";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Abcde123#";
    public static void main(String[] args) {
        // Perform all CRUD operations
        createTable();
        insertData();
        readData();
        updateData();
        deleteData();
    }

    // Create a table for demonstration purposes
    private static void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Customers ("
                + "customer_id SERIAL PRIMARY KEY ,"
                + "first_name VARCHAR(50) NOT NULL,"
                + "last_name VARCHAR(50) NOT NULL,"
                + "email VARCHAR(100) UNIQUE NOT NULL,"
                + "phone VARCHAR(15),"
                + "address VARCHAR(255),"
                + "city VARCHAR(50)"
                + ")";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Table 'users' created successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Insert data into the table
    private static void insertData() {
        String insertSQL = "INSERT INTO Customers (first_name, last_name, email, phone, address, city) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, "Bray");
            preparedStatement.setString(2, "Marubu");
            preparedStatement.setString(3, "marubub1@gmail.com");
            preparedStatement.setString(4, "2547424204");
            preparedStatement.setString(5, "145 Uhuru Street");
            preparedStatement.setString(6, "Jamaica");
            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println(rowsInserted + " row(s) inserted.");

            preparedStatement.setString(1, "John");
            preparedStatement.setString(2, "Wambua");
            preparedStatement.setString(3, "wambua@gmail.com");
            preparedStatement.setString(4, "2547412204");
            preparedStatement.setString(5, "200 Kimathi Street");
            preparedStatement.setString(6, "Florida");
            rowsInserted = preparedStatement.executeUpdate();
            System.out.println(rowsInserted + " row(s) inserted.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Read data from the table
    private static void readData() {
        String selectSQL = "SELECT * FROM Customers";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("customer_id");
                String name = resultSet.getString("first_name");
                String email = resultSet.getString("email");
                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Update data in the table
    private static void updateData() {
        String updateSQL = "UPDATE Customers SET email = ? WHERE first_name = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, "johnwambua@gmail.com");
            preparedStatement.setString(2, "John");
            int rowsUpdated = preparedStatement.executeUpdate();
            System.out.println(rowsUpdated + " row(s) updated.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Delete data from the table
    private static void deleteData() {
        String deleteSQL = "DELETE FROM Customers WHERE first_name = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

            preparedStatement.setString(1, "John");
            int rowsDeleted = preparedStatement.executeUpdate();
            System.out.println(rowsDeleted + " row(s) deleted.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
