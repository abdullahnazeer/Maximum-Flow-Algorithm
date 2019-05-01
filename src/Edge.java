public class Edge {

    // Abdullah Nazeer
    // IIT ID - 2017145
    // UoW ID - w1673684

    /* I hereby declare that the used snippets of code are not solely mines and a large proportion of them were obtained from online open-source
     * repositories and sites. I have cloned a repository fromGitHub and have made modifications to it in order to fulfill the requirements
     * asked for in the specifications.

     * The initial cloned version was written by an author named 'Ruben Beyer'
     * https://github.com/Speedy-Consoles/Ford-Fulkerson.git
     */

    private final Object target;

    private final Object start;

    private final int capacity;

    // Constructor for the class Edge - used to initialize an edge before adding it to the graph

    Edge(Object start, Object target, int capacity) {
        this.capacity = capacity;
        this.target = target;
        this.start = start;
    }

    Object getTarget() {
        return target;
    }

    int getCapacity() {
        return capacity;
    }

    Object getStart() {
        return start;
    }

    @Override
    public String toString() {
        return this.start + "->" + this.target + "(" + this.capacity + ")";
    }

}
