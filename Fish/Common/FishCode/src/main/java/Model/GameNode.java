package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Represents a game tree of an entire Fish game, starting from a single game state (a root node).
 * A game state has information about the board, the players, the current player turn, and the status.
 * The root node is represented with having a parent of null.
 * The game tree is traversed by moving states with each child state having the result of a player making a move
 * in the game. The child game tree will have the updated state.
 * If there are no possible actions for the current player to take and the game is not over,
 * the tree will populate with a "skip" child where the only difference between the parent and child is the current player.
 * When the game is over, there will be no possible moves to make and the status in the game state will be switched
 * to GAMESTATUS.OVER and there will be no children states for the game tree.
 */
public class GameNode implements IGameTree {

  private final IFishModel gameState;
  private final IGameTree parent;

  /**
   * Creates a single node for the game tree. Each node represents a single game state and keep tracks
   * of its parent game node which is the game state it originated from. A root node is represented
   * with having a parent of null.
   *
   * @param gameState the current game state.
   * @param parent the parent node it originated from.
   */
  public GameNode(IFishModel gameState, IGameTree parent) {
    this.gameState = gameState;
    this.parent = parent;
  }

  @Override
  public List<GameNode> generatePossibleGameState() {
    List<GameNode> ans = new ArrayList<>();
    if (this.gameState.getGameStatus() == GameStatus.OVER) {
      return ans;
    }
    // add move
    ans.addAll(addPossibleMove());
    // skip a turn
    if ((!this.gameState.hasGameEnded()) && (ans.size() == 0)) {
      ans.add(addPossibleSkip());
    }
    return ans;
  }

  /**
   * Determines all children node that are possible moves from this current state.
   * @return list of Model.GameNode representing a new game state after taking a possible move from this current state.
   */
  private List<GameNode> addPossibleMove(){
    List<GameNode> ans = new ArrayList<>();
    // moving penguins
    for (Penguin penguin : this.gameState.getCurrentTurn().getPenguins()) {
      for (Pos2D pos : this.gameState.getBoard().possibleTileToMoveTo(penguin.getPosition(), this.gameState.getPenguinPositions())) {
        IFishModel copy = this.gameState.copyFishModel();
        copy.movePenguin(penguin, pos);
        GameNode toAdd = new GameNode(copy, this);
        ans.add(toAdd);
      }
    }
    return ans;
  }

  /**
   * Determines the child node from this current state if we skip a player.
   * @return a Model.GameNode representing the game state after skipping a player.
   */
  private GameNode addPossibleSkip() {
    IFishModel copy = this.gameState.copyFishModel();
    copy.skipPlayer();
    return new GameNode(copy, this);
  }

  @Override
  public boolean currentSkipTurn() {
    IFishModel actualNextState = this.generatePossibleGameState().get(0).getGameState();
    IFishModel expectedNextState = this.takeAction(new Skip());

    return actualNextState.equals(expectedNextState);
  }

  @Override
  public IFishModel takeAction(Action action) {
    IFishModel copy = this.gameState.copyFishModel();
    action.go(copy);
    return copy;
  }

  @Override
  public <T> List<T> appliesFunction(Function<IFishModel, T> function) {
    List<T> ans = new ArrayList<>();
    for (GameNode child : this.generatePossibleGameState()) {
      ans.add(function.apply(child.gameState));
    }
    return ans;
  }

  @Override
  public IGameTree getParent() {
    if (this.parent != null) {
      return this.parent;
    } else {
      throw new IllegalArgumentException("This is a starting state, there is no parent");
    }
  }

  @Override
  public IFishModel getGameState() {
    return this.gameState.copyFishModel();
  }

  @Override
  public boolean equals(Object a) {
    if (this == a) {
      return true;
    }
    if (!(a instanceof GameNode)) {
      return false;
    }
    GameNode that = (GameNode) a;
    return (this.gameState.equals(that.gameState) && this.parent.equals(that.parent));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.gameState, this.parent);
  }
}