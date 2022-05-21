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
    public FileSystem(File inputFile){
        this(inputFile.getAbsolutePath());
    }

    // Constructor that creates a new FileSystem object with the given inputFile that contains the file system information
    public FileSystem(String inputFile) {
        this.nameMap = new MyHashMap();
        this.dateMap = new MyHashMap();
        try {
            File f = new File(inputFile);
            Scanner sc = new Scanner(f);
            ArrayList<FileData> someFiles = new ArrayList<FileData>();
            // each line contains the file's info
            while (sc.hasNextLine()) {
                // System.out.println("bruh " + someFiles.size());
                String nextLine = sc.nextLine();
                String[] data = nextLine.split(", ");
                // System.out.println(data);

                // adding this file's info to the filesystem
                FileData oneFile = new FileData(data[0], data[1], data[2]);
                // for (int i = 0; i < someFiles.size(); i++) {
                //     if (someFiles.get(i).name == oneFile.name && someFiles.get(i).lastModifiedDate == oneFile.lastModifiedDate && someFiles.get(i).dir == oneFile.dir) {
                //         continue;
                //     }
                // }
                someFiles.add(oneFile);
                add(data[0], data[1], data[2]);

                // each map gets one!
                // nameMap.put(oneFile.name, someFiles);
                // dateMap.put(oneFile.lastModifiedDate, someFiles);
                //System.out.format("name: date\n%s: %s\n", oneFile.name, nameMap.get(oneFile.name));
                //System.out.println(nameMap.keys());
            }
            //System.out.println("ok" + findFilesByDate());
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
        ArrayList<FileData> someFiles = new ArrayList<FileData>();
        FileData oneFile = new FileData(fileName, directory, modifiedDate);
        someFiles.add(oneFile);

        boolean inNameMap = false;
        boolean inDateMap = false;

        boolean flag = false;

        // no duplicates!

        // if it already exists, i need to add it to the alraedy existing key's values
        if(nameMap.keys().contains(fileName)) {
            ArrayList<FileData> moreFiles = nameMap.get(fileName);
            for (int i = 0; i < moreFiles.size(); i++) {
                if(moreFiles.get(i).dir != oneFile.dir) {
                    nameMap.get(fileName).add(oneFile);
                }
            }
            flag = true;
        } 
        // otherwise, i'll just make a new key and add it
        else {
            inNameMap = nameMap.put(oneFile.name, someFiles);
        }


        // if it already exists, i need to add it to the alraedy existing key's values
        if(dateMap.keys().contains(modifiedDate)) {
            dateMap.get(modifiedDate).add(oneFile);
            flag=true;
        } 
        // otherwise, i'll just make a new key and add it
        else {
            inDateMap = dateMap.put(oneFile.lastModifiedDate, someFiles);
        }

        // true if it didnt exist previously, false otherwise
        return inNameMap && inDateMap && !flag;

    }

    // This method should return a single FileData object with the given name and directory
    public FileData findFile(String name, String directory) {
        ArrayList<String> allDates = new ArrayList<String>(dateMap.keys());

        // first checking if the filesystem contains this key
        if(nameMap.containsKey(name)) {
            //System.out.println("This ran");

            for (int i = 0; i < allDates.size(); i++) {
                String aKey = allDates.get(i);
                ArrayList<FileData> someFiles = dateMap.get(aKey);

                // if it does contain the key, loop through the object array that is its value
                for (int j = 0; j < someFiles.size(); j++) {
                    FileData someFile = someFiles.get(j);

                    // if the directory is the same as well (same as the name), return the file
                    if(someFile.dir.equals(directory) && someFile.name.equals(name)) {
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
        ArrayList<FileData> returnable = new ArrayList<FileData>();

        ArrayList<String> allNames = new ArrayList<String>(dateMap.keys());

        // first checking if the filesystem contains this key
        if(nameMap.containsKey(name)) {

            for (int i = 0; i < allNames.size(); i++) {
                String aKey = allNames.get(i);
                ArrayList<FileData> someFiles = dateMap.get(aKey);

                // if it does contain the key, loop through the object array that is its value
                for (int j = 0; j < someFiles.size(); j++) {
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
        ArrayList<FileData> returnable = new ArrayList<FileData>();

        ArrayList<String> allNames = new ArrayList<String>(nameMap.keys());
        // first checking if the filesystem contains this key
        if(dateMap.containsKey(modifiedDate)) {

            // if it does, i can use the nameMap and loop through the keys there
            // & check each date that matches modifiedDate
            int theSize = allNames.size();
            for (int i = 0; i < theSize; i++) {
                // System.out.println("FIRST FOR");
                String aKey = allNames.get(i);
                ArrayList<FileData> someFiles = nameMap.get(aKey);
                //System.out.println("lol" + someFiles.size());
                
                // for this key in nameMap, check its modifiedDate, see if it equals the same
                for (int j = 0; j < someFiles.size(); j++) {
                    // System.out.println("lol" + someFiles.size());
                    // System.out.println("SECOND FOR");
                    FileData someFile = someFiles.get(j);
                    // System.out.println(someFile.name);
                    if (someFile.lastModifiedDate.equals(modifiedDate) && !(returnable.contains(someFile))) {
                        //System.out.println("hi" + modifiedDate);
                        returnable.add(someFile); }
                }

            }

        }
        // System.out.println(returnable.size());
        return returnable;
    }

    // This find method should return a list of FileData with the given modifiedDate that has at least another 
    // file with the same file name in a different directory
    public ArrayList<FileData> findFilesInMultDir(String modifiedDate) {
        ArrayList<FileData> filesByDate = findFilesByDate(modifiedDate);
        ArrayList<FileData> returnable = new ArrayList<FileData>();

        for (int i = 0; i < filesByDate.size(); i++) {
            FileData firstLoopFile = filesByDate.get(i);
            String firstLoopFileName = firstLoopFile.name;
            String firstLoopFileDir = firstLoopFile.dir;
            
            for (int j = 0; j < filesByDate.size(); j++) {
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

    // This method should remove all the files with the given name in the FileSystem. 
    // Return true if success, false otherwise
    public boolean removeByName(String name) {
        boolean isRemovedDate = false;
        boolean isRemovedName = nameMap.remove(name);

        // every key in date map..
        ArrayList<String> allDateMapKeys = new ArrayList<String>(dateMap.keys());

        for (int i = 0; i < allDateMapKeys.size(); i++) {
            String aKey = allDateMapKeys.get(i);
            ArrayList<FileData> someFiles = dateMap.get(aKey);

                // if this key has the specified name, REMOVE IT
                for (int j = 0; j < someFiles.size(); j++) {
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
        boolean isRemovedDate = false;
        boolean isRemovedName = false;
        ArrayList<String> allNames = new ArrayList<String>(nameMap.keys());
        ArrayList<String> allDates = new ArrayList<String>(dateMap.keys());

        // removing in nameMap
        for (int i = 0; i < allNames.size(); i++) {
            String keyName = allNames.get(i);
            ArrayList<FileData> dataInKey = nameMap.get(keyName);

            for (int j = 0; j < dataInKey.size(); j++) {
                FileData currentKey = dataInKey.get(j);

                if(keyName.equals(name) && currentKey.dir.equals(directory)) {
                    nameMap.get(keyName).remove(j);
                    isRemovedName = true;
                }

            }
            if (nameMap.get(keyName).isEmpty()) {
                nameMap.remove(keyName);
                isRemovedName = true;
            }

        }
        // removing in dateMap
        for (int i = 0; i < allDates.size(); i++) {
            String keyName = allDates.get(i);
            ArrayList<FileData> dataInKey = dateMap.get(keyName);

            for (int j = 0; j < dataInKey.size(); j++) {
                FileData currentKey = dataInKey.get(j);

                if(currentKey.name.equals(name) && currentKey.dir.equals(directory)) {
                    dateMap.get(keyName).remove(j);
                    isRemovedDate = true;
                }

            }

            if (dateMap.get(keyName).isEmpty()) {
                dateMap.remove(keyName);
                isRemovedDate = true;
            }

        }
        return isRemovedName && isRemovedDate;
    }

}
