package taskmanagertool;

public class Task {
    private int id;
    private String name;
    private String category;
    private String priority;
    private boolean isCompleted;
    private String completed; // New field

    public Task(String name, String category, String priority) {
        this.name = name;
        this.category = category;
        this.priority = priority;
        this.isCompleted = false;
        this.completed = "No"; // Initialize as "No"
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
        this.completed = isCompleted ? "Yes" : "No"; // Update the completed field
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCompleted() {
        return completed;
    }

    @Override
    public String toString() {
        return "Task: " + name + ", Category: " + category + ", Priority: " + priority + ", Completed: " + completed;
    }
}