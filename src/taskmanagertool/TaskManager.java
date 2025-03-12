package taskmanagertool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private Folder currentFolder;

    public TaskManager() {
        currentFolder = null;
    }

    // Folder-related methods
    public boolean addFolder(String folderName) {
        String sql = "INSERT INTO folders(name) VALUES(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, folderName);
            pstmt.executeUpdate();
            return true; // Folder created successfully
        } catch (SQLException e) {
            System.err.println("Error creating folder: " + e.getMessage());
            return false; // Folder creation failed
        }
    }

    public boolean editFolderName(String currentName, String newName) {
        String sql = "UPDATE folders SET name = ? WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setString(2, currentName);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0; // Return true if the folder was updated
        } catch (SQLException e) {
            System.err.println("Error editing folder name: " + e.getMessage());
            return false; // Folder update failed
        }
    }

    public boolean deleteFolder(String folderName) {
        String sql = "DELETE FROM folders WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, folderName);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0; // Return true if the folder was deleted
        } catch (SQLException e) {
            System.err.println("Error deleting folder: " + e.getMessage());
            return false; // Folder deletion failed
        }
    }

    public boolean searchFolder(String folderName) {
        String sql = "SELECT * FROM folders WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, folderName);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Return true if the folder exists
        } catch (SQLException e) {
            System.err.println("Error searching folder: " + e.getMessage());
            return false; // Folder search failed
        }
    }

    public boolean selectFolder(String folderName) {
        String sql = "SELECT * FROM folders WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, folderName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                currentFolder = new Folder(rs.getString("name"));
                currentFolder.setId(rs.getInt("id"));
                return true; // Folder selected successfully
            } else {
                return false; // Folder not found
            }
        } catch (SQLException e) {
            System.err.println("Error selecting folder: " + e.getMessage());
            return false; // Folder selection failed
        }
    }

    public List<String> getFolders() {
        List<String> folders = new ArrayList<>();
        String sql = "SELECT * FROM folders";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                folders.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving folders: " + e.getMessage());
        }
        return folders; // Return list of folder names
    }

    // Task-related methods
    public boolean addTaskToCurrentFolder(String taskName, String category, String priority) {
        if (currentFolder != null) {
            String sql = "INSERT INTO tasks(name, category, priority, folder_id) VALUES(?, ?, ?, ?)";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, taskName);
                pstmt.setString(2, category);
                pstmt.setString(3, priority);
                pstmt.setInt(4, currentFolder.getId());
                pstmt.executeUpdate();
                return true; // Task added successfully
            } catch (SQLException e) {
                System.err.println("Error adding task: " + e.getMessage());
                return false; // Task addition failed
            }
        } else {
            return false; // No folder selected
        }
    }

    public List<Task> getTasksInCurrentFolder() {
        List<Task> tasks = new ArrayList<>();
        if (currentFolder != null) {
            String sql = "SELECT * FROM tasks WHERE folder_id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, currentFolder.getId());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Task task = new Task(
                            rs.getString("name"),
                            rs.getString("category"),
                            rs.getString("priority")
                    );
                    task.setCompleted(rs.getBoolean("completed"));
                    tasks.add(task);
                }
            } catch (SQLException e) {
                System.err.println("Error retrieving tasks: " + e.getMessage());
            }
        }
        return tasks; // Return list of tasks in the current folder
    }

    public boolean markTaskAsComplete(String taskName) {
        if (currentFolder != null) {
            String sql = "UPDATE tasks SET completed = TRUE WHERE name = ? AND folder_id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, taskName);
                pstmt.setInt(2, currentFolder.getId());
                int rowsUpdated = pstmt.executeUpdate();
                return rowsUpdated > 0; // Return true if the task was marked as complete
            } catch (SQLException e) {
                System.err.println("Error marking task as complete: " + e.getMessage());
                return false; // Task update failed
            }
        } else {
            return false; // No folder selected
        }
    }

    public boolean markTaskAsIncomplete(String taskName) {
        if (currentFolder != null) {
            String sql = "UPDATE tasks SET completed = FALSE WHERE name = ? AND folder_id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, taskName);
                pstmt.setInt(2, currentFolder.getId());
                int rowsUpdated = pstmt.executeUpdate();
                return rowsUpdated > 0; // Return true if the task was marked as incomplete
            } catch (SQLException e) {
                System.err.println("Error marking task as incomplete: " + e.getMessage());
                return false; // Task update failed
            }
        } else {
            return false; // No folder selected
        }
    }

    public boolean editTask(String taskName, int fieldChoice, String newValue) {
        if (currentFolder != null) {
            String sql;
            switch (fieldChoice) {
                case 1:
                    sql = "UPDATE tasks SET name = ? WHERE name = ? AND folder_id = ?";
                    break;
                case 2:
                    sql = "UPDATE tasks SET category = ? WHERE name = ? AND folder_id = ?";
                    break;
                case 3:
                    sql = "UPDATE tasks SET priority = ? WHERE name = ? AND folder_id = ?";
                    break;
                default:
                    return false; // Invalid field choice
            }

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, newValue);
                pstmt.setString(2, taskName);
                pstmt.setInt(3, currentFolder.getId());
                int rowsUpdated = pstmt.executeUpdate();
                return rowsUpdated > 0; // Return true if the task was updated
            } catch (SQLException e) {
                System.err.println("Error editing task: " + e.getMessage());
                return false; // Task update failed
            }
        } else {
            return false; // No folder selected
        }
    }

    public Task searchTask(String taskName) {
        if (currentFolder != null) {
            String sql = "SELECT * FROM tasks WHERE name = ? AND folder_id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, taskName);
                pstmt.setInt(2, currentFolder.getId());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    Task task = new Task(
                            rs.getString("name"),
                            rs.getString("category"),
                            rs.getString("priority")
                    );
                    task.setCompleted(rs.getBoolean("completed"));
                    return task; // Return the found task
                }
            } catch (SQLException e) {
                System.err.println("Error searching task: " + e.getMessage());
            }
        }
        return null; // Task not found or no folder selected
    }

    public List<Task> filterTasksByCompletion(boolean isComplete) {
        List<Task> tasks = new ArrayList<>();
        if (currentFolder != null) {
            String sql = "SELECT * FROM tasks WHERE folder_id = ? AND completed = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, currentFolder.getId());
                pstmt.setBoolean(2, isComplete);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Task task = new Task(
                            rs.getString("name"),
                            rs.getString("category"),
                            rs.getString("priority")
                    );
                    task.setCompleted(rs.getBoolean("completed"));
                    tasks.add(task);
                }
            } catch (SQLException e) {
                System.err.println("Error filtering tasks: " + e.getMessage());
            }
        }
        return tasks; // Return list of filtered tasks
    }

    public boolean deleteTask(String taskName) {
        if (currentFolder != null) {
            String sql = "DELETE FROM tasks WHERE name = ? AND folder_id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, taskName);
                pstmt.setInt(2, currentFolder.getId());
                int rowsDeleted = pstmt.executeUpdate();
                return rowsDeleted > 0; // Return true if the task was deleted
            } catch (SQLException e) {
                System.err.println("Error deleting task: " + e.getMessage());
                return false; // Task deletion failed
            }
        } else {
            return false; // No folder selected
        }
    }
}