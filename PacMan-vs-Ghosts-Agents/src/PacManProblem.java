import game.core.Game;
import game.core.Game.DM;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class PacManProblem implements Problem<Integer,Integer> {


	private Game g;

	public PacManProblem (Game g) {
		this.g = g;
	}

	///////////////////////////////////////////////////////////////////////////////
	public Integer initialState() {
		return g.getCurPacManLoc();
	}


	////////////////////////////////////////////////////////////////////////////////
	public List<Integer> actions(Integer state) {
		List<Integer> mylist = new ArrayList<Integer>();
		int a[] = g.getPossibleDirs(state, -1, true);
		for(int i: a) {
			mylist.add(i);
		}

		return mylist;
	}

	////////////////////////////////////////////////////////////////////////////////
	public Integer result(Integer state, Integer action) {
		return g.getNeighbour(state, action);
	}

	////////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////////
	public boolean isGoal(Integer state) {
		//SOME THINGS

		int superPill = g.getPowerPillIndex(state);
		int simplePill = g.getPillIndex(state);
		//int my = getNearestGhost(state);
		//int mydistances = g.getTarget(state, g.getCurGhostLoc(1), true, DM.PATH);


		if(g.isEdible(0)||g.isEdible(1)||g.isEdible(2)||g.isEdible(3)){
			for (int i = 0; i < 4; i++) {
				if (g.isEdible(i) && state == g.getCurGhostLoc(i)) {
					//System.out.println("FOUND GHOST EDIBLE AS GOAL: " + i);
					return true;
				}
			}}
		else{
			if ( g.getNumActivePills()>10){
				if (superPill >= 0 && g.checkPowerPill(superPill)) {
					//System.out.println("FOUND SUPER PILL: " + state);
					return true;
				}

				if (simplePill>=0 && g.checkPill(simplePill)) {
					//System.out.println("FOUND PILL: " + state);
					return true;
				}}
			else if (superPill >= 0 && g.checkPowerPill(superPill)) {
				//System.out.println("FOUND SUPER PILL: " + state);
				return true;
			}

			if (simplePill>=0 && g.checkPill(simplePill)) {
				//System.out.println("FOUND PILL: " + state);
				return true;}}

		return false;
	}

	////////////////////////////////////////////////////////////////////////////////
	public int getNearestGhost(Integer state) {
		int[] ghostLoc = new int [4];
		HashMap<Integer, Integer> distMapToGhostIdx = new HashMap<>();
		for(int i = 0; i < 4; i++) {
			ghostLoc[i] = g.getCurGhostLoc(i);
			distMapToGhostIdx.put(g.getCurGhostLoc(i), i);
		}
		int nearestDist = g.getTarget(state, ghostLoc, true, DM.PATH);
		int nearestGhost = distMapToGhostIdx.get(nearestDist);
		return nearestGhost;
	}



	public double cost(Integer state, Integer action) {
		int nextState = result(state, action);
		int superPill = g.getPowerPillIndex(nextState);
		int simplePill = g.getPillIndex(nextState);


		for(int i = 0; i < 4; i++) if((nextState == g.getCurGhostLoc(i)) && !g.isEdible(i)) return 20000000;

		int nearestGhost = getNearestGhost(nextState);
		int nearestGhostLoc = g.getCurGhostLoc(nearestGhost);
		int distToNearest = g.getPathDistance(nextState, nearestGhostLoc);

		//
		//

		if (superPill >= 0 && g.checkPowerPill(superPill) && (g.isEdible(1)||g.isEdible(0)||g.isEdible(2)||g.isEdible(3))) return 10000000;
		if (!g.isEdible(nearestGhost) && distToNearest <= 15) return 100000 - distToNearest*2500 + 10;

		if (g.isEdible(nearestGhost)) {
			// && distToNearest <= 50 && g.getEdibleTime(nearestGhost) >= 25
			return 1;
		}

		if (superPill >= 0 && g.checkPowerPill(superPill) && !g.isEdible(nearestGhost) && distToNearest >= 25) return 100000;

		//if(superPill >= 0 && g.checkPowerPill(superPill)&&(g.isEdible(0)||g.isEdible(1)||g.isEdible(2)||g.isEdible(3))) return 100000;

		if (simplePill >= 0 && g.checkPill(simplePill) && g.getNumActivePills()==1 && g.getNumActivePowerPills()>=1 ) return 100000;
		//if (simplePill >= 0 && g.checkPill(simplePill) && (g.isEdible(1)||g.isEdible(0)||g.isEdible(2)||g.isEdible(3))) return 100000;
		if (simplePill >= 0 && g.checkPill(simplePill) && g.getNumActivePills()==1 && g.getNumActivePowerPills()==0) return 2000;
		if (simplePill >= 0 && g.checkPill(simplePill)) return 2000;
		// we need to give low costs to empty slots so, when we add up to path, it should not increase cost of path that much
		return 1;
	}
}
