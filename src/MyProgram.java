import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.util.HashMap;

import Server.FlightServerInterface;

public class MyProgram {

	static final int REGISTRY_PORT = 4221;
	static final String HOSTS = "localhost";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String[] serverNames = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] managerId = new String[2];
		HashMap<String,String> citiesAlias = new HashMap<String,String>();
		citiesAlias.put("montreal", "MTL");
		citiesAlias.put("washington","WSL");
		citiesAlias.put("new delhi","NDH");
		String[] response;
		try {
			if (System.getSecurityManager() == null)
				System.setSecurityManager ( new SecurityManager() );

			serverNames = lookUpServers();
			FlightServerInterface server = null;


			System.out.println("****************************************************");
			System.out.println(" Flight Reservation System");
			System.out.println("****************************************************");
			System.out.println("");
			System.out.println("Please choose from the following menu");
			System.out.println("1) User View");
			System.out.println("2) Manager View");
			System.out.println("");
			System.out.print(":>_");
			String s = br.readLine();

			if(s.equals("1")) {
				System.out.println("");
				System.out.println("Following are the options for user mode:");
				System.out.println("1) Book a flight");
				System.out.println("");
				s = br.readLine();
				if(s.equals("1")) {

					String firstName, lastName, address, departure, arrival, deptDate, time, flightType = "Economy",phoneNumber;

					System.out.println("Departing from: ");
					departure = br.readLine();

					if(citiesAlias.containsKey(departure.toLowerCase()))
					{
						int index=0;
						for(int si=0;si<serverNames.length;si++)
						{
							if(serverNames[si].equals("//"+HOSTS+":"+REGISTRY_PORT+"/"+citiesAlias.get(departure)))
								index = si;
						}
						server = (FlightServerInterface) Naming.lookup("rmi:"+serverNames[index]);
					} else {
						System.out.println("The city you entered is not in the database, trying entering Montreal, Washington or New Delhi");
						throw new Exception();
					}


					System.out.println("Arriving to:");
					arrival = br.readLine();
					System.out.println("Departure date (YYYY-MM-DD):");
					deptDate = br.readLine();
					System.out.println("Departure Time (HH:MM):");
					time = br.readLine();
					System.out.println("Select your seating class:");
					System.out.println("1) Economy Class");
					System.out.println("2) Business Class");
					System.out.println("3) First Class");
					String cl = br.readLine();
					if(cl.equals("1"))
						flightType = "economy";
					else if(cl.equals("2"))
						flightType = "business";
					else if(cl.equals("3"))
						flightType = "firstclass";

					System.out.println("Enter your First name");
					firstName = br.readLine();
					System.out.println("Enter your Last name:");
					lastName = br.readLine();
					System.out.println("Enter your complete Address:");
					address = br.readLine();
					System.out.println("Telephone number:");
					phoneNumber = br.readLine();

					System.out.println("");
					System.out.println(">>>>>>>| Please Wait, while we book your flight |<<<<<<<");

					//Call BookFLight here
					response = server.bookFlight(firstName, lastName, address, phoneNumber, arrival, deptDate, time, flightType);
					if(response[0].equals("Success")) {
						System.out.println(response[0]+": "+response[1]);
						Logger.writeLog("Success","BookFlight", departure, "Flight has been successfuly booked with following information ("+departure+' '+deptDate+' '+time+' '+arrival+' '+firstName+' '+lastName, null);

					} else {
						System.out.println(response[0]+": "+response[1]);
						Logger.writeLog("Error","BookFlight", departure, "Error occured while booking a flight, please see server logs. ("+departure+' '+deptDate+' '+time+' '+arrival+' '+firstName+' '+lastName, null);
					}


				} else {
					System.out.println("Invalid option selected, the program is now exiting");
				}
			} else {
				System.out.println("****************************************************");
				System.out.println(" MANAGER MODE");
				System.out.println("****************************************************");
				System.out.println("");
				System.out.println("Enter your manager ID:");
				s = br.readLine();
				managerId[1] = s.substring(s.length()-4,s.length());
				managerId[0] = s.substring(0,s.length()-4);
				int index=0;
				for(int si=0;si<serverNames.length;si++)
				{
					if(serverNames[si].equals("//"+HOSTS+":"+REGISTRY_PORT+"/"+managerId[0]))
						index = si;
				}

				server = (FlightServerInterface) Naming.lookup("rmi:"+serverNames[index]);



				System.out.println("Following are the options for Manager mode:");
				System.out.println("1) Get booked flight count");
				System.out.println("2) Edit a flight record");
				System.out.println("3) Add a new flight record");
				System.out.println("4) Remove a flight record");
				s = br.readLine();
				if(s.equals("1")) {
					System.out.println("");
					System.out.println(">>>>>>>| Please Wait, contacting server for booked flight count. |<<<<<<<");
					System.out.println("Total Booked flight across all servers are: "+server.getBookedFlightCount(managerId[0]+managerId[1]));

				} else if(s.equals("2")) {
					//Edit a record
					int recordId;
					String fieldName="", newValue;

					System.out.println("Enter the Record ID: ");
					recordId = Integer.parseInt(br.readLine());
					System.out.println("What would you like to modify in a flight:");
					System.out.println("1) Arrival City");
					System.out.println("2) Departure Date (YYYY-MM-DD)");
					System.out.println("3) Departure Time (HH:MM)");
					System.out.println("4) Seating Capacity");
					s = br.readLine();
					if(s.equals("4"))
					{
						System.out.println("Select the seating class:");
						System.out.println("");
						System.out.println("1) Economy Class");
						System.out.println("2) Business Class");
						System.out.println("3) First Class");
						String cl = br.readLine();
						System.out.println("Enter the new capacity:");
						s = br.readLine();

						if(cl.equals("1"))
							fieldName = "economy";
						else if(cl.equals("2"))
							fieldName = "business";
						else if(cl.equals("3"))
							fieldName = "firstclass";
						newValue = s;


					} else {

						if(s.equals("1"))
							fieldName = "arrivalCity";
						else if(s.equals("2"))
							fieldName = "deptDate";
						else if(s.equals("3"))
							fieldName = "deptTime";

						System.out.println("Enter the new value:");
						newValue = br.readLine();

					}

					System.out.println("");
					System.out.println(">>>>>>>| Please Wait, while we modify the flight record |<<<<<<<");
					newValue+="|"+managerId[0]+managerId[1];
					response = server.editFlightRecord(recordId, fieldName, newValue);
					if(response[0].equals("Success")) {
						System.out.println(response[0]+": "+response[1]);
						Logger.writeLog("Success","editFlightRecord", "", "Flight record has been successfuly changed with the information ("+recordId+' '+fieldName+' '+newValue, managerId[0]+managerId[1]);

					} else {
						System.out.println(response[0]+": "+response[1]);
						Logger.writeLog("Error","BookFlight", "", "Error occured while modifying your flight record, please see server logs. ("+recordId+' '+fieldName+' '+newValue, managerId[0]+managerId[1]);
					}


				} else if(s.equals("3")) {
					//add a new flight

					String arrivalCity, deptDate, deptTime;
					int economySeats, businessSeats, firstClassSeats;

					System.out.println("");
					System.out.println("Enter the destination city:");
					arrivalCity = br.readLine();
					System.out.println("Enter the departure date (YYYY-MM-DD):");
					deptDate = br.readLine();
					System.out.println("Enter the departure time (HH:MM):");
					deptTime = br.readLine();
					System.out.println("Seating capacity for ECONOMY CLASS:");
					economySeats = Integer.parseInt(br.readLine());
					System.out.println("Seating capacity for BUSINESS CLASS:");
					businessSeats = Integer.parseInt(br.readLine());
					System.out.println("Seating capacity for FIRST CLASS:");
					firstClassSeats = Integer.parseInt(br.readLine());

					response = server.addFlightRecord(arrivalCity+"|"+managerId[0]+managerId[1], deptDate, deptTime, economySeats, businessSeats, firstClassSeats);

					if(response[0].equals("Success")) {
						System.out.println(response[0]+": "+response[1]);
						Logger.writeLog("Success","AddFlight", managerId[0], "A new Flight has been successfuly added ("+managerId[0]+' '+deptDate+' '+deptTime+' '+arrivalCity, managerId[0]+managerId[1]);

					} else {
						System.out.println(response[0]+": "+response[1]);
						Logger.writeLog("Error","AddFlight", managerId[0], "Error occured while adding a new flight, please see server logs. ("+managerId[0]+' '+deptDate+' '+deptTime+' '+arrivalCity, managerId[0]+managerId[1]);
					}


				} else if(s.equals("4")) {
					//remove a flight record
					int recordId;
					String deptDate;
					System.out.println("");
					System.out.println("Enter the departure date:");
					deptDate = br.readLine();
					System.out.println("Enter the Record ID: ");
					recordId = Integer.parseInt(br.readLine());
					response = server.removeFlight(deptDate+"|"+managerId[0]+managerId[1], recordId);
					if(response[0].equals("Success")) {
						System.out.println(response[0]+": "+response[1]);
						Logger.writeLog("Success","removeFlight", managerId[0], "Your Flight has been successfuly removed ("+managerId[0]+' '+deptDate+' '+recordId, managerId[0]+managerId[1]);

					} else {
						System.out.println(response[0]+": "+response[1]);
						Logger.writeLog("Error","AddFlight", managerId[0], "Error occured while removing your flight, please see server logs. ("+managerId[0]+' '+deptDate+' '+recordId, managerId[0]+managerId[1]);
					}

				} else {
					System.out.println("Invalid option selected, the program is now exiting");
				}
			}


		} catch (Exception e)
		{
			System.out.println("Error:" + e.getMessage());
		}

	}

	private static String[] lookUpServers() throws Exception
	{
		String lookupHost = HOSTS + ":" + REGISTRY_PORT +"/";
		String[] names = Naming.list("rmi://"+lookupHost);
		return names;
	}

}
