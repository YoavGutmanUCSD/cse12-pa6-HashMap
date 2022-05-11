import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// This class will be used to represent the entire structure of the file system.
public class FileSystem {

    MyHashMap<String, ArrayList<FileData>> nameMap;
    MyHashMap<String, ArrayList<FileData>> dateMap;

    // Default constructor that creates a new FileSystem object and initializes its instance variable.
    public FileSystem() {
        this.nameMap = new MyHashMap();
        this.dateMap = new MyHashMap();
    }

    // Constructor that creates a new FileSystem object with the given inputFile that contains the file system information
    public FileSystem(String inputFile) {
        this.nameMap = new MyHashMap();
        this.dateMap = new MyHashMap();
        try {
            File f = new File(inputFile);
            Scanner sc = new Scanner(f);

            // each line contains the file's info
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(", ");

                // adding this file's info to the filesystem
                ArrayList<FileData> someFiles = new ArrayList();
                FileData oneFile = new FileData(data[0], data[1], data[2]);
                someFiles.add(oneFile);

                // each map gets one!
                nameMap.put(oneFile.name, someFiles);
                dateMap.put(oneFile.lastModifiedDate, someFiles);

            }
            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    // This method should create a FileData object with the given file information and 
    // add it to the instance variables of FileSystem. 

    // This method should return true if the file is successfully added to the FileSystem, and 
    // false if a file with the same name already exists in that directory
    public boolean add(String fileName, String directory, String modifiedDate) {
        // make the file..
        ArrayList<FileData> someFiles = new ArrayList();
        FileData oneFile = new FileData(fileName, directory, modifiedDate);
        someFiles.add(oneFile);

        // add the file to the filesystem..
        boolean inNameMap = nameMap.put(oneFile.name, someFiles);
        boolean inDateMap = dateMap.put(oneFile.lastModifiedDate, someFiles);

        return inNameMap && inDateMap;

    }

    // This method should return a single FileData object with the given name and directory
    public FileData findFile(String name, String directory) {
        ArrayList<String> allNames = new ArrayList<String>(dateMap.keys());

        // first checking if the filesystem contains this key
        if(nameMap.containsKey(name)) {

            for (int i = 0; i < allNames.size(); i++) {
                String aKey = allNames.get(i);
                ArrayList<FileData> someFiles = dateMap.get(aKey);

                // if it does contain the key, loop through the object array that is its value
                for (int j = 0; j < someFiles.size(); i++) {
                    FileData someFile = someFiles.get(j);

                    // if the directory is the same as well (same as the name), return the file
                    if(someFile.dir.equals(directory)) {
                        return someFile;
                    }
                }

            }

        }

        // if it doesn't contain the file, return null
        return null;
    }

    // This method should return an array list of string that represents all unique file names
    //  across all directories within the fileSystem
    public ArrayList<String> findAllFilesName() {
        ArrayList<String> allNames = new ArrayList<String>(nameMap.keys());
        return allNames;
    }

    // The find method should return a list of FileData with the given name. 
    // Should not modify the FileSystem itself. Return an empty list if such a file does not exist.
    public ArrayList<FileData> findFilesByName(String name) {
        ArrayList<FileData> returnable = new ArrayList();

        ArrayList<String> allNames = new ArrayList<String>(dateMap.keys());

        // first checking if the filesystem contains this key
        if(nameMap.containsKey(name)) {

            for (int i = 0; i < allNames.size(); i++) {
                String aKey = allNames.get(i);
                ArrayList<FileData> someFiles = dateMap.get(aKey);

                // if it does contain the key, loop through the object array that is its value
                for (int j = 0; j < someFiles.size(); i++) {
                    FileData someFile = someFiles.get(j);
                    if (someFile.name.equals(name)) {
                        returnable.add(someFile); }
                }

            }

        }
        return returnable;
    }

    // This find method should return a list of FileData with the given modifiedDate.
    public ArrayList<FileData> findFilesByDate(String modifiedDate) {
        ArrayList<FileData> returnable = new ArrayList();

        ArrayList<String> allNames = new ArrayList<String>(nameMap.keys());

        // first checking if the filesystem contains this key
        if(dateMap.containsKey(modifiedDate)) {

            for (int i = 0; i < allNames.size(); i++) {
                String aKey = allNames.get(i);
                ArrayList<FileData> someFiles = nameMap.get(aKey);

                // if it does contain the key, loop through the object array that is its value
                for (int j = 0; j < someFiles.size(); i++) {
                    FileData someFile = someFiles.get(j);
                    if (someFile.lastModifiedDate.equals(modifiedDate)) {
                        returnable.add(someFile); }
                }

            }

        }
        return returnable;
    }

    // This find method should return a list of FileData with the given modifiedDate that has at least another 
    // file with the same file name in a different directory
    public ArrayList<FileData> findFilesInMultDir(String modifiedDate) {
        ArrayList<FileData> filesByDate = findFilesByDate(modifiedDate);
        ArrayList<FileData> returnable = new ArrayList();

        for (int i = 0; i < filesByDate.size(); i++) {
            FileData firstLoopFile = filesByDate.get(i);
            String firstLoopFileName = firstLoopFile.name;
            String firstLoopFileDir = firstLoopFile.dir;
            
            for (int j = 0; j < filesByDate.size(); i++) {
                FileData secondLoopFile = filesByDate.get(j);
                String secondLoopFileName = secondLoopFile.name;
                String secondLoopFileDir = secondLoopFile.dir;

                if(firstLoopFileName.equals(secondLoopFileName) && 
                    firstLoopFileDir != secondLoopFileDir && 
                    !returnable.contains(secondLoopFile)) { 
                        returnable.add(secondLoopFile);
                }

            }

        }

        return returnable;

    }

    // This method should remove all the files with the given name in the FileSystem. Return true if success, false otherwise
    public boolean removeByName(String name) {
        boolean isRemovedDate = false;
        boolean isRemovedName = nameMap.remove(name);

        ArrayList<String> allNames = new ArrayList<String>(dateMap.keys());

        for (int i = 0; i < allNames.size(); i++) {
            String aKey = allNames.get(i);
            ArrayList<FileData> someFiles = dateMap.get(aKey);

                // if it does contain the key, loop through the object array that is its value
                for (int j = 0; j < someFiles.size(); i++) {
                    FileData someFile = someFiles.get(j);
                   if (someFile.name.equals(name)) {
                       dateMap.remove(aKey);
                       isRemovedDate = true;
                   }
                }
        }

        return (isRemovedDate && isRemovedName);
    }

    // This method should remove a certain file with the given name and directory. 
    // Return true if success, false otherwise.
    public boolean removeFile(String name, String directory) {
        boolean isNameRemoved = removeByName(name);

        boolean isRemovedDate = false;
        boolean isRemovedName = dateMap.remove(directory);
        ArrayList<String> allNames = new ArrayList<String>(nameMap.keys());

        for (int i = 0; i < allNames.size(); i++) {
            String aKey = allNames.get(i);
            ArrayList<FileData> someFiles = nameMap.get(aKey);

                // if it does contain the key, loop through the object array that is its value
                for (int j = 0; j < someFiles.size(); i++) {
                    FileData someFile = someFiles.get(j);
                   if (someFile.name.equals(directory)) {
                        nameMap.remove(aKey);
                       isRemovedDate = true;
                   }
                }
        }

        boolean isDateRemoved =  isRemovedDate && isRemovedName;

        return isDateRemoved && isNameRemoved;
    }

}
