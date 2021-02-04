package Player;

public class DroppedOutPlayer extends InternalPlayer {
    /**
     * Creates a single internal player to play a game of View.Fish.
     *
     * @param name     the name of the player
     * @param age      the age of the player
     * @param strategy the chosen strategy it would like to implement
     * @param depth    the number of turns it would like to look ahead using the strategy
     */
    public DroppedOutPlayer(String name, int age, IStrategy strategy, int depth) {
        super(name, age, strategy, depth);
    }

    @Override
    public boolean acceptWin() {
        throw new RuntimeException("Runtime error.");
    }
}
