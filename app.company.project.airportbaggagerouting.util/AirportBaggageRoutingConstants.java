package app.company.project.airportbaggagerouting.util;

public class AirportBaggageRoutingConstants {
	
	public static final String INPUT_DATA_FILE_PATH = "resources/InputData.txt";
	
	public static final String  NEW_LINE_SEPARATOR= "\n";
	public static final String  SPACE_SEPARATOR= "\\s+";
	
	public static final String AIRPORT_BAGGAGE_CONVEYOR_DATA_HEADER = "Conveyor System";
	public static final String AIRPORT_BAGGAGE_DEPARTURES_DATA_HEADER = "Departures";
	public static final String AIRPORT_BAGGAGE_BAGS_DATA_HEADER = "Bags";
	public static final String AIRPORT_BAGGAGE_DATA_HEADER = "# Section:";
	
	public static final String FLIGHT_ARRIVAL = "ARRIVAL";
	public static final String WHITE_SPACE= " ";
	public static final String COLAN= ":";
	public final static String DESTINATION_NODE_BAGGAGE_CLAIM ="BaggageClaim";
	
	//Application error codes - 9000 - 9999
	
	public static final int CUSTOM_ERROR_CODE_FILE_VALIDATION = 9001;
	public static final int CUSTOM_ERROR_CODE_AIRPORT_NODE_VALIDATION = 9002;
	public static final int CUSTOM_ERROR_CODE_OPTIMAL_ROUTE_FLOW = 9003;
	
	//Testing purpose
	public static final String INPUT_DATA_FILE_PATH_EMPTY = "test/resources/InputData_Empty.txt";
  public static final String INPUT_DATA_FILE_PATH_HeadersOnly = "test/resources/InputData_HeadersOnly.txt";
  public static final String INPUT_DATA_FILE_PATH_INVALID = "test/resources/InputData_Invalid.txt";

}
