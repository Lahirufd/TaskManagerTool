package taskmanagertool;

class Task {
    private String name;
    private String category;
    private String priority;
    private boolean isCompleted;

    public Task(String name, String category, String priority) {
        this.name = name;
        this.category = category;
        this.priority = priority;
        this.isCompleted = false;
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
    }

    @Override
    public String toString() {
        return "Task: " + name + ", Category: " + category + ", Priority: " + priority + ", Completed: " + isCompleted;
    }
}


