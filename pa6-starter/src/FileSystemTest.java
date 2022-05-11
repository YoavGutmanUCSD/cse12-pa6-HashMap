import static org.junit.Assert.*;

import org.junit.*;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileSystemTest {

    @Test
    public void properAdding() {
        String input = "input.txt";

        FileSystem newFileSystem = new FileSystem(input);

        ArrayList<FileData> filesByDate = newFileSystem.findFilesByDate("02/06/2021");
        
        assertEquals(filesByDate.size(), 2);

    }
}
