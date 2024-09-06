package taskmanagertool;

class TaskLinkedList {
    private TaskNode head;

    public TaskLinkedList() {
        head = null;
    }
    
    public TaskNode getHead() {
        return head;
    }

    public void add(Task task) {
        TaskNode newNode = new TaskNode(task);
        if (head == null) {
            head = newNode;
        } else {
            TaskNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public Task search(String taskName) {
        TaskNode current = head;
        while (current != null) {
            if (current.task.getName().equals(taskName)) {
                return current.task;
            }
            current = current.next;
        }
        return null;
    }

    public void delete(String taskName) {
        if (head == null) return;

        if (head.task.getName().equals(taskName)) {
            head = head.next;
            return;
        }

        TaskNode current = head;
        while (current.next != null && !current.next.task.getName().equals(taskName)) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
        }
    }

    public void viewAll() {
        TaskNode current = head;
        while (current != null) {
            System.out.println(current.task);
            current = current.next;
        }
    }
}

