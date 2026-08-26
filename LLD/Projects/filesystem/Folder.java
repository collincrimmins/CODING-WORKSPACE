package LLD.Projects.filesystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Folder extends Obj {
    private final Map<String, Obj> children;

    public Folder(String name) {
        super(name);
        this.children = new HashMap<>();
    }

    public void addChild(Obj obj) {
        if (children.containsKey(obj.getName())) {
            throw new IllegalStateException("[Add] Child w/ name already exists: " + obj.getName());
        }

        children.put(obj.getName(), obj);
        obj.setParent(this);
    }

    public void deleteChild(String name) {
        if (!children.containsKey(name)) {
            throw new IllegalStateException("[Delete] Child w/ name does not exist: " + name);
        }

        Obj deletedObject = children.remove(name);;
        if (deletedObject != null) {
            deletedObject.setParent(null);
        }
    }

    public Obj getChild(String name) {
        return children.get(name);
    }

    public boolean hasChild(String name) {
        return children.containsKey(name);
    }

    public List<Obj> getChildren() {
        return new ArrayList<>(children.values());
    }

    @Override
    public boolean isFolder() {
        return true;
    }
}
