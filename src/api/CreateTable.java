package api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {

    static final String DB_URL = "jdbc:mysql://localhost/";
    static final String USER = "root";
    static final String PASS = "";

    public static void createTable() {
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {

            System.out.println("connected Database successfully...");

            String sql = "CREATE DATABASE sys_proj_B01;";
            try {
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                System.out.println("database exists");
            }
            System.out.println("created Database successfully...");

            sql = "USE sys_proj_B01; ";
            try {
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                System.out.println("the Database is already used");
            }
            System.out.println("use Database successfully...");

            sql = "CREATE TABLE Singers (\n"
                    + "id INT(11) AUTO_INCREMENT NOT NULL, "
                    + "name VARCHAR(30) NOT NULL ,"
                    + "age INT(3) NOT NULL,"
                    + "PRIMARY KEY (id))";
            try {
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                System.out.println("could not creat table");
            }
            System.out.println("Table created successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
