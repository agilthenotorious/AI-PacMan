import java.util.List;

// S = state type, A = action type
interface Problem<S, A> {
	S initialState();
	List<A> actions(S state);
	S result(S state, A action);
	boolean isGoal(S state);
	double cost(S state, A action);
}