## Self-Evaluation Form for Milestone 6

Indicate below where your TAs can find the following elements in your strategy and/or player-interface modules:

The implementation of the "steady state" phase of a board game
typically calls for several different pieces: playing a *complete
game*, the *start up* phase, playing one *round* of the game, playing a *turn*, 
each with different demands. The design recipe from the prerequisite courses call
for at least three pieces of functionality implemented as separate
functions or methods:

- the functionality for "place all penguins"
https://github.ccs.neu.edu/CS4500-F20/cox/blob/0c493fec9b815dab28bbd1c6d336d02e8acb38e3/Fish/Admin/Other/main/java/Referee.java#L157-L198

- a unit test for the "place all penguins" funtionality 
https://github.ccs.neu.edu/CS4500-F20/cox/blob/0c493fec9b815dab28bbd1c6d336d02e8acb38e3/Fish/Admin/Other/test/java/RefereeTest.java#L97-L107


- the "loop till final game state"  function
https://github.ccs.neu.edu/CS4500-F20/cox/blob/0c493fec9b815dab28bbd1c6d336d02e8acb38e3/Fish/Admin/Other/main/java/Referee.java#L27-L53

- this function must initialize the game tree for the players that survived the start-up phase
https://github.ccs.neu.edu/CS4500-F20/cox/blob/0c493fec9b815dab28bbd1c6d336d02e8acb38e3/Fish/Admin/Other/main/java/Referee.java#L51

- a unit test for the "loop till final game state"  function
https://github.ccs.neu.edu/CS4500-F20/cox/blob/0c493fec9b815dab28bbd1c6d336d02e8acb38e3/Fish/Admin/Other/test/java/RefereeTest.java#L109-L125


- the "one-round loop" function
https://github.ccs.neu.edu/CS4500-F20/cox/blob/0c493fec9b815dab28bbd1c6d336d02e8acb38e3/Fish/Admin/Other/main/java/Referee.java#L106-L144

- a unit test for the "one-round loop" function
https://github.ccs.neu.edu/CS4500-F20/cox/blob/0c493fec9b815dab28bbd1c6d336d02e8acb38e3/Fish/Admin/Other/test/java/RefereeTest.java#L82-L95


- the "one-turn" per player function
https://github.ccs.neu.edu/CS4500-F20/cox/blob/0c493fec9b815dab28bbd1c6d336d02e8acb38e3/Fish/Admin/Other/main/java/Referee.java#L119-L123

- a unit test for the "one-turn per player" function with a well-behaved player 
https://github.ccs.neu.edu/CS4500-F20/cox/blob/0c493fec9b815dab28bbd1c6d336d02e8acb38e3/Fish/Admin/Other/test/java/RefereeTest.java#L109-L125


- a unit test for the "one-turn" function with a cheating player
https://github.ccs.neu.edu/CS4500-F20/cox/blob/0c493fec9b815dab28bbd1c6d336d02e8acb38e3/Fish/Admin/Other/test/java/RefereeTest.java#L243-L250
（We don't know how to simulate cheating player, so we manually remove the player)


- a unit test for the "one-turn" function with an failing player
https://github.ccs.neu.edu/CS4500-F20/cox/blob/0c493fec9b815dab28bbd1c6d336d02e8acb38e3/Fish/Admin/Other/test/java/RefereeTest.java#L243-L250
（We don't know how to simulate failing player, so we manually remove the player)


- for documenting which abnormal conditions the referee addresses 
https://github.ccs.neu.edu/CS4500-F20/cox/blob/0c493fec9b815dab28bbd1c6d336d02e8acb38e3/Fish/Admin/Other/main/java/Referee.java#L106-L144

- the place where the referee re-initializes the game tree when a player is kicked out for cheating and/or failing 
https://github.ccs.neu.edu/CS4500-F20/cox/blob/0c493fec9b815dab28bbd1c6d336d02e8acb38e3/Fish/Admin/Other/main/java/Referee.java#L154


**Please use GitHub perma-links to the range of lines in specific
file or a collection of files for each of the above bullet points.**

  WARNING: all perma-links must point to your commit "0c493fec9b815dab28bbd1c6d336d02e8acb38e3".
  Any bad links will be penalized.
  Here is an example link:
    <https://github.ccs.neu.edu/CS4500-F20/cox/tree/0c493fec9b815dab28bbd1c6d336d02e8acb38e3/Fish>

