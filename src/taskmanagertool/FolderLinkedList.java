package taskmanagertool;

class FolderLinkedList {
    private FolderNode head;

    public FolderLinkedList() {
        head = null;
    }

    public void addFolder(Folder folder) {
        FolderNode newNode = new FolderNode(folder);
        if (head == null) {
            head = newNode;
        } else {
            FolderNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public Folder searchFolder(String folderName) {
        FolderNode current = head;
        while (current != null) {
            if (current.folder.getName().equals(folderName)) {
                return current.folder;
            }
            current = current.next;
        }
        return null;
    }

    public void deleteFolder(String folderName) {
        if (head == null) return;

        if (head.folder.getName().equals(folderName)) {
            head = head.next;
            return;
        }

        FolderNode current = head;
        while (current.next != null && !current.next.folder.getName().equals(folderName)) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
        }
    }

    public void viewFolders() {
        FolderNode current = head;
        while (current != null) {
            System.out.println(current.folder.getName());
            current = current.next;
        }
    }
}

