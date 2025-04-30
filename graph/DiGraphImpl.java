package graph;

import java.util.*;

public class DiGraphImpl implements DiGraph {

	private List<GraphNode> nodeList = new ArrayList<>();

	public Boolean addNode(GraphNode node) {
		if (getNode(node.getValue()) != null) {
			return false;
		}
		nodeList.add(node);
		return true;
	}

	public Boolean removeNode(GraphNode node) {
		GraphNode target = getNode(node.getValue());
		if (target == null) {
			return false;
		}

		for (int i = 0; i < nodeList.size(); i++) {
			nodeList.get(i).removeNeighbor(target);
		}

		nodeList.remove(target);
		return true;
	}

	public Boolean setNodeValue(GraphNode node, String newNodeValue) {
		GraphNode target = getNode(node.getValue());
		if (target == null || getNode(newNodeValue) != null) {
			return false;
		}
		target.setValue(newNodeValue);
		return true;
	}

	public String getNodeValue(GraphNode node) {
		GraphNode target = getNode(node.getValue());
		if (target == null) {
			return null;
		} else {
			return target.getValue();
		}
	}

	public Boolean addEdge(GraphNode fromNode, GraphNode toNode, Integer weight) {
		GraphNode from = getNode(fromNode.getValue());
		GraphNode to = getNode(toNode.getValue());
		if (from == null || to == null) {
			return false;
		}
		return from.addNeighbor(to, weight);
	}

	public Boolean removeEdge(GraphNode fromNode, GraphNode toNode) {
		GraphNode from = getNode(fromNode.getValue());
		GraphNode to = getNode(toNode.getValue());
		if (from == null || to == null) {
			return false;
		}
		return from.removeNeighbor(to);
	}

	public Boolean setEdgeValue(GraphNode fromNode, GraphNode toNode, Integer newWeight) {
		GraphNode from = getNode(fromNode.getValue());
		GraphNode to = getNode(toNode.getValue());
		if (from == null || to == null || from.getDistanceToNeighbor(to) == null) {
			return false;
		}
		from.removeNeighbor(to);
		from.addNeighbor(to, newWeight);
		return true;
	}

	public Integer getEdgeValue(GraphNode fromNode, GraphNode toNode) {
		GraphNode from = getNode(fromNode.getValue());
		GraphNode to = getNode(toNode.getValue());
		if (from == null || to == null) {
			return null;
		}
		return from.getDistanceToNeighbor(to);
	}

	public List<GraphNode> getAdjacentNodes(GraphNode node) {
		GraphNode target = getNode(node.getValue());
		if (target == null) {
			return null;
		} else {
			return target.getNeighbors();
		}
	}

	public Boolean nodesAreAdjacent(GraphNode fromNode, GraphNode toNode) {
		return getEdgeValue(fromNode, toNode) != null;
	}

	public Boolean nodeIsReachable(GraphNode fromNode, GraphNode toNode) {
		return bfs(fromNode, toNode) != null;
	}

	public Boolean hasCycles() {
		Set<GraphNode> white = new HashSet<>(nodeList);
		Set<GraphNode> gray = new HashSet<>();
		Set<GraphNode> black = new HashSet<>();

		for (int i = 0; i < nodeList.size(); i++) {
			GraphNode node = nodeList.get(i);
			if (white.contains(node) && hasCycleDFS(node, white, gray, black)) {
				return true;
			}
		}
		return false;
	}

	private boolean hasCycleDFS(GraphNode node, Set<GraphNode> white, Set<GraphNode> gray, Set<GraphNode> black) {
		white.remove(node);
		gray.add(node);

		List<GraphNode> neighbors = node.getNeighbors();
		for (int i = 0; i < neighbors.size(); i++) {
			GraphNode neighbor = neighbors.get(i);
			if (black.contains(neighbor)) {
				continue;
			}
			if (gray.contains(neighbor) || hasCycleDFS(neighbor, white, gray, black)) {
				return true;
			}
		}

		gray.remove(node);
		black.add(node);
		return false;
	}

	public int fewestHops(GraphNode fromNode, GraphNode toNode) {
		List<GraphNode> path = bfs(fromNode, toNode);
		if (path == null) {
			return -1;
		} else {
			return path.size() - 1;
		}
	}

	private List<GraphNode> bfs(GraphNode fromNode, GraphNode toNode) {
		GraphNode start = getNode(fromNode.getValue());
		GraphNode end = getNode(toNode.getValue());
		if (start == null || end == null) {
			return null;
		}

		Map<GraphNode, GraphNode> prev = new HashMap<>();
		Queue<GraphNode> queue = new LinkedList<>();
		Set<GraphNode> visited = new HashSet<>();

		queue.add(start);
		visited.add(start);

		while (!queue.isEmpty()) {
			GraphNode current = queue.poll();
			if (current.equals(end)) {
				return buildPath(prev, start, end);
			}

			List<GraphNode> neighbors = current.getNeighbors();
			for (int i = 0; i < neighbors.size(); i++) {
				GraphNode neighbor = neighbors.get(i);
				if (!visited.contains(neighbor)) {
					visited.add(neighbor);
					prev.put(neighbor, current);
					queue.add(neighbor);
				}
			}
		}
		return null;
	}

	public int shortestPath(GraphNode fromNode, GraphNode toNode) {
		List<GraphNode> path = dijkstra(fromNode, toNode);
		if (path == null) {
			return -1;
		}

		int sum = 0;
		for (int i = 0; i < path.size() - 1; i++) {
			sum += getEdgeValue(path.get(i), path.get(i + 1));
		}
		return sum;
	}
//reworked online impl
	private List<GraphNode> dijkstra(GraphNode fromNode, GraphNode toNode) {
		GraphNode start = getNode(fromNode.getValue());
		GraphNode end = getNode(toNode.getValue());
		if (start == null || end == null) {
			return null;
		}

		Map<GraphNode, Integer> dist = new HashMap<>();
		Map<GraphNode, GraphNode> prev = new HashMap<>();
		PriorityQueue<GraphNode> pq = new PriorityQueue<>(new Comparator<GraphNode>() {
			public int compare(GraphNode a, GraphNode b) {
				return Integer.compare(dist.get(a), dist.get(b));
			}
		});

		for (int i = 0; i < nodeList.size(); i++) {
			GraphNode node = nodeList.get(i);
			dist.put(node, Integer.MAX_VALUE);
		}
		dist.put(start, 0);
		pq.add(start);

		while (!pq.isEmpty()) {
			GraphNode current = pq.poll();
			if (current.equals(end)) {
				return buildPath(prev, start, end);
			}

			List<GraphNode> neighbors = current.getNeighbors();
			for (int i = 0; i < neighbors.size(); i++) {
				GraphNode neighbor = neighbors.get(i);
				int alt = dist.get(current) + current.getDistanceToNeighbor(neighbor);
				if (alt < dist.get(neighbor)) {
					dist.put(neighbor, alt);
					prev.put(neighbor, current);
					pq.add(neighbor);
				}
			}
		}
		return null;
	}

	private List<GraphNode> buildPath(Map<GraphNode, GraphNode> prev, GraphNode start, GraphNode end) {
		LinkedList<GraphNode> path = new LinkedList<>();
		GraphNode current = end;
		while (current != null) {
			path.addFirst(current);
			current = prev.get(current);
		}
		if (path.getFirst().equals(start)) {
			return path;
		} else {
			return null;
		}
	}

	public List<GraphNode> getNodes() {
		return nodeList;
	}

	public GraphNode getNode(String nodeValue) {
		for (int i = 0; i < nodeList.size(); i++) {
			GraphNode n = nodeList.get(i);
			if (n.getValue().equals(nodeValue)) {
				return n;
			}
		}
		return null;
	}
//temp add bc might be wrong idk
	public List<GraphNode> getFewestHopsPath(GraphNode fromNode, GraphNode toNode) {
		return bfs(fromNode, toNode);
	}

	public List<GraphNode> getShortestPath(GraphNode fromNode, GraphNode toNode) {
		return dijkstra(fromNode, toNode);
	}

	public Boolean addEdgeStr(String fromVal, String toVal, Integer weight) {
		GraphNode from = getNode(fromVal);
		GraphNode to = getNode(toVal);
		if (from == null || to == null) {
			return false;
		}
		return addEdge(from, to, weight);
	}
}
