import static org.junit.Assert.*;

import org.junit.*;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileSystemTest {

    @Test
    public void properAdding() {
        String input = "C:/Users/anima/OneDrive/Documents/GitHub/cse12-pa6-HashMap/pa6-starter/src/input.txt";

        FileSystem newFileSystem = new FileSystem(input);

        ArrayList<FileData> filesByDate = newFileSystem.findFilesByDate("02/06/2021");
        
        assertEquals(filesByDate.size(), 1);

    }

    @Test
    public void properRemoval() {
        String input = "input.txt";

        FileSystem newFileSystem = new FileSystem(input);

        newFileSystem.removeFile("mySample.txt", "02/06/2021");
        
        ArrayList<FileData> filesByDate = newFileSystem.findFilesByDate("02/06/2021");

        assertEquals(filesByDate.size(), 2);

    }


    @Test
    public void canYouGetTheseNames() {
        String input = "input.txt";

        FileSystem newFileSystem = new FileSystem(input);

        ArrayList<String> allNames = newFileSystem.findAllFilesName();
        
    }
}
