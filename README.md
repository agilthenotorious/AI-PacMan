NOTE: To see my code implementation, check files on AI-PacManGame/PacMan-vs-Ghosts-Agents/src/... I implemented Uniform Cost Search algorithm.

Check Project Webpage : https://ksvi.mff.cuni.cz/~dingle/2019/ai/ms_pacman/ms_pacman.html

![mspac](https://user-images.githubusercontent.com/45079660/73780251-1dc16b80-475c-11ea-87b9-44cff6e2e99e.png)

Overview
This is a Java implementation of the classic video game Ms. Pac-Man. You can play it live from the keyboard, but it is mostly intended for experimenting with algorithms that play the game automatically. You can write your own agent that plays the game using the API described below.

History
Midway released the arcade game Ms. Pac-Man in 1981. Like its predecessor Pac-Man, it quickly became popular around the world.

In recent years, the University of Essex ran a series of competitions for software agents that attempt to play Ms. Pac-Man automatically, including the Ms. Pac-Man versus Ghosts Competition from 2011-12. Simon Lucas wrote an implementation of Ms. Pac-Man for that competition, and it was later modified by Philipp Rohlfshagen and other authors. The implementation here is derived from that code, with further changes by Jakub Gemrot and Adam Dingle from the Faculty of Mathematics and Physics at Charles University.

The more recent Ms. Pac-Man Vs. Ghost Team Competition has its own updated implementation of Ms. Pac-Man. Perhaps we will merge in changes from that codebase at some point.

Building the game
This version of Ms. Pac-Man vs. Ghosts works with Java 11, 12, or 13, and probably older Java versions as well.

The sources include.project files for Eclipse.You should easily be able to load them into Eclipse, IntelliJ, or Visual Studio Code, all of which understand the Eclipse .project file format.

Playing the game
To play the game from the keyboard in a default configuration, run the class PacManSimulator in PacMan-vs-Ghosts/src/game/PacManSimulator.java. Use the arrow keys to move. You can press 'P' to pause the game, or 'N' to advance the game by a single frame.

This is a fairly faithful recreation of the original Ms. Pac-Man game, with some differences. In particular, there are no bonus fruits.

Writing an agent
Here is the documentation for the API you can use to build agents to play the game.

You can enhance the MyPacMan class to build a custom agent that controls Ms. Pac-Man. On each tick, the game will call your implementation of the tick() method, where you can decide which action Ms. Pac-Man should take.

The Game interface has everything you need to find about the game state. Note that

The maze is represented as a graph of nodes. Each node is a distinct position in the maze and has a unique integer index. There are about 1,000 nodes in each maze (the exact number varies from level to level) and they are evenly spaced throughout the maze.
Each pill in the maze has a unique index.
Ghosts are numbered from 0 to 3.
Directions are listed at the top of interface Game (UP=0, RIGHT=1, etc.)
The game normally runs at 25 ticks per second.
Here are some of the most important Game methods:

int getCurPacManLoc() – return Ms. Pac-Man's current position, i.e. the graph node where she is currently located.

int getCurGhostLoc(int whichGhost) – return the given ghost's current position.

int getCurGhostDir(int whichGhost) – return the given ghost's current direction.

int getX(int nodeIndex), int getY(int nodeIndex) - return the X/Y pixel positions of the given graph node

int getNeighbour(int nodeIndex, int direction) – return the given node's neighbor in the given direction, or -1 if none.

boolean isEdible(int whichGhost) – true if the given ghost is currently edible

int getEdibleTime(int whichGhost) - the number of remaining ticks during which the given ghost will be edible.

int getPillIndex(int nodeIndex) – return the index of the pill that is/was at the given position, or -1 if this is not a position that ever holds a pill in this maze. Note that this method returns the same value whether or not the pill has already been eaten. You must call checkPill to find out whether the pill still exists. (Do not pass -1 to checkPill; that will result in an out of bounds error.)

boolean checkPill(int pillIndex) – return true if the given pill still exists, i.e. has not been eaten.

double getEuclideanDistance(int from, int to)
int getManhattanDistance(int from, int to)

Return the Euclidean or Manhattan distance between two maze nodes.

int getPathDistance(int from, int to)

Return the shortest-path distance walking through the maze without going through walls. This method uses a precomputed table so it is very fast (it doesn't need to perform a depth-first search of its own!)

Note that if g is the position of a ghost in its lair, and p is any position in the maze, then getPathDistance(g, p) will return -1, meaning that there is no path from the ghost's position to the maze position since the lair is surrounded by walls.

public int getGhostPathDistance(int whichGhost, int to)

Return the shortest distance that the given ghost may travel through the maze to reach the given position, taking into consideration that ghosts may not reverse direction.

Warning: this method is about 50-150x slower than getPathDistance! (In my experiments it typically runs in tens of microseconds, whereas getPathDistance takes under 1 microsecond.) That's because it (unfortunately) does not use a precomputed table, so it must trace a path through the maze.

The GameView class has various static methods that draw text or graphics overlaid on the game view. These are handy for debugging. Perhaps the most useful is

public static void addPoints(Game game, Color color, int... nodeIndices)

Draw a set of points at the given maze nodes, in the given color. This is a nice way to draw a path through the maze. (You must call this method anew on every tick if you want the path you draw to remain visible.)

See the sample agent NearestPillPacManVS.java for an example of using these drawing methods.

Other notes
The PacMan-vs-Ghosts-Agents subproject contains a set of sample agents (some that control Ms. Pac-Man, and also some that control ghosts).

The PacMan-vs-Ghosts-Tournament subproject is a console application that can evaluate agents, outputting results to CSV files.

When running an agent, press the 'H' key to "hijack" control and manually navigate Ms. Pac-Man.

Change log
v 2.2.0

CODE MOVED TO MY OWN REPOSITORY: https://github.com/kefik/MsPacMan-vs-Ghosts-AI
changed the way images and text files are loaded (now they are loaded as resources)
reworked Exec into PacManSimulator & PacManReplayer
you can run the game WITHOUT ghosts as well (in order to play with pathfinding only)
fixed native ghosts bug (infinite loop)
PacManReplayer is fully working! Issue from 2.1.2 solved by explicitly logging node indices (positions) of Ms Pac-Man & Ghosts
GameView allows to display DebugTexts as well
Example agents moved to separate project PacMan-vs-Ghosts-Agents
Direction enum created
PacMan & Ghosts actions are now transfered in PacManAction & GhostsActions objects rather than numbers
Maze module created that allows you to access the maze in an OOP way
PacManHijackController created that allows you to pause/advance single frame/hijack PacMan controls at any time (great for debugging!)
Visualization now contains Scale2x mode (taken from MarioAI, kudos to Sergey Karakovskiy), which is enabled as default
starter package deleted (all example agents contain main() methods so they are directly runnable / modifiable / copy-paste ...)
the simulation features TIMED execution of Pac-Man/Ghosts logic only (but you can always set high thinking time as there is no "fixed" waiting)
v 2.1.2

changed the way the images and text files are loaded to allow applet version to work
added a way to G to record games for javascript replays on web-site
localised file-name descriptors
added the following helper methods:
public int getNextEdibleGhostScore();
public int getNumActivePills();
public int getNumActivePowerPills();
public int[] getPillIndicesActive();
public int[] getPowerPillIndicesActive();
updated the sample controllers to use the new functions
added boolean flag to GameView to prevent unnecessary storing of information
use StringBuilder to save replays
updated recording feature to flush string after each save
include starter package in code distribution
known issues:

bug in recording replays: the ghost update method (every nth game tick) causes the replay to crash whenever a power pill has been eaten). This is caused by the lack of update for edible ghosts. Fix in progress.
v 2.1.1

changed the graphics in GameView to do double buffering
added the ability to do simple visuals for debugging/testing/demonstrations
changed the way a game is initialised: removed the singleton pattern
added NearestPillPacManVS to illustrate the visuals
added 5 (utility) methods in Game/G:
public int[] getPath(int from,int to);
public int getTarget(int from,int[] targets,boolean nearest,DM measure);
public int[] getGhostPath(int whichGhost,int to);
public int getGhostPathDistance(int whichGhost,int to);
public int getGhostTarget(int from,int[] targets,boolean nearest);
changelog now included in source code distribution
v 2.1.0

fixed the creation of the junction array in the class Node (changed from >3 to >2)
changed the spelling from juntionIndices to junctionIndices
added 2 methods to Game (and G) to get all possible directions for Ms Pac-Man and the ghosts as an array
changed the sample controllers accordingly
changed the game core accordingly
removed Random from G (can use it from Game)
changed advanceGame(-) to return the actual actions taken (primarily for replays)
fixed the replay mechanism which was buggy in some cases where a life was lost
added a sample experimental setup to Exec to illsutrate how to run many games efficiently
fixed nearestPillPac-Man to include search for power pills
changed the way ghost reversals are done (now using a Boolean flag)
added more comments to source code, especially in Game
v 2.0.2

fixed the isJunction function which now checks for more than 2 options, not 3 (thanks to Daryl)
v 2.0.1

fixed the speed of the ghosts when edible - now they move more slowly, as before (thanks to Kien)
the scores obtained for eating ghosts in succession was incorrect - now it is 200-400-800-1600 (thanks to Kien)
added the ability to record and replay games by saving the actions taken by the controllers
v 2.0.0

complete revamp of the code. Please see documentation on the website for information regarding the code
