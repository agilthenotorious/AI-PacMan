import cz.cuni.mff.amis.pacman.tournament.EvaluateAgentConsole;

class Evaluate {
	public static void main(String[] args) {

		String[] args1 = {
				  "-s", "20" // "seed"
				, "-o", "-pp true -tp 1.0 -gc 4 -lc 20 -v false -2x true -p false -tt 40 -r true"   // prototype-options";
				, "-c", "50"  // run-count
				, "-r", "1"  // one-run-repetitions
				, "-p", "MyPacMan" // fully qualified class name
				, "-i", "MyPacMan"   // agent-id
				, "-d", "./results" // result-dir"	
    };

    EvaluateAgentConsole.main(args1);
  }    
}
	
