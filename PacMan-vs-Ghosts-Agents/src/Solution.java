import java.util.List;

class Solution<S, A> {
    public List<A> actions;  // series of actions from start state to goal state
    public S goalState;      // goal state that was reached
    public double pathCost;  // total cost from start state to goal

    public Solution(List<A> actions, S goalState, double pathCost) {
        this.actions = actions; this.goalState = goalState; this.pathCost = pathCost;
    }

    // Return true if this is a valid solution to the given problem.
    public boolean isValid(Problem<S, A> prob) {
        S state = prob.initialState();
        double cost = 0.0;

        // Check that the actions actually lead from the problem's initial state to the goal.
        for (A action : actions) {
            state = prob.result(state, action);
            cost += prob.cost(state, action);
        }

        return state.equals(goalState) && prob.isGoal(goalState) && pathCost == cost;
    }

    // Describe a solution.
    static <S, A> void report(Solution<S, A> solution, Problem<S, A> prob) {
        if (solution == null)
            System.out.println("no solution found");
        else if (!solution.isValid(prob))
            System.out.println("solution is invalid!");
        else {
            System.out.println("solution is valid");
            System.out.format("total cost is %.1f\n", solution.pathCost);
        }
    }
}