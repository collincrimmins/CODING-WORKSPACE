package LLD.Projects.filesystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.management.RuntimeErrorException;

public class FileSystem {
    private final Folder root;

    public FileSystem() {
        root = new Folder("/");
    }

    // Create
    public File createFile(String name, String content, String path) {
        if (path.equals("/")) {
            throw new RuntimeException("Cannot create file at root");
        }

        // Get Parent Folder
        Folder myParent = iteratePath(path);

        // Create File & Set Parent
        File newFile = new File(name, content);
        newFile.setParent(myParent);
        myParent.addChild(newFile);

        return newFile;
    }

    public Folder createFolder(String name, String path) {
        // Get Parent Folder
        Folder myParent = iteratePath(path);

        // Create Folder & Set Parent
        Folder newFolder = new Folder(name);
        newFolder.setParent(myParent);
        myParent.addChild(newFolder);

        return newFolder;
    }

    // Iterate Path
    public Folder iteratePath(String path) {
        // Root
        if (path.length() == 1) {
            return root;
        }

        // Prepare Path
        path = path.substring(1, path.length());
        String[] pathNames = path.split("/");
        //System.out.println(Arrays.toString(pathNames));

        // Iterate
        Folder current = root;
        for (String nextFolderName : pathNames) {
            if (!current.hasChild(nextFolderName)) {
                throw new RuntimeException("Path is invalid at folder name: " + nextFolderName);
            }

            current = (Folder) current.getChild(nextFolderName);
        }

        return current;
    }

    public File iteratePathToFile(String path) {
        // Root
        if (path.length() == 1) {
            return null;
        }

        // Prepare Path
        path = path.substring(1, path.length());
        String[] pathNames = path.split("/");
        int pathIteration = 0;

        // Iterate
        Folder current = root;
        File result = null;
        for (String nextFolderName : pathNames) {
            if (!current.hasChild(nextFolderName)) {
                throw new RuntimeException("Path is invalid at folder name: " + nextFolderName);
            }

            // Reached File
            if (pathIteration == pathNames.length - 1) {
                result = (File) current.getChild(nextFolderName);
                break;
            }

            current = (Folder) current.getChild(nextFolderName);
            
            pathIteration = pathIteration + 1;
        }

        return result;
    }

    // Get Object
    public Obj getFile(String path) {
        Obj obj = iteratePathToFile(path);
        return obj;
    }

    // List Contents
    public List<Obj> listContents(Folder folder) {
        return folder.getChildren();
    }

    // Rename
    public void rename(Obj obj, String name) {
        obj.setName(name);
    }

    // Delete
    public void delete(Obj obj) {
        deleteDFS(obj);

        obj.getParent().deleteChild(obj.getName());
        System.out.println("deleted " + obj.getName());
    }

    private void deleteDFS(Obj obj) {
        if (obj.isFolder()) {
            // Delete Children
            Folder thisFolder = (Folder) obj;
            for (Obj child : thisFolder.getChildren()) {
                // DFS First to Bottom
                deleteDFS(child);
                
                // Delete from List of Children
                thisFolder.deleteChild(child.getName());
                System.out.println("deleted " + child.getName());
            }
        }
    }

    // Print
    public void printFileSystem() {
        printFileSystemDFS(root, 0);
    }

    private void printFileSystemDFS(Folder folder, int depth) {
        for (Obj obj : folder.getChildren()) {
            // Depth
            for (int i = 0; i < depth; i++) {
                System.out.print("|");
            }
            System.out.print(" ");

            // Obj Name
            System.out.println(obj.getName());

            // DFS
            if (obj.isFolder()) {
                printFileSystemDFS((Folder) obj, depth + 1);
            }
        }
    }
}
