Penguin locations were originally stored in tiles and in the board but it has since moved into the game state data representation (FishModel).

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/f38fd798ecdc9ecaa5c8ef374d203f9e9ea102a7/Fish/Common/FishCode/src/main/java/Tile.java#L7-L11

Null should not be used to represent a hole in the board but it has since been changed by using an individual class named Hole. The board is now
a 2D array of Piece that is either a Tile or Hole.

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/d06b5b292bd10b181ea335717cef098542165860/Fish/Common/FishCode/src/main/java/Hole.java

Insufficient tests for possibleTileToMoveTo (should check the actual positions, not just the size of the output list).

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/f38fd798ecdc9ecaa5c8ef374d203f9e9ea102a7/Fish/Common/FishCode/src/test/java/BoardTest.java#L141-L173

Insufficient code clarity on possibleTileToMoveTo. Since this method is rather complicated due to our coordinate system, we have added a detailed explaination in our README.

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/f38fd798ecdc9ecaa5c8ef374d203f9e9ea102a7/Fish/README.md

Board interpretation was insufficient, missing interpretation of how 2D row and column position represent hexagon coordinates. We have now added pictures of our 2D coordinate system in our README.

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/f38fd798ecdc9ecaa5c8ef374d203f9e9ea102a7/Fish/README.md

Unit test for the functionality that checks if no move is possible was missing and unit tests has since been added for this functionality.

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/d06b5b292bd10b181ea335717cef098542165860/Fish/Common/FishCode/src/test/java/FishModelTest.java#L183-L196

Test not accurate for turn-taking functionality as taking a turn can include moving a penguin and switching to the next player. We have added unit test to check if after making a move, the current player updates and if we skip the player, the current player changes.

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/d06b5b292bd10b181ea335717cef098542165860/Fish/Common/FishCode/src/test/java/FishModelTest.java#L283-L289

Insufficient coverage of unit tests for avatar placement functionality. We have added additional unit tests for the avatar placement functionality.

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/bb653d81aa235cc4297acba7ac153d5264aaab33/Fish/Common/FishCode/src/test/java/FishModelTest.java#L123-L165

Weak interpretation or data definition for game states and purpose statement for creating a game state. Game states are now defined as "A game state keeps track of the current board used, the players that are in the game, the current player who has the turnand the status of the game (either Pending, Playing or Over)."

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/09ddcd0033d24239c1820832d53244463b1d5be1/Fish/Common/FishCode/src/main/java/Model/FishModel.java#L7-L13

Should add more clarity for movePenguin to show that after changing positions of penguins, we remove the previous position and not the new position. We have now added a internal variable of the old position to make it more readable.

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/a60fdbba5e7c13c9a72afb167a9e7145bac0a375/Fish/Common/FishCode/src/main/java/Model/FishModel.java#L131-L150

Unit tests for takeAction does not cover at least two different depths. We have added 3 additional unit tests to cover another depth.

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/a60fdbba5e7c13c9a72afb167a9e7145bac0a375/Fish/Common/FishCode/src/test/java/StrategyTest.java#L234-L254

The InternalPlayer does not need receiveInfo and winnerInfo since the AI would not need to know who won in the game just if the game has ended. These fields have been taken out.

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/10bd250a6f0ecf17425a3d66952271423be1e779/Fish/Player/InternalPlayer.java#L5-L15

Purpose statement of turn action does not specify what happens when the current player does not have valid moves.

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/09ddcd0033d24239c1820832d53244463b1d5be1/Fish/Player/Strategy.java#L47

Data definition/interpretation of the game tree doesn't mention how "skip" transitions are dealt with. The added interpretation is now highlighted in the link.

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/09ddcd0033d24239c1820832d53244463b1d5be1/Fish/Common/FishCode/src/main/java/Model/GameNode.java#L8-L18

Add unit tests for cheating players.

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/d5556f545a7eaf378a94a6bbd837986ff1e3e5c0/Fish/Common/FishCode/src/test/java/RefereeTest.java#L256-L322

Checking of legal Pos2Ds should be in the board.

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/10bd250a6f0ecf17425a3d66952271423be1e779/Fish/Common/FishCode/src/main/java/Model/Board.java#L361-L370

Place penguin does not check if there is a penguin already on the given position. We have added a check method before placing the penguin on that tile to check if the given position is found in our list of penguin positions (all players) on the board.

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/10bd250a6f0ecf17425a3d66952271423be1e779/Fish/Common/FishCode/src/main/java/Model/Board.java#L333-L359

Place Penguin would end the game if the first penguin was placed in a tile that has no possible moves. We have removed checkGameStatus which checks if the game has ended to startGame (officially starts the game) instead to see if after the placement of all penguins, there are any possible moves to make.

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/a60fdbba5e7c13c9a72afb167a9e7145bac0a375/Fish/Common/FishCode/src/main/java/Model/FishModel.java#L215-L219
