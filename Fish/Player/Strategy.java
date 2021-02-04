package Player;

import Model.*;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the strategy component that players can use to determine their next action for a fish game.
 * This component takes care of two decisions: penguin placement and a choice of action for the player
 * whose turn it is.
 */
public class Strategy implements IStrategy {

  /**
   * Determines the next available free spot to place a penguin following a zig zag pattern that
   * starts from the top left corner. This strategy searches for the next available position from
   * left to right in each row and moves down to the next row when one is filled up.
   * Assumes that referee has set up a board large enough to fit all penguins.
   * @param currentState currentState the current state of the game
   * @return  the next available free spot
   * @throws IllegalArgumentException if all tiles in the board are filled with penguins which should
   *                                  not happen as we assume that referee will set up a board large enough.
   */
  public Pos2D getPenguinPlacement(IFishModel currentState) {
    List<Pos2D> penguinPositions = currentState.getPenguinPositions();
    for (int i = 0; i < currentState.getBoard().get2DTiles().length * 2; i++) {
      int j = (i % 2 == 0) ? 0 : 1;
      for (; j < currentState.getBoard().get2DTiles()[0].length ; j+=2) {
        if (!penguinPositions.contains(new Pos2D(j, Math.round(i / 2)))
                && !currentState.getBoard().get2DTiles()[i / 2][j].equals(new Hole())) {
          return new Pos2D(j, Math.round(i / 2));
        }
      }
    }
    throw new IllegalArgumentException("Should not be here. The referee should set up a big enough board.");
  }

  /**
   * Determines the action the current player can take that realizes the best gain after looking
   * ahead N > 0 turns in the game tree.
   *
   * Best gain after N turns is the highest score a player can make after playing N turns assuming
   * all opponents picks the action that minimizes the score of the player.
   *
   * This function should only be called when the player has valid moves to make.
   *
   * @param currState the current state of the game
   * @param depth represents the N turns the player wants to look ahead
   * @return the action that realizes the best gain after looking ahead N turns
   */
  public Action getPlayerAction(IFishModel currState, int depth) {
    GameNode currNode = new GameNode(currState, null);
    AbstractMap.SimpleEntry<GameNode, Integer> miniMaxGain = miniMax(currNode, depth, currState.getCurrentTurn().getColor());
    GameNode nextNode = miniMaxGain.getKey();
    if (nextNode != null) {
      return getAction(currNode, nextNode);
    } else {
      throw new IllegalArgumentException("Game has ended.");
    }
  }

  /**
   * Builds the future game tree and determines the next immediate game state and score resulting
   * from taking the action that provides the best gain after looking ahead N > 0 turns in the
   * game tree.
   *
   * @param currNode the node representing the current state of the game in the tree
   * @param depth represents the N turns the player wants to look ahead
   * @param colour the player (maximizer) colour
   * @return the immediate child node from the current state that will provide the best gain and
   * the score of the player that is the best gain at N turns.
   */
  private AbstractMap.SimpleEntry<GameNode, Integer> miniMax(GameNode currNode, int depth, Colour colour) {
    // base case
    boolean isTerminalNode = currNode.generatePossibleGameState().isEmpty();
    if (depth == 0 || isTerminalNode) {
      return new AbstractMap.SimpleEntry<>(null, currNode.getGameState().getScoreOfPlayer(colour));
      // maximizer
    } else if (currNode.getGameState().getCurrentTurn().getColor().equals(colour)) {
      AbstractMap.SimpleEntry<GameNode, Integer> maximizer = new AbstractMap.SimpleEntry<>(null, -1);
      for (GameNode child : currNode.generatePossibleGameState()) {
        if (maximizer.getValue() < miniMax(child, depth - 1, colour).getValue()) {
          maximizer = new AbstractMap.SimpleEntry<>(child, miniMax(child, depth - 1, colour).getValue());
        } else if (maximizer.getValue() == miniMax(child, depth - 1, colour).getValue()) {
          maximizer = new AbstractMap.SimpleEntry<>(
                  new GameNode(currNode.takeAction(tieBreaker(getPossibleActions
                          (currNode, child, maximizer.getKey()))), currNode), maximizer.getValue());
        }
      }
      return maximizer;
      // minimizer
    } else {
      AbstractMap.SimpleEntry<GameNode, Integer> minimizer = new AbstractMap.SimpleEntry<>(null, Integer.MAX_VALUE);
      for (GameNode child : currNode.generatePossibleGameState()) {
        if (minimizer.getValue() > miniMax(child, depth, colour).getValue()) {
          minimizer = new AbstractMap.SimpleEntry<>(child, miniMax(child, depth, colour).getValue());
        }
      }
      return minimizer;
    }
  }

  /**
   * Determines a list of two actions. Each action represents a move that will be determined from looking
   * at the penguins of the current player in the current state and a child state.
   *
   * @param currNode the node representing the current state of the game in the tree
   * @param child1 a single child state
   * @param child2 the other child state
   * @return a list of actions (currNode -> child1 state and currNode -> child2 state)
   */
  private List<Action> getPossibleActions(GameNode currNode, GameNode child1, GameNode child2) {
    Action original = getAction(currNode, child2);
    Action other = getAction(currNode, child1);
    List<Action> ans = new ArrayList<>();
    ans.add(original);
    ans.add(other);
    return ans;
  }

  /**
   * Finds the action that represents a move that will happened between current state and the provided
   * child state. Determined from looking at the penguins of the current player in the current state
   * and a child state.
   *
   * @param currNode the node representing the current state of the game in the tree
   * @param nextNode the child node from current node
   * @return an action that represents the move that happened between current state to child state
   */
  private Action getAction(GameNode currNode, GameNode nextNode) {
    for (Penguin p : currNode.getGameState().getCurrentTurn().getPenguins()) {
      if (!nextNode.getGameState().getPenguins().contains(p)) {
        for (Penguin newP : nextNode.getGameState().getPenguins()) {
          if (!currNode.getGameState().getPenguins().contains(newP)) {
            return new Move(p, newP.getPosition());
          }
        }
      }
    }
    return new Skip();
  }

  /**
   * Tie breaker that looks for the top-most row of the "from" position, the left-most column of the
   * "from" position, the top-most row of the "to" position, and the left-most column of the "to"
   * position, in exactly this order.
   *
   * @param possibleMoves a list of possible actions (must be a move) that has the same best gain
   * @return a single action that is chosen using the tie breaker
   */
  public Action tieBreaker(List<Action> possibleMoves) {
    Action ans = possibleMoves.get(0);
    for (int i = 1; i < possibleMoves.size(); i++) {
      Penguin fromPenguin = possibleMoves.get(i).getPenguin();
      Pos2D from = fromPenguin.getPosition();
      Pos2D to = possibleMoves.get(i).getAfterPosition();
      Pos2D theirFrom = convertPos2DToCommon(from);
      Pos2D theirTo = convertPos2DToCommon(to);
      Pos2D theirAnsFrom = convertPos2DToCommon(ans.getPenguin().getPosition());
      Pos2D theirAnsTo = convertPos2DToCommon(ans.getAfterPosition());
      if (theirAnsFrom.getY() > theirFrom.getY()) {
        ans = possibleMoves.get(i);
      } else if ((theirAnsFrom.getY() == theirFrom.getY()) && (theirAnsFrom.getX() > theirFrom.getX())) {
        ans = possibleMoves.get(i);
      } else if ((theirAnsFrom.getY() == theirFrom.getY()) && (theirAnsFrom.getX() == theirFrom.getX())
              && (theirAnsTo.getY() > theirTo.getY())) {
        ans = possibleMoves.get(i);
      } else if ((theirAnsFrom.getY() == theirFrom.getY()) && (theirAnsFrom.getX() == theirFrom.getX())
              && (theirAnsTo.getY() == theirTo.getY()) && (theirAnsTo.getX() > theirTo.getX())) {
        ans = possibleMoves.get(i);
      }
    }
    return ans;
  }

  /**
   * Converts our coordinate system to the common coordinate system that is agreed in the assignment.
   *
   * @param pos the position to convert
   * @return the converted position
   */
  private Pos2D convertPos2DToCommon(Pos2D pos) {
    int x = pos.getX();
    int y = pos.getY();
    if (x % 2 == 0) {
      return new Pos2D(x / 2, y * 2);
    } else {
      return new Pos2D(x / 2, y * 2 + 1);
    }
  }
}