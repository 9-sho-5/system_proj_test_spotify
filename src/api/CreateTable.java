package api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
                System.out.println("created Database successfully...");
            } catch (SQLException e) {
                System.out.println("database exists");
            }

            sql = "USE sys_proj_B01; ";
            try {
                stmt.executeUpdate(sql);
                System.out.println("use Database successfully...");
            } catch (SQLException e) {
                System.out.println("the Database is already used");
            }

            sql = "CREATE TABLE Singers (\n"
                    + "id INT(11) AUTO_INCREMENT NOT NULL, "
                    + "name VARCHAR(30) NOT NULL ,"
                    + "age INT(3) NOT NULL,"
                    + "PRIMARY KEY (id))";
            try {
                stmt.executeUpdate(sql);
                System.out.println("Table created successfully...");
            } catch (SQLException e) {
                System.out.println("the table is exists");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {

            String sql = "CREATE DATABASE sys_proj_B01;";
            try {
                stmt.executeUpdate(sql);
                System.out.println("created Database successfully...");
            } catch (SQLException e) {
                System.out.println("database exists");
            }

            sql = "USE sys_proj_B01; ";
            try {
                stmt.executeUpdate(sql);
                System.out.println("use Database successfully...");
            } catch (SQLException e) {
                System.out.println("the Database is already used");
            }

            sql = "select database()";
            try {
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()) {
                    String name = rs.getString("database()");
                    System.out.println(name + " is selected");
                }
            } catch (SQLException e) {
                System.out.println("エラー");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
