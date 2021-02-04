import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;


/**
 * Test Class for Xyes.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class XyesTest {
    private final Xyes xyes = new Xyes();

    /**
     * a helper to repeat a string.
     * @param s the string to repeat
     * @param n the number of times to repeat
     * @return a string
     */
    private String repeatStr(String s, int n){
        // this line is copied from:
        // https://stackoverflow.com/questions/1235179/simple-way-to-repeat-a-string-in-java
        // user102008's answer, edited by Jin Kwon.
        return new String(new char[n]).replace("\0", s);
    }
    @Test
    public void A_testLimited1() {
        String[] arguments = {"-limit", "Hi", "Prof"};
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        xyes.main(arguments);
        assertEquals(outContent.toString(),
                repeatStr("Hi Prof\n",20));
    }

    @Test
    public void A_testLimited2() {
        String[] arguments = {"-limit"};
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setOut(new PrintStream(outContent));
        xyes.main(arguments);
        assertEquals(outContent.toString(), repeatStr("\n",20));
    }

    @Test
    public void B_testInfinite1() throws InterruptedException {
        String testString = "testString";
        String[] arguments = {testString};
        ByteArrayOutputStream outContent1 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent1));
        Thread thread = new Thread(() -> xyes.main(arguments));
        thread.start();
        Thread.sleep(10);
        String output1 = outContent1.toString();
        Thread.sleep(10);
        String output2 = outContent1.toString();
        thread.stop();
        assertTrue( output2.length() >= output1.length());
        assertTrue(output1.startsWith("testString"));
    }

    @Test
    public void B_testInfinite2() throws InterruptedException{
        String[] arguments = {};
        ByteArrayOutputStream outContent2 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent2));
        Thread thread = new Thread(() -> xyes.main(arguments));
        thread.start();
        Thread.sleep(10);
        String output1 = outContent2.toString().replace("testString\n","");
        Thread.sleep(10);
        String output2 = outContent2.toString().replace("testString\n","");
        thread.stop();
        assertTrue( output2.length() >= output1.length());
        assertTrue(output1.startsWith("Hello World"));
    }
}
