import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Xtcp {

  public static void main(String args[]) throws IOException {
    int port;
    if (args.length == 1) {
      try {
        port = Integer.parseInt(args[0]);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Only accepts integers as port");
      }
    } else if (args.length == 0) {
      port = 4567;
    } else {
      throw new IllegalArgumentException("Should only have at most 1 argument which represents the port");
    }
    ServerSocket serverSocket = new ServerSocket(port);
    System.out.println("Current Port " + port);
    serverSocket.setSoTimeout(3000);

    Socket server = serverSocket.accept();
    System.out.println("Server is running...");
    DataInputStream in = new DataInputStream(server.getInputStream());
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

    JsonStreamParser parser = new JsonStreamParser(reader);
    JsonArray values = new JsonArray();

    while (parser.hasNext()) {
      values.add(parser.next());
    }

    JsonObject output1 = new JsonObject();
    output1.addProperty("count", values.size());
    output1.add("seq", values);

    JsonArray output2 = new JsonArray();
    output2.add(values.size());
    for (int i = values.size() - 1; i >= 0; i--) {
      output2.add(values.get(i));
    }

   DataOutputStream out = new DataOutputStream(server.getOutputStream());
    String output = "\n" + output1.toString() + "\n" + output2.toString() + "\n";
    out.writeUTF(output);
    server.close();
    System.out.println("Disconnected");
  }
}


