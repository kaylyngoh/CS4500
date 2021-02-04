## Tournament Manager
The tournament manager’s main responsibility would be to run a tournament of Fish. The tournament will be run as a single-elimination type tournament, where losers of each game/match-up are immediately eliminated from the tournament. The winners of each match-up will go against other winners from other match-ups. Each winner will play another round of Fish game until the final match-up, where the winner of this final game becomes the tournament champion. 

The tournament manager’s responsibilities include: signing up players for tournaments, allocating players to games, creating referees to run games, and collecting tournament statistics while also informing tournament observers of on-going actions.

### Sign up players for games

After players have signed up for the tournament (communication layer) through the server, the tournament manager will need to retrieve certain information such as the player’s name, age etc. to create the player component that will be passed to the referee. 

### Allocate players to games

The tournament manager will need to allocate players by randomly choosing players from the 1000s signed up players it interacts with. The tournament manager should try to create as many 4-player games as possible and if the last game to be created does not have enough players (minimum of 2 players) it will create either 2- or 3-player games using the last two games created from the pool of players. It will then need to sort the allocated players by age and pass it to the referee to run the game. It will need to inform the player that it has been assigned to this game for this round.

### Create referees to run games

The tournament manager will need to create a referee to run a single game. For each game, it will need to create a referee by passing in the row-by-column dimensions of the board to be used for the game and the list of players it has assigned to this game. The row-by-column dimensions should produce a board with a minimum of 6-N (N being the number of players) times N number of tiles and a maximum of 25 tiles in order to fit in all the penguins from all the players. Other than this specification, the tournament manager is able to randomize the number to pass as the row-by-column dimensions.

### Collect statistics from referees and run tournament statistics for future games

Once a game has ended, the tournament manager is able to collect statistics from the referee by calling getWinner(), getCheaters(), getWinningScore() and getGameResult(). getWinner and getCheaters will output a list of players, getWinningScore will output an int indicating the score of the winner(s) and getGameResult() will return a HashMap of players and integer, mapping the score to the player. These statistics can be used to notify tournament observers of tournament statistics/actions.

### Inform tournament observers on tournament statistics and on-going actions

The tournament manager will need to provide and notify tournament statistics and on-going actions to the tournament observers. Tournament observers are able to call the methods indicated below to receive the information desired at any time during the tournament. Other than that, tournament managers will need to send information of tournament statistics/on-going actions in set intervals, such as at the end of each game or the start of a new game to tournament observers who have opted to do so. 

#### Methods to provide requested statistics to the tournament observers:
Observers may use the below methods at any time, and call it as many times needed.

**getAllEndedGamesResults()**

This should return the results such as winners, cheaters and winning score of all previous games with its round number and game number.

**getEndedGamesResults(int gameNumber)**

This should return the results such as its winners, cheaters and winning score of the specified game.

**getAllRunningGamesInfo()**

This should inform the user of the players who are competing in all running games. 

**getTournamentTree()**

The tournament manager will be able to build a tournament tree with the round numbers and game numbers and provide it to the game observers along with the winners of each game.

### Inform players on tournament statistics and on-going actions

The tournament manager will also need to provide and notify players if they have won a single game and is able to proceed to the next round or have been disqualified during the current round after each game. 

#### Methods to provide requested statistics to the players:

Players may use the below methods at any time, and call it as many times needed. The only requirement would be that it calls getGamesPlayed to know the game number it would like to request information on before any methods that require it.

**getGamesPlayed(Player player)**

This should return a list of game numbers the given player has played in. The player should use this before every method that requires a game number in order to determine the game number of the game it would like to request information on.

**getPlayerGameInfo(Player player, int gameNumber)**

This should return the statistics such as the player’s score in the past game specified. In order to use this method, the player should use getPlayerTournamentStatus to get their most recent game number. 

**getPlayerTournamentStatus(Player player)**

This should return the game number and round number the given player is in right now. 

**getPlayerStatus(Player player)**

This should return a boolean to indicate if the given player is still competing in the tournament or it has been eliminated.

**isCompeting(Player player)**

This should return a boolean to indicate if the most current game this player is competing in is still running or has ended.

**getCurrentRound(Player player)**

This should return an int to indicate the most recent round the player has played in.
