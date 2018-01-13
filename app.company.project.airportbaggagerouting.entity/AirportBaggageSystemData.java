
/**
*Module description: model object for Sectional data from input file.
*/
package app.company.project.airportbaggagerouting.entity;

import java.util.List;
import java.util.Map;

public class AirportBaggageSystemData {
	
	private List<BiDirectionalEdge> edgesList;
	private Map<String,String> departuresMap;
	private List<BaggageItem> bagsList;
	
	public List<BiDirectionalEdge> getEdgesList() {
		return edgesList;
	}
	public void setEdgesList(List<BiDirectionalEdge> edgesList) {
		this.edgesList = edgesList;
	}
	public Map<String, String> getDeparturesMap() {
		return departuresMap;
	}
	public void setDeparturesMap(Map<String, String> departuresMap) {
		this.departuresMap = departuresMap;
	}
	public List<BaggageItem> getBagsList() {
		return bagsList;
	}
	public void setBagsList(List<BaggageItem> bagsList) {
		this.bagsList = bagsList;
	}
	
	

}
