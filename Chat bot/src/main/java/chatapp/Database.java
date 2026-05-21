package chatapp;

import java.sql.*;

public class Database {
    private static final String URL = "jdbc:sqlite:chat.db";

    public static void init() {
        try (Connection conn = DriverManager.getConnection(URL)) {
            Statement stmt = conn.createStatement();

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT UNIQUE,
                    password TEXT
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS messages (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    sender TEXT,
                    message TEXT,
                    time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);

            System.out.println("Database ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean registerUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(URL)) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO users(username, password) VALUES(?, ?)"
            );
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean loginUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(URL)) {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=?"
            );
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }

    public static void saveMessage(String sender, String message) {
        try (Connection conn = DriverManager.getConnection(URL)) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO messages(sender, message) VALUES(?, ?)"
            );
            ps.setString(1, sender);
            ps.setString(2, message);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}