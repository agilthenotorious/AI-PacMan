public class Node<S, A> implements Comparable<Node<S,A>>{
	public S state;
	public Node<S, A> parent;
	public A action;
	public double cost;

	//constructor:
	public Node(S nodestate, Node<S, A> nodeparent, A nodeaction, double nodecost) {
		this.state = nodestate;
		this.parent = nodeparent;
		this.action = nodeaction;
		this.cost = nodecost;
	}
	public Node(Node<S,A> another) {
		this.state = another.state;
		this.parent = another.parent;
		this.cost = another.cost;
		this.action = another.action;
	}
	@Override
	public int compareTo(Node<S, A> compareToNode) {
		if ( this.cost > compareToNode.cost){
			return 1;
		} else {
			if( this.cost< compareToNode.cost) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}