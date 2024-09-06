package taskmanagertool;

public class TaskManager {
    private FolderLinkedList folderList;
    private Folder currentFolder;

    public TaskManager() {
        folderList = new FolderLinkedList();
        currentFolder = null;
    }

    // Folder-related methods
    public void addFolder(String folderName) {
        Folder folder = new Folder(folderName);
        folderList.addFolder(folder);
        System.out.println("Folder '" + folderName + "' created.");
    }

    public void editFolderName(String currentName, String newName) {
        Folder folder = folderList.searchFolder(currentName);
        if (folder != null) {
            folder.setName(newName);
            System.out.println("Folder name changed from '" + currentName + "' to '" + newName + "'.");
        } else {
            System.out.println("Folder '" + currentName + "' not found.");
        }
    }
    
    public void deleteFolder(String folderName) {
        folderList.deleteFolder(folderName);
        System.out.println("Folder '" + folderName + "' deleted.");
    }

    public void searchFolder(String folderName) {
        Folder folder = folderList.searchFolder(folderName);
        if (folder != null) {
            System.out.println("Folder '" + folderName + "' found.");
        } else {
            System.out.println("Folder not found.");
        }
    }

    public boolean selectFolder(String folderName) {
        Folder folder = folderList.searchFolder(folderName);
        if (folder != null) {
            currentFolder = folder;
            System.out.println("Folder '" + folderName + "' selected.");
            return true;
        } else {
            return false;
        }
    }

    public void viewFolders() {
        System.out.println("Available folders:");
        folderList.viewFolders();
    }

    // Task-related methods
    public void addTaskToCurrentFolder(String taskName, String category, String priority) {
        if (currentFolder != null) {
            Task task = new Task(taskName, category, priority);
            currentFolder.getTaskList().add(task);
            System.out.println("Task '" + taskName + "' added to folder '" + currentFolder.getName() + "'.");
        } else {
            System.out.println("No folder selected.");
        }
    }

    public void viewCurrentFolderTasks() {
        if (currentFolder == null) {
            System.out.println("No folder selected.");
            return;
        }

        System.out.println("Tasks in folder '" + currentFolder.getName() + "':");
        System.out.println("-----------------------------");
        TaskNode current = currentFolder.getTaskList().getHead();

        if (current == null) {
            System.out.println("No tasks found.");
            return;
        }

        while (current != null) {
            Task task = current.getTask();
            System.out.println("Task Name: " + task.getName());
            System.out.println("Category: " + task.getCategory());
            System.out.println("Priority: " + task.getPriority());
            System.out.println("-----------------------------"); // Separator between tasks
            current = current.getNext();
        }
    }

    public void markTaskAsComplete(String taskName) {
        if (currentFolder != null) {
            Task task = currentFolder.getTaskList().search(taskName);
            if (task != null) {
                task.setCompleted(true);
                System.out.println("Task '" + taskName + "' marked as complete.");
            } else {
                System.out.println("Task not found.");
            }
        } else {
            System.out.println("No folder selected.");
        }
    }

    public void deleteTask(String taskName) {
        if (currentFolder != null) {
            currentFolder.getTaskList().delete(taskName);
            System.out.println("Task '" + taskName + "' deleted.");
        } else {
            System.out.println("No folder selected.");
        }
    }
}


