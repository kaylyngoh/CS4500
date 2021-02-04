package View;

import java.util.ArrayList;
import java.util.List;

import Model.Board;
import Model.Colour;
import Model.FishModel;
import Model.Player;
import Model.Pos2D;

/**
 * Used for GUI testing.
 */
public class Fish {

  public static void main(String[] args) {
    Board board = new Board(3, 3, 5);
    board.removeTile(new Pos2D(1, 1));
    List<Player> players = new ArrayList<Player>();
    Player player1 = new Player(1, Colour.RED);
    Player player2 = new Player(2, Colour.BLACK);
    players.add(player1);
    players.add(player2);
    FishModel model = new FishModel(board, players);
    model.placePenguin(new Pos2D(0,0));
    View view = new View(model);
    view.makeVisible();
  }

}
