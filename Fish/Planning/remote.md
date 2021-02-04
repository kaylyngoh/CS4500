### Starting tournament
At the start of the tournament, the clients will need to sign in to the server through TCP with their name and age. The message that is expected by the server during sign up is in the form of a JSON array: [String, int], where the string represents the unique name of the player and int represents the age of the player. The server will not send any acknowledgement after the client signs up. The connection between clients and server will terminate if the client violates any protocols as described below:
* The player tries to cheat, violating the rules of Fish by giving incorrect actions
* The player fails to provide a valid action before time out

![Screen Shot 2020-11-20 at 12 32 43 AM](https://media.github.ccs.neu.edu/user/7627/files/ebed7b80-2ac7-11eb-9729-8bbecb14a995)

### During the tournament
The remote proxy players will live on the server side and will pretend to be a “local” player to interact with the tournament manager and referee to compete in our Fish tournament.
Clients would need to build a remote proxy to pretend to be components on the server side to allow interactions with the tournament manager and referee. 

![Screen Shot 2020-11-20 at 12 33 22 AM](https://media.github.ccs.neu.edu/user/7627/files/0293d280-2ac8-11eb-95c0-80ea7b315a18)

During the tournament, the server will send a Place Penguin message to clients to request for a placement for one of their penguins. Once all penguins of every client are placed, the server will then send a Move Penguin message to request for the action the client would like to take. The server will send messages to a client depending on whose turn it is in the game. The turn will alternate according to the tournament policy.

The messages will be as follows:

Place Penguin message will be in the form of a JSON array with two fields:
First is a string “place-penguin” and the second is State, a JSON object representing the state of the game at the moment. The expected Placement message from the client will be a Position.

Move Penguin message will be in the form of a JSON array with two fields:
First is a string “move-penguin” and the second is State, a JSON object representing the state of the game at the moment. The expected Action message from the client will be an Action.

State is {“players”: Player*, “board”: Board}

Player* is a JSON array of Player: Player*: [Player, … , Player], the first player in the list is the current player to make a turn.

Board is a JSON array of JSON arrays where each element is either 0 or a number between 1 and 5. 0 represents a hole in the board and all other numbers represents the number of fishes on the tile. Each JSON array in the JSON array represents a single row on the board. 

A Player is a JSON Object with three fields: Player: {“color”: Color, “score”: Natural, “places”: [Position, … Position]}. The available colors are "red", "white", "brown", "black". 

A Position is a JSON array with two natural numbers representing the board row and board column respectively.

An Action is a JSON array of Positions: [Position, Position], first position indicating the penguin the player would like to move and the second is the position the player would like to move the penguin to.

### End of the tournament

![Screen Shot 2020-11-20 at 12 34 36 AM](https://media.github.ccs.neu.edu/user/7627/files/2eaf5380-2ac8-11eb-8209-afa115cd63ce)

At the end of the tournament, the server will send a Result message to all remaining players to inform them about the results of the tournament. The Result message will be in the form of a JSON array of String, representing the unique names of the winners. Once they have received this message, the clients are free to close the TCP connection.
