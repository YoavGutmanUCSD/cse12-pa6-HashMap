import static org.junit.Assert.*;

import org.junit.*;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileSystemTest {

    @Test
    public void properAdding() {
        //String input = new File("src/input.txt").getAbsolutePath();
        String input = "input.txt";

        FileSystem newFileSystem = new FileSystem(input);

        ArrayList<String> testerArray = newFileSystem.findAllFilesName();
        ArrayList<FileData> filesByDate = newFileSystem.findFilesByDate("02/01/2021");
        ArrayList<FileData> filesByName = newFileSystem.findFilesByName("mySample.txt");
        // for (int i = 0 ; i < filesByDate.size(); i++) {
        //     System.out.println("\n hi" + filesByDate.get(i).name);
        // }
        //System.out.println(filesByDate.size());

        assertEquals(filesByName.size(), 2);
        //assertEquals(filesByDate.size(), 2);
        //assertEquals(testerArray.size(), 3);

    }

    @Test
    public void properRemoval() {
        //String input = new File("src/input.txt").getAbsolutePath();
        String input = "input.txt";

        FileSystem newFileSystem = new FileSystem(input);

        newFileSystem.removeFile("mySample.txt", "02/06/2021");
        
        ArrayList<FileData> filesByDate = newFileSystem.findFilesByDate("02/01/2021");

        for (int i = 0 ; i < filesByDate.size(); i++) {
            System.out.println("\n hi" + filesByDate.get(i).name);
        }

        assertEquals(filesByDate.size(), 2);

    }


    @Test
    public void canYouGetTheseNames() {
        //String input = new File("src/input.txt").getAbsolutePath();
        String input = "input.txt";

        FileSystem newFileSystem = new FileSystem(input);

        ArrayList<String> allNames = newFileSystem.findAllFilesName();
        
    }
}
