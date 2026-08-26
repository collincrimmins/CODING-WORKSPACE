package LLD.Projects.other_filesystem;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileSystem {
    private final Folder root;
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public FileSystem() {
        this.root = new Folder("/");
    }

    public File createFile(String path, String content) {
        if (path.equals("/")) {
            throw new IllegalArgumentException("Cannot create file at root");
        }

        Folder parent = resolveParent(path);
        String fileName = extractName(path);

        // Lock
        rwLock.writeLock().lock();
        try {
            if (parent.hasChild(fileName)) {
                throw new IllegalStateException("Entry laready exists: " + fileName);
            }

            File file = new File(fileName, content);
            parent.addChild(file);
            return file;
        } finally {
            rwLock.writeLock().unlock();;
        }
    }

    public Folder createFolder(String path) {
        if (path.equals("/")) {
            throw new IllegalStateException("Root already exists");
        }

        Folder parent = resolveParent(path);
        String folderName = extractName(path);

        if (parent.hasChild(folderName)) {
            throw new IllegalStateException("Entry already exists: " + folderName);
        }

        Folder folder = new Folder(folderName);
        parent.addChild(folder);
        return folder;
    }

    public void delete(String path) {
        if (path.equals("/")) {
            throw new IllegalArgumentException("Cannot delete root");
        }

        Folder parent = resolveParent(path);
        String name = extractName(path);

        FileSystemEntry removed = parent.removeChild(name);
        if (removed == null) {
            throw new IllegalArgumentException("Entry not found: " + path);
        }
    }

    public List<FileSystemEntry> list(String path) {
        FileSystemEntry entry = resolvePath(path);

        if (!entry.isDirectory()) {
            throw new IllegalArgumentException("cannot list contents of a file");
        }

        return ((Folder) entry).getChildren();
    }

    public FileSystemEntry get(String path) {
        rwLock.readLock().lock();
        try {
            return resolvePath(path);
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public void rename(String path, String newName) {
        if (path.equals("/")) {
            throw new IllegalArgumentException("Cannot rename root");
        }

        if (newName == null || newName.isEmpty() || newName.contains("/")) {
            throw new IllegalArgumentException("Invalid name");
        }

        Folder parent = resolveParent(path);
        String oldName = extractName(path);

        if (!parent.hasChild(oldName)) {
            throw new IllegalArgumentException("entry not found");
        }

        if (parent.hasChild(newName)) {
            throw new IllegalStateException("Entry already exists " + newName);
        }

        FileSystemEntry entry = parent.removeChild(oldName);
        entry.setName(newName);
        parent.addChild(entry);
    }

    public void move(String srcPath, String destPath) {
        if (srcPath.equals("/")) {
            throw new IllegalArgumentException("Cannot move root");
        }

        Folder srcParent = resolveParent(srcPath);
        String srcName = extractName(srcPath);
        FileSystemEntry entry = srcParent.getChild(srcName);

        if (entry == null) {
            throw new IllegalArgumentException("Source not found: " + srcPath);
        }

        Folder destParent = resolveParent(destPath);
        String destName = extractName(destPath);

        // Lock in Alphabetical Order by Path
        Folder firstLock = srcParent.getPath().compareTo(destParent.getPath()) > 0 ? srcParent : destParent;
        Folder secondLock = srcParent.getPath().compareTo(destParent.getPath()) > 0 ? destParent : srcParent;

        synchronized(firstLock) {
            synchronized(secondLock) {
                if (entry.isDirectory()) {
                    Folder current = destParent;
                    while (current != null) {
                        if (current == entry) {
                            throw new IllegalArgumentException("cannot move folder into itself");
                        }
                        current = current.getParent();
                    }
                }

                if (destParent.hasChild(destName)) {
                    throw new IllegalStateException("Destination already exists: " + destPath);
                }

                srcParent.removeChild(srcName);
                entry.setName(destName);
                destParent.addChild(entry);
            }
        }
    }

    private FileSystemEntry resolvePath(String path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Path cannot be empty");
        }

        if (!path.startsWith("/")) {
            throw new IllegalArgumentException("Path must be absolute");
        }

        if (path.equals("/")) {
            return root;
        }

        String[] parts = path.substring(1).split("/");
        FileSystemEntry current = root;

        for (String part : parts) {
            if (part.isEmpty()) {
                throw new IllegalArgumentException("Invalid path: consective slashes");
            }

            if (!current.isDirectory()) {
                throw new IllegalArgumentException("Not a directory");
            }

            FileSystemEntry child = ((Folder) current).getChild(part);
            if (child == null) {
                throw new IllegalArgumentException("Path not found " + path);
            }

            current = child;
        }

        return current;
    }

    private Folder resolveParent(String path) {
        if (path.equals("/")) {
            throw new IllegalArgumentException("Root has no parent");
        }

        int lastSlash = path.lastIndexOf("/");
        String parentPath = lastSlash == 0 ? "/" : path.substring(0, lastSlash);

        FileSystemEntry parent = resolvePath(parentPath);

        if (!parent.isDirectory()) {
            throw new IllegalArgumentException("parent is not a directory");
        }

        return (Folder) parent;
    }

    private String extractName(String path) {
        int lastSlash = path.lastIndexOf("/");
        return path.substring(lastSlash + 1);
    }
}
