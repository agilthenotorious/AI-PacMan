import java.util.*;
import java.util.Collections;
class Ucs<S, A> {

	public static <S, A> Solution<S, A> search(Problem<S, A> prob) {
		Node<S, A> startnode = new Node<S, A>(
				prob.initialState(),
				null,
				null,
				0.0
		);

		PriorityQueue<Node<S,A>> frontierPriorityQueue = new PriorityQueue<Node<S, A>> ();

		HashMap<S, Node<S,A>> frontierMap = new HashMap<S, Node<S, A>>();
		HashSet<S> explored = new HashSet<>();

		frontierPriorityQueue.add(startnode);

		while( !frontierPriorityQueue.isEmpty() ) {
			List<A> mylist = new ArrayList<A>();
			Node<S, A> firstNode = frontierPriorityQueue.poll();
			explored.add(firstNode.state);
			if (prob.isGoal(firstNode.state)) {
				Node<S,A> copycurrent = new Node<S,A>(firstNode);
				if (copycurrent.parent != null) {
					while(copycurrent.parent != null) {
						mylist.add(copycurrent.action);
						copycurrent = copycurrent.parent;
					}
				}
				Collections.reverse(mylist);
				Solution<S,A> mysolution = new Solution<S,A>(mylist,firstNode.state,firstNode.cost);
				return mysolution;
			}
			for (A action : (List<A>)prob.actions(firstNode.state)) {
				S nextstate = prob.result(firstNode.state, action);
				//if(nextstate!=null){
				//	int mynext = ((Integer) nextstate).intValue();
				//	if (mynext==-1 ) continue; }
				try
				{
					if (nextstate!=null){}


				}
				catch(NullPointerException e)
				{

				}
				if (explored.contains(nextstate)) continue;
				if (nextstate!=null){

					Node<S, A> mynode = new Node<S, A>(
							nextstate,
							firstNode,
							action,
							0
					);
					mynode.cost = firstNode.cost + prob.cost(firstNode.state, action);
					if (!frontierMap.containsKey(mynode.state)) {
						frontierMap.put(mynode.state,mynode);
						frontierPriorityQueue.add(mynode);
					} else {
						Node<S,A> oldNode = frontierMap.get(mynode.state);
						double oldCost = oldNode.cost;
						if(mynode.cost<oldCost) {
							frontierMap.replace(mynode.state, oldNode, mynode);
							frontierPriorityQueue.remove(oldNode);
							frontierPriorityQueue.add(mynode);
						}
					}
				}
			}

		}
		return null;
	}
}