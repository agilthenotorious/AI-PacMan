import game.PacManSimulator;
import game.controllers.ghosts.game.GameGhosts;
import game.controllers.pacman.PacManHijackController;
import game.core.Game;
import java.awt.Color;
import game.core.GameView;

public final class MyPacMan extends PacManHijackController 
{	
	@Override
	public void tick(Game game, long timeDue) {
		// Code your agent here.
		PacManProblem pacManProblem = new PacManProblem (game);
		int state = pacManProblem.initialState();
		Solution<Integer,Integer> n = Ucs.search(pacManProblem);
		if (n == null) {
			System.out.println("Couldnt find goal node, so going on current direction");
			pacman.set(game.getCurPacManDir());
			return;
		}
		int[] ghostDistances = new int[]{ game.getPathDistance(state, game.getCurGhostLoc(0)), game.getPathDistance(state, game.getCurGhostLoc(1)), game.getPathDistance(state, game.getCurGhostLoc(2)), game.getPathDistance(state, game.getCurGhostLoc(3))};
		GameView.addText(0, 10, Color.YELLOW, "Ghost distances: " + ghostDistances[0] + ", " + ghostDistances[1] + ", " + ghostDistances[2] + ", " + ghostDistances[3]);

		GameView.addPoints(game,Color.GREEN,game.getPath(state, n.goalState));
		System.out.println("Decideddd to go on goal node: " + n.goalState + " with path " + n.pathCost);

		int action = n.actions.get(0);
		pacman.set(action);
	}

	public static void main(String[] args) {
		// If seed = 0, a random seed is chosen on every run.  Set seed to a positive value
		// for repeatable play.
		int seed = 0;
		
		// The number of lives to start with.
		int lives = 3;
		
		// The number of milliseconds between frames; 40 ms = 25 frames per second.
		int thinkTime = 40;
		
		PacManSimulator.play(new MyPacMan(), new GameGhosts(), seed, lives, thinkTime);
	}
}