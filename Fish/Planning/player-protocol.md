# Player Protocol
We used a Java Interface to represent the player-interface API, in which the player component is able to communicate with the referee. We have included the following functionalities in our player-interface: the referee can get the penguin that the player wants move; the referee can get the next position where the player would like to place a penguin or move a penguin; the referee can ask the player whether it would like to receive information about the end of a game and tournament.

*As a reminder, players must follow the following coordinate system when returning positions:*

![Screen Shot 2020-10-10 at 6 10 53 PM](https://media.github.ccs.neu.edu/user/7627/files/adf25480-0b24-11eb-9ca3-a284b1bbd1bd)


### getPositionToPlace
Purpose: To get the position where the player would like to place a penguin.

In order for the game to start, the referee will need to set up the board used to play the game and also place the penguins of the players on the board. The referee may use this method to get the intended position from the player about the position on the board it would like to place its penguins. Players must return a list with only 2 integers inside it. The first representing the x coordinate of the penguin it would like to place and the second represents the y coordinate.

### penguinToMove
Purpose: to get the penguin the player would like to move.

A player may move any of its penguins that are on the board. In order to do so, the referee may use this method to get the position of an existing penguin from the player. Similarly, the players must return a list with only 2 integers inside it. The first representing the x coordinate of the penguin it would like to place and the second represents the y coordinate. If the penguin given is invalid, which means the move is invalid, the referee will need to call this method again to receive another penguin that is valid.

### getNextPositionToMove
Purpose: to get the next position the player would like to move an existing penguin to.

A player may move its penguin across several boundaries in a row but the move must be a straight line up to but not including holes in the board and avatars on tiles. The tile on which the penguin rested gets removed from the board and its fish go into the possession of that penguin’s player. Similarly to getPositionToPlace, the referee may use this method to get the intended position from the player about the position on the board it would like to move its penguin to. Players must return a list with only 2 integers inside it. The first representing the x coordinate of the penguin it would like to place and the second represents the y coordinate. In order for the player to move a penguin, the referee must know which penguin the player would like to move first. Thus, the referee would need to call the method penguinToMove before calling this method to complete a single move. If the penguin or position given is invalid, which means the move is invalid, the referee will need to call both methods: penguinToMove and getNextPositionToMove again to receive another move. If the player would like to skip its turn, it may return the same position of which an existing penguin that it owns is located on. 

### receiveInformationAboutEnd
Purpose: to determine if the player would like to receive information about the end of a game and tournament. 

The referee may determine if the player would like to receive a set of information about the end of a game and tournament in the form of a HashMap<String, String>. The HashMap will have two times the number of winners of key-value pairs. Two key-value pair represents a single winner (key represents the type of information is holds, the value represents the actual information):
* The first pair represents the name of the player that has won the game or tournament
* The second pair represents the the score of the winner

The player must return a boolean, true indicating it would like to receive the above set of information and false indicating it would not like to receive the above set of information.

### getPlayerAge
Purpose: to determine the age of a player.

The referee will need determine which player gets the next turn using a player's age, as players get turns in increasing order of age. The referee may use this in the beginning of the game to create an order or loop through the players using this method to get the age and determine the next turn after each action. The player will need to return an integer to represent its age.

### getPlayerName
Purpose: to determine the name of a player.

In the case where the player would like to receive information about the end of a game and tournament, the referee will inform the player with the name of the winner and score of the winner. Thus, the referee will need to have a method to get the name of a player. The player will need to return a string that represents its name.
