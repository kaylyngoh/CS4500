The ordering of the players in the output of the xstate integration test should be ordered by turn where the current player is the first in the list rather than the list ordered by age. We have added a method to sort the list by turn in xstate since our data representation keeps track of turn by the current player field rather than using the list.

Failing test: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/d06b5b292bd10b181ea335717cef098542165860/Fish/Common/FishCode/src/test/java/xStateTest.java#L210-L245

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/a60fdbba5e7c13c9a72afb167a9e7145bac0a375/Fish/Common/FishCode/src/main/java/Other/xstate.java#L105-L116

The function to create a board with a minimum of 1-fish tiles does not absolutely guarantee a minimum of 1-fish tiles. This was not caught in our unit test since
we were using a random generator and have made changes to the functionality by counting down the number of 1-fish tiles still needed in the board rather than
using the random generator.

Failing test: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/2901780c25da6520fa97537a1468c4597de7630a/Fish/Common/FishCode/src/test/java/BoardTest.java#L96-L113

Fix: https://github.ccs.neu.edu/CS4500-F20/bartonville/blob/2901780c25da6520fa97537a1468c4597de7630a/Fish/Common/FishCode/src/main/java/Model/Board.java#L106-L126







