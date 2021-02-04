package Player;

import Model.*;

/**
 * Represents a single internal player that would play a View.Fish Game. The player will utilize its chosen
 * strategy to determine the action it will like to take, using the number of turns it would like to look ahead.
 */
public class InternalPlayer implements IPlayer {

  private String name;
  private int age;
  private IStrategy strategy;
  private int depth;
  private GameStatus gameStatus;
  private boolean isWinner;

  /**
   * Creates a single internal player to play a game of View.Fish.
   * @param name the name of the player
   * @param age  the age of the player
   * @param strategy the chosen strategy it would like to implement
   * @param depth the number of turns it would like to look ahead using the strategy
   */
  public InternalPlayer(String name, int age, IStrategy strategy, int depth) {
    this.name = name;
    this.age = age;
    this.strategy = strategy;
    this.depth = depth;
    this.gameStatus = GameStatus.PENDING;
    this.isWinner = false;
  }

  @Override
  public Action getMove(IFishModel state) {
    return this.strategy.getPlayerAction(state, this.depth);
  }

  @Override
  public Pos2D getPositionToPlace(IFishModel state) {
    return this.strategy.getPenguinPlacement(state);
  }

  @Override
  public String getPlayerName() {
    return this.name;
  }

  @Override
  public int getPlayerAge() {
    return this.age;
  }

  @Override
  public void startGame() {
    this.gameStatus = GameStatus.PLAYING;
  }

  @Override
  public void endGame() {
    this.gameStatus = GameStatus.OVER;
  }

  @Override
  public GameStatus getGameStatus(){
    return this.gameStatus;
  }

  @Override
  public boolean getIsWinner() {
    return this.isWinner;
  }

  @Override
  public boolean acceptWin() {
    this.isWinner = true;
    return true;
  }

}