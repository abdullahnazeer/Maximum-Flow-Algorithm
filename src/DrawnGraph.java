import java.util.HashMap;
import java.util.LinkedList;

// Abdullah Nazeer
// IIT ID - 2017145
// UoW ID - w1673684


/* After comparison and judgement between all the algorithms related to the calculation of Maximum Flow, I have decided to
* proceed with the algorithm developed by L.R Ford, Jr. & D.R Fulkerson.
* In my opinion, the Ford-Fulkerson algorithm seems to have a short running time and the determination of the Maximum Flow is very fast and accurate
* I have also reviewed the algorithm produced by Ahuja & Orlin and have compared its running time with that of the Fulkerson one.
* In conclusion, I have deduced that the Fulkerson algorithm excels the Orlin algorithm in terms of performance and efficiency
*
* I hereby declare that the used snippets of code are not solely mines and a large proportion of them were obtained from online open-source
* repositories and sites. I have cloned a repository fromGitHub and have made modifications to it in order to fulfill the requirements
* asked for in the specifications.
*
* The initial cloned version was written by an author named 'Ruben Beyer'
* https://github.com/Speedy-Consoles/Ford-Fulkerson.git
*/


public class DrawnGraph {

	private HashMap<Object, Node> nodes = new HashMap<Object, Node>();

	private LinkedList<Edge> edges = new LinkedList<Edge>();

	/* This method is used to build the graph. It adds an edge to the graph along with its connecting nodes
	*  The node identifier can be any Object and two Objects are the same node if and only if, they are equal
	*  (according to their equals function) */

	// startNodeID - Object that identifies the starting node of the edge
	// endNodeID - Object that identifies the ending node of the edge
	// capacity - Flow Capacity of the edge in question

	void addEdge(Object startNodeID, Object endNodeID, int capacity) {
		Node startNode;
		Node endNode;

		//Checking to ensure that the startNodeID and the endNodeID are in the nodes list


		if (!this.nodes.containsKey(startNodeID)) {
			startNode = new Node();
			this.nodes.put(startNodeID, startNode);
		} else {
			startNode = this.nodes.get(startNodeID);
		}
		if (!this.nodes.containsKey(endNodeID)) {
			endNode = new Node();
			this.nodes.put(endNodeID, endNode);
		} else {
			endNode = this.nodes.get(endNodeID);
		}

		// A new edge is created and to it is passed the startNodeID, the endNodeID and the capacity of the edge

		Edge edge = new Edge(startNodeID, endNodeID, capacity);
		startNode.addEdge(edge);
		endNode.addEdge(edge);
		this.edges.add(edge);
	}

	//getNode() returns the Node by use of its Node ID

	Node getNode(Object nodeID) {
		return this.nodes.get(nodeID);
	}

	LinkedList<Edge> getEdges() {
		return this.edges;
	}
}
