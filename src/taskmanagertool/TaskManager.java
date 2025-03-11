package taskmanagertool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskManager {
    private Folder currentFolder;

    public TaskManager() {
        currentFolder = null;
    }

    // Folder-related methods
    public void addFolder(String folderName) {
        String sql = "INSERT INTO folders(name) VALUES(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, folderName);
            pstmt.executeUpdate();
            System.out.println("Folder '" + folderName + "' created.");
        } catch (SQLException e) {
            System.out.println("Error creating folder: " + e.getMessage());
        }
    }

    public void editFolderName(String currentName, String newName) {
        String sql = "UPDATE folders SET name = ? WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setString(2, currentName);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Folder name changed from '" + currentName + "' to '" + newName + "'.");
            } else {
                System.out.println("Folder '" + currentName + "' not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error editing folder name: " + e.getMessage());
        }
    }

    public void deleteFolder(String folderName) {
        String sql = "DELETE FROM folders WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, folderName);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Folder '" + folderName + "' deleted.");
            } else {
                System.out.println("Folder '" + folderName + "' not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting folder: " + e.getMessage());
        }
    }

    public void searchFolder(String folderName) {
        String sql = "SELECT * FROM folders WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, folderName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Folder '" + folderName + "' found.");
            } else {
                System.out.println("Folder not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error searching folder: " + e.getMessage());
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
                System.out.println("Folder '" + folderName + "' selected.");
                return true;
            } else {
                System.out.println("Folder '" + folderName + "' not found.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error selecting folder: " + e.getMessage());
            return false;
        }
    }

    public void viewFolders() {
        String sql = "SELECT * FROM folders";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("Available folders:");
            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Error viewing folders: " + e.getMessage());
        }
    }

    // Task-related methods
    public void addTaskToCurrentFolder(String taskName, String category, String priority) {
        if (currentFolder != null) {
            String sql = "INSERT INTO tasks(name, category, priority, folder_id) VALUES(?, ?, ?, ?)";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, taskName);
                pstmt.setString(2, category);
                pstmt.setString(3, priority);
                pstmt.setInt(4, currentFolder.getId());
                pstmt.executeUpdate();
                System.out.println("Task '" + taskName + "' added to folder '" + currentFolder.getName() + "'.");
            } catch (SQLException e) {
                System.out.println("Error adding task: " + e.getMessage());
            }
        } else {
            System.out.println("No folder selected.");
        }
    }

    public void viewCurrentFolderTasks() {
        if (currentFolder == null) {
            System.out.println("No folder selected.");
            return;
        }

        String sql = "SELECT * FROM tasks WHERE folder_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, currentFolder.getId());
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Tasks in folder '" + currentFolder.getName() + "':");
            System.out.println("-----------------------------");
            while (rs.next()) {
                System.out.println("Task Name: " + rs.getString("name"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("Priority: " + rs.getString("priority"));
                System.out.println("Completed: " + rs.getBoolean("completed"));
                System.out.println("-----------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing tasks: " + e.getMessage());
        }
    }

    public void markTaskAsComplete(String taskName) {
        if (currentFolder != null) {
            String sql = "UPDATE tasks SET completed = TRUE WHERE name = ? AND folder_id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, taskName);
                pstmt.setInt(2, currentFolder.getId());
                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Task '" + taskName + "' marked as complete.");
                } else {
                    System.out.println("Task '" + taskName + "' not found.");
                }
            } catch (SQLException e) {
                System.out.println("Error marking task as complete: " + e.getMessage());
            }
        } else {
            System.out.println("No folder selected.");
        }
    }

    public void markTaskAsIncomplete(String taskName) {
        if (currentFolder != null) {
            String sql = "UPDATE tasks SET completed = FALSE WHERE name = ? AND folder_id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, taskName);
                pstmt.setInt(2, currentFolder.getId());
                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Task '" + taskName + "' marked as incomplete.");
                } else {
                    System.out.println("Task '" + taskName + "' not found.");
                }
            } catch (SQLException e) {
                System.out.println("Error marking task as incomplete: " + e.getMessage());
            }
        } else {
            System.out.println("No folder selected.");
        }
    }

    public void editTask(String taskName, int fieldChoice, String newValue) {
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
                    System.out.println("Invalid field choice. No changes made.");
                    return;
            }

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, newValue);
                pstmt.setString(2, taskName);
                pstmt.setInt(3, currentFolder.getId());
                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Task '" + taskName + "' updated.");
                } else {
                    System.out.println("Task '" + taskName + "' not found.");
                }
            } catch (SQLException e) {
                System.out.println("Error editing task: " + e.getMessage());
            }
        } else {
            System.out.println("No folder selected.");
        }
    }

    public void searchTask(String taskName) {
        if (currentFolder != null) {
            String sql = "SELECT * FROM tasks WHERE name = ? AND folder_id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, taskName);
                pstmt.setInt(2, currentFolder.getId());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Task found:");
                    System.out.println("Task Name: " + rs.getString("name"));
                    System.out.println("Category: " + rs.getString("category"));
                    System.out.println("Priority: " + rs.getString("priority"));
                    System.out.println("Completed: " + rs.getBoolean("completed"));
                } else {
                    System.out.println("Task '" + taskName + "' not found.");
                }
            } catch (SQLException e) {
                System.out.println("Error searching task: " + e.getMessage());
            }
        } else {
            System.out.println("No folder selected.");
        }
    }

    public void filterTasksByCompletion(boolean isComplete) {
        if (currentFolder == null) {
            System.out.println("No folder selected.");
            return;
        }

        String sql = "SELECT * FROM tasks WHERE folder_id = ? AND completed = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, currentFolder.getId());
            pstmt.setBoolean(2, isComplete);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Tasks that are " + (isComplete ? "complete:" : "incomplete:"));
            System.out.println("-----------------------------");
            while (rs.next()) {
                System.out.println("Task Name: " + rs.getString("name"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("Priority: " + rs.getString("priority"));
                System.out.println("Completed: " + rs.getBoolean("completed"));
                System.out.println("-----------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error filtering tasks: " + e.getMessage());
        }
    }

    public void deleteTask(String taskName) {
        if (currentFolder != null) {
            String sql = "DELETE FROM tasks WHERE name = ? AND folder_id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, taskName);
                pstmt.setInt(2, currentFolder.getId());
                int rowsDeleted = pstmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Task '" + taskName + "' deleted.");
                } else {
                    System.out.println("Task '" + taskName + "' not found.");
                }
            } catch (SQLException e) {
                System.out.println("Error deleting task: " + e.getMessage());
            }
        } else {
            System.out.println("No folder selected.");
        }
    }
}