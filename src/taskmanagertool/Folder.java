package taskmanagertool;

class Folder {
    private String name;
    private TaskLinkedList taskList;

    public Folder(String name) {
        this.name = name;
        this.taskList = new TaskLinkedList();
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public TaskLinkedList getTaskList() {
        return taskList;
    }

    @Override
    public String toString() {
        return name;
    }
}


