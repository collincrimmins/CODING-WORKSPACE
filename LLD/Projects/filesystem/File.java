package LLD.Projects.filesystem;

public class File extends Obj {
    private String content;

    public File(String name, String content) {
        super(name);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean isFolder() {
        return false;
    }
}
