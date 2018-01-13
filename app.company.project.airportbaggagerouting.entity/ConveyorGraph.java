/*
* A Graph object consists of List of Nodes and List of Edges
*/
package app.company.project.airportbaggagerouting.entity;

import java.util.List;

public class ConveyorGraph {
	
	private final List<AirportGateNode> nodes;
    private final List<BiDirectionalEdge> edges;

    public ConveyorGraph(List<AirportGateNode> nodes, List<BiDirectionalEdge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public List<AirportGateNode> getNodes() {
        return nodes;
    }

    public List<BiDirectionalEdge> getEdges() {
        return edges;
    }

}
