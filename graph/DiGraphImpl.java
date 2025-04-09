package graph;

import java.util.*;

public class DiGraphImpl implements DiGraph {

    private List<GraphNode> nodeList = new ArrayList<>();

    @Override
    public Boolean addNode(GraphNode node) {
        if (!nodeList.contains(node)) {
            nodeList.add(node);
            return true;
        }
        return false;
    }

    @Override
    public Boolean removeNode(GraphNode node) {
        if (!nodeList.contains(node)) return false;

        nodeList.remove(node);
        for (GraphNode n : nodeList) {
            n.removeEdge(node);
        }
        return true;
    }

    @Override
    public Boolean setNodeValue(GraphNode node, String newNodeValue) {
        for (GraphNode n : nodeList) {
            if (n.equals(node)) {
                n.setValue(newNodeValue);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getNodeValue(GraphNode node) {
        for (GraphNode n : nodeList) {
            if (n.equals(node)) {
                return n.getValue();
            }
        }
        return null;
    }

    @Override
    public Boolean addEdge(GraphNode fromNode, GraphNode toNode, Integer weight) {
        GraphNode from = getNode(fromNode.getValue());
        GraphNode to = getNode(toNode.getValue());
        if (from == null || to == null) return false;
        from.addEdge(to, weight);
        return true;
    }

    @Override
    public Boolean removeEdge(GraphNode fromNode, GraphNode toNode) {
        GraphNode from = getNode(fromNode.getValue());
        GraphNode to = getNode(toNode.getValue());
        if (from == null || to == null) return false;
        from.removeEdge(to);
        return true;
    }

	@Override 
	public Boolean setEdgeValue(GraphNode fromNode, GraphNode toNode, Integer newWeight) {
		GraphNode from = getNode((fromNode.getValue()));
		GraphNode to = getNode(toNode.getValue());
		if (from == null || to == null) return false;
		from.setEdgeWeight(to, newWeight);
		return true;
	}

	@Override
	public Integer getEdgeValue(GraphNode fromNode, GraphNode toNode) {
		GraphNode from = getNode(fromNode.getValue());
		GraphNode to = getNode(toNode.getValue());
		if (from == null || to == null) return null;
		return from.getEdgeWeight(to);

	}

	@Override 
	public List<GraphNode> getAdjacentNodes(GraphNode node) {
		GraphNode n = getNode(node.getValue());
		if (n == null) return null;
		
	}
