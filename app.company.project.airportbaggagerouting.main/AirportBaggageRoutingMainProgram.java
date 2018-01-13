/*
* Driver program for processing input file and get required formatted output.
*/
package app.company.project.airportbaggagerouting.main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import app.company.project.airportbaggagerouting.entity.AirportBaggageSystemData;
import app.company.project.airportbaggagerouting.entity.AirportGateNode;
import app.company.project.airportbaggagerouting.entity.BaggageItem;
import app.company.project.airportbaggagerouting.entity.BiDirectionalEdge;
import app.company.project.airportbaggagerouting.entity.ConveyorGraph;
import app.company.project.airportbaggagerouting.service.AirportBaggageService;
import app.company.project.airportbaggagerouting.service.AirportBaggageServiceImpl;
import app.company.project.airportbaggagerouting.util.AirportBaggageRoutingConstants;
import app.company.project.airportbaggagerouting.util.BaggageInputFileValidationUtil;
import app.company.project.airportbaggagerouting.util.BaggageRoutingException;
import app.company.project.airportbaggagerouting.util.DijkstraAlgorithm;

public class AirportBaggageRoutingMainProgram {



	public static void main(String[] args) {
		// Key Assumption: Input content is available through some text file object path can be provided via input arguments or taking some default one as per requirements.
		//Step1:Read the input file.
		File inputDataFile = null;
		System.out.println("Input argument file path as per requirements:"+args[0]);
		if(null==args[0]){
			inputDataFile=new File(AirportBaggageRoutingConstants.INPUT_DATA_FILE_PATH);
		}else{
			inputDataFile=new File(args[0]);
		}

		if(inputDataFile.exists() && inputDataFile.length() > 0){
			System.out.println("File Object created based on input data file path.");

			/*
			 * Step 2:  Validate input content is in required format or not and get the input content model object
			 */

			try {
				BaggageInputFileValidationUtil oValidation = new BaggageInputFileValidationUtil();

				AirportBaggageSystemData oAirportBaggageSystemData = new AirportBaggageSystemData();

				oAirportBaggageSystemData = oValidation.validateInputFile(inputDataFile,oAirportBaggageSystemData);

				Map<String, String> departuresMap = oAirportBaggageSystemData.getDeparturesMap();

				List<BiDirectionalEdge> allEdgesList = oAirportBaggageSystemData.getEdgesList();
				//Step 3: Get unique Nodes from these Edges defined in input #Section 1:
				Set<AirportGateNode> airportGateNodeSet = new HashSet<AirportGateNode>();
				Set<String> nodeNamesSet = new HashSet<String>();
				for(BiDirectionalEdge directedEdge: allEdgesList){
					AirportGateNode sourceNode = directedEdge.getSourceNode();
					AirportGateNode destNode = directedEdge.getDestinationNode();					
					nodeNamesSet.add(sourceNode.getNodeName());
					nodeNamesSet.add(destNode.getNodeName());
				}

				System.out.println("Unique Node names:"+nodeNamesSet.size());
				for(String nodeName:nodeNamesSet){
					airportGateNodeSet.add(new AirportGateNode(nodeName));
				}
				List<AirportGateNode> definedNodesList = new ArrayList<AirportGateNode>(airportGateNodeSet);
				System.out.println("Unique List of Airport Gate Nodes defined in Section 1:"+definedNodesList.size());

				//Step 4: Create a Conveyor Graph from the available List of Nodes and bidirectional edges.
				ConveyorGraph conveyorgraph = new ConveyorGraph(definedNodesList, allEdgesList);

				//Step 5: Initiate DijkstraAlgorithm once for the given Conveyor System Graph object. 
				DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(conveyorgraph);


				//Step 6: Iterate through Input Bag Numbers ArrayList (maintains insertion order from input file) and Print required output format : <Flight Number> <OptimalPath> : <total time>
				System.out.println("Finding Optimal Route and Time between Arrival Node and Destination Node.");

				List<BaggageItem> bagsList = oAirportBaggageSystemData.getBagsList();

				for(BaggageItem bag: bagsList){
					String bagNumber = bag.getBagNumber();
					String entryGateNode = bag.getEntryGateNode();
					String flightNumber = bag.getFlightNumber();

					String destGateNode;
					if(flightNumber.equals(AirportBaggageRoutingConstants.FLIGHT_ARRIVAL)){
						destGateNode=AirportBaggageRoutingConstants.DESTINATION_NODE_BAGGAGE_CLAIM;
					}else{
						destGateNode=departuresMap.get(flightNumber);
					}
					//Step 6.x: Get Optimal Route for Baggage from Service which uses DijkstraAlgorithm
					AirportBaggageService oAirportBaggageService = new AirportBaggageServiceImpl();
					String optimalPathandTime = oAirportBaggageService.findOptimalRoutePathAndTime(entryGateNode, destGateNode, dijkstraAlgorithm);

					//Step 6.x: Output the required format.<Bag_Number> <point_1> <point_2> [<point_3>, …] : <total_travel_time>

					System.out.println(bagNumber+AirportBaggageRoutingConstants.WHITE_SPACE+optimalPathandTime);

				}

			} catch (BaggageRoutingException e) {
				//Log the exception.
				System.err.println("Exception details="+e.getMessage()+" && Error Code="+e.getErrorCode());
			}
		}else{
			//Log Error and return from program.
			System.err.println("Input Data File is either empty or doesn't exist in follwing path: "+AirportBaggageRoutingConstants.INPUT_DATA_FILE_PATH+" . Please verify and provide valid input file.");

		}

	}

}
