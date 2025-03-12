package taskmanagertool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TaskManagerTool extends JFrame {
    private TaskManager taskManager;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JLabel selectedFolderLabel;

    public TaskManagerTool() {
        taskManager = new TaskManager();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Task Manager Tool");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Create the main panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add the folder panel and task panel to the main panel
        mainPanel.add(createFolderPanel(), "FolderPanel");
        mainPanel.add(createTaskPanel(), "TaskPanel");

        // Add the main panel to the frame
        add(mainPanel);

        setVisible(true);
    }

    private JPanel createFolderPanel() {
        JPanel folderPanel = new JPanel(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("Folder Operations", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        folderPanel.add(titleLabel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 10, 10)); // 6 rows, 1 column, with spacing
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 150, 20, 150)); // Add padding

        // Buttons
        JButton createFolderButton = createStyledButton("Create Folder");
        JButton viewFoldersButton = createStyledButton("View Folders");
        JButton editFolderButton = createStyledButton("Edit Folder");
        JButton deleteFolderButton = createStyledButton("Delete Folder");
        JButton searchFolderButton = createStyledButton("Search Folder");
        JButton selectFolderButton = createStyledButton("Select Folder");

        buttonPanel.add(createFolderButton);
        buttonPanel.add(viewFoldersButton);
        buttonPanel.add(editFolderButton);
        buttonPanel.add(deleteFolderButton);
        buttonPanel.add(searchFolderButton);
        buttonPanel.add(selectFolderButton);

        // Add components to the folder panel
        folderPanel.add(buttonPanel, BorderLayout.CENTER);

        // Button Actions
        createFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String folderName = JOptionPane.showInputDialog(TaskManagerTool.this, "Enter folder name:");
                if (folderName != null && !folderName.isEmpty()) {
                    boolean success = taskManager.addFolder(folderName);
                    if (success) {
                        JOptionPane.showMessageDialog(TaskManagerTool.this, "Folder created: " + folderName);
                    } else {
                        JOptionPane.showMessageDialog(TaskManagerTool.this, "Failed to create folder.");
                    }
                } else {
                    JOptionPane.showMessageDialog(TaskManagerTool.this, "Folder name cannot be empty!");
                }
            }
        });

        viewFoldersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> folders = taskManager.getFolders();
                if (folders.isEmpty()) {
                    JOptionPane.showMessageDialog(TaskManagerTool.this, "No folders found.");
                } else {
                    StringBuilder folderList = new StringBuilder("Available folders:\n");
                    for (String folder : folders) {
                        folderList.append(folder).append("\n");
                    }
                    JOptionPane.showMessageDialog(TaskManagerTool.this, folderList.toString());
                }
            }
        });

        editFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentName = JOptionPane.showInputDialog(TaskManagerTool.this, "Enter current folder name:");
                String newName = JOptionPane.showInputDialog(TaskManagerTool.this, "Enter new folder name:");
                if (currentName != null && newName != null) {
                    boolean success = taskManager.editFolderName(currentName, newName);
                    if (success) {
                        JOptionPane.showMessageDialog(TaskManagerTool.this, "Folder name changed from '" + currentName + "' to '" + newName + "'.");
                    } else {
                        JOptionPane.showMessageDialog(TaskManagerTool.this, "Folder '" + currentName + "' not found.");
                    }
                }
            }
        });

        deleteFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String folderName = JOptionPane.showInputDialog(TaskManagerTool.this, "Enter folder name to delete:");
                if (folderName != null) {
                    boolean success = taskManager.deleteFolder(folderName);
                    if (success) {
                        JOptionPane.showMessageDialog(TaskManagerTool.this, "Folder '" + folderName + "' deleted.");
                    } else {
                        JOptionPane.showMessageDialog(TaskManagerTool.this, "Folder '" + folderName + "' not found.");
                    }
                }
            }
        });

        searchFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String folderName = JOptionPane.showInputDialog(TaskManagerTool.this, "Enter folder name to search:");
                if (folderName != null) {
                    boolean found = taskManager.searchFolder(folderName);
                    if (found) {
                        JOptionPane.showMessageDialog(TaskManagerTool.this, "Folder '" + folderName + "' found.");
                    } else {
                        JOptionPane.showMessageDialog(TaskManagerTool.this, "Folder not found.");
                    }
                }
            }
        });

        selectFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String folderName = JOptionPane.showInputDialog(TaskManagerTool.this, "Enter folder name to select:");
                if (folderName != null) {
                    boolean success = taskManager.selectFolder(folderName);
                    if (success) {
                        selectedFolderLabel.setText("Task Operations on " + folderName + " Folder");
                        cardLayout.show(mainPanel, "TaskPanel"); // Switch to the task panel
                    } else {
                        JOptionPane.showMessageDialog(TaskManagerTool.this, "Folder '" + folderName + "' not found.");
                    }
                }
            }
        });

        return folderPanel;
    }

    private JPanel createTaskPanel() {
        JPanel taskPanel = new JPanel(new BorderLayout());

        // Selected Folder Label
        selectedFolderLabel = new JLabel("Task Operations on None Folder", SwingConstants.CENTER);
        selectedFolderLabel.setFont(new Font("Arial", Font.BOLD, 24));
        taskPanel.add(selectedFolderLabel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(8, 1, 10, 10)); // 8 rows, 1 column, with spacing
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 150, 20, 150)); // Add padding

        // Buttons
        JButton addTaskButton = createStyledButton("Add Task");
        JButton viewTasksButton = createStyledButton("View Tasks");
        JButton editTaskButton = createStyledButton("Edit Task");
        JButton searchTaskButton = createStyledButton("Search Task");
        JButton filterTasksButton = createStyledButton("Filter Tasks");
        JButton markCompleteButton = createStyledButton("Mark Task as Complete");
        JButton markIncompleteButton = createStyledButton("Mark Task as Incomplete");
        JButton deleteTaskButton = createStyledButton("Delete Task");
        JButton backButton = createStyledButton("Back");

        buttonPanel.add(addTaskButton);
        buttonPanel.add(viewTasksButton);
        buttonPanel.add(editTaskButton);
        buttonPanel.add(searchTaskButton);
        buttonPanel.add(filterTasksButton);
        buttonPanel.add(markCompleteButton);
        buttonPanel.add(markIncompleteButton);
        buttonPanel.add(deleteTaskButton);

        // Add components to the task panel
        taskPanel.add(buttonPanel, BorderLayout.CENTER);

        // Back Button Panel (centered at the bottom)
        JPanel backButtonPanel = new JPanel(new GridLayout(1, 1)); // 1 row, 1 column
        backButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 300, 20, 300)); // Add vertical padding
        backButtonPanel.add(backButton);
        taskPanel.add(backButtonPanel, BorderLayout.SOUTH);

        // Button Actions
        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskName = JOptionPane.showInputDialog(TaskManagerTool.this, "Enter task name:");
                if (taskName != null && !taskName.isEmpty()) {
                    String taskCategory = JOptionPane.showInputDialog(TaskManagerTool.this, "Enter task category:");
                    if (taskCategory != null && !taskCategory.isEmpty()) {
                        String taskPriority = JOptionPane.showInputDialog(TaskManagerTool.this, "Enter task priority:");
                        if (taskPriority != null && !taskPriority.isEmpty()) {
                            boolean success = taskManager.addTaskToCurrentFolder(taskName, taskCategory, taskPriority);
                            if (success) {
                                JOptionPane.showMessageDialog(TaskManagerTool.this, "Task added: " + taskName);
                            } else {
                                JOptionPane.showMessageDialog(TaskManagerTool.this, "Failed to add task.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(TaskManagerTool.this, "Task priority cannot be empty!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(TaskManagerTool.this, "Task category cannot be empty!");
                    }
                } else {
                    JOptionPane.showMessageDialog(TaskManagerTool.this, "Task name cannot be empty!");
                }
            }
        });

        viewTasksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Task> tasks = taskManager.getTasksInCurrentFolder();
                if (tasks.isEmpty()) {
                    JOptionPane.showMessageDialog(TaskManagerTool.this, "No tasks found.");
                } else {
                    // Create a custom dialog to display tasks
                    JDialog taskViewerDialog = new JDialog(TaskManagerTool.this, "Task Viewer", true);
                    taskViewerDialog.setSize(400, 200);
                    taskViewerDialog.setLayout(new BorderLayout());
                    taskViewerDialog.setLocationRelativeTo(TaskManagerTool.this);

                    // Panel to display task details
                    JPanel taskDetailsPanel = new JPanel(new GridLayout(4, 1, 10, 10)); // 4 rows for fields
                    JLabel nameLabel = new JLabel();
                    JLabel categoryLabel = new JLabel();
                    JLabel priorityLabel = new JLabel();
                    JLabel completedLabel = new JLabel();

                    // Add left padding to labels
                    int leftPadding = 20; // Set the desired left padding in pixels
                    nameLabel.setBorder(BorderFactory.createEmptyBorder(0, leftPadding, 0, 0));
                    categoryLabel.setBorder(BorderFactory.createEmptyBorder(0, leftPadding, 0, 0));
                    priorityLabel.setBorder(BorderFactory.createEmptyBorder(0, leftPadding, 0, 0));
                    completedLabel.setBorder(BorderFactory.createEmptyBorder(0, leftPadding, 0, 0));

                    taskDetailsPanel.add(nameLabel);
                    taskDetailsPanel.add(categoryLabel);
                    taskDetailsPanel.add(priorityLabel);
                    taskDetailsPanel.add(completedLabel);

                    // Buttons for navigation
                    JButton previousButton = new JButton("Previous");
                    JButton nextButton = new JButton("Next");

                    // Track current task index
                    int[] currentIndex = {0}; // Using an array to make it mutable in the lambda

                    // Function to update task details
                    Runnable updateTaskDetails = () -> {
                        Task currentTask = tasks.get(currentIndex[0]);
                        nameLabel.setText("Name: " + currentTask.getName());
                        categoryLabel.setText("Category: " + currentTask.getCategory());
                        priorityLabel.setText("Priority: " + currentTask.getPriority());
                        completedLabel.setText("Completed: " + currentTask.isCompleted());
                    };

                    // Initialize with the first task
                    updateTaskDetails.run();

                    // Add action listeners for navigation buttons
                    previousButton.addActionListener(evt -> {
                        if (currentIndex[0] > 0) {
                            currentIndex[0]--;
                            updateTaskDetails.run();
                        }
                    });

                    nextButton.addActionListener(evt -> {
                        if (currentIndex[0] < tasks.size() - 1) {
                            currentIndex[0]++;
                            updateTaskDetails.run();
                        }
                    });

                    // Button panel
                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
                    buttonPanel.add(previousButton);
                    buttonPanel.add(nextButton);

                    // Add components to the dialog
                    taskViewerDialog.add(taskDetailsPanel, BorderLayout.CENTER);
                    taskViewerDialog.add(buttonPanel, BorderLayout.SOUTH);

                    // Show the dialog
                    taskViewerDialog.setVisible(true);
                }
            }
        });

        editTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskName = JOptionPane.showInputDialog(TaskManagerTool.this, "Enter task name to edit:");
                if (taskName != null) {
                    String[] options = {"Task Name", "Task Category", "Task Priority"};
                    int fieldChoice = JOptionPane.showOptionDialog(TaskManagerTool.this, "Which field do you want to edit?", "Edit Task",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (fieldChoice != JOptionPane.CLOSED_OPTION) {
                        String newValue = JOptionPane.showInputDialog(TaskManagerTool.this, "Enter new value:");
                        if (newValue != null) {
                            boolean success = taskManager.editTask(taskName, fieldChoice + 1, newValue);
                            if (success) {
                                JOptionPane.showMessageDialog(TaskManagerTool.this, "Task '" + taskName + "' updated.");
                            } else {
                                JOptionPane.showMessageDialog(TaskManagerTool.this, "Task '" + taskName + "' not found.");
                            }
                        }
                    }
                }
            }
        });

        searchTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskName = JOptionPane.showInputDialog(TaskManagerTool.this, "Enter task name to search:");
                if (taskName != null) {
                    Task task = taskManager.searchTask(taskName);
                    if (task != null) {
                        JOptionPane.showMessageDialog(TaskManagerTool.this, "Task found:\n" +
                                "Name: " + task.getName() + "\n" +
                                "Category: " + task.getCategory() + "\n" +
                                "Priority: " + task.getPriority() + "\n" +
                                "Completed: " + task.isCompleted());
                    } else {
                        JOptionPane.showMessageDialog(TaskManagerTool.this, "Task '" + taskName + "' not found.");
                    }
                }
            }
        });

        filterTasksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"Complete Tasks", "Incomplete Tasks"};
                int filterChoice = JOptionPane.showOptionDialog(TaskManagerTool.this, "Filter tasks by:", "Filter Tasks",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (filterChoice != JOptionPane.CLOSED_OPTION) {
                    boolean filterByCompleted = (filterChoice == 0); // true for complete tasks, false for incomplete tasks
                    List<Task> filteredTasks = taskManager.filterTasksByCompletion(filterByCompleted);

                    if (filteredTasks.isEmpty()) {
                        JOptionPane.showMessageDialog(TaskManagerTool.this, "No tasks found.");
                    } else {
                        // Create a custom dialog to display filtered tasks
                        JDialog taskViewerDialog = new JDialog(TaskManagerTool.this, "Filtered Task Viewer", true);
                        taskViewerDialog.setSize(400, 200);
                        taskViewerDialog.setLayout(new BorderLayout());
                        taskViewerDialog.setLocationRelativeTo(TaskManagerTool.this);

                        // Panel to display task details
                        JPanel taskDetailsPanel = new JPanel(new GridLayout(4, 1, 10, 10)); // 4 rows for fields
                        JLabel nameLabel = new JLabel();
                        JLabel categoryLabel = new JLabel();
                        JLabel priorityLabel = new JLabel();
                        JLabel completedLabel = new JLabel();

                        // Add left padding to labels
                        int leftPadding = 20; // Set the desired left padding in pixels
                        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, leftPadding, 0, 0));
                        categoryLabel.setBorder(BorderFactory.createEmptyBorder(0, leftPadding, 0, 0));
                        priorityLabel.setBorder(BorderFactory.createEmptyBorder(0, leftPadding, 0, 0));
                        completedLabel.setBorder(BorderFactory.createEmptyBorder(0, leftPadding, 0, 0));

                        taskDetailsPanel.add(nameLabel);
                        taskDetailsPanel.add(categoryLabel);
                        taskDetailsPanel.add(priorityLabel);
                        taskDetailsPanel.add(completedLabel);

                        // Buttons for navigation
                        JButton previousButton = new JButton("Previous");
                        JButton nextButton = new JButton("Next");

                        // Track current task index
                        int[] currentIndex = {0}; // Using an array to make it mutable in the lambda

                        // Function to update task details
                        Runnable updateTaskDetails = () -> {
                            Task currentTask = filteredTasks.get(currentIndex[0]);
                            nameLabel.setText("Name: " + currentTask.getName());
                            categoryLabel.setText("Category: " + currentTask.getCategory());
                            priorityLabel.setText("Priority: " + currentTask.getPriority());
                            completedLabel.setText("Completed: " + currentTask.isCompleted());
                        };

                        // Initialize with the first task
                        updateTaskDetails.run();

                        // Add action listeners for navigation buttons
                        previousButton.addActionListener(evt -> {
                            if (currentIndex[0] > 0) {
                                currentIndex[0]--;
                                updateTaskDetails.run();
                            }
                        });

                        nextButton.addActionListener(evt -> {
                            if (currentIndex[0] < filteredTasks.size() - 1) {
                                currentIndex[0]++;
                                updateTaskDetails.run();
                            }
                        });

                        // Button panel
                        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
                        buttonPanel.add(previousButton);
                        buttonPanel.add(nextButton);

                        // Add components to the dialog
                        taskViewerDialog.add(taskDetailsPanel, BorderLayout.CENTER);
                        taskViewerDialog.add(buttonPanel, BorderLayout.SOUTH);

                        // Show the dialog
                        taskViewerDialog.setVisible(true);
                    }
                }
            }
        });

        markCompleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskName = JOptionPane.showInputDialog(TaskManagerTool.this, "Enter task name to mark as complete:");
                if (taskName != null) {
                    boolean success = taskManager.markTaskAsComplete(taskName);
                    if (success) {
                        JOptionPane.showMessageDialog(TaskManagerTool.this, "Task '" + taskName + "' marked as complete.");
                    } else {
                        JOptionPane.showMessageDialog(TaskManagerTool.this, "Task '" + taskName + "' not found.");
                    }
                }
            }
        });

        markIncompleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskName = JOptionPane.showInputDialog(TaskManagerTool.this, "Enter task name to mark as incomplete:");
                if (taskName != null) {
                    boolean success = taskManager.markTaskAsIncomplete(taskName);
                    if (success) {
                        JOptionPane.showMessageDialog(TaskManagerTool.this, "Task '" + taskName + "' marked as incomplete.");
                    } else {
                        JOptionPane.showMessageDialog(TaskManagerTool.this, "Task '" + taskName + "' not found.");
                    }
                }
            }
        });

        deleteTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskName = JOptionPane.showInputDialog(TaskManagerTool.this, "Enter task name to delete:");
                if (taskName != null) {
                    boolean success = taskManager.deleteTask(taskName);
                    if (success) {
                        JOptionPane.showMessageDialog(TaskManagerTool.this, "Task '" + taskName + "' deleted.");
                    } else {
                        JOptionPane.showMessageDialog(TaskManagerTool.this, "Task '" + taskName + "' not found.");
                    }
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "FolderPanel"); // Switch back to the folder panel
            }
        });

        return taskPanel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                // Create rounded corners
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Rounded corners with radius 20
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                // Draw rounded border
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); // Rounded border with radius 20
                g2.dispose();
            }
        };

        // Button styling
        button.setFont(new Font("Arial", Font.PLAIN, 14)); // Smaller font
        button.setBackground(new Color(59, 89, 182)); // Blue color
        button.setForeground(Color.WHITE); // White text
        button.setFocusPainted(false); // Remove focus border
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // Smaller padding
        button.setContentAreaFilled(false); // Make the button transparent for custom painting
        button.setOpaque(false); // Ensure the button is not opaque

        return button;
    }

    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TaskManagerTool();
            }
        });
    }
}