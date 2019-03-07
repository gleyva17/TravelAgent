package AirTravel;

import Travel.SeatClass;
import java.util.HashMap;

public class SystemManager {
	
	private HashMap<String, Airport> airports;
    private HashMap<String, Airline> airlines;

    public SystemManager()
    {
        airports = new HashMap<>();
        airlines = new HashMap<>();
    }

    public void createAirport(String aName)
    {
        if (aName == null)
        {
            System.out.println("Null is not a valid name for an Airport!");
        }
        else
        {
            try
            {
                if (!airports.containsKey(aName))
                {
                    airports.put(aName, new Airport(aName));
                    System.out.println("Airport " + aName + " was added");
                }
                else
                {
                    System.out.println("Airport "+ aName +"already exists!");
                }
            }
            catch (IllegalArgumentException e)
            {
                System.out.println("Airport names must be exactly 3 characters long!");
            }
        }
    }

    public void createAirline(String aName)
    {
        if (aName == null)
        {
            System.out.println("Null is not a valid name for an airline!");
        }
        else
        {
            try
            {
                if (!airlines.containsKey(aName))
                {
                    airlines.put(aName, new Airline(aName));
                    System.out.println("Airline " + aName + " was added");
                }
                else
                {
                    System.out.println("Airport "+ aName +" already exists!");
                }
            }
            catch (IllegalArgumentException e)
            {
                System.out.println("Airline names must be between 1 and 6 alphanumeric characters!");
            }
        }
    }

    public void createFlight(String airportName, String origin, String destination, int year, int month, int day, String id)
    {
        if (airports.containsKey(origin) && airports.containsKey(destination))
        {
            if (airlines.containsKey(airportName))
            {
                Airline temp = airlines.get(airportName);
                if (temp.addFlight(id, origin, destination, year, month, day))
                {
                    System.out.println("Flight " + id + " was added to airline " + airportName);
                }
                else
                {
                    System.out.println("Flight could not be added!");
                }

            }
            else
            {
                System.out.println("Airline doesn't exist!");
            }
        }
        else
        {
            System.out.println("Origin or destination is invalid.");
        }
    }

    public void createSection(String air, String flightID, int rows, int columns, SeatClass seatClass)
    {
        if (seatClass == null)
        {
            System.out.println("seat class cannot be Null!");
        }
        else
        {
            if (airlines.containsKey(air))
            {
                Airline temp = airlines.get(air);
                if (temp.createFlightSection(flightID, seatClass, rows, columns))
                {
                    System.out.println("Section in "+ seatClass +" was added to flight " + flightID);
                }
                else
                {
                    System.out.println("Flight section could not be added!");
                }

            }
            else
            {
                System.out.println("Airline "+ air +" doesn't exist!");
            }
        }
    }

    public void createSection(String air, String flightID, int rows, char columns, SeatClass seatClass)
    {
        if (seatClass == null)
        {
            System.out.println("Seat class cannot be Null!");
        }
        else
        {
            if (airlines.containsKey(air))
            {
                Airline temp = airlines.get(air);
                if (temp.createFlightSection(flightID, seatClass, rows, columns))
                {
                    System.out.println("Section in "+ seatClass +" class was added to flight " + flightID);
                }
                else
                {
                    System.out.println("Flight section could not be added!");
                }

            }
            else
            {
                System.out.println("Airline "+ air +" doesn't exist!");
            }
        }
    }

    public void findAvailableFlights(String origin, String destination, int... ints) //[Origin, Destination, Year, Month, Day]
    {
        int[] temp = new int[3];
        if (origin == null || destination == null || !airports.containsKey(origin) || !airports.containsKey(destination))
        {
            System.out.println("Unknown Origin or Destination!");
            return;
        }

        if (ints.length > 0)
        {
            temp[0] = ints[0];
            temp[1] = ints[1];
            temp[2] = ints[2];
        }

        System.out.printf("Flights from %s to %s\n", origin, destination);
        boolean isFlight = false;
        for (String tempAirLiner : airlines.keySet())
        {
            for (String flight : airlines.get(tempAirLiner).getFlight(origin, destination, temp[0], temp[1], temp[2]))
            {
                isFlight = true;
                System.out.println("\t" + flight);
            }
        }
        if (!isFlight)
        {
            System.out.println("\tNo Flights Scheduled");
        }
    }

    public void bookSeat(String airline, String flight, SeatClass seatClass, int row, char col)
    {
        if (row == 0 || col == 0)
        {
            System.out.println("Seat position cannot be Null!");
        }
        else
        {
            if (airlines.containsKey(airline))
            {
                Airline temp = airlines.get(airline);
                if (temp.bookSeat(flight, seatClass, row, col))
                {
                    System.out.println("Seat " + row + col + " of flight " + flight + " was booked in " + seatClass + " class");
                }
                else
                {
                    System.out.println("Seat could not be booked!");
                }

            }
            else
            {
                System.out.println("Airline " + airline +" doesn't exist!");
            }
        }
    }

    public void bookSeat(String airline, String flight, SeatClass seatClass, boolean window, boolean aisle)
    {
        if (airlines.containsKey(airline))
        {
            Airline temp = airlines.get(airline);
            if (temp.bookSeat(flight, seatClass, window, aisle))
            {
                if (window)
                {
                    System.out.println("A window seat on flight " + flight + ", in "+ seatClass+ "class was successfully booked");
                }
                else if (aisle)
                {
                    System.out.println("An aisle seat on flight " + flight + ", in "+ seatClass+  " class was successfully booked");
                }
                else
                {
                    System.out.println("A regular seat on flight " + flight + ", in "+ seatClass+ " class was successfully booked");
                }
            }
            else
            {
                System.out.println("Seat could not be booked!");
            }

        }
        else
        {
            System.out.println( "Airline "+ airline + " doesn't exist!");
        }
    }

    public void changePricing(String airline, String source, String destination, SeatClass seatClass, double newPrice)
    {
        if (!airlines.containsKey(airline))
        {
            System.out.println(airline +" is an Unknown Airline");
            return;
        }
        if (!airports.containsKey(source) || !airports.containsKey(destination))
        {
            System.out.println("Unknown Airport");
            return;
        }

        if (airlines.get(airline).setPricing(source, destination, seatClass, newPrice))
        {
            System.out.println("Price was successfully changed.");
        }
        else
        {
            System.out.println("Failed to change price");
        }
    }

    public void displaySystemDetails()
    {
        for (String aLiner : airlines.keySet())
        {
            airlines.get(aLiner).display();
        }
    }

    public void addAirports(Airport[] airports)
    {
        for (Airport tempAirport : airports)
        {
            this.airports.put(tempAirport.getName(), tempAirport);
        }
    }

    public void addAirlines(Airline[] airlines)
    {
        for (Airline tempAirline : airlines)
        {
            this.airlines.put(tempAirline.getName(), tempAirline);
        }
    }

    public String getAMS()
    {
        return String.format("[%s]{%s}", getAirportAMS(), getAirlineAMS());
    }

    private String getAirportAMS()
    {
        boolean first = true;
        String str = "";
        for (Airport tempAirport : this.airports.values())
        {
            if (first)
            {
                first = false;
            }
            else
            {
                str += ",";
            }

            str += tempAirport.getAMSmemento();
        }
        return str;
    }

    private String getAirlineAMS()
    {
        boolean first = true;
        String str = "";
        for (Airline tempAirline : this.airlines.values())
        {
            if (!first)
            {
                str += ",";
            }
            else
            {
                first = false;
            }
            str += tempAirline.getAMSmemento();
        }
        return str;
    }

}
