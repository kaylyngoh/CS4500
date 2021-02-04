package Model;

import java.util.List;
import java.util.function.Function;

/**
 * Interface to represent a game tree of the View.Fish game.
 */
public interface IGameTree {

  /**
   * Generates the next layer of the game tree from the current game state.
   *
   * @return a list of game nodes, representing all the possible actions the current player can make.
   */
  List<GameNode> generatePossibleGameState();

  /**
   * Determines if the given action on the current game state is illegal or not.
   *
   * @return the game state after this action has been taken on the current state.
   * @throws IllegalArgumentException if the action is illegal.
   */
  IFishModel takeAction(Action action);

  /**
   * Applies the given function on all the children nodes from this current node (all the possible
   * game states from the current game state).
   *
   * @return a list of T after applying the function on each children state.
   */
  <T> List<T> appliesFunction(Function<IFishModel, T> function);

  /**
   * Getter method to get the parent node, the state this current node originated from.
   *
   * @return the parent node of current node.
   */
  IGameTree getParent();

  /**
   * Getter method to get the current game state from this node.
   *
   * @return the current game state.
   */
  IFishModel getGameState();

  /**
   * Checks if the only move left for the player is to skip their turn.
   * Only gets called when there is one child node.
   * @return boolean representing whether or not their action is a skip.
   */
  boolean currentSkipTurn();

}