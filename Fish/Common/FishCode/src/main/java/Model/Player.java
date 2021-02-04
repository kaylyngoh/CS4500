package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single player.
 */
public class Player {

  private final int age;
  private final Colour color;
  private int score;
  private List<Penguin> penguins;

  /**
   * Creates a player with the given age and color, as assigned by the referee.
   *
   * @param color the color it is assigned to.
   * @param age the age of the player.
   */
  public Player(int age, Colour color) {
    this.age = age;
    this.color = color;
    this.score = 0;
    this.penguins = new ArrayList<>();
  }

  /**
   * Creates a player with the given age, color (as assigned by the referee), its score and penguins
   * it owns.
   *
   * @param color the color it is assigned to.
   * @param age the age of the player.
   * @param score the current score of the player.
   * @param penguins the list of penguins it owns.
   */
  public Player(int age, Colour color, int score, List<Penguin> penguins) {
    this.age = age;
    this.color = color;
    this.score = score;
    this.penguins = penguins;
  }

  /**
   * Copy constructor of a penguin.
   *
   * @param p the player to copy
   */
  public Player(Player p) {
    this(p.age, p.color, p.score, p.getPenguins());
  }

  /**
   * Getter method for the score of the player
   */
  public int getScore() {
    return this.score;
  }

  /**
   * Getter method for the color a player is assigned to.
   */
  public Colour getColor() {
    return this.color;
  }

  /**
   * Getter method for the age of a player.
   */
  public int getAge() {
    return this.age;
  }


  @Override
  public boolean equals(Object a) {
    if (this == a) {
      return true;
    }
    if (!(a instanceof Player)){
      return false;
    }
    Player that = (Player) a;
    return ((this.color.equals(that.color)) && (this.age == that.age) && (this.score == that.score) && (this.penguins.containsAll(that.penguins))
            && (that.penguins.containsAll(this.penguins)));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.color, this.age, this.score, this.penguins);
  }

  public void addPenguin(Pos2D position) {
    Penguin penguin = new Penguin(this.color, position);
    this.penguins.add(penguin);
  }

  /**
   * Locates and updates the penguin to the updated position after a move.
   *
   * @param penguin the penguin to update.
   * @param position the position the penguin will move to.
   * @throws IllegalArgumentException if the given penguin is not owned by this player.
   */
  public void updatePenguin(Penguin penguin, Pos2D position) {
    for (Penguin p : this.penguins) {
      if (p.equals(penguin)) {
        p.updatePos(position);
        return;
      }
    }
    throw new IllegalArgumentException("Cannot find penguin in player's penguin list");
  }

  /**
   * Getter method to the list of penguin this player owns.
   *
   */
  public List<Penguin> getPenguins() {
    List<Penguin> clone = new ArrayList<Penguin>();
    for (Penguin p : this.penguins) {
      clone.add(new Penguin(p));
    }
    return clone;
  }

  /**
   * Determines if this player does not have any possible moves to make on any of its penguins.
   *
   * @param board the board used for the game.
   * @param penguinPos the list of positions of all the penguins in the game
   * @return true if there are no possible moves to make, false if there are.
   */
  public boolean hasNoMoveAvailable(Board board, List<Pos2D> penguinPos) {
    boolean hasNoMoveAvailable = true;
    for (Penguin p : this.penguins) {
      hasNoMoveAvailable = hasNoMoveAvailable && (board.possibleTileToMoveTo(p.getPosition(), penguinPos).size() == 0);
    }
    return hasNoMoveAvailable;
  }

  /**
   * Adds the given score to the player's current score.
   *
   * @param score the score to add.
   */
  public void addScore(int score) {
    this.score = this.score + score;
  }


}