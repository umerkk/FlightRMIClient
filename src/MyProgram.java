import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;

public class MyProgram {

	static final int REGISTRY_PORT = 4221;
	static final String HOSTS = "localhost";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String[] serverNames = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try {
			if (System.getSecurityManager() == null)
	            System.setSecurityManager ( new SecurityManager() );
			
			//serverNames = lookUpServers();
			
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
					
					String firstName, lastName, address, departure, arrival, deptDate, time, flightType,phoneNumber;
					
					System.out.println("Departing from: ");
					departure = br.readLine();
					System.out.println("Arriving to:");
					arrival = br.readLine();
					System.out.println("Departure date:");
					deptDate = br.readLine();
					System.out.println("Departure Time:");
					time = br.readLine();
					System.out.println("Select your seating class:");
					System.out.println("1) Economy Class");
					System.out.println("2) Business Class");
					System.out.println("3) First Class");
					String cl = br.readLine();
					if(cl.equals("1"))
						flightType = "Economy";
					else if(cl.equals("2"))
						flightType = "Business";
					else if(cl.equals("3"))
						flightType = "FirstClass";
					
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
					
					
				} else {
					System.out.println("Invalid option selected, the program is now exiting");
				}
			} else {
				System.out.println("****************************************************");
				System.out.println(" MANAGER MODE");
				System.out.println("****************************************************");
				System.out.println("");
				System.out.println("Following are the options for Manager mode:");
				System.out.println("1) Get booked flight count");
				System.out.println("2) Edit a flight record");
				s = br.readLine();
				if(s.equals("1")) {
					System.out.println("");
					System.out.println(">>>>>>>| Please Wait, contacting server for booked flight count. |<<<<<<<");
				} else if(s.equals("2")) {
					//Edit a record
					int recordId;
					String fieldName, newValue;
					
					System.out.println("Enter the Record ID: ");
					recordId = Integer.parseInt(br.readLine());
					System.out.println("What would you like to modify in a flight:");
					System.out.println("1) Arrival City");
					System.out.println("2) Departure Date");
					System.out.println("3) Departure Time");
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

					
				} else {
					System.out.println("Invalid option selected, the program is now exiting");
				}
			}
			
			//System.out.println("****************************************************");
			
			
			/*FlightServerInterface server = (FlightServerInterface) Naming.lookup("rmi:"+serverNames[0]);
			
			
			System.out.println(server.addFlightRecord("Karachi", "2016-12-25", "21:05", 10, 20, 30));
			System.out.println(server.addFlightRecord("Houston", "2016-12-25", "21:05", 10, 20, 30));
			
			System.out.println(server.bookFlight("Muhammad", "Umer", "SDFF", 43434, "Karachi", "2016-12-25", "21:05", "Economy"));
			*/
			
			
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
