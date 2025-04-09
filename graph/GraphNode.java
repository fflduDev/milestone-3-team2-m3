package graph;

import java.util.HashMap;
import java.util.Map;

public class GraphNode {
	private String value;
	private Map<GraphNode, Integer> edges;

	public GraphNode(String value) {
		this.value = value;
		this.edges = new HashMap<>();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Map<GraphNode, Integer> getEdges() {
		return edges;
	}

	public void addEdge(GraphNode toNode, int weight) {
		edges.put(toNode, weight);
	}

	public void removeEdge(GraphNode toNode) {
		edges.remove(toNode);
	}

	public boolean hasEdge(GraphNode toNode) {
		return edges.containsKey(toNode);
	}

	public Integer getEdgeWeight(GraphNode toNode) {
		return edges.get(toNode);
	}

	public void setEdgeWeight(GraphNode toNode, int weight) {
		edges.put(toNode, weight);
	}

	public void printNeighbors() {
		System.out.println("All edges from <" + value + "> are:");
		if (edges.isEmpty()) {
			System.out.println("- There is no edge from <" + value + ">.");
		} else {
			for (GraphNode neighbor : edges.keySet()) {
				System.out.println("- Edge to <" + neighbor.getValue() + ">, with weight " + edges.get(neighbor) + ".");
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GraphNode) {
			GraphNode other = (GraphNode) obj;
			return this.value.equals(other.value);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}
}
