## Data Representation
### FishGame:
The FishGame will be a new Java class, structured similarly to a tree to represent all potential moves in any state reachable from a starting point. The starting state is a FishModel (game state) where all the players have placed all their available penguins. The first layer of the tree will represent all the possible moves from the starting state, and the second layer will represent all the possible moves from any of the states in layer one and so on. FishGame will keep track of a single node, a new Java class named GameNode, which would be the starting game state or root of the tree. 

### GameNode:
GameNode represents a single game state. With the root in FishGame, FishGame is able to keep track of all the potential moves as each node will be keeping track of their parent node, the game state they represent and their children which are all the potential moves from the current game state. 

The tree structure will most likely resemble to the below code in Java:

![Screen Shot 2020-10-16 at 5 01 12 AM](https://media.github.ccs.neu.edu/user/7627/files/9a564c00-0f71-11eb-8409-55b7853b9882)

![Screen Shot 2020-10-16 at 5 01 22 AM](https://media.github.ccs.neu.edu/user/7627/files/aa6e2b80-0f71-11eb-98da-911473635adf)

Both players and referees will be able to use this tree structure to check the validity of movements and plan for their next move. 

## External Interface
1. Check if the given game state (GameNode) which includes a potential move is inside the children of the current game state.
2. Get the list of all the potential moves (children of current game node) in order to determine next valid moves.
