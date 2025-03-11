package taskmanagertool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:taskmanager.db"; // Database file in the current directory

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        conn.createStatement().execute("PRAGMA foreign_keys = ON;"); // Enable foreign key support
        return conn;
    }

    public static void initializeDatabase() {
        String createFoldersTable = "CREATE TABLE IF NOT EXISTS folders (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL UNIQUE);";

        String createTasksTable = "CREATE TABLE IF NOT EXISTS tasks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "category TEXT, " +
                "priority TEXT, " +
                "completed BOOLEAN DEFAULT FALSE, " +
                "folder_id INTEGER, " +
                "FOREIGN KEY (folder_id) REFERENCES folders(id));";

        try (Connection conn = getConnection();
             var stmt = conn.createStatement()) {
            // Create tables
            stmt.execute(createFoldersTable);
            stmt.execute(createTasksTable);
            System.out.println("Database initialized successfully.");
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }
}