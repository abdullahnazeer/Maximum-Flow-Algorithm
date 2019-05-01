import org.graphstream.graph.implementations.SingleGraph;

import java.util.*;

// Abdullah Nazeer
// IIT ID - 2017145
// UoW ID - w1673684

// Importing important libraries to be used for graphically displaying the graph. The graphical representation of the graph
// was not completed properly but the edges, along with their capacities are displayed in the CLI (along with the Maximum Flow)

/* I hereby declare that the used snippets of code are not solely mines and a large proportion of them were obtained from online open-source
 * repositories and sites. I have cloned a repository from GitHub and have made modifications to it in order to fulfill the requirements
 * asked for in the specifications.

 * The initial cloned version was written by an author named 'Ruben Beyer'
 * https://github.com/Speedy-Consoles/Ford-Fulkerson.git
 */

public class FordFulkersonAlgorithm {

    // Map being declared to store the adjacency matrix

    private Map<Integer, List<Integer>> adjacencyMatrixList;

    public static void main(String[] args) {

        SingleGraph graph = new SingleGraph("Use");

        Random random = new Random();

        int numberOfNodes = random.nextInt(6) + 6;

        int source;
        int sink;

        DrawnGraph g = new DrawnGraph();

        source = 0;
        sink = numberOfNodes - 1;

        for (int i=0; i<=sink; i++){
            String id = "" + i + "";
            graph.addNode(id);
        }

        // Stylesheet - allows for the graph nodes and edges to be made more attractive

        graph.addAttribute("ui.stylesheet", "node {shape: box;fill-color: blue, green, red;text-mode:normal;text-background-mode: plain;size-mode: fit;text-size:13;text-visibility-mode: normal; fill-mode: dyn-plain;}");

        System.out.println("Edges Generated + Respective Capacities");
        System.out.println();
        System.out.println("Total Number of Nodes: " + sink);
        System.out.println();
        System.out.println("Number of Edges: " + sink);
        System.out.println();
        for (int i = 0; i <= sink; i++) {
            int endNode = random.nextInt(sink) + 1;
            int capacity = random.nextInt(15) + 5;

            // Creation of the parameters (to be sent when adding the edges and nodes to the graphical representation)

            String id = "" + i + "" + endNode;
            String fromNode = "" + i + "";
            String toNode = "" + endNode + "";

            g.addEdge(i, endNode, capacity);
            if (i != endNode){
                graph.addEdge(id, fromNode, toNode,true);
            }

            // Displaying of all node parent - node child relation together with their respective capacities

            System.out.println("From Node " + i + " to Node " + endNode + " | Capacity " + capacity);

            //graph.addEdge("" + 0 + "" + endNode, "" + 0 + "", "" + endNode + "",true);
        }


        HashMap<Edge, Integer> flow = getMaxFlow(g, source, sink);

        System.out.println();

        // Output of the Maximum Flow of the graph and displaying of the visual graph

        System.out.print("The Maximum Flow for the Generated Graph is: ");
        System.out.println(getFlowSize(flow, g, source));

        graph.display();

    }

    static HashMap<Edge, Integer> getMaxFlow(DrawnGraph g, Object source,
                                             Object sink) {

        // The augmenting paths from the Source to the Sink - found during each iteration

        LinkedList<Edge> path;

        // The flow, i.e. the capacity of each edge that is actually used

        HashMap<Edge, Integer> flow = new HashMap<Edge, Integer>();

        // Initializing all flows to an initial value of 0

        for (Edge e : g.getEdges()) {
            flow.put(e, 0);
        }

        // The Core Algorithm

        while ((path = breadthFirstSearch(g, source, sink, flow)) != null) {

            // Activating this output will illustrate how the algorithm works
            // System.out.println(path);
            // Find out the flow that can be sent on the found path

            Object lastNode = source;
            int minCapacity = Integer.MAX_VALUE;
            for (Edge edge : path) {
                int capacity;

                // Although the edges are directed they can be used in both
                // directions if the capacity is partially used, so this if
                // statement is necessary to find out the edge's actual
                // direction.

                if (edge.getStart().equals(lastNode)) {
                    capacity = edge.getCapacity() - flow.get(edge);
                    lastNode = edge.getTarget();
                } else {
                    capacity = flow.get(edge);
                    lastNode = edge.getStart();
                }
                if (capacity < minCapacity) {
                    minCapacity = capacity;
                }
            }

            // Change flow of all edges of the path by the value calculated
            // above.

            lastNode = source;
            for (Edge edge : path) {
                // If statement like above
                if (edge.getStart().equals(lastNode)) {
                    flow.put(edge, flow.get(edge) + minCapacity);
                    lastNode = edge.getTarget();
                } else {
                    flow.put(edge, flow.get(edge) - minCapacity);
                    lastNode = edge.getStart();
                }
            }
        }
        return flow;
    }

    //This method is used to determine the maximum flow by taking into consideration all the forward flows

    // graph - The drawn graph

    // source - The Object identifying the source node of the flow

    static int getFlowSize(HashMap<Edge, Integer> flow, DrawnGraph graph,
                           Object source) {
        int maximumFlow = 0;
        Node sourceNode = graph.getNode(source);
        for (int i = 0; i < sourceNode.getOutLeadingOrder(); i++) {
            maximumFlow += flow.get(sourceNode.getEdge(i));
        }
        return maximumFlow;
    }

    // start - Object that identifies the start node of the search

    // target - Object that identifies the end node of the search

    /* flow - HashMap that stores the current flow amount of an edge.If the value is greater than 0,
     * it will be used for backward flow. If the flow amount is equal to the capacity of that edge,
     * it will be ignored */

    /* return - A list of all edges found in the augmenting path is returned in the order they are met.
     * Null will be returned if there exists no path.
     * If the start node == target node, an empty list is returned */

    static LinkedList<Edge> breadthFirstSearch(DrawnGraph g, Object start, Object target,
                                               HashMap<Edge, Integer> flow) {

        // The Edge by which a child node was reached - stored in a HashMap

        HashMap<Object, Edge> parent = new HashMap<Object, Edge>();

        // All outer nodes of the current search iteration

        LinkedList<Object> spectatingNodes = new LinkedList<Object>();

        // We need to put the start node into those two

        parent.put(start, null);
        spectatingNodes.add(start);

        // The actual algorithm

        all:
        while (!spectatingNodes.isEmpty()) {

            // This variable is needed to prevent the JVM from having a
            // concurrent modification

            LinkedList<Object> newSpectatingNodes = new LinkedList<Object>();

            // Iterate through all nodes in the fringe

            for (Object nodeID : spectatingNodes) {
                Node node = g.getNode(nodeID);

                // Iterate through all the edges of the node

                for (int i = 0; i < node.getOutLeadingOrder(); i++) {
                    Edge e = node.getEdge(i);

                    // Only add the node if the flow can be sent as backward flow
                    // Terminate the iteration if the target node is reached

                    if (e.getStart().equals(nodeID)
                            && !parent.containsKey(e.getTarget())
                            && flow.get(e) < e.getCapacity()) {
                        parent.put(e.getTarget(), e);
                        if (e.getTarget().equals(target)) {
                            break all;
                        }

                        newSpectatingNodes.add(e.getTarget());

                    } else if (e.getTarget().equals(nodeID)
                            && !parent.containsKey(e.getStart())
                            && flow.get(e) > 0) {
                        parent.put(e.getStart(), e);
                        if (e.getStart().equals(target)) {
                            break all;
                        }

                        newSpectatingNodes.add(e.getStart());
                    }
                }
            }

            // Replace the spectating nodes with the newly updated spectating nodes

            spectatingNodes = newSpectatingNodes;
        }

        // If no path is found, return null

        if (spectatingNodes.isEmpty()) {
            return null;
        }

        // In the case of a path being found, re-construct it

        Object node = target;
        LinkedList<Edge> path = new LinkedList<Edge>();
        while (!node.equals(start)) {
            Edge e = parent.get(node);
            path.addFirst(e);
            if (e.getStart().equals(node)) {
                node = e.getTarget();
            } else {
                node = e.getStart();
            }
        }

        // Return the path

        return path;
    }

}
