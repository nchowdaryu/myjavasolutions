package app.company.project.airportbaggagerouting.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import app.company.project.airportbaggagerouting.entity.AirportGateNode;
import app.company.project.airportbaggagerouting.entity.BiDirectionalEdge;
import app.company.project.airportbaggagerouting.entity.ConveyorGraph;

public class DijkstraAlgorithm {
	
	private final List<AirportGateNode> nodes;
	private final List<BiDirectionalEdge> edges;
	private Set<AirportGateNode> settledNodes;
	private Set<AirportGateNode> unSettledNodes;
	private Map<AirportGateNode, AirportGateNode> predecessors;
	private Map<AirportGateNode, Integer> distance;

	public DijkstraAlgorithm(ConveyorGraph graph) {
		// create a copy of the array so that we can operate on this array
		this.nodes = new ArrayList<AirportGateNode>(graph.getNodes());
		this.edges = new ArrayList<BiDirectionalEdge>(graph.getEdges());
	}
	
	
	public void execute(AirportGateNode source) {
		settledNodes = new HashSet<AirportGateNode>();
		unSettledNodes = new HashSet<AirportGateNode>();
		distance = new HashMap<AirportGateNode, Integer>();
		predecessors = new HashMap<AirportGateNode, AirportGateNode>();
		distance.put(source, 0);
		unSettledNodes.add(source);
		while (unSettledNodes.size() > 0) {
			AirportGateNode node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistances(node);
		}
	}	
	

	private void findMinimalDistances(AirportGateNode node) {
		List<AirportGateNode> adjacentNodes = getNeighbors(node);
		for (AirportGateNode target : adjacentNodes) {
			int i= getShortestDistance(node) + getDistance(node, target);
			if (getShortestDistance(target) > i) {
				distance.put(target, i);
				predecessors.put(target, node);
				unSettledNodes.add(target);
			}
		}

	}
	/**
	 * Gets the travel time between two Nodes - i.e travel time of given Directed Edge between those 2 nodes.
	 * @param node
	 * @param target
	 * @return
	 */

	private int getDistance(AirportGateNode node, AirportGateNode target) {
		for (BiDirectionalEdge edge : edges) {
			if (edge.getSourceNode().getNodeName().equals(node.getNodeName())
					&& edge.getDestinationNode().equals(target)) {
				return edge.getTime();
			}
		}
		throw new RuntimeException("Should not happen");
	}

	private List<AirportGateNode> getNeighbors(AirportGateNode node) {
		List<AirportGateNode> neighbors = new ArrayList<AirportGateNode>();
		for (BiDirectionalEdge edge : edges) {
			if (edge.getSourceNode().getNodeName().equals(node.getNodeName())
					&& !isSettled(edge.getDestinationNode())) {
				neighbors.add(edge.getDestinationNode());
			}			
		
		}
		return neighbors;
	}

	private AirportGateNode getMinimum(Set<AirportGateNode> vertexes) {
		AirportGateNode minimum = null;
		for (AirportGateNode vertex : vertexes) {
			if (minimum == null) {
				minimum = vertex;
			} else {
				if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
					minimum = vertex;
				}
			}
		}
		return minimum;
	}

	private boolean isSettled(AirportGateNode vertex) {
		return settledNodes.contains(vertex);
	}

	private int getShortestDistance(AirportGateNode destination) {
		Integer d = distance.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}

	/*
	 * This method returns the path from the source to the selected target and
	 * NULL if no path exists
	 */
	public Map<Integer,LinkedList<AirportGateNode>> getOptimalPathAndTimeMap(AirportGateNode target) {
		LinkedList<AirportGateNode> path = new LinkedList<AirportGateNode>();
		AirportGateNode step = target;
		int totalSum=0;
		// check if a path exists
		if (predecessors.get(step) == null) {
			return null;
		}
		path.add(step);
		while (predecessors.get(step) != null) {
			totalSum+=getDistance(step, predecessors.get(step));
			step = predecessors.get(step);
			path.add(step);
		}
		// Put it into the correct order
		Collections.reverse(path);
		
		//Create a map that holds path & travel time.
		Map<Integer,LinkedList<AirportGateNode>> map = new HashMap<Integer,LinkedList<AirportGateNode>>();
		map.put(Integer.valueOf(totalSum), path);
		return map;
	}	
	
}
