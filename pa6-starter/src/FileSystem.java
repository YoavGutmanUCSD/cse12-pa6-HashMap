import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// This class will be used to represent the entire structure of the file system.
////////////////////////////////////////////////////////////////////////////////////////////////////
public class FileSystem {

    MyHashMap<String, ArrayList<FileData>> nameMap = new MyHashMap();
    MyHashMap<String, ArrayList<FileData>> dateMap = new MyHashMap();

    // Default constructor that creates a new FileSystem object and initializes its instance variable.
    public FileSystem() {
        this.nameMap = new MyHashMap();
        this.dateMap = new MyHashMap();
    }
    // public FileSystem(File inputFile){
    //     this(inputFile.getAbsolutePath());
    // }

    // Constructor that creates a new FileSystem object with the given inputFile that contains the file system information
    // String inputFile: file to read
    public FileSystem(String inputFile) {
        this.nameMap = new MyHashMap();
        this.dateMap = new MyHashMap();
        try {
            File z = new File(inputFile);
            String strDir = z.getAbsoluteFile().getParent();
            File fileDir = new File(strDir);
            String[] fLst = fileDir.list();

            if (fLst != null) {

                for (int i = 0; i < fLst.length; i++) {
                    String filename = fLst[i];
                    // System.out.println(filename);

                    String strDirectory = new File(filename).getAbsolutePath();
                    //System.out.println(strDirectory);
                    File directory = new File(strDirectory);

                    if (directory.isDirectory()) {
                        if (filename.charAt(0) == '.') {
                            continue;
                        }
                        //System.out.println(filename);
                        String[] fLst2 = directory.list();
                        // System.out.println(fLst2[7]);
                        for (int j = 0; j < fLst2.length; j++) {
                            String filename2 = fLst2[j];
                            //System.out.println(filename2);
                            if (filename2.equalsIgnoreCase(inputFile)) {
                                //System.out.println(filename2 + " found");

                                strDir = strDirectory +"\\" + filename2;
                                // System.out.println(strDir);

                                break;


                            }

                        }

                    }
                } 
            } 
            // else {
            //     strDir = orgInput;
            // }

            File f = new File(strDir);
            // File z = new File(inputFile);

            // File parentDir = z.getAbsoluteFile().getParentFile();
            // String parentDirName = z.getAbsoluteFile().getParent();

            // System.out.println(parentDirName);

            // File f = new File(parentDirName + "/" + inputFile);




            Scanner sc = new Scanner(z);
            // ArrayList<FileData> someFiles = new ArrayList<FileData>();
            // each line contains the file's info
            while (sc.hasNextLine()) {
                // System.out.println("bruh " + someFiles.size());
                String nextLine = sc.nextLine();
                String[] data = nextLine.split(", ");
                // System.out.println(data);

                // adding this file's info to the filesystem
                // FileData oneFile = new FileData(data[0], data[1], data[2]);
                // for (int i = 0; i < someFiles.size(); i++) {
                //     if (someFiles.get(i).name == oneFile.name && someFiles.get(i).lastModifiedDate == oneFile.lastModifiedDate && someFiles.get(i).dir == oneFile.dir) {
                //         continue;
                //     }
                // }
                // someFiles.add(oneFile);
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

    // This method creates a FileData object with the given file information and 
    // adds it to the instance variables of FileSystem. 
    // It returns true if the file is successfully added to the FileSystem, and 
    // false if a file with the same name already exists in that directory
    //
    // String fileName: name of file to add
    // String directory: directory of file to add
    // String modifiedDate: the date of the last modification to this file
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
            for (int i = moreFiles.size()-1; i >= 0; i--) {
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
    // 
    /* This method searches the file system for the file with the given criteria,
     * and returns the results. Returns null if nothing is found.
     * String name: file name
     * String directory: path to file directory
     */
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

    /* This method takes a name and returns an ArrayList containing all of the files 
     * with the given name.
     *
     * String name: name of file.
     * Return value: ArrayList of all FileData objects with name as given.
     * The return value will be an empty list if no objects have the given name.
     */
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
                    if (someFile.name.equals(name) && !(returnable.contains(someFile))) {
                        returnable.add(someFile); }
                }

            }

        }
        return returnable;
    }

    // This find method should return a list of FileData with the given modifiedDate.
    /* This method takes a modification date and returns an ArrayList containing all of the files 
     * with the given modification date.
     *
     * String modifiedDate: modification date of file.
     * Return value: ArrayList of all FileData objects with modification date as given.
     * The return value will be an empty list if no objects have the given modification date.
     */
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

    /* This method returns the list of files that have the same name, are in multiple directories,
     * and have the given modification date.
     *
     * String modifiedDate: modification date of file.
     * Return value: ArrayList of all FileData objects in multiple directories with given date
     * The return value will be an empty list if no objects meet the criteria.
     */
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

    // Walk through all files in this directory and print out the file name. 
    // Print "Dir: {file}" if the file is a directory, and run the function on the remainder
    // Print "File: {file}" if the file is not a directory, and do nothing else.
    public void walk( String path ) {

        File root = new File( path );
        File[] list = root.listFiles();

        if (list == null) return;

        for ( File f : list ) {
            if ( f.isDirectory() ) {
                walk( f.getAbsolutePath() );
                System.out.println( "Dir:" + f.getAbsoluteFile() );
            }
            else {
                System.out.println( "File:" + f.getAbsoluteFile() );
            }
        }
    }

}


