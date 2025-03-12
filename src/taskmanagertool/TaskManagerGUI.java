package taskmanagertool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TaskManagerGUI extends JFrame {
    private TaskManager taskManager;

    public TaskManagerGUI() {
        taskManager = new TaskManager();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Task Manager Tool");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Create the main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Add components to the main panel
        mainPanel.add(createFolderPanel(), BorderLayout.NORTH);
        mainPanel.add(createTaskPanel(), BorderLayout.CENTER);

        // Add the main panel to the frame
        add(mainPanel);

        setVisible(true);
    }

    private JPanel createFolderPanel() {
        JPanel folderPanel = new JPanel(new GridLayout(0, 2));

        // Folder Name Input
        JLabel folderNameLabel = new JLabel("Folder Name:");
        JTextField folderNameField = new JTextField(20);

        // Buttons
        JButton createFolderButton = new JButton("Create Folder");
        JButton viewFoldersButton = new JButton("View Folders");

        // Add components to the folder panel
        folderPanel.add(folderNameLabel);
        folderPanel.add(folderNameField);
        folderPanel.add(createFolderButton);
        folderPanel.add(viewFoldersButton);

        // Button Actions
        createFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String folderName = folderNameField.getText();
                if (!folderName.isEmpty()) {
                    boolean success = taskManager.addFolder(folderName);
                    if (success) {
                        JOptionPane.showMessageDialog(TaskManagerGUI.this, "Folder created: " + folderName);
                    } else {
                        JOptionPane.showMessageDialog(TaskManagerGUI.this, "Failed to create folder.");
                    }
                } else {
                    JOptionPane.showMessageDialog(TaskManagerGUI.this, "Folder name cannot be empty!");
                }
            }
        });

        viewFoldersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> folders = taskManager.getFolders();
                if (folders.isEmpty()) {
                    JOptionPane.showMessageDialog(TaskManagerGUI.this, "No folders found.");
                } else {
                    StringBuilder folderList = new StringBuilder("Available folders:\n");
                    for (String folder : folders) {
                        folderList.append(folder).append("\n");
                    }
                    JOptionPane.showMessageDialog(TaskManagerGUI.this, folderList.toString());
                }
            }
        });

        return folderPanel;
    }

    private JPanel createTaskPanel() {
        JPanel taskPanel = new JPanel(new GridLayout(0, 2));

        // Task Name Input
        JLabel taskNameLabel = new JLabel("Task Name:");
        JTextField taskNameField = new JTextField(20);

        // Task Category Input
        JLabel taskCategoryLabel = new JLabel("Task Category:");
        JTextField taskCategoryField = new JTextField(20);

        // Task Priority Input
        JLabel taskPriorityLabel = new JLabel("Task Priority:");
        JTextField taskPriorityField = new JTextField(20);

        // Buttons
        JButton addTaskButton = new JButton("Add Task");
        JButton viewTasksButton = new JButton("View Tasks");

        // Add components to the task panel
        taskPanel.add(taskNameLabel);
        taskPanel.add(taskNameField);
        taskPanel.add(taskCategoryLabel);
        taskPanel.add(taskCategoryField);
        taskPanel.add(taskPriorityLabel);
        taskPanel.add(taskPriorityField);
        taskPanel.add(addTaskButton);
        taskPanel.add(viewTasksButton);

        // Button Actions
        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskName = taskNameField.getText();
                String taskCategory = taskCategoryField.getText();
                String taskPriority = taskPriorityField.getText();

                if (!taskName.isEmpty() && !taskCategory.isEmpty() && !taskPriority.isEmpty()) {
                    boolean success = taskManager.addTaskToCurrentFolder(taskName, taskCategory, taskPriority);
                    if (success) {
                        JOptionPane.showMessageDialog(TaskManagerGUI.this, "Task added: " + taskName);
                    } else {
                        JOptionPane.showMessageDialog(TaskManagerGUI.this, "Failed to add task.");
                    }
                } else {
                    JOptionPane.showMessageDialog(TaskManagerGUI.this, "All fields are required!");
                }
            }
        });

        viewTasksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Task> tasks = taskManager.getTasksInCurrentFolder();
                if (tasks.isEmpty()) {
                    JOptionPane.showMessageDialog(TaskManagerGUI.this, "No tasks found.");
                } else {
                    StringBuilder taskList = new StringBuilder("Tasks in current folder:\n");
                    for (Task task : tasks) {
                        taskList.append("Name: ").append(task.getName())
                                .append(", Category: ").append(task.getCategory())
                                .append(", Priority: ").append(task.getPriority())
                                .append(", Completed: ").append(task.isCompleted())
                                .append("\n");
                    }
                    JOptionPane.showMessageDialog(TaskManagerGUI.this, taskList.toString());
                }
            }
        });

        return taskPanel;
    }

    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TaskManagerGUI();
            }
        });
    }
}