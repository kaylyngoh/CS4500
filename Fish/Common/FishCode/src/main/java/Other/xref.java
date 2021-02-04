package Other;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Admin.*;
import Model.Pos2D;
import Player.*;

public class xref {

  public static void main(String[] args) {
    // reads the input stream and parses it into two json arrays: board and position
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    JsonObject input = (JsonObject) JsonParser.parseReader(reader);
    int row = input.getAsJsonPrimitive("row").getAsInt();
    int column = input.getAsJsonPrimitive("column").getAsInt();
    int fish = input.getAsJsonPrimitive("fish").getAsInt();
    JsonArray players = input.getAsJsonArray("players");
    List<IPlayer> ourIPlayers = getOurIPlayers(players);
    int ourCol = column * 2;
    int ourRow = (int) Math.ceil((double)row / 2);
    List<Pos2D> ourHoles = getOurHoles(row, ourRow, ourCol);
    IReferee referee = new Referee(ourIPlayers, ourRow, ourCol, fish, ourHoles);
    List<IPlayer> ourWinners = referee.getWinners();
    generateOutput(ourWinners);
    System.exit(0);
  }

  private static List<Pos2D> getOurHoles(int row, int ourRow, int ourCol) {
    if (row % 2 == 0) {
      return new ArrayList<>();
    } else {
      List<Pos2D> ans = new ArrayList<>();
      for (int i = 1; i < ourCol; i += 2) {
        ans.add(new Pos2D(i, ourRow - 1));
      }
      return ans;
    }
  }

  private static void generateOutput(List<IPlayer> ourWinners) {
    List<String> names = new ArrayList<>();
    for (IPlayer player : ourWinners) {
      names.add(player.getPlayerName());
    }
    Collections.sort(names);
    JsonArray output = new JsonArray();
    for (String name : names) {
      output.add(name);
    }
    System.out.print(output);
  }

  private static List<IPlayer> getOurIPlayers(JsonArray players) {
    List<IPlayer> ans = new ArrayList<>();
    int position = 0;
    for (JsonElement player : players) {
      JsonArray p = (JsonArray) player;
      String name = p.get(0).getAsString();
      int depth = p.get(1).getAsInt();
      IPlayer toAdd = new InternalPlayer(name, position, new Strategy(), depth);
      ans.add(toAdd);
      position += 1;
    }
    return ans;
  }

}
