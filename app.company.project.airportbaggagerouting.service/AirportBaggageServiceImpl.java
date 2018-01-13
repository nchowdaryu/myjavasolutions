package app.company.project.airportbaggagerouting.service;

import java.util.LinkedList;
import java.util.Map;

import app.company.project.airportbaggagerouting.entity.AirportGateNode;
import app.company.project.airportbaggagerouting.util.AirportBaggageRoutingConstants;
import app.company.project.airportbaggagerouting.util.BaggageRoutingException;
import app.company.project.airportbaggagerouting.util.DijkstraAlgorithm;

public class AirportBaggageServiceImpl implements AirportBaggageService {
	
	
	@Override
	public String findOptimalRoutePathAndTime(String entryNode,
			String destNode, DijkstraAlgorithm dijkstra)
			throws BaggageRoutingException {
		
		
		String optimalPathandTime = "";
		try{
			//Execute the Algorithm for given sourceNode.
			dijkstra.execute(new AirportGateNode(entryNode));
			Map<Integer,LinkedList<AirportGateNode>> optimalPathAndTimeMap = dijkstra.getOptimalPathAndTimeMap(new AirportGateNode(destNode));
            
            
            for (Map.Entry<Integer, LinkedList<AirportGateNode>> entry : optimalPathAndTimeMap.entrySet()) {
            	Integer key = entry.getKey();	                	
                LinkedList<AirportGateNode> pathList = entry.getValue();
                
                StringBuilder sb = new StringBuilder();
                //Concatenate All Travel Path  Nodes.
            	for (AirportGateNode node : pathList) {
            		sb.append(node.getNodeName());
            		sb.append(AirportBaggageRoutingConstants.WHITE_SPACE);
                   
                }
            	
            	//Concatenate  Travel Time.	                	
            	sb.append(AirportBaggageRoutingConstants.COLAN);
            	sb.append(AirportBaggageRoutingConstants.WHITE_SPACE);
            	sb.append(key);
            	
            	optimalPathandTime = sb.toString();
            }                
			
		}catch(Exception e){
			
			throw new BaggageRoutingException("Something went wrong during Optimal Route find. Exception details="+e.getMessage(), AirportBaggageRoutingConstants.CUSTOM_ERROR_CODE_OPTIMAL_ROUTE_FLOW);
			
		}
    
		return optimalPathandTime;
	}	
  
}
