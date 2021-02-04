# Game State
[x] Place penguin does not check if there is a penguin already on the tile

[x] Unit test for the functionality that checks if no move is possible was missing.

[x] checkGameStatus should be removed from placePenguin and moved to startGame instead since it may end the game after placing a single penguin in a tile that has no possible moves

[x] Test not accurate for turn-taking functionality as taking a turn can include moving a penguin and switching to the next player

[x] Insufficient coverage of unit tests for avatar placement functionality

[x] Weak interpretation or data definition for game states and purpose statement for creating a game state (should include relationship of all the components of a game state)

[x] Should add more clarity for movePenguin to show that after changing positions of penguins, we remove that previous position and not the new position 

# Board
[x] createTileWithOneOrRandomFishNum does not guarantee a minimum of one fish tiles and should be changed

[x] Penguin locations should not be stored in tiles

[x] Should not use null as a hole in the board

[x] Insufficient tests for possibleTileToMoveTo (should check the actual positions, not just the size of the output list)

[x] Insufficient code clarity on possibleTileToMoveTo

[x] Board interpretation was insufficient, missing interpretation of how 2D row and column position represent hexagon coordinates

# Referee
[x] Unit tests for cheating players 

[ ] Unit tests for failing players

[ ] Determine how referees would know when a player has no moves and needs to be skipped

# Strategy
[x] Unit tests for takeAction does not cover at least two different depths 

[x] Purpose statement of turn action does not specify what happens when the current player does not have valid moves

# Game Tree
[x] Data definition/interpretation of the game tree doesn't mention how "skip" transitions are dealt with.

# Player
[x] The InternalPlayer does not need receiveInfo and winnerInfo since the AI would not need to know who won in the game just if the game has ended

# Pos2D
[x] Check for validity of Pos2D should not be in the Pos2D, but rather in the board.

# Integration Tests
[x] xstate: The ordering of the players in the output should be ordered by turn where the current player is the first in the list



