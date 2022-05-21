import static org.junit.Assert.*;

import org.junit.*;

public class FileDataTest {

    @Test
    public void toStringTester() {
        FileData obj = new FileData("IAmAFile", "Path", "Today");
        //System.out.print(obj);
        assertEquals("{Name: " + "IAmAFile" + ", Directory: " + "Path" +", Modified Date: "+ "Today" +"}", obj.toString());
    }

    @Test
    public void properCreation() {
        FileData obj = new FileData("IAmAFile", "Path", "Today");
        System.out.print(obj);
        assertEquals(obj.name, "IAmAFile");
    }
}
