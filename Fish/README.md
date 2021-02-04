# Fish

This folder will contain all the code needed to run our Fish game, a project for CS4500 Software Development. 

Fish is a board game that is loosely based on "Hey, that's my fish". More information on the game can be found [here](https://www.ccs.neu.edu/home/matthias/4500-f20/fish.html).

## Folder structure:

Common: Folder used to keep all codes for data representations of game logic and game state which includes data representation of tiles, avatars, and boards.

Planning: Folder used to keep all the potential plans for the program and for self-evaluation.

### Milestones

#### Milestone 1
All required submissions: system.pdf and milestones.pdf can be found under Planning.

#### Milestone 2
All required functions for tiles and board can be found in Common/FishCode/src/main/java. These functions are tested in the internal unit tests found in Common/FishCode/src/test/java. The only functionality that is not tested in unit tests is rendering the graphics of tiles. We were able to test that in a main method in Common/FishCode/src/main/java/Fish that renders the desired board.

**Specifications on data representaion of the Board and Tiles**

The board is represented with a 2D array of tiles. Each position in the 2D array will have tile. We have chosen to use a tile with 0 fish on it to represent a hole. In addition, we have decided to index the tiles on the board as below (Example of a board with 2 rows and 4 columns):

![Screen Shot 2020-10-10 at 6 10 53 PM](https://media.github.ccs.neu.edu/user/7627/files/adf25480-0b24-11eb-9ca3-a284b1bbd1bd)

**Specifications on the functionality of determining possible positions to move to**

We have a main function possibleTileToMoveTo that calls two helper methods: addTopDownTiles and addDiagonalTiles. Both methods use -1, 0 and 1 to determine the direction of the straight line, e.g. i = -1 and j = -1 represents the straightline in the direction of upper left of the position. Generally when i = -1, it means up and i = 1 it means down, when j = -1 means left, j = 1 measn down. The method addTopDownTiles adds all the tiles in the same column of the given position that is before a hole by looping through the given direction from the given position. The method addDiagonalTiles adds all the tiles in the diagonal straight line from the position. It will receive, the direction (i and j), the x and y position of next possible position to add to the list and a boolean indicating if the possible position after that is a position beside it or located diagonally. This is due to our chosen coordinate system and you will be able to get a better understanding through the following examples:

When the position is found in an even numbered column, the upper directions will first move diagonally then to the side. The lower directions will first move to the side, then diagonally. The x and y in the method represents the first move, the boolean represents the move after.

![Screen Shot 2020-10-16 at 6 45 43 AM](https://media.github.ccs.neu.edu/user/7627/files/5700db00-0f7b-11eb-87b6-508ba5b25d75)
![Screen Shot 2020-10-16 at 6 45 48 AM](https://media.github.ccs.neu.edu/user/7627/files/59fbcb80-0f7b-11eb-8c0a-aba9c5a2e4fb)

When the position is found in an odd numbered column, the lower upper directions will first move diagonally then to the side. The upper directions will first move to the side, then diagonally. Likewise, the x and y in the method represents the first move, the boolean represents the move after.

![Screen Shot 2020-10-16 at 6 51 13 AM](https://media.github.ccs.neu.edu/user/7627/files/0047d100-0f7c-11eb-9c89-2f56c1e5c761)


#### Milestone 3
All required functions for game state can be found in Common/FishCode/src/main/java/FishModel and are tested in the internal unit tests found in Common/FishCode/src/test/java.

#### Milestone 4
All required functions for game tree can be found in Common/FishCode/src/main/java/GameNode and are tested in the internal unit tests found in Common/FishCode/src/test/java.

#### Milestone 5
All required functions for strategy can be found in Player/Strategy and are tested in the internal unit tests found in Common/FishCode/src/test/java/StrategyTest.

#### Milestone 6
All required functions for the player component can be found in Player/InternalPlayer and are tested in the internal unit tests found in Common/FishCode/src/test/java/InternalPlayerTest. All required functions for the referee component can be found in Admin/Referee and are tested in the internal unit tests found in Common/FishCode/src/test/java/RefereeTest.

#### Milestone 8
All required functions for the tournament manager component can be found in Admin/Manager and are tested in the internal unit tests found in Common/FishCode/src/test/java/ManagerTest.

## How to run test script
Run `./xtest` on the command-line to run all unit tests for this project and get its results.
