import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

/**
 * Test Class for Xjson.
 */
public class XjsonTest {

  @Test
  public void testXjson() throws IOException {

    JsonArray array1 = (JsonArray) JsonParser.parseReader(new FileReader("16-in.json"));
    JsonArray array2 = (JsonArray) JsonParser.parseReader(new FileReader("16-out.json"));

    for (int i = 0; i < array1.size(); i++) {

      JsonObject obj = (JsonObject) array1.get(i);
      JsonObject obj2 = (JsonObject) array2.get(i);
      Iterator<String> keys = (Iterator<String>) obj.keySet();

      while (keys.hasNext()) {
        String key = keys.next();
        if (obj.get(key) instanceof JsonObject) {
          System.setIn(new ByteArrayInputStream(obj.get(key).toString().getBytes()));
        }
      }

      Xjson test = new Xjson();
      test.main(null);

      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outContent));


      String[] outputs = outContent.toString().split("\n");
      JsonObject obj3 = new JsonObject();
      obj3.add("one", JsonParser.parseString(outputs[0]));
      obj3.add("two", JsonParser.parseString(outputs[1]));

      assertEquals(obj2, obj3);
    }
  }

}
