package org.project;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBTest {
    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/secure_chat";
        String username = "root";
        String password = "admin";

        try (Connection conn = DriverManager.getConnection(jdbcURL, username, password)) {
            System.out.println(" Connected to: " + conn.getCatalog());
        } catch (Exception e) {
            System.err.println(" Connection failed: " + e.getMessage());
        }
    }
}