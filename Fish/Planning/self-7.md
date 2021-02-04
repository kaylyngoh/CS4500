## Self-Evaluation Form for Milestone 7

Please respond to the following items with

1. the item in your `todo` file that addresses the points below.
    It is possible that you had "perfect" data definitions/interpretations
    (purpose statement, unit tests, etc) and/or responded to feedback in a 
    timely manner. In that case, explain why you didn't have to add this to
    your `todo` list.

2. a link to a git commit (or set of commits) and/or git diffs the resolve
   bugs/implement rewrites: 

These questions are taken from the rubric and represent some of the most
critical elements of the project, though by no means all of them.

(No, not even your sw arch. delivers perfect code.)

### Board

- a data definition and an interpretation for the game _board_

[x] Board interpretation was insufficient, missing interpretation of how 2D row and column position represent hexagon coordinates

https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/f38fd798ecdc9ecaa5c8ef374d203f9e9ea102a7/Fish/README.md

- a purpose statement for the "reachable tiles" functionality on the board representation

[x] Insufficient code clarity on possibleTileToMoveTo

https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/f38fd798ecdc9ecaa5c8ef374d203f9e9ea102a7/Fish/README.md

- two unit tests for the "reachable tiles" functionality

We already had 4 unit tests that checks the "reachable tiles" before Milestone 7. We did find a bug in this functionality in Milestone 3 and have fixed it plus added unit tests to check its accuracy which can be found in the link below:

https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/master/Fish/Common/FishCode/src/test/java/BoardTest.java#L148-L193


### Game States 


- a data definition and an interpretation for the game _state_

[x] Weak interpretation or data definition for game states and purpose statement for creating a game state (should include relationship of all the components of a game state)

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/09ddcd0033d24239c1820832d53244463b1d5be1/Fish/Common/FishCode/src/main/java/Model/FishModel.java#L7-L13

- a purpose statement for the "take turn" functionality on states

We did not receive any feedback regarding the purpose statement on this functionality. However, please find it in the link below:

https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/master/Fish/Common/FishCode/src/main/java/Model/IFishModel.java#L12-L19

- two unit tests for the "take turn" functionality 

We already had around 9 unit tests take checks different requirements to take a turn and to make a turn which can be found in the link below:

https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/master/Fish/Common/FishCode/src/test/java/FishModelTest.java


### Trees and Strategies


- a data definition including an interpretation for _tree_ that represent entire games

[x] Data definition/interpretation of the game tree doesn't mention how "skip" transitions are dealt with.

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/09ddcd0033d24239c1820832d53244463b1d5be1/Fish/Common/FishCode/src/main/java/Model/GameNode.java#L8-L18

- a purpose statement for the "maximin strategy" functionality on trees

[x] Purpose statement of turn action does not specify what happens when the current player does not have valid moves

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/09ddcd0033d24239c1820832d53244463b1d5be1/Fish/Player/Strategy.java#L47

- two unit tests for the "maximin" functionality 

[x] Unit tests for takeAction does not cover at least two different depths

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/a60fdbba5e7c13c9a72afb167a9e7145bac0a375/Fish/Common/FishCode/src/test/java/StrategyTest.java#L234-L254

We have added 3 additional unit tests to cover another depth and now have a total of 6 unit tests for the "maximin" functionality.

### General Issues

Point to at least two of the following three points of remediation: 


- the replacement of `null` for the representation of holes with an actual representation 

[x] Should not use null as a hole in the board

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/d06b5b292bd10b181ea335717cef098542165860/Fish/Common/FishCode/src/main/java/Hole.java

- one name refactoring that replaces a misleading name with a self-explanatory name

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/e03cbf0bbfd15d03e9b050f5ed10d6507fc9930c/Fish/Admin/Referee.java#L53-L67

Moved the setup game functionality to a separate function. Was previously unclear as to what the code does in the constructor.

- a "debugging session" starting from a failed integration test:
  - the failed integration test
  - its translation into a unit test (or several unit tests)
  - its fix
  - bonus: deriving additional unit tests from the initial ones 
  
[x] xstate: The ordering of the players in the output should be ordered by turn where the current player is the first in the list

Failing test: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/d06b5b292bd10b181ea335717cef098542165860/Fish/Common/FishCode/src/test/java/xStateTest.java#L210-L245

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/a60fdbba5e7c13c9a72afb167a9e7145bac0a375/Fish/Common/FishCode/src/main/java/Other/xstate.java#L105-L116

### Bonus

Explain your favorite "debt removal" action via a paragraph with
supporting evidence (i.e. citations to git commit links, todo, `bug.md`
and/or `reworked.md`).

While working on this milestone, we did not encounter big bugs since we have addressed many issues immediately once we have received feedback or have found a problem. However, during the code walk to onboard the new partner, we found a few minor bugs and also had many discussions on edge cases that may affect the game in the future. The second bug "The function to create a board with a minimum of 1-fish tiles does not absolutely guarantee a minimum of 1-fish tiles" addressed in the bugs.md file was something that was certainly overlooked by the original partners that created this code base and would not have been caught unless by a fresh pair of eyes. Another two minor reworks that was caught by the new partner were the last two in the reworked.md file: "Place penguin does not check if there is a penguin already on the given position" and "Place Penguin would end the game if the first penguin was placed in a tile that has no possible moves". As mentioned these tech debt would mostly likely cost us in the future if not addressed immediately since both of them were included in core functionalities that are used in the game. We would have needed to use up lots of time just to catch a single bug when encountered if not during this code walk for the new partner. 

