package LLD.Projects.filesystem;

import java.util.Arrays;
import java.util.List;

public class Main {
    /*
    
    Requirements
    - File Structure (Folders & Files)
    - Navigate Paths ("/home/folder/file.txt")
    - Create & Delete Folders 
    - Create & Delete Files
    - File "content" is a String value
    - List contents of a folder
    - Edge Cases (Name collision, creating files in root "/", nonexistant path)

    Entities
    - FileSystem
    - Obj (Base Obj of Files and Folders)
    - Files
    - Folders

    */
    public static void main(String[] args) {
        FileSystem fs = new FileSystem();

        // Create
        Folder homeFolder = fs.createFolder("home", "/");
        fs.createFolder("homeDepth2", "/home/");
        fs.createFolder("homeDepth3", "/home/homeDepth2/");
        fs.createFile("file1.txt", "", "/home");
        fs.createFile("file2.txt", "", "/home");
        fs.createFile("file3.txt", "", "/home");
        fs.createFile("file.txt", "", "/home/homeDepth2");
        fs.createFile("file.txt", "", "/home/homeDepth2/homeDepth3");

        fs.createFolder("folder", "/");
        Folder myFolder1 = fs.createFolder("folder2", "/folder");
        fs.createFolder("folder3", "/folder/folder2");
        fs.createFile("file.txt", "", "/folder");
        fs.createFile("file.txt", "", "/folder/folder2");
        fs.createFile("file2.txt", "", "/folder/folder2");
        fs.createFile("file3.txt", "", "/folder/folder2");
        fs.createFile("file4.txt", "", "/folder/folder2");
        File myFile1 = fs.createFile("file5.txt", "", "/folder/folder2");

        // GetFile
        System.out.println("### GetFile ###");
        Obj myObject1 = fs.getFile("/folder/folder2/file.txt");
        System.out.println("file [" + myObject1.getName() + "] in folder [" + myObject1.getParent().getName() + "]");
        System.out.println(" ");

        // GetPath ("/folder/folder2/file.txt/")
        System.out.println("### GetPath ###");
        String myObjectPath = myObject1.getPath();
        System.out.println(myObjectPath);
        System.out.println(" ");

        // Rename
        System.out.println("### Rename ###");
        fs.rename(myObject1, "mynewfilename.txt");
        System.out.println(myObject1.getName());
        System.out.println(" ");

        // List Contents
        System.out.println("### ListContents ###");
        List<Obj> listOfContent = fs.listContents(myFolder1);
        System.out.println(listOfContent.toString());
        System.out.println(" ");

        // Delete
        System.out.println("### Delete ###");
        fs.delete(homeFolder);
        System.out.println(" ");

        // Print File System
        System.out.println("### Print File System ###");
        fs.printFileSystem();
        System.out.println(" ");
        










        // Error Testing

        // Cannot create duplicate name folder
        //fs.createFolder("folder", "/");

        // Cannot create duplicate name file
        //fs.createFile("file.txt", "", "/home");

        // Cannot create File in root directory
        //fs.createFile("fileRoot.txt", "", "/");
    }
}
