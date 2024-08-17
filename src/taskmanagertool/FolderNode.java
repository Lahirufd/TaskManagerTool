package taskmanagertool;

class FolderNode {
    Folder folder;
    FolderNode next;

    public FolderNode(Folder folder) {
        this.folder = folder;
        this.next = null;
    }
}

