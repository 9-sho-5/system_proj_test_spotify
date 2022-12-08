package api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

public class CreateTable extends HttpServlet {

    // private static CreateTable db = new CreateTable();
    private final static String DB_URL = "jdbc:sqlite:sys_proj_B01.sqlite3";

    // private CreateTable(){
    //     DB_URL = null;
    // }

    // public static CreateTable getInstance() {
    //     return db;
    // }

    // public void setDB_URL(String url) {
    //     DB_URL = url;
    // }

    public static void createTable() {

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            e.getMessage();
        }
        
        // Open a connection
        try (
                Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();) {

            System.out.println("connected Database successfully...");

            String sql = "sqlite3 sys_proj_B01.sqlite3;";
            try {
                stmt.executeUpdate(sql);
                System.out.println("created Database successfully...");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("database exists");
            }

            // sql = "USE sys_proj_B01; ";
            // try {
            //     stmt.executeUpdate(sql);
            //     System.out.println("use Database successfully...");
            // } catch (SQLException e) {
            //     System.out.println(e.getMessage());
            //     System.out.println("the Database is already used");
            // }

            // sql = "CREATE TABLE Ranking (\n"
            //         + "id INT(11) AUTO_INCREMENT NOT NULL, "
            //         + "track_name VARCHAR(255) NOT NULL ,"
            //         + "artist_name VARCHAR(255) NOT NULL ,"
            //         + "album_name VARCHAR(255) NOT NULL ,"
            //         + "album_image_url VARCHAR(255) NOT NULL ,"
            //         + "PRIMARY KEY (id))";
            sql = "CREATE TABLE IF NOT EXISTS Ranking (\n"
                    + "id integer primary key autoincrement, "
                    + "track_name text ,"
                    + "artist_name text ,"
                    + "album_name text ,"
                    + "album_image_url text);";
            try {
                stmt.executeUpdate(sql);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            // try {
            // System.out.println("Table created successfully...");
            // } catch (SQLException e) {
            // System.out.println("the table is exists");
            // }
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void dropDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
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

    public boolean checkDatabase() {
        boolean flg = false;
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();) {

            String sql = "CREATE DATABASE sys_proj_B01;";
            try {
                stmt.executeUpdate(sql);
                System.out.println("created Database successfully...");
            } catch (SQLException e) {
                System.out.println("database exists");
            }

            // sql = "USE sys_proj_B01; ";
            // try {
            //     stmt.executeUpdate(sql);
            //     System.out.println("use Database successfully...");
            // } catch (SQLException e) {
            //     System.out.println("the Database is already used");
            // }

            sql = "select database()";
            try {
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
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

        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();) {

            String sql = "USE sys_proj_B01; ";
            try {
                stmt.executeUpdate(sql);
                System.out.println("use Database successfully...");
            } catch (SQLException e) {
                System.out.println("the Database is already used");
            }

            sql = String.format("insert into Ranking(track_name, artist_name, album_name, album_image_url) values ('%s', '%s', '%s', '%s');", track_name, artist_name,
                    album_name, album_image_url);
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
