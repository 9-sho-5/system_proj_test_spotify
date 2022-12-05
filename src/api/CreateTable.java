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

            sql = "CREATE TABLE Ranking (\n"
                    + "id INT(11) AUTO_INCREMENT NOT NULL, "
                    + "track_name VARCHAR(255) NOT NULL ,"
                    + "artist_name VARCHAR(255) NOT NULL ,"
                    + "album_name VARCHAR(255) NOT NULL ,"
                    + "album_image_url VARCHAR(255) NOT NULL ,"
                    + "PRIMARY KEY (id))";
                    stmt.executeUpdate(sql);
            // try {
            //     System.out.println("Table created successfully...");
            // } catch (SQLException e) {
            //     System.out.println("the table is exists");
            // }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dropDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {

            System.out.println("connected Database successfully...");

            String sql = "drop DATABASE sys_proj_B01;";
            try {
                stmt.executeUpdate(sql);
                System.out.println("created Database successfully...");
            } catch (SQLException e) {
                System.out.println("database exists");
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkDatabase() {
        boolean flg = false;
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
                flg = true;
            } catch (SQLException e) {
                System.out.println("エラー");
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flg;
    }

    public static void insertData(String track_name, String artist_name, String album_name, String album_image_url) {

        System.out.println("=== insert Data ===");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {

            String sql = "USE sys_proj_B01; ";
            try {
                stmt.executeUpdate(sql);
                System.out.println("use Database successfully...");
            } catch (SQLException e) {
                System.out.println("the Database is already used");
            }

            sql = String.format("insert into Ranking values (0, '%s', '%s', '%s', '%s');", track_name, artist_name, album_name, album_image_url);
            try {
                stmt.executeUpdate(sql);
                System.out.println("inserted data completely!!");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
