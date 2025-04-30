package graph;

import java.util.ArrayList;
import java.util.List;

public class GraphTester {

	private static DiGraph graph;
	
	//helper function - print every node's value in a node list
	public static void printNodesValues(List<GraphNode> nodeList) {
		System.out.println("Nodes are:");
		if (nodeList == null) System.out.println("---- no nodes ----");
		else nodeList.forEach((n) -> System.out.println(n.getValue()));
	}
	
	public static void printPath(List<GraphNode> path) {
		System.out.println("Path is:");
		if (path == null) System.out.println("---- no path found ----");
		else {
			List<String> pathStr = new ArrayList<String>();
			path.forEach((node) -> pathStr.add(node.getValue()));
			System.out.println(String.join(" -> ", pathStr));
		}
	}
	
	public static void main(String[] args) {	
		graph = new DiGraphImpl();
		
		//add nodes
		graph.addNode(new GraphNode("A"));
		graph.addNode(new GraphNode("A"));
		graph.addNode(new GraphNode("B"));
		graph.addNode(new GraphNode("C"));	
		graph.addNode(new GraphNode("D"));
		graph.addNode(new GraphNode("E"));
		graph.addNode(new GraphNode("F"));
		graph.addNode(new GraphNode("G"));
		graph.addNode(new GraphNode("H"));
		
		//add edges
		graph.addEdgeStr("A", "B", 5);
		graph.addEdgeStr("B", "C", 5);
		graph.addEdgeStr("C", "D", 1);
		graph.addEdgeStr("E", "F", 1);
		graph.addEdgeStr("F", "A", 1);
		graph.addEdgeStr("C", "F", 2);
		graph.addEdgeStr("D", "B", 15);
		graph.addEdgeStr("G", "C", 5);
		graph.addEdgeStr("G", "E", 8);
		
		// graph.addEdge("A", "B", 5);
		// graph.addEdgeStr("B", "C", 5);
		// graph.addEdgeStr("C", "D", 1);
		// graph.addEdgeStr("E", "F", 1);
		// graph.addEdgeStr("F", "A", 1);
		// graph.addEdgeStr("C", "F", 2);
		// graph.addEdgeStr("D", "B", 15);
		// graph.addEdgeStr("G", "C", 5);
		// graph.addEdgeStr("G", "E", 8);


		//describe
		printNodesValues(graph.getNodes());
		graph.getNodes().forEach(n -> n.printNeighbors());
		
		//test reachability
		System.out.print("F is reachable to E: ");
		System.out.println(graph.nodeIsReachable(new GraphNode("F"), new GraphNode("E"))); //false
		System.out.print("F is reachable to D: ");
		System.out.println(graph.nodeIsReachable(new GraphNode("F"), new GraphNode("D"))); //true
		
		//test hasCycles
		System.out.print("Graph has cycles: ");
		System.out.println(graph.hasCycles());
		
		//test fewest hops
		System.out.println("Fewest hop from G to B is: " + graph.fewestHops(new GraphNode("G"), new GraphNode("B")));
		printPath(graph.getFewestHopsPath(new GraphNode("G"), new GraphNode("B")));
		
		//test shortest path
		System.out.println("Shortest from G to B is: " + graph.shortestPath(new GraphNode("G"), new GraphNode("B"))); 
		printPath(graph.getShortestPath(new GraphNode("G"), new GraphNode("B")));
	}
	
}
