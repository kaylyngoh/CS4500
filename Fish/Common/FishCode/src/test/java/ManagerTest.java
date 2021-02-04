import Admin.Manager;
import Model.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import Player.IPlayer;
import Player.InternalPlayer;
import Player.NonAcceptingPlayer;
import Player.DroppedOutPlayer;
import Admin.Referee;
import Player.Strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ManagerTest {

    private List<IPlayer> players1;
    private List<IPlayer> players2;
    private List<IPlayer> players3;
    private List<IPlayer> players4;
    private List<IPlayer> players5;
    private List<IPlayer> players6;

    @Before
    public void init() {
        this.players1 = new ArrayList<>();
        this.players2 = new ArrayList<>();
        this.players3 = new ArrayList<>();
        this.players4 = new ArrayList<>();
        this.players5 = new ArrayList<>();
        this.players6 = new ArrayList<>();

        IPlayer p1 = new InternalPlayer("Alice", 1, new Strategy(), 1);
        IPlayer p2 = new InternalPlayer("Bob", 2, new Strategy(), 1);
        IPlayer p3 = new InternalPlayer("Sam", 3, new Strategy(), 2);
        IPlayer p4 = new InternalPlayer("Tina", 4, new Strategy(), 1);
        IPlayer p5 = new InternalPlayer("Julia", 5, new Strategy(), 2);
        IPlayer p6 = new InternalPlayer("Mike", 6, new Strategy(), 1);
        IPlayer p7 = new InternalPlayer("Laura", 9, new Strategy(), 2);
        IPlayer p8 = new InternalPlayer("Peter", 10, new Strategy(), 1);

        IPlayer p9 = new NonAcceptingPlayer("Jack", 12, new Strategy(), 1);
        IPlayer p10 = new NonAcceptingPlayer("Jill", 13, new Strategy(), 1);
        IPlayer p11 = new NonAcceptingPlayer("John", 11, new Strategy(), 1);

        IPlayer p12 = new DroppedOutPlayer("Ed", 11, new Strategy(), 1);
        IPlayer p13 = new DroppedOutPlayer("Joseph", 15, new Strategy(), 1);

        this.players1.add(p1);

        this.players2.add(p1);
        this.players2.add(p2);
        this.players2.add(p3);
        this.players2.add(p4);

        this.players3.add(p4);
        this.players3.add(p5);
        this.players3.add(p6);
        this.players3.add(p7);
        this.players3.add(p8);

        this.players4.add(p1);
        this.players4.add(p2);
        this.players4.add(p3);
        this.players4.add(p4);
        this.players4.add(p5);
        this.players4.add(p6);
        this.players4.add(p7);
        this.players4.add(p8);

        this.players5.add(p9);
        this.players5.add(p10);
        this.players5.add(p11);

        this.players6.add(p12);
        this.players6.add(p13);
    }

    @Test
    public void testTournamentStart() {
        Manager testManager = new Manager(this.players2, true);

        testManager.informTournamentStart();

        for (IPlayer player : testManager.getPlayers()) {
            assertEquals(GameStatus.PLAYING, player.getGameStatus());
        }
    }

    @Test (expected = IllegalArgumentException.class)
    public void testManagerOnePlayer() {
        Manager testManager = new Manager(this.players1);
    }

    @Test
    public void testAllocatePlayersToGamesNoBacktrack() {
        Manager testManager = new Manager(this.players2, true);

        testManager.informTournamentStart();
        testManager.allocatePlayersToGames();
        assertEquals(1, testManager.getReferees().size());
        assertEquals(4, testManager.getReferees().get(0).getPlayersInThisGame().entrySet().size());
    }

    @Test
    public void testAllocatePlayersToGamesBacktrack() {
        Manager testManager = new Manager(this.players3, true);

        testManager.informTournamentStart();
        testManager.allocatePlayersToGames();
        assertEquals(2, testManager.getReferees().size());
        assertEquals(3, testManager.getReferees().get(0).getPlayersInThisGame().entrySet().size());
        assertEquals(2, testManager.getReferees().get(1).getPlayersInThisGame().entrySet().size());
    }

    @Test
    public void testNonAcceptingPlayer() {
        Manager testManager = new Manager(this.players5);

        for (IPlayer player : testManager.getPlayers()) {
            assertFalse(player.getIsWinner());
        }
    }

    @Test
    public void testDroppedOutPlayer() {
        Manager testManager = new Manager(this.players6);

        assertEquals(0, testManager.getPlayersInRunning().size());
        assertEquals(0, testManager.getPlayers().size());
    }

    @Test
    public void testRunCompleteTournament() {
        Manager testManager = new Manager(this.players4);

        int winningPlayers = 0;

        for (IPlayer player : testManager.getPlayers()) {
            if (player.getIsWinner()) {
                winningPlayers++;
            }
        }

        assertTrue(winningPlayers >= 1);
    }
}
