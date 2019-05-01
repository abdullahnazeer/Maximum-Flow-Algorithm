import java.util.ArrayList;

    // Abdullah Nazeer
    // IIT ID - 2017145
    // UoW ID - w1673684

    /* I hereby declare that the used snippets of code are not solely mines and a large proportion of them were obtained from online open-source
    * repositories and sites. I have cloned a repository fromGitHub and have made modifications to it in order to fulfill the requirements
    * asked for in the specifications.

    * The initial cloned version was written by an author named 'Ruben Beyer'
    * https://github.com/Speedy-Consoles/Ford-Fulkerson.git
    */

public class Node {

    // Declaration of ArrayList to store all the edges present in the graph

	private ArrayList<Edge> edges = new ArrayList<Edge>();

	// Getters and Setters to add and retrieve edges from the graph

	void addEdge(Edge edge) {
		this.edges.add(edge);
	}

    int getOutLeadingOrder() {
        return this.edges.size();
    }

	Edge getEdge(int number) {
		if (this.edges.size() <= number) {
			return null;
		} else {
			return this.edges.get(number);
		}
	}


}
