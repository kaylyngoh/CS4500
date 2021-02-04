## Self-Evaluation Form for Milestone 2
**The data description of tiles, including an interpretation:**

https://github.ccs.neu.edu/CS4500-F20/cox/blob/7e3194681f78b28c797de619cd9a952dda48edbb/Fish/Common/src/Tile.java#L7-L24

We decided to create a new class Tile to represent a single tile. Each tile will have a fishNum which represents the number of fish on the tile and an attribute penguin to indicate if there is a penguin on the tile (null representing no penguin).

**The data description of boards, include an interpretation:**

https://github.ccs.neu.edu/CS4500-F20/cox/blob/7e3194681f78b28c797de619cd9a952dda48edbb/Fish/Common/src/Board.java#L5-L31

We decided to create a new class Board where the board is represented with a 2D array of tiles. Each position in the 2D array will have either a tile or null which we have chosen to use to represent a hole. In addition, we have decided to index the tiles on the board as below (Example of a board with 2 rows and 4 columns):

![Screen Shot 2020-10-10 at 6 10 53 PM](https://media.github.ccs.neu.edu/user/7627/files/adf25480-0b24-11eb-9ca3-a284b1bbd1bd)

There are 3 constructors to build a board. The first makes a 2D board with row number of rows and col number of columns with each tile having fishNum number of fish. The second makes a 2D board with r number of rows and c number of columns with each tile having a random number of fishes using maxFishNum as an upper bound. The third is used for testing as we will use the seed for the random generator.

**The functionality for removing a tile:**

The removeTile function takes the given position, and sets the given position on the board to be null as we have chosen to represent a hole in the board as null. While reviewing our code again, we realized we have forgotten to check whether the given position on the board is a valid position on the board or not. We will be adding that in for our next submission.

Purpose: 
https://github.ccs.neu.edu/CS4500-F20/cox/blob/7e3194681f78b28c797de619cd9a952dda48edbb/Fish/Common/src/Board.java#L107

Signature: 
https://github.ccs.neu.edu/CS4500-F20/cox/blob/7e3194681f78b28c797de619cd9a952dda48edbb/Fish/Common/src/Board.java#L109

Unit tests: 
https://github.ccs.neu.edu/CS4500-F20/cox/blob/7e3194681f78b28c797de619cd9a952dda48edbb/Fish/Common/test/BoardTest.java#L76-L82

**The functionality for reaching other tiles on the board:**

We have a main function possibleTileToMoveTo and a helper function possibleTilesBeforeHole for determining tiles reachable via straight lines from a given tile. The helper function possibleTilesBeforeHole takes in i (x-axis direction), j (y-axis direction), the row of the current position and column of the current position. The x and y-axis direction will determine the direction of the straight line. For example, i = -1 and j = -1 means the upper left straight line. The helper function uses a counter (starts from 1) to determine the next tile in the given direction. If it does not meet a hole in the next position, the counter will be increased by 1, and the calculation will be continued. Otherwise, the implementation will terminate once null (a hole) is found. All the tiles in before a hole will be added into an ArrayList of Tile, and the list will be returned which represents the possible tiles that the player can move to from a given position.

Purpose: 
https://github.ccs.neu.edu/CS4500-F20/cox/blob/7e3194681f78b28c797de619cd9a952dda48edbb/Fish/Common/src/Board.java#L118

Signature: 
https://github.ccs.neu.edu/CS4500-F20/cox/blob/7e3194681f78b28c797de619cd9a952dda48edbb/Fish/Common/src/Board.java#L120-L121

Unit tests: 
https://github.ccs.neu.edu/CS4500-F20/cox/blob/7e3194681f78b28c797de619cd9a952dda48edbb/Fish/Common/test/BoardTest.java#L127-L141

