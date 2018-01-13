/*
* Airport COnveyor System Node object
*/

package app.company.project.airportbaggagerouting.entity;


public class AirportGateNode {
	
	private String nodeName;	
	
	public AirportGateNode(String nodeName) {
		super();
		this.nodeName = nodeName;
	}

	public String getNodeName() {
		return nodeName;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nodeName == null) ? 0 : nodeName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AirportGateNode other = (AirportGateNode) obj;
		if (nodeName == null) {
			if (other.nodeName != null)
				return false;
		} else if (!nodeName.equals(other.nodeName))
			return false;
		return true;
	}  
	
	

}
