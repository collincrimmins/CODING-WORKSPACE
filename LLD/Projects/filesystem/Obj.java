package LLD.Projects.filesystem;

public abstract class Obj {
    private String name;
    private Folder parent;

    public Obj(String name) {
        this.name = name;
    }

    public Folder getParent() {
        return parent;
    }

    public void setParent(Folder newParent) {
        this.parent = newParent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (parent == null) {
            this.name = name;
            return;
        }

        // Check that Name does not exist in Parent Folder
        if (parent.hasChild(name)) {
            throw new IllegalArgumentException("[Rename] new name already exists in parent folder: " + name);
        }

        // Update Parent Folder
        String previousName = this.name;
        Folder myParentFolder = parent;
        myParentFolder.deleteChild(previousName); // This will automatically set my Obj Parent to null

        // Update Name & Add to Parent Folder
        this.name = name;
        myParentFolder.addChild(this);
    }

    // GetPath
    public String getPath() {
        String result = "";

        Obj current = this;
        while (current.getParent() != null) {
            result = current.getName() + "/" + result;
            current = current.getParent();
        }

        return "/" + result;
    }

    public abstract boolean isFolder();
}
