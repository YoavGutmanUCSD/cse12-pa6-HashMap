import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileSystem {

    MyHashMap<String, ArrayList<FileData>> nameMap;
    MyHashMap<String, ArrayList<FileData>> dateMap;

    // TODO
    public FileSystem() {

    }

    // TODO
    public FileSystem(String inputFile) {
        // Add your code here
        try {
            File f = new File(inputFile);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(", ");
                // Add your code here
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    // TODO
    public boolean add(String fileName, String directory, String modifiedDate) {
        return false;
    }

    // TODO
    public FileData findFile(String name, String directory) {
        return null;
    }

    // TODO
    public ArrayList<String> findAllFilesName() {
        return null;
    }

    // TODO
    public ArrayList<FileData> findFilesByName(String name) {
        return null;
    }

    // TODO
    public ArrayList<FileData> findFilesByDate(String modifiedDate) {
        return null;
    }

    // TODO
    public ArrayList<FileData> findFilesInMultDir(String modifiedDate) {
        return null;
    }

    // TODO
    public boolean removeByName(String name) {
        return false;
    }

    // TODO
    public boolean removeFile(String name, String directory) {
        return false;
    }

}
