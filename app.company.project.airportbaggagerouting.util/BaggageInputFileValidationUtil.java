package app.company.project.airportbaggagerouting.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.company.project.airportbaggagerouting.entity.AirportBaggageSystemData;
import app.company.project.airportbaggagerouting.entity.AirportGateNode;
import app.company.project.airportbaggagerouting.entity.BaggageItem;
import app.company.project.airportbaggagerouting.entity.BiDirectionalEdge;

public class BaggageInputFileValidationUtil {

	/**
	 * This method validates all the contents of input file is according to required format or not
	 * and sets data sections to given object.
	 * @param inputDataFile
	 * @param oAirportBaggageSystemData
	 * @return
	 * @throws BaggageRoutingException
	 */
	public AirportBaggageSystemData validateInputFile(File inputDataFile, AirportBaggageSystemData oAirportBaggageSystemData) throws BaggageRoutingException{

		final String methodName= "validateInputFile";
		StringBuilder fileContent = new StringBuilder();
		boolean isValidConveyorData, isValidDeparturesData, isValidBagsData = false;
		try {

			//Iterate through file content and set it in a String object for better validations.

			FileReader fileReader = new FileReader(inputDataFile);

			// Always wrap FileReader in BufferedReader for better performance.
			BufferedReader bufferedReader =  new BufferedReader(fileReader);
			String line;
			while((line = bufferedReader.readLine()) != null) {
				if(!line.trim().isEmpty()){ //Removes any Leading or trailing spaces and empty lines.
					fileContent.append(line.trim()); 
					fileContent.append(AirportBaggageRoutingConstants.NEW_LINE_SEPARATOR); //
				}

			}

			//Close the I/O access objects.
			fileReader.close();
			bufferedReader.close();

			//System.out.println("File content="+fileContent.toString().trim()); -> Debug log

			//Split the File content based on various standard sections and validate.
			String conveyorData;
			String departuresData;
			String bagsData;

			String[] splitContentParts = fileContent.toString().trim().split(AirportBaggageRoutingConstants.AIRPORT_BAGGAGE_DATA_HEADER);
			System.out.println("Split Content Parts lenght:="+splitContentParts.length);
			if(splitContentParts.length==4){ //Ensuring Conveyor Header Data is present
				conveyorData = splitContentParts[1];
				departuresData = splitContentParts[2];
				bagsData = splitContentParts[3];

				String[] splitConveyorDataParts = conveyorData.split("\n");
				isValidConveyorData = isValidConveyorData(splitConveyorDataParts, oAirportBaggageSystemData);
				System.out.println("Conveyor Data is valid:"+isValidConveyorData);

				String[] splitDepartureDataParts = departuresData.split("\n");
				isValidDeparturesData = isValidDeparturesData(splitDepartureDataParts, oAirportBaggageSystemData);
				System.out.println("Departures Data is valid:"+isValidDeparturesData);

				String[] splitBagsDataParts = bagsData.split("\n");
				isValidBagsData = isValidBagsData(splitBagsDataParts, oAirportBaggageSystemData);
				System.out.println("Bags Data is valid:"+isValidBagsData);

			}else{
				String errorMessage = "File Input Content is not in correct format. Please check input requiremnts.";

				throw new BaggageRoutingException(errorMessage, AirportBaggageRoutingConstants.CUSTOM_ERROR_CODE_FILE_VALIDATION);


			}          


		} catch (Exception e) {
			//Build the error and re-throw it.
			String errorMessage = "Something went wrong in method:"+methodName+". Exception details="+e.getMessage();

			throw new BaggageRoutingException(errorMessage, AirportBaggageRoutingConstants.CUSTOM_ERROR_CODE_FILE_VALIDATION);
		}

		return oAirportBaggageSystemData;

	}
	/**
	 * This method validates bi-directional graph describing the conveyor system Format: <Node 1> <Node 2> <travel_time>

	 * @param splitConveyorDataParts
	 * @return
	 */

	public boolean isValidConveyorData(String[] splitConveyorDataParts, AirportBaggageSystemData oAirportBaggageSystemData){
		boolean isValid = false;
		List<BiDirectionalEdge> edgesList=new ArrayList<BiDirectionalEdge>();

		int length = splitConveyorDataParts.length;
		System.out.println("splitConveyorDataParts length:"+length);
		String splitconveyorDataPartsHeader = splitConveyorDataParts[0].trim();
		System.out.println("splitconveyorDataParts header line:"+splitconveyorDataPartsHeader);
		if(length>2 && AirportBaggageRoutingConstants.AIRPORT_BAGGAGE_CONVEYOR_DATA_HEADER.equalsIgnoreCase(splitconveyorDataPartsHeader)){ //ensuring atleast one

			for(int i=1; i< length; i++){ //Skip the Header and validate data, hence index=1
				//System.out.println(splitConveyorDataParts[i]); > debug Log.
				String[] subDataParts = splitConveyorDataParts[i].trim().split("\\s+");
				int subDataLength = subDataParts.length;
				//System.out.println("subDataParts length:"+subDataLength); > debug Log.
				if(subDataLength!=3){
					isValid = false;
					System.err.println("Conveyor System Data is not in correct format. Please read input content requirements.");
					break;
				}

				isValid = true;
				BiDirectionalEdge edge = new BiDirectionalEdge(new AirportGateNode(subDataParts[0]), new AirportGateNode(subDataParts[1]), Integer.valueOf(subDataParts[2]));
				BiDirectionalEdge reverseEdge = new BiDirectionalEdge(new AirportGateNode(subDataParts[1]), new AirportGateNode(subDataParts[0]), Integer.valueOf(subDataParts[2]));

				edgesList.add(edge);
				edgesList.add(reverseEdge);
			}

			System.out.println("Total No.of Edges from Conveyor System."+edgesList.size());
			oAirportBaggageSystemData.setEdgesList(edgesList);
		}

		return isValid;

	}
	/**
	 * This method validates Departures Data format. <flight_id> <flight_gate> <destination_place> <flight_time>
	 * @param splitDeparturesDataParts
	 * @return boolean
	 */

	public boolean isValidDeparturesData(String[] splitDeparturesDataParts, AirportBaggageSystemData oAirportBaggageSystemData){
		boolean isValid = false;
		Map<String,String> departuresMap = new HashMap<String,String>();

		int length = splitDeparturesDataParts.length;
		System.out.println("splitDeparturesDataParts length:"+length);
		String splitDeparturesDataPartsHeader = splitDeparturesDataParts[0].trim();
		System.out.println("splitDeparturesDataParts header line:"+splitDeparturesDataPartsHeader);
		if(length>2 && AirportBaggageRoutingConstants.AIRPORT_BAGGAGE_DEPARTURES_DATA_HEADER.equalsIgnoreCase(splitDeparturesDataPartsHeader)){ //ensuring atleast one

			for(int i=1; i< length; i++){ //Skip the Header and validate data, hence index=1
				//System.out.println(splitDeparturesDataParts[i]); //> debug Log.
				String[] subDataParts = splitDeparturesDataParts[i].trim().split("\\s+");
				int subDataLength = subDataParts.length;
				//System.out.println("subDataParts length:"+subDataLength); -> debug Log.
				if(subDataLength!=4){
					isValid = false;
					System.err.println("Departures Data is not in correct format. Please read input content requirements.");
					break;
				}

				isValid = true;
				departuresMap.put(subDataParts[0], subDataParts[1]); // key - Flight Number : value - AirportGateNode

			}

			System.out.println("Total No.of Departures."+departuresMap.size());
			oAirportBaggageSystemData.setDeparturesMap(departuresMap);
		}

		return isValid;

	}

	/**
	 * This method validates Baggage List Format <bag_number> <entry_point> <flight_id>	
	 * @param splitBagsDataParts
	 * @return
	 */
	public boolean isValidBagsData(String[] splitBagsDataParts, AirportBaggageSystemData oAirportBaggageSystemData){
		boolean isValid = false;
		List<BaggageItem> bagsList = new ArrayList<BaggageItem>(); //Form a List Object to Hold bags

		int length = splitBagsDataParts.length;
		System.out.println("splitBagsDataParts length:"+length);
		String splitBagsDataPartsHeader = splitBagsDataParts[0].trim();
		System.out.println("splitBagsDataParts header line:"+splitBagsDataPartsHeader);
		if(length>2 && AirportBaggageRoutingConstants.AIRPORT_BAGGAGE_BAGS_DATA_HEADER.equalsIgnoreCase(splitBagsDataPartsHeader)){ //ensuring atleast one

			for(int i=1; i< length; i++){ //Skip the Header and validate data, hence index=1
				//System.out.println(splitBagsDataParts[i]); > debug Log.
				String[] subDataParts = splitBagsDataParts[i].trim().split("\\s+");
				int subDataLength = subDataParts.length;
				//System.out.println("subDataParts length:"+subDataLength); > debug Log.
				if(subDataLength!=3){
					isValid = false;
					System.err.println("Bags Data is not in correct format. Please read input content requirements.");
					break;
				}

				isValid = true;
				BaggageItem oBag = new BaggageItem(subDataParts[0], subDataParts[1], subDataParts[2]);
				bagsList.add(oBag);
			}

			System.out.println("Total No.of Bags to be routed."+bagsList.size());
			oAirportBaggageSystemData.setBagsList(bagsList);
		}

		return isValid;

	}

}
