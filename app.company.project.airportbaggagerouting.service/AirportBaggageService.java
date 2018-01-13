package app.company.project.airportbaggagerouting.service;

import app.company.project.airportbaggagerouting.util.BaggageRoutingException;
import app.company.project.airportbaggagerouting.util.DijkstraAlgorithm;

public interface AirportBaggageService {
	/**
	 * This method returns optimal route path between source and destination nodes based on DijkstraAlgorithm
	 * @param entryNode
	 * @param destNode
	 * @param dijkstra
	 * @return
	 * @throws BaggageRoutingException
	 */
	public String findOptimalRoutePathAndTime(String entryNode,String destNode, DijkstraAlgorithm dijkstra) throws BaggageRoutingException;

}
