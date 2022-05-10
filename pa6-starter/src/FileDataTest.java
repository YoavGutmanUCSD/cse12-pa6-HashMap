import static org.junit.Assert.*;

import org.junit.*;

public class FileDataTest {

    @Test
    public void testerOne() {
        FileData obj = new FileData("IAmAFile", "Path", "Today");
        System.out.print(obj);
        assertEquals(1,2);
    }
}
