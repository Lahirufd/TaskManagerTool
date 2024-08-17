package taskmanagertool;

import java.util.Scanner;

public class TaskManagerTool {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();

        while (true) {
            System.out.println("Folder Management:");
            System.out.println("1. Create Folder");
            System.out.println("2. View All Folders");
            System.out.println("3. Edit Folder Name");
            System.out.println("4. Delete Folder");
            System.out.println("5. Search Folders");
            System.out.println("6. Select Folder");
            System.out.println("7. Exit");
            System.out.print("Please choose an option (1-7): ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter folder name: ");
                    String folderName = scanner.nextLine();
                    taskManager.addFolder(folderName);
                    break;
                case 2:
                    taskManager.viewFolders();
                    break;
                case 3:
                    System.out.print("Enter current folder name: ");
                    String currentName = scanner.nextLine();
                    System.out.print("Enter new folder name: ");
                    String newName = scanner.nextLine();
                    taskManager.editFolderName(currentName, newName);
                    break;
                case 4:
                    System.out.print("Enter folder name to delete: ");
                    String deleteFolderName = scanner.nextLine();
                    taskManager.deleteFolder(deleteFolderName);
                    break;
                case 5:
                    System.out.print("Enter folder name to search: ");
                    String searchFolderName = scanner.nextLine();
                    taskManager.searchFolder(searchFolderName);
                    break;
                case 6:
                    System.out.print("Enter folder name to select: ");
                    String selectFolderName = scanner.nextLine();
                    taskManager.selectFolder(selectFolderName);
                    manageTasksInFolder(scanner, taskManager);
                    break;
                case 7:
                    System.out.println("Exiting the tool. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println(); //For blank line between operations
        }
    }

    private static void manageTasksInFolder(Scanner scanner, TaskManager taskManager) {
        while (true) {
            System.out.println("\nTask Management in Folder:");
            System.out.println("1. Add Task to Selected Folder");
            System.out.println("2. View Tasks in Selected Folder");
            System.out.println("3. Mark Task as Complete");
            System.out.println("4. Delete Task");
            System.out.println("5. Back to Folder Management");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter task name: ");
                    String taskName = scanner.nextLine();
                    System.out.print("Enter task category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter task priority: ");
                    String priority = scanner.nextLine();
                    taskManager.addTaskToCurrentFolder(taskName, category, priority);
                    break;
                case 2:
                    taskManager.viewCurrentFolderTasks();
                    break;
                case 3:
                    System.out.print("Enter task name to mark as complete: ");
                    String completeTaskName = scanner.nextLine();
                    taskManager.markTaskAsComplete(completeTaskName);
                    break;
                case 4:
                    System.out.print("Enter task name to delete: ");
                    String deleteTaskName = scanner.nextLine();
                    taskManager.deleteTask(deleteTaskName);
                    break;
                case 5:
                    return;  // Exit task management and go back to folder management
            }
        }
    }
}


