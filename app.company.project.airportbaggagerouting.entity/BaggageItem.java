/*
* Bags Section model object
*/
package app.company.project.airportbaggagerouting.entity;

public class BaggageItem {
	
	private String bagNumber;
	private String entryGateNode;
	private String flightNumber;
	
	
	public BaggageItem(String bagNumber, String entryGateNode,
			String flightNumber) {
		super();
		this.bagNumber = bagNumber;
		this.entryGateNode = entryGateNode;
		this.flightNumber = flightNumber;
	}
	public String getBagNumber() {
		return bagNumber;
	}
	public String getEntryGateNode() {
		return entryGateNode;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	
	

}
