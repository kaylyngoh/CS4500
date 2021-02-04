package xjson;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Xjson {
  public static void main(String args[]) throws IOException {
    Scanner scan = new Scanner(System.in);
    JsonArray inputs = new JsonArray();

    while (scan.hasNextLine()) {
      String nextLine = scan.nextLine();
      nextLine = addSpaceForQuotes(nextLine);
      nextLine = addSpaceForSingleSide(nextLine, "{", "}");
      nextLine = addSpaceForSingleSide(nextLine, "[", "]");
      nextLine = addSpaceForBothSide(nextLine, "true");
      nextLine = addSpaceForBothSide(nextLine, "false");
      nextLine = addSpaceForBothSide(nextLine, "null");
      Scanner scan2 = new Scanner(nextLine);
      while (scan2.hasNext()) {
        String next = scan2.next();
        while (!checkOccurrenceEqual(next, '{', '}') ||
            !checkOccurrenceEqual(next, '[', ']')) {
          if (!scan2.hasNext()) {
            throw new IllegalArgumentException("Not well-formed json value");
          } else {
            next = next + scan2.next();
          }
        }
        inputs.add(JsonParser.parseString(next));
      }
    }

    JsonObject obj1 = new JsonObject();
    obj1.addProperty("count", inputs.size());
    obj1.add("seq", inputs);
    System.out.println(obj1);
    JsonArray obj2 = new JsonArray();
    obj2.add(inputs.size());
    for (int i = inputs.size() - 1; i >= 0; i--) {
      obj2.add(inputs.get(i));
    }
    System.out.println(obj2);

  }

  private static String addSpaceForBothSide(String nextLine, String s) {
    return nextLine.replace(s," "+s+" ");
  }

  private static boolean checkOccurrenceEqual(String next, char c, char c1) {
    return getOccurrence(next, c) == getOccurrence(next, c1);
  }
  //https://www.baeldung.com/java-count-chars
  private static int getOccurrence(String someString, char someChar) {
    int count = 0;

    for (int i = 0; i < someString.length(); i++) {
      if (someString.charAt(i) == someChar) {
        count++;
      }
    }
    return count;
  }

  private static String addSpaceForSingleSide(String nextLine, String l, String r) {
    nextLine = nextLine.replace(l," "+l);
    nextLine = nextLine.replace(l,l+" ");
    return nextLine;
  }

  private static String addSpaceForQuotes(String nextLine) {
    int count = 0;
    List<Character> chars = new ArrayList<Character>();
    for (char e : nextLine.toCharArray()) {
      chars.add(e);
    }
    for (int i = 0; i < chars.size(); ++i) {
      if (chars.get(i) == '\"') {
        if (count % 2 == 0 && i != 0) {
          chars.add(i, ' ');
          ++i;
        }else if(count % 2 != 0 && i != chars.size() - 1) {
          chars.add(i + 1, ' ');
          ++i;
        }
        ++count;
      }
    }
    // https://stackoverflow.com/questions/6324826/converting-arraylist-of-characters-to-a-string
    // by Vineet Reynolds
    StringBuilder ans = new StringBuilder(chars.size());
    for (Character ch : chars) {
      ans.append(ch);
    }
    return ans.toString();
  }
}

